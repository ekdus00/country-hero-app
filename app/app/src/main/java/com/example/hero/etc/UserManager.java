package com.example.hero.etc;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String USER_ID_KEY = "userId";
    private static final String USER_TYPE_KEY = "userType";
    private SharedPreferences sharedPreferences;

    public UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID_KEY, userId);
        editor.apply();
    }

    public void saveUserType( String userType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TYPE_KEY, userType);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID_KEY, null);
    }

    public String getUserType() {
        return sharedPreferences.getString(USER_TYPE_KEY, null);
    }

    public void saveUserInfoInputCompleted(boolean isCompleted) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("UserInfoInputCompleted", isCompleted);
        editor.apply();
    }

    public boolean isUserInfoInputCompleted() {
        return sharedPreferences.getBoolean("UserInfoInputCompleted", false);
    }

    public void clearUserDetails() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ID_KEY);
        editor.remove(USER_TYPE_KEY);
        editor.apply();
    }

}


