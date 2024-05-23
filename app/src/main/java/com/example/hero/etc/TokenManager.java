package com.example.hero.etc;

import android.content.Context;
import android.content.SharedPreferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class TokenManager {
    private static final String PREF_NAME = "token_preferences";
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";
    private static final String REFRESH_TOKEN_EXPIRY_KEY = "refresh_token_expiry";
    private SharedPreferences prefs;
    private Context context;
    public TokenManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveAccessTokens(String accessToken) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        Log.e("tag", "액세스토큰저장");
        editor.apply();
    }
    public void saveRefreshTokens(String refreshToken) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REFRESH_TOKEN_KEY, refreshToken);
        Log.e("tag", "리프레시토큰저장");
        editor.apply();
    }
    public void saveExpiryTimeTokens() {
        SharedPreferences.Editor editor = prefs.edit();
        long expiryTime = System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000; // 30일의 밀리초
        editor.putLong(REFRESH_TOKEN_EXPIRY_KEY, expiryTime);
        Log.e("tag", "리프레시토큰 만료기간 저장");
        editor.apply();
    }

    public String getAccessToken() {
        return prefs.getString(ACCESS_TOKEN_KEY, null);
    }

    public String getRefreshToken() {
        return prefs.getString(REFRESH_TOKEN_KEY, null);
    }

    public boolean isRefreshTokenExpired() {
        long expiryTime = prefs.getLong(REFRESH_TOKEN_EXPIRY_KEY, 0);
        return System.currentTimeMillis() > expiryTime;
    }

    public void clearTokens() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(ACCESS_TOKEN_KEY);
        editor.remove(REFRESH_TOKEN_KEY);
        editor.remove(REFRESH_TOKEN_EXPIRY_KEY);
        editor.apply();
    }
}

