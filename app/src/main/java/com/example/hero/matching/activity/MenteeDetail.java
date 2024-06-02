package com.example.hero.matching.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnCommentClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.activity.EmployerData;
import com.example.hero.job.activity.JobDetail;
import com.example.hero.job.adapter.JobCommentAdapter;
import com.example.hero.job.dto.JobPostCommentDeleteRequestDTO;
import com.example.hero.job.dto.JobPostCommentRequestDTO;
import com.example.hero.job.dto.JobPostCommentResponseDTO;
import com.example.hero.job.dto.JobPostCommentUpdateRequestDTO;
import com.example.hero.matching.adapter.MatchingCommentAdapter;
import com.example.hero.matching.dto.MatchingDetailResponseDTO;
import com.example.hero.matching.dto.MatchingPostCommentDeleteRequestDTO;
import com.example.hero.matching.dto.MatchingPostCommentRequestDTO;
import com.example.hero.matching.dto.MatchingPostCommentResponseDTO;
import com.example.hero.matching.dto.MatchingPostCommentUpdateRequestDTO;
import com.example.hero.resume.activity.ResumeCheck;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 구인자
public class MenteeDetail extends AppCompatActivity {

    LinearLayout backBtn; // 뒤로가기 버튼
    int matchingId;

    TextView title;
    TextView name;
    TextView createdDate;
    TextView userName;
    TextView grade;
    TextView area;
    TextView career;
    TextView eduDate;
    TextView eduContent;

    TextView comment;
    ImageButton submitCommentBtn;
    RecyclerView recyclerView;
    MatchingCommentAdapter commentAdapter;
    ScrollView scrollView;

    ImageButton goMatchingPostBtn;
    int commentParent = -1;

    OnCommentClickListener buttonClickListener;

    private final TokenManager tokenManager = new TokenManager(this);
    ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_detail);

        Intent intent = getIntent();
        if (intent.getStringExtra("matchingId") == null) {
            matchingId = -1;
        } else {
            matchingId = Integer.parseInt(intent.getStringExtra("matchingId"));
        }
        title = findViewById(R.id.title_text);
        name = findViewById(R.id.txt_name);
        createdDate = findViewById(R.id.txt_created_date);
        userName = findViewById(R.id.txt_user_name);
        grade = findViewById(R.id.txt_grade);
        area = findViewById(R.id.txt_area);
        career = findViewById(R.id.txt_career);
        eduDate = findViewById(R.id.txt_edu_date);
        eduContent = findViewById(R.id.txt_edu_content);
        scrollView = findViewById(R.id.scroll_view);

        recyclerView = findViewById(R.id.recycler_view);

        goMatchingPostBtn = findViewById(R.id.edit_btn);
        goMatchingPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenteeDetail.this, MatchingPost.class);
                startActivity(intent);
            }
        });

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
            apiService =  RetrofitClient.getClient(tokenManager).create(ApiService.class);
            Call<MatchingDetailResponseDTO> detailCall = apiService.getMatchingDetail(matchingId);
            detailCall.enqueue(new Callback<MatchingDetailResponseDTO>() {
                @Override
                public void onResponse(Call<MatchingDetailResponseDTO> call, Response<MatchingDetailResponseDTO> response) {
                    if (response.isSuccessful()) {
                        MatchingDetailResponseDTO matchingDetailResponseDTO = response.body();
                        assert matchingDetailResponseDTO != null;

                        title.setText(matchingDetailResponseDTO.getMatchingName());
                        name.setText(matchingDetailResponseDTO.getUserName());
                        userName.setText(matchingDetailResponseDTO.getUserName());
                        area.setText(matchingDetailResponseDTO.getCountry()+ " " +matchingDetailResponseDTO.getCity());
                        eduDate.setText(matchingDetailResponseDTO.getStartEduDate()+"~"+matchingDetailResponseDTO.getEndEduDate());
                        eduContent.setText(matchingDetailResponseDTO.getEduContent());


                        Log.d("MENTOR_DETAIL_PAGE", matchingDetailResponseDTO.toString());
                    } else {
                        try {
                            Log.e("MENTOR_DETAIL_PAGE", "Response error: " + response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MatchingDetailResponseDTO> call, Throwable t) {
                    Log.e("MENTOR_DETAIL_PAGE", "MENTEE_DETAIL_PAGE 에러 발생", t);
                }
            });
        }

        backBtn = findViewById(R.id.back_btn); // 뒤로가기 버튼의 객체(linear layout)를 id로 찾아서 받아옴
        backBtn.setOnClickListener(new View.OnClickListener() { // 뒤로가기 버튼(linear layout)에 클릭 이벤트를 달아줌
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenteeDetail.this, MatchingList.class);
                startActivity(intent);
            } // 클릭하면 뒤로가기 동작이 실행됨
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenteeDetail.this,ResumeCheck.class);
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
                    Intent intent = new Intent(MenteeDetail.this, MenteeDetail.class);
                    intent.putExtra("matchingId", matchingId);
                    startActivity(intent);
//                    startActivity(new Intent(JobDetail.this, JobDetail.class));
                    Log.e("api", "매칭상세 댓글 서버응답 성공" + response.code() + ", " + response.message());

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