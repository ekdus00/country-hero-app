package com.example.hero.setting;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.FcmTokenManager;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.TokenManager;
import com.example.hero.FAQ.FAQActivity;
import com.example.hero.guide.UserGuideActivity;
import com.example.hero.login.activity.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {
    LinearLayout backBtn;
    TextView userGuideBtn;
    TextView faqBtn, user_secession_btn;
    private FcmTokenManager fcmTokenManager;
    private TokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tokenManager = new TokenManager(this);
        fcmTokenManager = new FcmTokenManager(this);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userGuideBtn = findViewById(R.id.user_guide_btn);
        userGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UserGuideActivity.class);
                startActivity(intent);
            }
        });
        faqBtn = findViewById(R.id.faq_btn);
        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, FAQActivity.class);
                startActivity(intent);
            }
        });

        user_secession_btn = findViewById(R.id.user_secession_btn);
        user_secession_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        SwitchCompat switchCompat = findViewById(R.id.switch_btn_push);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
                String fcmToken = fcmTokenManager.getFCMToken();
                Log.v(TAG, "fcm토큰: " + fcmToken);

                //푸시승인 서버요청
                Call<Void> call = apiService.approveFCM(fcmToken);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Log.e("tag", "푸시승인 서버요청 성공");

                        } else {
                            Log.e("tag", "푸시승인 서버응답 실패" + response.code() + ", " + response.message());
                            Log.e("tag", "푸시승인 서버응답 실패" + response.errorBody());
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("tag", "푸시승인 서버요청 실패", t);
                    }
                });
            } else {
                ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
                String fcmToken = fcmTokenManager.getFCMToken();

                //푸시거절 서버요청
                Call<Void> call = apiService.refuseFCM(fcmToken);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Log.e("tag", "푸시거절 서버요청 성공");

                        } else {
                            Log.e("tag", "푸시거절 서버응답 실패" + response.code() + ", " + response.message());
                            Log.e("tag", "푸시거절 서버응답 실패" + response.errorBody());
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("tag", "푸시승인 서버요청 실패", t);
                    }
                });
            }
        });

    }

    private void blockRequest() {
        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //회원탈퇴 서버요청
        Call<Void> call = apiService.withdrawalUser("dkdk");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Intent intent = new Intent(SettingActivity.this, Login.class);
                    startActivity(intent);
                    finish();

                    Log.e("tag", "회원탈퇴 서버요청 성공");
                } else {
                    Log.e("tag", "회원탈퇴 서버응답 실패" + response.code() + ", " + response.message());
                    Log.e("tag", "회원탈퇴 서버응답 실패" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("tag", "회원탈퇴 서버요청 실패", t);
            }
        });
    }

    private void showDialog() {
        // AlertDialog를 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("탈퇴하시겠습니까?");

        builder.setPositiveButton("확인", (dialog, which) -> {
            blockRequest();
            dialog.dismiss();
        });

        // 취소 버튼 설정
        builder.setNegativeButton("취소", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}