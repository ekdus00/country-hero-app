package com.example.hero.etc;

import android.util.Log;

import com.example.hero.login.activity.LogOut;
import com.example.hero.login.activity.RefreshToken;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    private TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
//        if (tokenManager.isAccessTokenExpired()) {
//            // Handle token refresh here if you want to make it synchronous
//            Log.e("AuthInterceptor", "액세스 토큰 만료");
//        }

        String accessToken = tokenManager.getAccessToken();
        Request modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();
        return chain.proceed(modifiedRequest);
    }
}


//public class AuthInterceptor implements Interceptor {
//    private TokenManager tokenManager;
//    private String accessToken, refreshToken;
//
//    public AuthInterceptor(TokenManager tokenManager) {
//        this.tokenManager = tokenManager;
//    }
//    public Response intercept(Chain chain) throws IOException {
//        Request originalRequest = chain.request();
//        synchronized (this) {
//            String accessToken = tokenManager.getAccessToken();
//            if (accessToken == null) {
//                Log.e("AuthInterceptor", "Access token expired, refreshing...");
//                boolean refreshed = refreshToken();
//                if (tokenManager.isRefreshTokenExpired() || refreshToken == null) {
//                    handleLogout();
//                    return chain.proceed(originalRequest); // Optionally redirect to login instead
//                }
//                accessToken = tokenManager.getAccessToken(); // Get new token after refresh
//            }
//
//            Request modifiedRequest = originalRequest.newBuilder()
//                    .header("Authorization", "Bearer " + accessToken)
//                    .build();
//            return chain.proceed(modifiedRequest);
//        }
//    }






//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request originalRequest = chain.request();
//        accessToken = tokenManager.getAccessToken();
//        refreshToken = tokenManager.getRefreshToken();
//
//        if (tokenManager.isRefreshTokenExpired() || accessToken == null || refreshToken == null) {
//            // 리프레시 토큰이 만료되면 사용자를 로그아웃 처리
//            Log.e("RefreshToken", "리프레시 토큰 만료");
//            handleLogout();
//            return chain.proceed(originalRequest);
//        }
//
//        if (accessToken == null) {
//            Log.e("tag", "액세스토큰 없음");
//        }
//
//        // 요청에 토큰을 추가
//        Request modifiedRequest = originalRequest.newBuilder()
//                .header("Authorization", "Bearer " + accessToken)
//                .build();
//
//        Response response = chain.proceed(modifiedRequest);
//        Log.e("tag", "AuthInterceptor 실행");
//
//        // 만약 토큰이 만료되었다면(401, 403) 리프레시 토큰으로 액세스토큰 갱신 시도
//        if (response.code() == 401 || response.code() == 403) {
//            synchronized (this) {
//                RefreshToken tokenRefresher = new RefreshToken();
//                tokenRefresher.refreshToken();  // 갱신 시도
//                Log.e("tag", "토큰 갱신시도");
//
//                String newAccessToken = tokenManager.getAccessToken();  // 갱신된 토큰을 다시 가져옴
//                Request newRequest = originalRequest.newBuilder()
//                        .header("Authorization", "Bearer " + newAccessToken)
//                        .build();
//                return chain.proceed(newRequest);
//            }
//        }
//
//        return response;
//    }

//    private boolean refreshToken() {
//        try {
//            RefreshToken tokenRefresher = new RefreshToken();
//            tokenRefresher.refreshToken();
//            return true;
//        } catch (Exception e) {
//            Log.e("AuthInterceptor", "Failed to refresh token", e);
//            return false;
//        }
//    }
//
//    private void handleLogout() {
//    // 로그아웃 처리 로직 (예: 로그인 화면으로 이동)
////            tokenManager.clearTokens();
//        Log.e("tag", "로그아웃");
//    }
//
//}

