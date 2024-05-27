package com.example.hero.employer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.employer.adapter.EmployerStatusAdapterA;
import com.example.hero.employer.adapter.EmployerStatusAdapterB;
import com.example.hero.employer.dto.EmployInfoDTO;
import com.example.hero.employer.dto.EmployResponseDTO;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.home.activity.HomeApplicant;
import com.example.hero.home.adapter.JobInfoHomeAdapter;
import com.example.hero.home.adapter.ParticipateInfoHomeAdapter;
import com.example.hero.home.dto.JobInfoHomeDTO;
import com.example.hero.home.dto.ParticipateInfoHomeDTO;
import com.example.hero.home.dto.WorkerHomeDTO;
import com.example.hero.job.activity.JobDetail;
import com.example.hero.job.activity.JobPost;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerStatus extends AppCompatActivity {
    private RecyclerView employer_status_progress_recyclerView;
    private RecyclerView employer_status_deadline_recyclerView;
    private EmployerStatusAdapterA adapter1;
    private EmployerStatusAdapterB adapter2;
    private Context context;
    private OnItemClickListener itemClickListener;
    private ApiService apiService;
    private TokenManager tokenManager = new TokenManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_status);
        context = this;

        Button employer_status_job_post = (Button) findViewById(R.id.employer_status_job_post);

        employer_status_job_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobPost.class);
                startActivity(intent);
            }
        });

        employer_status_progress_recyclerView = findViewById(R.id.employer_status_progress_recyclerView);
        employer_status_deadline_recyclerView = findViewById(R.id.employer_status_deadline_recyclerView);

        employer_status_progress_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        employer_status_deadline_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter1 = new EmployerStatusAdapterA(new ArrayList<>(), itemClickListener);
        adapter2 = new EmployerStatusAdapterB(new ArrayList<>(), itemClickListener);

        employer_status_progress_recyclerView.setAdapter(adapter1);
        employer_status_deadline_recyclerView.setAdapter(adapter2);

        employerStatusRequest();

        itemClickListener = jobId -> {
            Intent intent = new Intent(EmployerStatus.this, EmployerStatusDetail.class);
            intent.putExtra("jobId", jobId);
            startActivity(intent);
        };
        
        //공고수정을위한 조회, 수정, 마감

    }//onCreate()


    private void employerStatusRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //나의공고 서버요청
        Call<EmployResponseDTO> call = apiService.getEmployList();
        call.enqueue(new Callback<EmployResponseDTO>() {
            @Override
            public void onResponse(Call<EmployResponseDTO> call, Response<EmployResponseDTO> response) {
                if (response.isSuccessful()) {
                    EmployResponseDTO dto = response.body();

                    adapter1.updateData(dto.getOpenEmployJobPostList());
                    adapter2.updateData(dto.getCloseEmployJobPostList());

//                    List<EmployInfoDTO> list1 = employResponseDTO.getOpenEmployJobPostList();
//                    List<EmployInfoDTO> list2 = employResponseDTO.getCloseEmployJobPostList();
//
//                    adapter1 = new EmployerStatusAdapterA(list1, itemClickListener);
//                    adapter2 = new EmployerStatusAdapterB(list2, itemClickListener);
//
//                    employer_status_progress_recyclerView.setAdapter(adapter1);
//                    employer_status_deadline_recyclerView.setAdapter(adapter2);

                } else {
                    Log.e("HTTP_ERROR", "Status Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<EmployResponseDTO> call, Throwable t) {
                Log.e("NETWORK_ERROR", "Failed to connect to the server", t);
            }
        });

    }





}