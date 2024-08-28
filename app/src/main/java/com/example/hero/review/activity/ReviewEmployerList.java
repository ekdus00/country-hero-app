package com.example.hero.review.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnButtonClickListener;
import com.example.hero.etc.OnButtonClickListenerReviewStatus;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.review.adatper.ReviewEmployerListAdapter;
import com.example.hero.review.adatper.ReviewListAdapter;
import com.example.hero.review.dto.OwnerReviewInfoDTO;
import com.example.hero.review.dto.WorkerReviewInfoDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewEmployerList extends AppCompatActivity {
    private RecyclerView review_employer_list_recyclerView;
    ReviewEmployerListAdapter adapter;
    private List<OwnerReviewInfoDTO> jobList;
    private ApiService apiService;
    private OnButtonClickListenerReviewStatus btnClickListener;
    private Context context;
    private TokenManager tokenManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_employer_list);
        context = this;
        tokenManager = new TokenManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("상호평가 가능목록");

        review_employer_list_recyclerView = findViewById(R.id.review_employer_list_recyclerView);;
        review_employer_list_recyclerView.setLayoutManager(new LinearLayoutManager(ReviewEmployerList.this));

        adapter = new ReviewEmployerListAdapter(jobList, btnClickListener);
        review_employer_list_recyclerView.setAdapter(adapter);

        btnClickListener = (jobId, targetUserId) -> {
            Intent intent = new Intent(ReviewEmployerList.this, ReviewEmployerPost.class);
            intent.putExtra("jobId", jobId);
            intent.putExtra("targetUserId", targetUserId);
            startActivity(intent);
        };

        reviewEmployerListRequest();

        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreate()

    private void reviewEmployerListRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //상호평가목록(구인자) 서버요청
        Call<List<OwnerReviewInfoDTO>> call = apiService.getOwnerReviewList();
        call.enqueue(new Callback<List<OwnerReviewInfoDTO>>() {
            @Override
            public void onResponse(Call<List<OwnerReviewInfoDTO>> call, Response<List<OwnerReviewInfoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<OwnerReviewInfoDTO> list = response.body();
                    adapter = new ReviewEmployerListAdapter(list, btnClickListener);
                    review_employer_list_recyclerView.setAdapter(adapter);

                    Log.e("tag", "상호평가목록(구인자) 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("tag", "상호평가목록(구인자) 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "상호평가목록(구인자) 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<List<OwnerReviewInfoDTO>> call, Throwable t) {
                Log.e("tag", "상호평가목록(구인자) 서버요청 실패", t);
            }
        });

    }




}
