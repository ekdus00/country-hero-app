package com.example.hero.mypage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.home.activity.HomeOwner;
import com.example.hero.job.activity.JobList;
import com.example.hero.login.activity.Login;
import com.example.hero.matching.activity.MatchingList;
import com.example.hero.mypage.dto.OwnerProfileDTO;
import com.example.hero.review.activity.ReviewEmployerList;
import com.example.hero.setting.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageOwner extends AppCompatActivity {
    private TokenManager tokenManager;
    private UserManager userManager;
    private ApiService apiService;
    private TextView myPage_name_textView, my_total_reviewAvg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_owner);
        tokenManager = new TokenManager(this);
        userManager = new UserManager(this);

        myPage_name_textView = findViewById(R.id.myPage_name_textView);
        my_total_reviewAvg = findViewById(R.id.my_total_reviewAvg);

        ownerHomeDataRequest();

        //회원정보수정
        Button my_page_Modify_Btn = findViewById(R.id.myPage_modify_btn);
        my_page_Modify_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModifyOwner.class);
                startActivity(intent);
            }
        });

        //로그아웃
        Button myPage_logout_btn = findViewById(R.id.myPage_logout_btn);
        myPage_logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenManager.clearTokens();
                tokenManager.clearTokens();
                userManager.clearUserDetails();
                Intent intent = new Intent(MyPageOwner.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        //설정
        Button setting_btn = findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageOwner.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        //지원현황
//        Button my_page_employer_status_btn = findViewById(R.id.myPage_employer_status_btn);
//        my_page_employer_status_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), EmployerStatus.class);
//                startActivity(intent);
//            }
//        });

        //상호평가
        Button myPage_review_btn = findViewById(R.id.myPage_review_btn);
        myPage_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewEmployerList.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    startActivity(new Intent(MyPageOwner.this, HomeOwner.class));
                    return true;
                } else if (id == R.id.nav_search) {
                    startActivity(new Intent(MyPageOwner.this, JobList.class));
                    return true;
                } else if (id == R.id.nav_matching) {
                    startActivity(new Intent(MyPageOwner.this, MatchingList.class));
                    return true;
                } else if (id == R.id.nav_mypage) {
                    startActivity(new Intent(MyPageOwner.this, MyPageOwner.class));
                    return true;
                }
                return false;
            }
        });

    }//onCreate()


    private void ownerHomeDataRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //마이페이지(구인자) 서버요청
        Call<OwnerProfileDTO> call = apiService.getOwnerProfile();
        call.enqueue(new Callback<OwnerProfileDTO>() {
            @Override
            public void onResponse(Call<OwnerProfileDTO> call, Response<OwnerProfileDTO> response) {
                if (response.isSuccessful()) {
                    OwnerProfileDTO dto = response.body();

                    String date1 = dto.getUserName();
                    Double date2 = dto.getTotalReviewAvg();

                    myPage_name_textView.setText(date1);
                    my_total_reviewAvg.setText(String.valueOf(date2));

                } else {
                    Log.e("api", "마이페이지(구인자) 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "마이페이지(구인자) 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<OwnerProfileDTO> call, Throwable t) {
                Log.e("api", "마이페이지(구인자) 서버요청 오류", t);
            }
        });
    }

}
