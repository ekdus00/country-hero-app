package com.example.hero.home.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.etc.TokenManager;
import com.example.hero.home.adapter.JobInfoHomeAdapter;
import com.example.hero.home.adapter.ParticipateInfoHomeAdapter;
import com.example.hero.home.dto.JobInfoHomeDTO;
import com.example.hero.home.dto.ParticipateInfoHomeDTO;
import com.example.hero.job.activity.JobDetail;
import com.example.hero.job.activity.JobList;
import com.example.hero.R;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.login.activity.Login;
import com.example.hero.matching.activity.MatchingList;
import com.example.hero.mypage.activity.MyPageWorker;
import com.example.hero.worker.activity.WorkerStatus;
import com.example.hero.home.dto.WorkerHomeDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeWorker extends AppCompatActivity {
    private ApiService apiService;
    private Context context;
    private OnItemClickListener itemClickListener;
    public RecyclerView recyclerView1;
    public RecyclerView recyclerView2;
    JobInfoHomeAdapter adapter1;
    ParticipateInfoHomeAdapter adapter2;
    private TokenManager tokenManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_worker);
        context = this;
        tokenManager = new TokenManager(this);

        recyclerView1 = findViewById(R.id.home_job_list_recyclerView);
        recyclerView2 = findViewById(R.id.home_applicant_recyclerView);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        homeApplicantRequest();

        //공고상세페이지 이동
        Button home_applicant_title1_btn = findViewById(R.id.home_applicant_title1_btn);
        home_applicant_title1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobList.class);
                startActivity(intent);
            }
        });

        //지원현황 이동
        Button home_applicant_title2_btn = findViewById(R.id.home_applicant_title2_btn);
        home_applicant_title2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkerStatus.class);
                startActivity(intent);
            }
        });

        itemClickListener = jobId -> {
            Intent intent = new Intent(HomeWorker.this, JobDetail.class);
            intent.putExtra("jobId", jobId);
            startActivity(intent);
        };

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    startActivity(new Intent(HomeWorker.this, HomeWorker.class));
                    return true;
                } else if (id == R.id.nav_search) {
                    startActivity(new Intent(HomeWorker.this, JobList.class));
                    return true;
                } else if (id == R.id.nav_matching) {
                    startActivity(new Intent(HomeWorker.this, MatchingList.class));
                    return true;
                } else if (id == R.id.nav_mypage) {
                    startActivity(new Intent(HomeWorker.this, MyPageWorker.class));
                    return true;
                }
                return false;
            }
        });


    } //onCreate()

    private void homeApplicantRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //홈화면(구직자) 서버요청
        Call<WorkerHomeDTO> call = apiService.getWorkerHome();
        call.enqueue(new Callback<WorkerHomeDTO>() {
            @Override
            public void onResponse(Call<WorkerHomeDTO> call, Response<WorkerHomeDTO> response) {
                if (response.isSuccessful()) {
                    WorkerHomeDTO workerHomeDTO = response.body();

                    List<JobInfoHomeDTO> list1 = workerHomeDTO.getJobList();
                    List<ParticipateInfoHomeDTO> list2 = workerHomeDTO.getParticipateList();

                    adapter1 = new JobInfoHomeAdapter(list1, itemClickListener);
                    adapter2 = new ParticipateInfoHomeAdapter(list2, itemClickListener);

                    recyclerView1.setAdapter(adapter1);
                    recyclerView2.setAdapter(adapter2);
                    Log.e("api", "홈화면(구직자) 서버요청 성공");

                } else {
                    Log.e("api", "홈화면(구직자) 서버응답 실패" + response.code() + ", " + response.message());
                    Log.e("api", "홈화면(구직자) 서버응답 실패" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<WorkerHomeDTO> call, Throwable t) {
                Log.e("api", "홈화면(구직자) 서버요청 실패", t);
            }
        });

    }




}
