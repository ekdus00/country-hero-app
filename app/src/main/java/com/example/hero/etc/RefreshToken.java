package com.example.hero.etc;

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
    private ApiService apiService;
    private TokenManager tokenManager;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tokenManager = new TokenManager(this);
        apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
        scheduleTokenRefresh();
    }

    private void scheduleTokenRefresh() {
        long accessRefreshTime = tokenManager.getAccessExpirationTime() - System.currentTimeMillis() - 30000; // 30 seconds before
        long refreshLogoutTime = tokenManager.getRefreshExpirationTime() - System.currentTimeMillis(); // At expiration

        handler.postDelayed(this::refreshToken, accessRefreshTime);
        handler.postDelayed(this::logoutUser, refreshLogoutTime);
    }

    private void refreshToken() {
        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null || tokenManager.isRefreshTokenExpired()) {
            logoutUser();  // If the refresh token is expired or not available, log out the user.
            return;
        }

        RefreshTokenRequestDTO dto = new RefreshTokenRequestDTO();
        dto.setRefreshToken(refreshToken);

        // Token refresh server request
        Call<Void> call = apiService.refreshToken(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String newAccessToken = response.headers().get("Authorization");

                    tokenManager.saveAccessTokens(newAccessToken);
                } else {
                    Log.e("RefreshToken", "Failed to refresh token: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("RefreshToken", "Error refreshing token", t);
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
