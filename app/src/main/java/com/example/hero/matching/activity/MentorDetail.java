package com.example.hero.matching.activity;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnCommentClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.job.activity.EmployerData;
import com.example.hero.matching.adapter.MatchingCommentAdapter;
import com.example.hero.matching.dto.MatchingDetailResponseDTO;
import com.example.hero.matching.dto.MatchingPostCommentDeleteRequestDTO;
import com.example.hero.matching.dto.MatchingPostCommentRequestDTO;
import com.example.hero.matching.dto.MatchingPostCommentResponseDTO;
import com.example.hero.matching.dto.MatchingPostCommentUpdateRequestDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MentorDetail extends AppCompatActivity {

    LinearLayout backBtn; // 뒤로가기 버튼
    int matchingId;

    TextView title, txt_link;
    TextView name;
    TextView area;
    TextView eduDate;
    TextView eduContent;

    ImageView imageView;
    TextView comment;
    ImageButton submitCommentBtn;
    RecyclerView recyclerView;
    MatchingCommentAdapter commentAdapter;
    ScrollView scrollView;

    ImageButton goMatchingPostEditBtn;
    int commentParent = -1;

    OnCommentClickListener buttonClickListener;
    private TokenManager tokenManager;
    private UserManager userManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);
        tokenManager = new TokenManager(this);
        userManager = new UserManager(this);

        Intent intent = getIntent();

        if(intent.getStringExtra("matchingId") == null) {
            matchingId = -1;
        } else {
            matchingId = Integer.parseInt(intent.getStringExtra("matchingId"));
        }

        title = findViewById(R.id.title_text);
        name = findViewById(R.id.txt_name);
        area = findViewById(R.id.txt_area);
        eduDate = findViewById(R.id.txt_edu_date);
        eduContent = findViewById(R.id.txt_edu_content);
        txt_link = findViewById(R.id.txt_link);

        goMatchingPostEditBtn = findViewById(R.id.edit_btn);
        goMatchingPostEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MentorDetail.this, MatchingPost.class);
                intent.putExtra("matchingId", matchingId);
                intent.putExtra("isEdit", true);
                startActivity(intent);
            }
        });

        imageView = findViewById(R.id.image_view);
        scrollView = findViewById(R.id.scroll_view);
        recyclerView = findViewById(R.id.recycler_view);

        comment = findViewById(R.id.txt_comment);
        submitCommentBtn = findViewById(R.id.submit_comment_btn);
        submitCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentCreate();
            }
        });

        commentRequest();
        buttonClickListener = (commentId, buttonType) -> {
            switch (buttonType) {
                case CHILD:
                    // MODIFY 버튼 클릭 이벤트 처리
                    Log.d(TAG, "CHILD" + commentId);
                    onChildComment(commentId);
                    break;
                case EDIT:
                    // DEADLINE 버튼 클릭 이벤트 처리
                    Log.d(TAG, "EDIT" + commentId);
                    onEditComment(commentId);
                    break;
                case DELETE:
                    // DEADLINE 버튼 클릭 이벤트 처리
                    Log.d(TAG, "DELETE" + commentId);
                    commentDelete(commentId);
                    break;
            }
        };

        if (matchingId != -1) {
            apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
            Call<MatchingDetailResponseDTO> callDetail = apiService.getMatchingDetail(matchingId);
            callDetail.enqueue(new Callback<MatchingDetailResponseDTO>() {
                @Override
                public void onResponse(Call<MatchingDetailResponseDTO> call, Response<MatchingDetailResponseDTO> response) {
                    if (response.isSuccessful()) {
                        MatchingDetailResponseDTO matchingDetailResponseDTO = response.body();
                        assert matchingDetailResponseDTO != null;

                        title.setText(matchingDetailResponseDTO.getMatchingName());
                        name.setText(matchingDetailResponseDTO.getUserName());
                        area.setText(matchingDetailResponseDTO.getCountry() + " " +matchingDetailResponseDTO.getCity());
                        eduDate.setText(matchingDetailResponseDTO.getStartEduDate() + "~" + matchingDetailResponseDTO.getEndEduDate());
                        eduContent.setText(matchingDetailResponseDTO.getEduContent());

                        txt_link.setText(matchingDetailResponseDTO.getSnsUrl());

                        // 게시글의 userId가 내 userId와 같다면 내 게시글이므로 수정버튼을 띄운다.
                        if(matchingDetailResponseDTO.isMyPost(userManager.getUserId())) {
                            goMatchingPostEditBtn.setVisibility(View.VISIBLE);
                        } else {
                            goMatchingPostEditBtn.setVisibility(View.GONE);
                        }

                        String imageData = matchingDetailResponseDTO.getMatchingImgFile();

                        if (imageData != null && imageData.length() > 0) {
                            // Base64 문자열을 바이트 배열로 디코드
                            byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
                            // 디코드된 바이트 배열을 Bitmap으로 변환
                            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            // Bitmap을 ImageView에 설정
                            imageView.setImageBitmap(bitmap);
                        } else {
                            // 이미지가 없을 경우 기본 이미지 설정
                            imageView.setImageResource(R.drawable.start_app);
                        }
                        Log.d("MENTEE_DETAIL_PAGE", matchingDetailResponseDTO.toString());
                    } else {
                        Log.e("MENTEE_DETAIL_PAGE", "Response error: " + response.errorBody());
                    }
                }
                @Override
                public void onFailure(Call<MatchingDetailResponseDTO> call, Throwable t) {
                    Log.e("MENTEE_DETAIL_PAGE", "MENTEE_DETAIL_PAGE 에러 발생", t);
                }
            });

        }

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MentorDetail.this, MatchingList.class);
                startActivity(intent);
            } // 클릭하면 뒤로가기 동작이 실행됨
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MentorDetail.this, EmployerData.class);
                startActivity(intent);
            }
        });
    }

    public void onChildComment(int commentId) {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("대댓글 작성하기");
        builder.setView(editText);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedComment = editText.getText().toString();
                commentCreateChild(commentId, updatedComment);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void onEditComment(int commentId) {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("댓글 수정하기");
        builder.setView(editText);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedComment = editText.getText().toString();
                commentUpdate(commentId, updatedComment);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void commentRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고댓글 목록조회 요청
        Call<List<MatchingPostCommentResponseDTO>> call2 = apiService.getMatchingCommentList(matchingId);
        call2.enqueue(new Callback<List<MatchingPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<MatchingPostCommentResponseDTO>> call, Response<List<MatchingPostCommentResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<MatchingPostCommentResponseDTO> comments = response.body();
                    commentAdapter = new MatchingCommentAdapter(comments, buttonClickListener);
                    recyclerView.setAdapter(commentAdapter);
                } else {
                    Log.e("api", "매칭상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "매칭상세 댓글 서버응답 오류" + response.errorBody().toString());                }
            }
            @Override
            public void onFailure(Call<List<MatchingPostCommentResponseDTO>> call, Throwable t) {
                Log.e("api", "매칭상세 댓글 서버요청 오류", t);
            }
        });
    }

    private void commentCreate() {
        String content = comment.getText().toString();

        MatchingPostCommentRequestDTO dto = new MatchingPostCommentRequestDTO();
        dto.setMatchingId(matchingId);
        dto.setCommentContent(content);

        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고댓글 작성 요청
        Call<List<MatchingPostCommentResponseDTO>> call2 = apiService.matchingCommentPost(dto);
        call2.enqueue(new Callback<List<MatchingPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<MatchingPostCommentResponseDTO>> call, Response<List<MatchingPostCommentResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<MatchingPostCommentResponseDTO> comments = response.body();
                    commentAdapter = new MatchingCommentAdapter(comments, buttonClickListener);
                    recyclerView.setAdapter(commentAdapter);

                    Log.e("api", "매칭상세 댓글 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("api", "매칭상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "매칭상세 댓글 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<List<MatchingPostCommentResponseDTO>> call, Throwable t) {
                Log.e("api", "매칭상세 댓글 서버요청 오류", t);
            }
        });
    }

    private void commentCreateChild(int parentId, String content) {
        MatchingPostCommentRequestDTO dto = new MatchingPostCommentRequestDTO();

        dto.setCommentParent(parentId);
        dto.setMatchingId(matchingId);
        dto.setCommentContent(content);

        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고 자식댓글 작성 요청
        Call<List<MatchingPostCommentResponseDTO>> call2 = apiService.matchingCommentPost(dto);
        call2.enqueue(new Callback<List<MatchingPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<MatchingPostCommentResponseDTO>> call, Response<List<MatchingPostCommentResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<MatchingPostCommentResponseDTO> comments = response.body();
                    commentAdapter = new MatchingCommentAdapter(comments, buttonClickListener);
                    recyclerView.setAdapter(commentAdapter);
                } else {
                    Log.e("api", "매칭상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "매칭상세 댓글 서버응답 오류" + response.errorBody().toString());                }
            }
            @Override
            public void onFailure(Call<List<MatchingPostCommentResponseDTO>> call, Throwable t) {
                Log.e("api", "매칭상세 댓글 서버요청 오류", t);
            }
        });
    }

    private void commentUpdate(int commentId, String content) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        MatchingPostCommentUpdateRequestDTO dto = new MatchingPostCommentUpdateRequestDTO();

        dto.setCommentId(commentId);
        dto.setMatchingId(matchingId);
        dto.setCommentContent(content);

        //공고댓글 수정 요청
        Call<List<MatchingPostCommentResponseDTO>> call2 = apiService.matchingPostCommentUpdate(dto);
        call2.enqueue(new Callback<List<MatchingPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<MatchingPostCommentResponseDTO>> call, Response<List<MatchingPostCommentResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<MatchingPostCommentResponseDTO> comments = response.body();
                    commentAdapter = new MatchingCommentAdapter(comments, buttonClickListener);
                    recyclerView.setAdapter(commentAdapter);
                } else {
                    Log.e("api", "매칭상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "매칭상세 댓글 서버응답 오류" + response.errorBody().toString());                }
            }
            @Override
            public void onFailure(Call<List<MatchingPostCommentResponseDTO>> call, Throwable t) {
                Log.e("api", "매칭상세 댓글 서버요청 오류", t);
            }
        });
    }

    private void commentDelete(int commentId) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        MatchingPostCommentDeleteRequestDTO dto = new MatchingPostCommentDeleteRequestDTO();

        dto.setCommentId(commentId);
        dto.setMatchingId(matchingId);

        //공고댓글 삭제 요청
        Call<List<MatchingPostCommentResponseDTO>> call2 = apiService.matchingPostCommentDelete(dto);
        call2.enqueue(new Callback<List<MatchingPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<MatchingPostCommentResponseDTO>> call, Response<List<MatchingPostCommentResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<MatchingPostCommentResponseDTO> comments = response.body();
                    commentAdapter = new MatchingCommentAdapter(comments, buttonClickListener);
                    recyclerView.setAdapter(commentAdapter);
                } else {
                    Log.e("api", "매칭상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "매칭상세 댓글 서버응답 오류" + response.errorBody().toString());                }
            }
            @Override
            public void onFailure(Call<List<MatchingPostCommentResponseDTO>> call, Throwable t) {
                Log.e("api", "매칭상세 댓글 서버요청 오류", t);
            }
        });
    }
}