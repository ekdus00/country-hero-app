package com.example.hero_home.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hero_home.R;
import com.example.hero_home.model.ClipDeleteRequestDTO;
import com.example.hero_home.model.LoginDTO;
import com.example.hero_home.model.WorkerHomeDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button matchingBtn = findViewById(R.id.matching_btn);
        Button boardWriteBtn = findViewById(R.id.board_write_btn);
        Button mentorDetailBtn = findViewById(R.id.mentor_detail_btn);
        Button menteeDetailBtn = findViewById(R.id.mentee_detail_btn);
        Button resumeWriteBtn = findViewById(R.id.resume_write_btn);
        Button scrapBtn = findViewById(R.id.scrap_btn);
        Button userGuideBtn = findViewById(R.id.user_guide_btn);
        Button faqBtn = findViewById(R.id.faq_btn);
        Button settingBtn = findViewById(R.id.setting_btn);
        Button refreshTokenBtn = findViewById(R.id.refresh_token_btn);

        matchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityMatching.class);
                startActivity(intent);
            }
        });

        boardWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityBoardWrite.class);
                startActivity(intent);
            }
        });

        mentorDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityMentorDetailPage.class);
                startActivity(intent);
            }
        });

        menteeDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityMenteeDetailPage.class);
                startActivity(intent);
            }
        });

        resumeWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityResumeWrite.class);
                startActivity(intent);
            }
        });

        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScrapActivity.class);
                startActivity(intent);
            }
        });

        userGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserGuideActivity.class);
                startActivity(intent);
            }
        });

        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FAQActivity.class);
                startActivity(intent);
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        refreshTokenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                Call<WorkerHomeDTO> call = apiService.login(new LoginDTO("tester100", "tester100@@"));
                call.enqueue(new Callback<WorkerHomeDTO>() {
                    @Override
                    public void onResponse(Call<WorkerHomeDTO> call, Response<WorkerHomeDTO> response) {
                        Log.d("MainActivity", response.toString());
                    }

                    @Override
                    public void onFailure(Call<WorkerHomeDTO> call, Throwable t) {
                        Log.d("MainActivity", t.toString());
                    }
                });
            }
        });
    }
}