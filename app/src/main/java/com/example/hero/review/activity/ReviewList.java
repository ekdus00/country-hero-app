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
import com.example.hero.employer.activity.EmployerStatus;
import com.example.hero.employer.activity.EmployerStatusDetail;
import com.example.hero.employer.dto.WorkerInfoDTO;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnButtonClickListener;
import com.example.hero.etc.OnButtonClickListenerReviewStatus;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.activity.JobDetail;
import com.example.hero.job.activity.JobList;
import com.example.hero.job.adapter.JobListAdapter;
import com.example.hero.job.dto.JobInfoDTO;
import com.example.hero.review.adatper.ReviewListAdapter;
import com.example.hero.review.dto.WorkerReviewInfoDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewList extends AppCompatActivity {
    private RecyclerView review_list_recyclerView;
    ReviewListAdapter adapter;
    private List<WorkerReviewInfoDTO> jobList;
    private ApiService apiService;
    private OnButtonClickListenerReviewStatus btnClickListener;
    private Context context;
    private TokenManager tokenManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list);
        context = this;
        tokenManager = new TokenManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("상호평가");

        review_list_recyclerView = findViewById(R.id.review_list_recyclerView);
        review_list_recyclerView.setLayoutManager(new LinearLayoutManager(ReviewList.this));

        adapter = new ReviewListAdapter(jobList, btnClickListener);
        review_list_recyclerView.setAdapter(adapter);

        btnClickListener = (jobId, targetUserId) -> {
            Intent intent = new Intent(ReviewList.this, ReviewPost.class);
            intent.putExtra("jobId", jobId);
            intent.putExtra("targetUserId", targetUserId);
            startActivity(intent);
        };

        reviewListRequest();

        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreate()



    private void reviewListRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //상호평가목록(구직자) 서버요청
        Call<List<WorkerReviewInfoDTO>> call = apiService.getWorkerReviewList();
        call.enqueue(new Callback<List<WorkerReviewInfoDTO>>() {
            @Override
            public void onResponse(Call<List<WorkerReviewInfoDTO>> call, Response<List<WorkerReviewInfoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    if (jobList != null) {
                        jobList.clear();  // 기존 데이터를 지우고
                        jobList.addAll(response.body());  // 새 데이터를 추가합니다.
                        adapter.notifyDataSetChanged();
                    }


//                    WorkerReviewInfoDTO workerInfoDTO = response.body();
//
//                    List<EmployInfoDTO> list1 = employResponseDTO.getOpenEmployJobPostList();
//                    List<EmployInfoDTO> list2 = employResponseDTO.getCloseEmployJobPostList();
//
//                    adapter1 = new EmployerStatusAdapterA(list1, itemClickListener);
//                    adapter2 = new EmployerStatusAdapterB(list2, itemClickListener);
//
//                    employer_status_progress_recyclerView.setAdapter(adapter1);
//                    employer_status_deadline_recyclerView.setAdapter(adapter2);

                    Log.e("tag", "상호평가목록(구직자) 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("tag", "상호평가목록(구직자) 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "상호평가목록(구직자) 서버응답 오류" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<WorkerReviewInfoDTO>> call, Throwable t) {
                Log.e("tag", "상호평가목록(구직자) 서버요청 실패", t);
            }
        });

    }





}
