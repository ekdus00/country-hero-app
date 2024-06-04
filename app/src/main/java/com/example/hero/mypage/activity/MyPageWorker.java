package com.example.hero.mypage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.clip.activity.ClipActivity;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.home.activity.HomeWorker;
import com.example.hero.job.activity.JobList;
import com.example.hero.login.activity.Login;
import com.example.hero.matching.activity.MatchingList;
import com.example.hero.mypage.dto.WorkerProfileDTO;
import com.example.hero.resume.activity.ResumePost;
import com.example.hero.review.activity.ReviewList;
import com.example.hero.setting.SettingActivity;
import com.example.hero.worker.activity.WorkerStatus;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.hero.login.dto.NaverLoginResultDTO;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.oauth.NidOAuthBehavior;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.view.NidOAuthLoginButton;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfileMap;
import com.navercorp.nid.profile.data.NidProfileResponse;


public class MyPageWorker extends AppCompatActivity {
    private TokenManager tokenManager;
    private UserManager userManager;
    private ApiService apiService;
    private TextView myPage_name_textView, my_total_reviewAvg, myPage_rating_textView, myPage_remain_TextView;
    private ImageView myPage_rating_imageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_worker);
        tokenManager = new TokenManager(this);
        userManager = new UserManager(this);

        myPage_name_textView = findViewById(R.id.myPage_name_textView);
        myPage_rating_textView = findViewById(R.id.myPage_rating_textView);
        myPage_remain_TextView = findViewById(R.id.myPage_remain_TextView);
        my_total_reviewAvg = findViewById(R.id.my_total_reviewAvg);

        myPage_rating_imageView = findViewById(R.id.myPage_rating_imageView);

        workerHomeDataRequest();

        //회원정보수정
        Button my_page_Modify_Btn = findViewById(R.id.myPage_modify_btn);
        my_page_Modify_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModifyWorker.class);
                startActivity(intent);
            }
        });

        //로그아웃
        Button myPage_logout_btn = findViewById(R.id.myPage_logout_btn);
        myPage_logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenManager.clearTokens();
                userManager.clearUserDetails();
                NaverIdLoginSDK.INSTANCE.logout();
                Intent intent = new Intent(MyPageWorker.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        //설정
        Button setting_btn = findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageWorker.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        //이력서관리
        Button my_page_Resume_Btn = findViewById(R.id.myPage_resume_btn);
        my_page_Resume_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResumePost.class);
                startActivity(intent);
            }
        });

        //지원현황
        Button my_page_apply_status_btn = findViewById(R.id.myPage_apply_status_btn);
        my_page_apply_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkerStatus.class);
                startActivity(intent);
            }
        });

        //스크랩목록
        Button myPage_clip_btn = findViewById(R.id.myPage_clip_btn);
        myPage_clip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClipActivity.class);
                startActivity(intent);
            }
        });

        //상호평가
        Button myPage_review_btn = findViewById(R.id.myPage_review_btn);
        myPage_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewList.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    startActivity(new Intent(MyPageWorker.this, HomeWorker.class));
                    return true;
                } else if (id == R.id.nav_search) {
                    startActivity(new Intent(MyPageWorker.this, JobList.class));
                    return true;
                } else if (id == R.id.nav_matching) {
                    startActivity(new Intent(MyPageWorker.this, MatchingList.class));
                    return true;
                } else if (id == R.id.nav_mypage) {
                    startActivity(new Intent(MyPageWorker.this, MyPageWorker.class));
                    return true;
                }
                return false;
            }
        });

    }//onCreate()



    private void workerHomeDataRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //마이페이지(구직자) 서버요청
        Call<WorkerProfileDTO> call = apiService.getWorkerProfile();
        call.enqueue(new Callback<WorkerProfileDTO>() {
            @Override
            public void onResponse(Call<WorkerProfileDTO> call, Response<WorkerProfileDTO> response) {
                if (response.isSuccessful()) {
                    WorkerProfileDTO dto = response.body();

                    String date1 = dto.getUserName();
                    Double date2 = dto.getTotalReviewAvg();
                    Integer date3 = dto.getRequiredReviewCount();
                    Integer date4 = dto.getUserLevel();

                    myPage_name_textView.setText(date1);
                    my_total_reviewAvg.setText(String.valueOf(date2));
                    myPage_remain_TextView.setText(String.valueOf(date3));

                    switch (date4) {
                        case 1:
                            myPage_rating_imageView.setImageResource(R.drawable.level_1_img);
                            myPage_rating_textView.setText("씨앗");
                            break;
                        case 2:
                            myPage_rating_imageView.setImageResource(R.drawable.level_2_img);
                            myPage_rating_textView.setText("새싹");
                            break;
                        case 3:
                            myPage_rating_imageView.setImageResource(R.drawable.level_3_img);
                            myPage_rating_textView.setText("나무");
                            break;
                        case 4:
                            myPage_rating_imageView.setImageResource(R.drawable.level_4_img);
                            myPage_rating_textView.setText("열매");
                            break;
                        default:
//                            myPage_rating_imageView.setImageResource(R.drawable.level_1_img);
//                            myPage_rating_textView.setText("씨앗");
                            break;
                    }

                } else {
                    Log.e("api", "마이페이지(구직자) 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "마이페이지(구직자) 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<WorkerProfileDTO> call, Throwable t) {
                Log.e("api", "마이페이지(구직자) 서버요청 오류", t);
            }
        });
    }


}