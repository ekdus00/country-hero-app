package com.example.hero.employer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.R;
import com.example.hero.employer.adapter.EmployerStatusAdapterA;
import com.example.hero.employer.adapter.EmployerStatusAdapterB;
import com.example.hero.employer.adapter.EmployerStatusDetailAdapter;
import com.example.hero.employer.dto.EmployInfoDTO;
import com.example.hero.employer.dto.EmployResponseDTO;
import com.example.hero.employer.dto.WorkerInfoDTO;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnButtonClickListener;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.dto.JobInfoDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerStatusDetail extends AppCompatActivity {
    private RecyclerView employer_status_detail_recyclerView;
    private EmployerStatusDetailAdapter adapter;
    private List<WorkerInfoDTO> workerList = new ArrayList<>();
    private Context context;
    private OnButtonClickListener buttonClickListener;
    private ApiService apiService;
    private int employerStatusDetailJobId;
    private TokenManager tokenManager = new TokenManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_status_detail);
        context = this;

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
        textView.setText("공고현황");

        employer_status_detail_recyclerView = findViewById(R.id.employer_status_detail_recyclerView);
        employer_status_detail_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EmployerStatusDetailAdapter(workerList, buttonClickListener);
        employer_status_detail_recyclerView.setAdapter(adapter);

        buttonClickListener = jobId -> {
            //이력서확인 화면 이름으로 바꿔야됨
            Intent intent = new Intent(EmployerStatusDetail.this, EmployerStatusDetail.class);
            intent.putExtra("jobId", jobId);
            startActivity(intent);
        };

        employerStatusDetailJobId = getIntent().getIntExtra("jobId", 0);
//        jobId = String.valueOf(employerStatusDetailJobId);
        employerStatusDetailRequest(employerStatusDetailJobId);


    }//onCreate()



    private void employerStatusDetailRequest(int employerStatusDetailJobId) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고현황 서버요청
        Call<List<WorkerInfoDTO>> call = apiService.getWorkerList(employerStatusDetailJobId);
        call.enqueue(new Callback<List<WorkerInfoDTO>>() {
            @Override
            public void onResponse(Call<List<WorkerInfoDTO>> call, Response<List<WorkerInfoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    workerList.clear();  // 기존 데이터를 지우고
                    workerList.addAll(response.body());  // 새 데이터를 추가합니다.
                    adapter.notifyDataSetChanged();

//                    WorkerInfoDTO workerInfoDTO = response.body();
//
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
            public void onFailure(Call<List<WorkerInfoDTO>> call, Throwable t) {
                Log.e("NETWORK_ERROR", "Failed to connect to the server", t);
            }
        });

    }






}
