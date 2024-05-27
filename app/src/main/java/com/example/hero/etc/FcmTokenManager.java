package com.example.hero.etc;

import android.content.Context;
import android.content.SharedPreferences;

public class FcmTokenManager {
    private static final String PREF_NAME = "firebase_prefs";
    private static final String FCM_TOKEN_KEY = "fcm_token";
    private SharedPreferences prefs;

    public FcmTokenManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveFCMToken(String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(FCM_TOKEN_KEY, token);
        editor.apply();
    }

    public String getFCMToken() {
        return prefs.getString(FCM_TOKEN_KEY, null);
    }
}

