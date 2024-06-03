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
    private static final String ACCESS_TOKEN_EXPIRY_KEY = "access_token_expiry";
    private SharedPreferences prefs;
    private Context context;
    public TokenManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveAccessTokens(String accessToken) {
        SharedPreferences.Editor editor = prefs.edit();
        long expiryTime = System.currentTimeMillis() + 9 * 60 * 1000; // 9분 뒤
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.putLong(ACCESS_TOKEN_EXPIRY_KEY, expiryTime);
        editor.apply();
        Log.e("TokenManager", "Access token and expiry time saved");

    }

    public void saveRefreshTokens(String refreshToken) {
        SharedPreferences.Editor editor = prefs.edit();
        long expiryTime = System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000; // 30일 뒤
        editor.putString(REFRESH_TOKEN_KEY, refreshToken);
        editor.putLong(REFRESH_TOKEN_EXPIRY_KEY, expiryTime);
        editor.apply();
        Log.e("TokenManager", "Refresh token and expiry time saved");
    }

    public boolean isAccessTokenExpired() {
        long expirationTime = prefs.getLong(ACCESS_TOKEN_EXPIRY_KEY, 0);
        return System.currentTimeMillis() > expirationTime;
    }

    public boolean isRefreshTokenExpired() {
        long expiryTime = prefs.getLong(REFRESH_TOKEN_EXPIRY_KEY, 0);
        return System.currentTimeMillis() > expiryTime;
    }

    public long getAccessExpirationTime() {
        return prefs.getLong(ACCESS_TOKEN_EXPIRY_KEY, 0);
    }
    public long getRefreshExpirationTime() {
        return prefs.getLong(REFRESH_TOKEN_EXPIRY_KEY, 0);
    }
    public String getAccessToken() {
        return prefs.getString(ACCESS_TOKEN_KEY, null);
    }
    public String getRefreshToken() {
        return prefs.getString(REFRESH_TOKEN_KEY, null);
    }

    public void clearTokens() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(ACCESS_TOKEN_KEY);
        editor.remove(REFRESH_TOKEN_KEY);
        editor.remove(REFRESH_TOKEN_EXPIRY_KEY);
        editor.remove(ACCESS_TOKEN_EXPIRY_KEY);
        editor.apply();
    }
}

