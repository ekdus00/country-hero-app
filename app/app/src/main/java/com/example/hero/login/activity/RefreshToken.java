package com.example.hero.login.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.TokenManager;
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
public class RefreshToken extends AppCompatActivity {
    private ApiService apiService;
    private TokenManager tokenManager;
    private long accessTokenExpirationTime = 600; // 액세스 토큰 만료 시간 (예: 10분)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tokenManager = new TokenManager(this);
        apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);

        scheduleTokenRefresh(accessTokenExpirationTime - 30);
    }

    public void refreshToken() {
        String refreshToken = tokenManager.getRefreshToken();

        if (tokenManager.isRefreshTokenExpired() || refreshToken == null) {
            // 리프레시 토큰이 만료되면 사용자를 로그아웃 처리
            Log.e("RefreshToken", "리프레시 토큰 만료");
            handleLogout();
            return;
        }

        RefreshTokenRequestDTO dto = new RefreshTokenRequestDTO();
        dto.setRefreshToken(refreshToken);

        //토큰재발급 서버요청
        Call<Void> call = apiService.refreshToken(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Headers headers = response.headers();
                    String newAccessToken = headers.get("Authorization");

                    tokenManager.saveAccessTokens(newAccessToken);
                    scheduleTokenRefresh(accessTokenExpirationTime - 30);
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

    private void scheduleTokenRefresh(long delayInSeconds) {
        new android.os.Handler().postDelayed(
                this::refreshToken,
                TimeUnit.SECONDS.toMillis(delayInSeconds)
        );
    }

    private void handleLogout() {
        // 로그아웃 처리 로직 (예: 로그인 화면으로 이동)
        tokenManager.clearTokens();
    }

}


//public class RefreshToken extends AppCompatActivity {
//    private ApiService apiService;
//    private String refreshToken;
//    private long tokenExpirationTime = 600; // 가정: 토큰 만료 시간이 10분(600초)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_refresh_token);
//
//        TokenManager tokenManager = new TokenManager(this);
//        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
//        refreshToken = getRefreshToken();
//
//        scheduleTokenRefresh(tokenExpirationTime - 30); // 만료 30초 전에 토큰 갱신
//    }
//
//    private void refreshToken() {
//        RefreshTokenRequestDTO dto = new RefreshTokenRequestDTO();
//        dto.setRefreshToken(refreshToken);
//
//        Call<Void> call = apiService.refreshToken(dto);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Headers headers = response.headers();
//                    String newAccessToken = headers.get("Authorization");
//                    String newRefreshToken = headers.get("Refresh-Token");
//                    updateTokens(newAccessToken, newRefreshToken);
//                    scheduleTokenRefresh(tokenExpirationTime - 30);
//                } else {
//                    Log.e("RefreshToken", "Failed to refresh token: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e("RefreshToken", "Error refreshing token", t);
//            }
//        });
//    }
//
//    private void scheduleTokenRefresh(long delayInSeconds) {
//        new android.os.Handler().postDelayed(
//                this::refreshToken,
//                TimeUnit.SECONDS.toMillis(delayInSeconds)
//        );
//    }
//
//    private void updateTokens(String accessToken, String refreshToken) {
//        // 토큰 저장 로직 구현, 예: SharedPreferences
//        SharedPreferences prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("AccessToken", accessToken);
//        editor.putString("RefreshToken", refreshToken);
//        editor.apply();
//    }
//
//    private String getRefreshToken() {
//        SharedPreferences prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
//        return prefs.getString("RefreshToken", "");
//    }
//}


//public class RefreshToken extends AppCompatActivity {
//    private Context context;
//    private Button login_sendBtn, login_joinBtn;
//    private EditText login_id_editText, login_pw_editText;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.login_main);
//
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        String refreshToken = "리프레시 토큰";
//
//        RefreshTokenRequestDTO dto = new RefreshTokenRequestDTO();
//        dto.setRefreshToken(refreshToken);
//
//        Call<Void> call = apiService.refreshToken(dto);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Headers headers = response.headers();
//                    String contentType = headers.get("Content-Type");
//                    String token = headers.get("Authorization");
//                    String refreshToken = headers.get("Refresh-Token");
//                    Log.d("Header", "Content-Type: " + contentType);
//                    Log.d("Header", "Authorization: " + token);
//                    Log.d("Header", "refreshToken: " + refreshToken);
//                    Log.e("api", "토근재발급 서버응답 오류코드" + response.code() + ", " + response.message());
//                    Log.e("api", "토근재발급 서버응답 오류" + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e("api", "토근재발급 서버요청 오류", t);
//            }
//        });
//
//
//
//
//    }
//
//}
