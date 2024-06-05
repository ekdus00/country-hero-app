package com.example.hero.etc;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.TokenManager;
import com.example.hero.login.activity.Login;
import com.example.hero.login.dto.LoginRequestDTO;
import com.example.hero.login.dto.RefreshTokenRequestDTO;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.login.dto.RefreshTokenRequestDTO;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;


public class RefreshToken extends AppCompatActivity {
    private TokenManager tokenManager;
    private Handler handler = new Handler();
    private static final long ONE_MINUTE_IN_MILLIS = 60 * 1000; // 1분
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tokenManager = new TokenManager(this);
        scheduleTokenRefresh();
    }

    private void scheduleTokenRefresh() {
        long accessTokenExpiryTime = tokenManager.getAccessExpirationTime();
        long refreshTokenExpiryTime = tokenManager.getRefreshExpirationTime();
        long currentTime = System.currentTimeMillis();

        long timeUntilAccessTokenRefresh = accessTokenExpiryTime - currentTime - ONE_MINUTE_IN_MILLIS;
        long timeUntilRefreshTokenExpiry = refreshTokenExpiryTime - currentTime;

        if (timeUntilAccessTokenRefresh > 0) {
            handler.postDelayed(this::refreshToken, timeUntilAccessTokenRefresh);
        } else {
            refreshToken(); // 토큰이 이미 만료되었으면 즉시 재발급 시도
        }

        if (timeUntilRefreshTokenExpiry > 0) {
            handler.postDelayed(this::logoutUser, timeUntilRefreshTokenExpiry);
        } else {
            logoutUser(); // 리프레시 토큰이 이미 만료되었으면 즉시 로그아웃
        }
    }



    private void refreshToken() {
        String refreshToken = tokenManager.getRefreshToken();
        Log.v(TAG, "리프레시토큰: " + refreshToken);

        if (refreshToken == null || tokenManager.isRefreshTokenExpired()) {
            logoutUser();
            return;
        }

        RefreshTokenRequestDTO dto = new RefreshTokenRequestDTO();
        dto.setRefreshToken(refreshToken);

        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //액세스 토큰 재발급
        Call<ResponseBody> call = apiService.refreshToken(dto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Headers headers = response.headers();
//                    String refreshAccessToken = headers.get("Authorization");
                    String refreshAccessToken = headers.get("Authorization").replace("Bearer ", "");
                    tokenManager.saveAccessTokens(refreshAccessToken);

                    String a = tokenManager.getAccessToken();
                    String b = tokenManager.getRefreshToken();
                    long c = tokenManager.getAccessExpirationTime();
                    long d = tokenManager.getRefreshExpirationTime();

                    Log.v(TAG, "액세스토큰: " + a);
                    Log.v(TAG, "리프레시토큰: " + b);
                    Log.v(TAG, "액세스토큰 남은시간: " + c);
                    Log.v(TAG, "리프레시토큰 남은시간: " + d);

//                    newAccessToken = refreshAccessToken;

                    Log.e("tag", "토큰재발급 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("tag", "토큰재발급 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "토큰재발급 서버응답 오류" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("tag", "토큰재발급 서버요청 오류", t);
            }
        });

    }

    private void logoutUser() {
        tokenManager.clearTokens();
        tokenManager.clearTokens();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
