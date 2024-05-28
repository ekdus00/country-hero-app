package com.example.hero.job.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.home.adapter.EmployInfoHomeAdapter;
import com.example.hero.home.adapter.JobInfoHomeAdapter;
import com.example.hero.home.adapter.ParticipateInfoHomeAdapter;
import com.example.hero.home.dto.EmployInfoHomeDTO;
import com.example.hero.home.dto.JobInfoHomeDTO;
import com.example.hero.job.adapter.EmployerDataAdapter;
import com.example.hero.job.dto.JobDetailResponseDTO;
import com.example.hero.job.dto.OwnerInfoDTO;
import com.example.hero.job.dto.OwnerInfoResponseDTO;
import com.example.hero.job.dto.OwnerReviewResultDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerData extends AppCompatActivity {
    private RecyclerView employer_comment_recyclerView;
    private ApiService apiService;
    private TextView employer_address, employer_email, employer_review_result, employer_review_detail1, employer_review_detail2, employer_review_detail3, name;
    private RatingBar employer_review_result_rating, employer_review_detail1_rating, employer_review_detail2_rating, employer_review_detail3_rating;
    private int jobId;
    EmployerDataAdapter adapter1;
    private TokenManager tokenManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_data);
        tokenManager = new TokenManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = toolbar.findViewById(R.id.toolbar_title);

        employer_comment_recyclerView = findViewById(R.id.employer_comment_recyclerView);
        employer_comment_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employer_address = findViewById(R.id.employer_address);
        employer_email = findViewById(R.id.employer_email);

        employer_review_result = findViewById(R.id.employer_review_result);
        employer_review_result_rating = findViewById(R.id.employer_review_result_rating);
        employer_review_detail1 = findViewById(R.id.employer_review_detail1);
        employer_review_detail1_rating = findViewById(R.id.employer_review_detail1_rating);
        employer_review_detail2 = findViewById(R.id.employer_review_detail2);
        employer_review_detail2_rating = findViewById(R.id.employer_review_detail2_rating);
        employer_review_detail3 = findViewById(R.id.employer_review_detail3);
        employer_review_detail3_rating = findViewById(R.id.employer_review_detail3_rating);

        jobId = getIntent().getIntExtra("jobId", 1);
        employerDataRequest();

        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreate


    private void employerDataRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //구인자정보 요청
        Call<OwnerInfoResponseDTO> call = apiService.getOwnerInfo(jobId);
        call.enqueue(new Callback<OwnerInfoResponseDTO>() {
            @Override
            public void onResponse(Call<OwnerInfoResponseDTO> call, Response<OwnerInfoResponseDTO> response) {
                if (response.isSuccessful()) {
                    OwnerInfoResponseDTO dto = response.body();

                    OwnerInfoDTO date1 = dto.getOwnerInfoDTO();
                    OwnerReviewResultDTO date2 = dto.getOwnerReviewResultDTO();

                    name.setText(date1.getUserName());
                    employer_address.setText(date1.getWorkAddress());
                    employer_email.setText(date1.getMail());

                    double rating1 = date2.getTotalReviewAvg();
                    double rating2 = date2.getItem1ReviewAvg();
                    double rating3 = date2.getItem2ReviewAvg();
                    double rating4 = date2.getItem3ReviewAvg();

                    employer_review_result.setText(String.format("%.1f", rating1));
                    employer_review_detail1.setText(String.format("%.1f", rating2));
                    employer_review_detail2.setText(String.format("%.1f", rating3));
                    employer_review_detail3.setText(String.format("%.1f", rating4));

                    List<String> EmployerDateComment = date2.getReviewContents();
                    adapter1 = new EmployerDataAdapter(EmployerDateComment);
                    employer_comment_recyclerView.setAdapter(adapter1);

                    Log.e("api", "구인자정보 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("api", "구인자정보 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "구인자정보 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<OwnerInfoResponseDTO> call, Throwable t) {
                Log.e("api", "구인자정보 서버요청 오류", t);
            }
        });
    }



}
