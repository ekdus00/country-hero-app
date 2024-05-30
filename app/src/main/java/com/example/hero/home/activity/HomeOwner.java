package com.example.hero.home.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.etc.ApiService;
import com.example.hero.employer.activity.EmployerStatus;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.activity.JobDetail;
import com.example.hero.job.dto.JobInfoDTO;
import com.example.hero.home.adapter.EmployInfoHomeAdapter;
import com.example.hero.home.adapter.JobInfoHomeAdapter;
import com.example.hero.home.dto.EmployInfoHomeDTO;
import com.example.hero.home.dto.JobInfoHomeDTO;
import com.example.hero.home.dto.OwnerHomeDTO;
import com.example.hero.job.activity.JobList;
import com.example.hero.R;
import com.example.hero.login.activity.Login;
import com.example.hero.mypage.activity.MyPageOwner;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeOwner extends AppCompatActivity {
    private ApiService apiService;
    private OnItemClickListener itemClickListener;
    private Context context;
    private List<JobInfoDTO> jobList;
    public RecyclerView recyclerView1;
    public RecyclerView recyclerView2;
    JobInfoHomeAdapter adapter1;
    EmployInfoHomeAdapter adapter2;
    private TextView home_recruiter_sum;
    private TextView home_recruiter_rate;
    private RatingBar home_recruiter_ratingbar;
    private TokenManager tokenManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_owner);
        context = this;
        tokenManager = new TokenManager(this);

        recyclerView1 = findViewById(R.id.home_job_list_recyclerView);
        recyclerView2 = findViewById(R.id.home_recruiter_recyclerView);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        home_recruiter_sum = findViewById(R.id.home_recruiter_sum);

        home_recruiter_rate = findViewById(R.id.home_recruiter_rate);
        home_recruiter_ratingbar = findViewById(R.id.home_recruiter_ratingbar);

        homeRecruiterRequest();

        Button home_recruiter_title3_btn = findViewById(R.id.home_recruiter_title3_btn);
        home_recruiter_title3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobList.class);
                startActivity(intent);
            }
        });

        Button home_recruiter_title1_btn = findViewById(R.id.home_recruiter_title1_btn);
        home_recruiter_title1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployerStatus.class);
                startActivity(intent);
            }
        });

        itemClickListener = jobId -> {
            Intent intent = new Intent(HomeOwner.this, JobDetail.class);
            intent.putExtra("jobId", jobId);
            startActivity(intent);
        };

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    startActivity(new Intent(HomeOwner.this, HomeOwner.class));
                    return true;
                } else if (id == R.id.nav_search) {
                    startActivity(new Intent(HomeOwner.this, JobList.class));
                    return true;
                } else if (id == R.id.nav_matching) {
                    startActivity(new Intent(HomeOwner.this, Login.class));
                    return true;
                } else if (id == R.id.nav_mypage) {
                    startActivity(new Intent(HomeOwner.this, MyPageOwner.class));
                    return true;
                }
                return false;
            }
        });


    } //onCreate()

    private void homeRecruiterRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //홈화면(구인자) 서버요청
        Call<OwnerHomeDTO> call = apiService.getOwnerHome();
        call.enqueue(new Callback<OwnerHomeDTO>() {
            @Override
            public void onResponse(Call<OwnerHomeDTO> call, Response<OwnerHomeDTO> response) {
                if (response.isSuccessful()) {
                    OwnerHomeDTO ownerHomeDTO = response.body();

                    List<JobInfoHomeDTO> list1 = ownerHomeDTO.getJobList();
                    List<EmployInfoHomeDTO> list2 = ownerHomeDTO.getEmpolyInfoHomeDTOList();

                    adapter1 = new JobInfoHomeAdapter(list1, itemClickListener);
                    adapter2 = new EmployInfoHomeAdapter(list2);

                    recyclerView1.setAdapter(adapter1);
                    recyclerView2.setAdapter(adapter2);

                    double rating = ownerHomeDTO.getTotalReviewAvg(); // 서버로부터 받은 평점
                    // TextView에 평점 표시
                    home_recruiter_rate.setText(String.format("%.1f", rating));  // 소수점 첫째 자리까지 표현
                    // RatingBar에 평점 설정
                    home_recruiter_ratingbar.setRating((float) rating);
                    home_recruiter_sum.setText(String.valueOf(ownerHomeDTO.getEmployCount()));
                    Log.e("api", "홈화면(구인자) 서버요청 성공");

                } else {
                    Log.e("api", "홈화면(구인자) 서버응답 실패" + response.code() + ", " + response.message());
                    Log.e("api", "홈화면(구인자) 서버응답 실패" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<OwnerHomeDTO> call, Throwable t) {
                Log.e("api", "홈화면(구인자) 서버요청 실패", t);
            }
        });

    }
}
