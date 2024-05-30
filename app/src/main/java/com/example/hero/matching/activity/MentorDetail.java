package com.example.hero.matching.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.example.hero.mypage.activity.MyPageApplicant;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MentorDetail extends AppCompatActivity {

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
    int commentParent = -1;

    private final TokenManager tokenManager = new TokenManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);

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
            Call<MatchingDetailResponseDTO> detailCall = apiServiceDetail.getMatchingDetail(matchingId);
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
                Intent intent = new Intent(MentorDetail.this, MatchingList.class);
                startActivity(intent);
            } // 클릭하면 뒤로가기 동작이 실행됨
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MentorDetail.this, MyPageApplicant.class);
                startActivity(intent);
            }
        });
    }
}