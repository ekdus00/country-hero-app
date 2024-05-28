package com.example.hero.review.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.dto.ParticipateRequestDTO;
import com.example.hero.review.dto.BlockRequestDTO;
import com.example.hero.review.dto.OwnerReviewUpdateRequestDTO;
import com.example.hero.review.dto.WorkerReviewUpdateRequestDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewEmployerPost extends AppCompatActivity {
    private RadioGroup radioGroupQ1, radioGroupQ2, radioGroupQ3, radioGroupQ4;
    private int score1, score2, score3, score4;
    private EditText review_post_editText;
    private Context context;
    private ApiService apiService;
    private TokenManager tokenManager;
    private int reviewJobId;
    private String reviewTargetId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_employer_post);
        context = this;
        tokenManager = new TokenManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("(구직자 이름)");

        radioGroupQ1 = findViewById(R.id.radioGroupQ1);
        radioGroupQ2 = findViewById(R.id.radioGroupQ2);
        radioGroupQ3 = findViewById(R.id.radioGroupQ3);
        radioGroupQ4 = findViewById(R.id.radioGroupQ4);

        review_post_editText = findViewById(R.id.review_post_editText);

        score1 = getScoreFromRadioGroup(radioGroupQ1);
        score2 = getScoreFromRadioGroup(radioGroupQ2);
        score3 = getScoreFromRadioGroup(radioGroupQ3);
        score4 = getScoreFromRadioGroup(radioGroupQ4);

        reviewJobId = getIntent().getIntExtra("jobId", 0);
        reviewTargetId = getIntent().getStringExtra("targetUserId");

        //상호평가 완료
        Button review_post_send = findViewById(R.id.review_employer_post_send);
        review_post_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewEmployerPostRequest();
            }
        });

        //구직자차단
        Button review_employer_post_block = findViewById(R.id.review_employer_post_block);
        review_employer_post_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataBlock();
            }
        });

        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreate()


    private void reviewEmployerPostRequest() {
        String reviewContent = review_post_editText.getText().toString();

        OwnerReviewUpdateRequestDTO dto = new OwnerReviewUpdateRequestDTO();

        dto.setReviewContent(reviewContent);
        dto.setReviewo1Score(score1);
        dto.setReviewo2Score(score2);
        dto.setReviewo3Score(score3);
        dto.setReviewo4Score(score4);

        dto.setJobId(reviewJobId);
        dto.setTargetUserType("worker");
        dto.setTargetUserId(reviewTargetId);

        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //상호평가(구인자) 서버요청
        Call<Void> call = apiService.updateOwnerReview(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Intent intent = new Intent(ReviewEmployerPost.this, ReviewEmployerList.class);
                    startActivity(intent);
                    Log.e("tag", "상호평가(구인자) 서버요청 성공" + response.code());
                } else {
                    Log.e("tag", "상호평가(구인자) 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "상호평가(구인자) 서버응답 오류" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("tag", "상호평가(구인자) 서버요청 실패", t);
            }
        });

    }

    private void fetchDataBlock() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        BlockRequestDTO dto = new BlockRequestDTO();
        dto.setTargetUserType("worker");
        dto.setTargetUserId(reviewTargetId);

        //구직자차단 서버요청
        Call<Void> call = apiService.ReviewBlock(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ReviewEmployerPost.this, ReviewEmployerList.class);
                    startActivity(intent);
                    Log.e("tag", "구직자차단 서버요청 성공" + response.code());
                } else {
                    Log.e("api", "구직자차단 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "구직자차단 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("api", "구직자차단 서버요청 오류", t);
            }
        });
    }


    private int getScoreFromRadioGroup(RadioGroup group) {
        int radioButtonID = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(radioButtonID);
        return Integer.parseInt(radioButton.getTag().toString());
    }



}
