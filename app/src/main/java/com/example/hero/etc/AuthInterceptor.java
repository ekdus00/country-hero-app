package com.example.hero.etc;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.hero.login.activity.Login;
import com.example.hero.login.dto.RefreshTokenRequestDTO;
import com.example.hero.setting.SettingActivity;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    private TokenManager tokenManager;
    private String accessToken;
    private String newAccessToken;
    private Context context;
    private UserManager userManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (tokenManager.isAccessTokenExpired()) {
            synchronized (this) {
                if (tokenManager.isAccessTokenExpired()) {
                    refreshToken();
                    if (newAccessToken != null) {
                        accessToken = newAccessToken;
                    } else {
                        throw new IOException("토큰 재발급 실패");
                    }
                } else if (tokenManager.isRefreshTokenExpired()) {
                    logoutUser();
                }
            }
        } else {
            accessToken = tokenManager.getAccessToken();
        }

        Request modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();
        return chain.proceed(modifiedRequest);
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

                    newAccessToken = refreshAccessToken;

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
        if (userManager != null) {
            userManager.clearUserDetails();
        } else {
            Log.e("UserManager", "UserManager instance is null");
        }
    }

}

