package com.example.hero.matching.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.matching.adapter.MatchingCommentAdapter;
import com.example.hero.matching.dto.MatchingDetailResponseDTO;
import com.example.hero.matching.dto.MatchingPostCommentRequestDTO;
import com.example.hero.matching.dto.MatchingPostCommentResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenteeDetail extends AppCompatActivity {

    LinearLayout backBtn; // 뒤로가기 버튼
    int matchingId;

    TextView title;
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
    int commentParent = -1;

    private final TokenManager tokenManager = new TokenManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_detail);

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


        imageView = findViewById(R.id.image_view);

        scrollView = findViewById(R.id.scroll_view);

        recyclerView = findViewById(R.id.recycler_view);

        comment = findViewById(R.id.txt_comment);
        submitCommentBtn = findViewById(R.id.submit_comment_btn);
        submitCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

                MatchingPostCommentRequestDTO matchingPostCommentRequestDTO;
                if (commentParent == -1) {
                    matchingPostCommentRequestDTO = new MatchingPostCommentRequestDTO(matchingId, comment.getText().toString(), 0);
                } else {
                    matchingPostCommentRequestDTO = new MatchingPostCommentRequestDTO(matchingId, comment.getText().toString(), commentParent);
                }


                Call<List<MatchingPostCommentResponseDTO>> call = apiService.matchingCommentPost(matchingPostCommentRequestDTO);
                call.enqueue(new Callback<List<MatchingPostCommentResponseDTO>>() {
                    @Override
                    public void onResponse(Call<List<MatchingPostCommentResponseDTO>> call, Response<List<MatchingPostCommentResponseDTO>> response) {
                        List<MatchingPostCommentResponseDTO> commentList = response.body();
                        assert commentList != null;
                        commentAdapter = new MatchingCommentAdapter(commentList);
                        recyclerView.setAdapter(commentAdapter);
                        commentAdapter.notifyDataSetChanged();
                        comment.setText("");
                        recyclerView.smoothScrollToPosition(commentList.size());
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                    @Override
                    public void onFailure(Call<List<MatchingPostCommentResponseDTO>> call, Throwable t) {

                    }
                });
            }
        });

        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
        Call<List<MatchingPostCommentResponseDTO>> call = apiService.getMatchingCommentList(matchingId);
        call.enqueue(new Callback<List<MatchingPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<MatchingPostCommentResponseDTO>> call, Response<List<MatchingPostCommentResponseDTO>> response) {
                List<MatchingPostCommentResponseDTO> commentList = response.body();
                assert commentList != null;
                commentAdapter = new MatchingCommentAdapter(commentList);
                recyclerView.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MatchingPostCommentResponseDTO>> call, Throwable t) {

            }
        });

        if (matchingId != -1) {
            ApiService apiServiceDetail = RetrofitClient.getClient(tokenManager).create(ApiService.class);
            Call<MatchingDetailResponseDTO> callDetail = apiServiceDetail.getMatchingDetail(matchingId);
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

        backBtn = findViewById(R.id.back_btn); // 뒤로가기 버튼의 객체(linear layout)를 id로 찾아서 받아옴
        backBtn.setOnClickListener(new View.OnClickListener() { // 뒤로가기 버튼(linear layout)에 클릭 이벤트를 달아줌
            @Override
            public void onClick(View view) {
                onBackPressed();
            } // 클릭하면 뒤로가기 동작이 실행됨
        });
    }
}