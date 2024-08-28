package com.example.hero.resume.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hero.R;
import com.example.hero.employer.activity.EmployerStatus;
import com.example.hero.employer.activity.EmployerStatusDetail;
import com.example.hero.employer.activity.JobEditPost;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.dto.JobPostCreateRequestDTO;
import com.example.hero.login.activity.FindId;
import com.example.hero.mypage.dto.OwnerUserInfoResponseDTO;
import com.example.hero.resume.adapter.CareerAdapter;
import com.example.hero.resume.dto.ResumeResponseDTO;
import com.example.hero.resume.dto.WorkerStateRequestDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeCheck extends AppCompatActivity {
    private ImageView resume_check_image;
    private TextView resume_name, resume_gender, resume_review, resume_info;
    private ApiService apiService;
    private TokenManager tokenManager;
    private RecyclerView resume_career;
    private CareerAdapter adapter;
    private String userId;
    private int jobId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_check);
        tokenManager = new TokenManager(this);

        resume_name = findViewById(R.id.resume_name);
        resume_gender = findViewById(R.id.resume_gender);
        resume_review = findViewById(R.id.resume_review);
        resume_check_image = findViewById(R.id.resume_check_image);
        resume_info = findViewById(R.id.resume_info);

        resume_career = findViewById(R.id.resume_career);
        resume_career.setLayoutManager(new LinearLayoutManager(this));

        userId = getIntent().getStringExtra("userId");
        jobId = getIntent().getIntExtra("jobId", 0);
        getResume();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("이력서 확인");
        
        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //승인
        Button resume_approval = findViewById(R.id.resume_approval);
        resume_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeApprove();
            }
        });

        //보류
        Button resume_defer = findViewById(R.id.resume_defer);
        resume_defer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeDefer();
            }
        });


    }//onCreate()


    public void getResume() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //이력서확인 서버요청
        Call<ResumeResponseDTO> call = apiService.checkResume(userId);
        call.enqueue(new Callback<ResumeResponseDTO>() {
            @Override
            public void onResponse(Call<ResumeResponseDTO> call, Response<ResumeResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResumeResponseDTO dto = response.body();
                    resume_name.setText(dto.getUserName());
                    resume_gender.setText(dto.getGender());
                    resume_review.setText(String.valueOf(dto.getTotalReviewScore()));
                    resume_info.setText(dto.getUserIntro());

                    List<String> careerList = dto.getEtcCareer();
                    adapter = new CareerAdapter(careerList);
                    resume_career.setAdapter(adapter);

                    String imageData = dto.getUserImgFile();
                    if (imageData != null && imageData.length() > 0) {
                        // Base64 문자열을 바이트 배열로 디코드
                        byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
                        // 디코드된 바이트 배열을 Bitmap으로 변환
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        // Bitmap을 ImageView에 설정
                        resume_check_image.setImageBitmap(bitmap);
                    } else {
                        // 이미지가 없을 경우 기본 이미지 설정
                        resume_check_image.setImageResource(R.drawable.mypage);
                    }

                    Log.e("api", "이력서확인 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "이력서확인 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "이력서확인 서버응답 오류" + response.errorBody().toString());                        }
            }
            @Override
            public void onFailure(Call<ResumeResponseDTO> call, Throwable t) {
                Log.e("api", "이력서확인 서버요청 오류", t);
            }
        });
    }

    public void resumeApprove() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        WorkerStateRequestDTO dto = new WorkerStateRequestDTO();
        dto.setJobId(jobId);
        dto.setTargetUserId(userId);
        dto.setTargetUserType("worker");

        //일자리승인 서버요청
        Call<ResponseBody> call = apiService.updateApprove(dto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ResumeCheck.this, "해당 구직자를 승인하였습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ResumeCheck.this, EmployerStatus.class);
                    startActivity(intent);

                    Log.e("api", "일자리승인 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "일자리승인 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "일자리승인 서버응답 오류" + response.errorBody().toString());                        
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("api", "일자리승인 서버요청 오류", t);
            }
        });
    }

    public void resumeDefer() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        WorkerStateRequestDTO dto = new WorkerStateRequestDTO();
        dto.setJobId(jobId);
        dto.setTargetUserId(userId);
        dto.setTargetUserType("worker");

        //일자리보류 서버요청
        Call<ResponseBody> call = apiService.updateDefer(dto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ResumeCheck.this, "해당 구직자를 보류하였습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ResumeCheck.this, EmployerStatus.class);
                    startActivity(intent);

                    Log.e("api", "일자리보류 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "일자리보류 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "일자리보류 서버응답 오류" + response.errorBody().toString());                        
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("api", "일자리보류 서버요청 오류", t);
            }
        });
    }



}
