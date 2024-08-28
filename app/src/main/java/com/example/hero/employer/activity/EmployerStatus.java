package com.example.hero.employer.activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.employer.adapter.EmployerStatusAdapterA;
import com.example.hero.employer.adapter.EmployerStatusAdapterB;
import com.example.hero.employer.dto.EmployInfoDTO;
import com.example.hero.employer.dto.EmployResponseDTO;
import com.example.hero.employer.dto.JobRequestDTO;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnButtonClickListenerOwnerStatus;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.activity.JobPost;
import com.example.hero.job.adapter.JobListAdapter;
import com.example.hero.job.dto.JobInfoDTO;

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
    private OnButtonClickListenerOwnerStatus buttonClickListener;
    private ApiService apiService;
    private TokenManager tokenManager;
    private int deadlineJobId;
    private List<EmployInfoDTO> list = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_status);
        context = this;
        tokenManager = new TokenManager(this);

        itemClickListener = jobId -> {
            Intent intent = new Intent(EmployerStatus.this, EmployerStatusDetail.class);
            intent.putExtra("jobId", jobId);
            startActivity(intent);
        };

        //공고 수정, 마감 버튼 이벤트 리스너
        buttonClickListener = (jobId, buttonType) -> {
            switch (buttonType) {
                case MODIFY:
                    // MODIFY 버튼 클릭 이벤트 처리
                    Log.d(TAG, "Modify button clicked for jobId: " + jobId);
                    Intent intent = new Intent(EmployerStatus.this, JobEditPost.class);
                    intent.putExtra("jobId", jobId);
                    intent.putExtra("buttonType", "modify");
                    startActivity(intent);
                    break;
                case DEADLINE:
                    // DEADLINE 버튼 클릭 이벤트 처리
                    Log.d(TAG, "Deadline button clicked for jobId: " + jobId);
                    deadlineJobId = jobId;
                    closeJobPost(deadlineJobId);
                    break;
            }
        };

        //공고작성
        ImageButton employer_status_job_post = findViewById(R.id.employer_status_job_post);
        employer_status_job_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobPost.class);
                startActivity(intent);
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("나의공고");

        employer_status_progress_recyclerView = findViewById(R.id.employer_status_progress_recyclerView);
        employer_status_deadline_recyclerView = findViewById(R.id.employer_status_deadline_recyclerView);

        employer_status_progress_recyclerView.setLayoutManager(new LinearLayoutManager(EmployerStatus.this));
        employer_status_deadline_recyclerView.setLayoutManager(new LinearLayoutManager(EmployerStatus.this));

        adapter1 = new EmployerStatusAdapterA(new ArrayList<>(), itemClickListener, buttonClickListener);
        adapter2 = new EmployerStatusAdapterB(new ArrayList<>(), itemClickListener);

        employer_status_progress_recyclerView.setAdapter(adapter1);
        employer_status_deadline_recyclerView.setAdapter(adapter2);

        employerStatusRequest();



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

                    Log.e("tag", "나의공고 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("tag", "나의공고 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "나의공고 서버응답 오류" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<EmployResponseDTO> call, Throwable t) {
                Log.e("tag", "나의공고 서버요청 실패", t);
            }
        });

    }

    private void closeJobPost(int deadlineJobId) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        JobRequestDTO dto = new JobRequestDTO();
        dto.setJobId(deadlineJobId);

        //공고마감 서버요청
        Call<Void> call = apiService.closeJobPost(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(EmployerStatus.this, EmployerStatus.class);
                    startActivity(intent);
                    Log.e("tag", "공고마감 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("tag", "공고마감 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "공고마감 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("tag", "공고마감 서버요청 실패", t);
            }
        });

    }







}