package com.example.hero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class JobDetail extends AppCompatActivity {
    private ApiService ApiService;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail);

        Button job_detail_employer_btn = findViewById(R.id.job_detail_employer_btn);

        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        job_detail_employer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployerData.class);
                startActivity(intent);
            }
        });

//        ApiService = RetrofitClient.getClient().create(ApiService.class);
//
//        Job request = new Job(1, "userType", "user_id"); // 요청 데이터 예시
//        Call<Job> call = ApiService.getJobDetail(jobDetailRequest);
//        call.enqueue(new Callback<Job>() {
//            @Override
//            public void onResponse(Call<Job> call, Response<Job> response) {
//                if (response.isSuccessful()) {
//                    Job jobDetails = response.body();
//                    // 여기에서 jobDetails를 사용하여 UI 업데이트
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Job> call, Throwable t) {
//                Log.e("JobDetail", "공고상세페이지 서버요청 실패", t);
//            }
//        });


    }
}
