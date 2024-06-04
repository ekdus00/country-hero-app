package com.example.hero.login.activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.home.activity.HomeOwner;
import com.example.hero.home.activity.HomeWorker;
import com.example.hero.login.dto.ExtraInfoDTO;
import com.example.hero.login.dto.LoginRequestDTO;
import com.example.hero.login.dto.LoginResultDTO;
import com.example.hero.login.dto.NaverLoginResultDTO;
import com.example.hero.review.dto.OwnerReviewInfoDTO;

import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTypeSet extends AppCompatActivity {
    private Context context;
    private Button user_type_owner, user_type_worker;
    private ApiService apiService;
    private String user_type_result;
    private String userId, loginType;
    private UserManager userManager;
    private TokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_user_type);
        context = this;
        userManager = new UserManager(this);
        tokenManager = new TokenManager(this);

        userId = getIntent().getStringExtra("userId");
        loginType = getIntent().getStringExtra("loginType");

        user_type_owner = findViewById(R.id.user_type_owner);
        user_type_worker = findViewById(R.id.user_type_worker);
        
        //구인자 선택
        user_type_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_type_result = "owner";

                if (loginType.equals("naver")) {
                    userTypeRequestNaver();
                } else {
                    userTypeRequest();
                }
            }
        });
        
        //구직자 선택
        user_type_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_type_result = "worker";

                if (loginType.equals("naver")) {
                    userTypeRequestNaver();
                } else {
                    userTypeRequest();
                }
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

    }//onCreate()

    private void userTypeRequestNaver() {
        ExtraInfoDTO dto = new ExtraInfoDTO();

        String naverId = userManager.getUserId();
        Log.v(TAG, "네이버아이디: " + naverId);

        dto.setUserId(naverId);
        dto.setUserType(user_type_result);

        userManager.saveUserType(user_type_result);

        //추가정보입력 서버요청
        apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
        Call<Void> call = apiService.setUserType(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    userManager.saveUserInfoInputCompleted(true);

//                    requestNaverLogin();

                    Log.e("tag", "추가정보입력 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Toast.makeText(context, "추가정보입력에 실패했습니다", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "추가정보입력 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "추가정보입력 서버응답 오류" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("tag", "추가정보입력 서버요청 오류", t);
            }
        });

    }

//    public void requestNaverLogin() {
//        apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
//        Call<NaverLoginResultDTO> call = apiService.naverLoginCallback();
//        call.enqueue(new Callback<NaverLoginResultDTO>() {
//            @Override
//            public void onResponse(Call<NaverLoginResultDTO> call, Response<NaverLoginResultDTO> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Headers headers = response.headers();
//
//                    String accessToken = headers.get("Authorization").replace("Bearer ", "");
//                    String refreshToken = headers.get("Refresh-Token");
//
//                    tokenManager.saveAccessTokens(accessToken);
//                    tokenManager.saveRefreshTokens(refreshToken);
//
//                    String a = tokenManager.getAccessToken();
//                    String b = tokenManager.getRefreshToken();
//                    long c = tokenManager.getAccessExpirationTime();
//                    long d = tokenManager.getRefreshExpirationTime();
//
//                    Log.v(TAG, "액세스토큰: " + a);
//                    Log.v(TAG, "리프레시토큰: " + b);
//                    Log.v(TAG, "액세스토큰 남은시간: " + c);
//                    Log.v(TAG, "리프레시토큰 남은시간: " + d);
//
//                    String userType = userManager.getUserType();
//
//                    if ("owner".equals(userType)) {
//                        Log.d("LoginActivity", "Navigating to HomeRecruiter");
//                        startActivity(new Intent(UserTypeSet.this, HomeOwner.class));
//                    } else if("worker".equals(userType)) {
//                        Log.d("LoginActivity", "Navigating to HomeApplicant");
//                        startActivity(new Intent(UserTypeSet.this, HomeWorker.class));
//                    }
//                    Log.e("login", "네이버로그인 서버응답 성공" + response.code() + ", " + response.message());
//                } else {
//                    Log.e("login", "네이버로그인 서버응답 오류코드" + response.code() + ", " + response.message());
//                    Log.e("login", "네이버로그인 서버응답 오류" + response.errorBody().toString());
//                }
//            }
//            @Override
//            public void onFailure(Call<NaverLoginResultDTO> call, Throwable t) {
//                Log.e("login", "네이버로그인 서버요청 오류", t);
//            }
//        });
//    }


    private void userTypeRequest() {
        ExtraInfoDTO dto = new ExtraInfoDTO();

        dto.setUserId(userId);
        dto.setUserType(user_type_result);

        userManager.saveUserId(userId);
        userManager.saveUserType(user_type_result);

        //추가정보입력 서버요청
        apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
        Call<Void> call = apiService.setUserType(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    userManager.saveUserInfoInputCompleted(true);

                    Intent intent = new Intent(UserTypeSet.this, Login.class);
                    startActivity(intent);
                    finish();

                    Log.e("tag", "추가정보입력 서버응답 성공" + response.code() + ", " + response.message());
                    
                } else {
                    Toast.makeText(context, "추가정보입력에 실패했습니다", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "추가정보입력 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "추가정보입력 서버응답 오류" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("tag", "추가정보입력 서버요청 오류", t);
            }
        });

    }



}
