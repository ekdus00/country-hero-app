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
import com.example.hero.employer.dto.WorkerInfoDTO;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.dto.JobPostCreateRequestDTO;
import com.example.hero.review.dto.WorkerReviewUpdateRequestDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewPost extends AppCompatActivity {
    private RadioGroup radioGroupQ1, radioGroupQ2, radioGroupQ3;
    private int score1, score2, score3;
    private EditText review_post_editText;
    private Context context;
    private ApiService apiService;
    private TokenManager tokenManager;
    private int reviewJobId;
    private String reviewTargetId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_post);
        context = this;
        tokenManager = new TokenManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("평가하기");

        radioGroupQ1 = findViewById(R.id.radioGroupQ1);
        radioGroupQ2 = findViewById(R.id.radioGroupQ2);
        radioGroupQ3 = findViewById(R.id.radioGroupQ3);

        review_post_editText = findViewById(R.id.review_post_editText);

        score1 = getScoreFromRadioGroup(radioGroupQ1);
        score2 = getScoreFromRadioGroup(radioGroupQ2);
        score3 = getScoreFromRadioGroup(radioGroupQ3);

        reviewJobId = getIntent().getIntExtra("jobId", 0);
        reviewTargetId = getIntent().getStringExtra("targetUserId");

        //상호평가 완료
        Button review_post_send = findViewById(R.id.review_post_send);
        review_post_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewPostRequest();
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

    
    private void reviewPostRequest() {
        String reviewContent = review_post_editText.getText().toString();

        WorkerReviewUpdateRequestDTO dto = new WorkerReviewUpdateRequestDTO();

        dto.setReviewContent(reviewContent);
        dto.setReview1Score(score1);
        dto.setReview2Score(score2);
        dto.setReview3Score(score3);

        dto.setJobId(reviewJobId);
        dto.setTargetUserId(reviewTargetId);
        dto.setTargetUserType("owner");

        //상호평가(구직자) 서버요청
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
        Call<Void> call = apiService.updateWorkerReview(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Intent intent = new Intent(ReviewPost.this, ReviewList.class);
                    startActivity(intent);
                    Log.e("tag", "상호평가(구직자) 서버요청 성공" + response.code());
                } else {
                    Log.e("tag", "상호평가(구직자) 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "상호평가(구직자) 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("tag", "상호평가(구직자) 서버요청 실패", t);
            }
        });

    }

    private int getScoreFromRadioGroup(RadioGroup group) {
        int radioButtonID = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(radioButtonID);
        return Integer.parseInt(radioButton.getTag().toString());
    }




}
