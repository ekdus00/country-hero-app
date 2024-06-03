package com.example.hero.etc;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hero.R;
import com.example.hero.home.activity.HomeOwner;
import com.example.hero.home.activity.HomeWorker;
import com.example.hero.login.activity.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends Activity {
    private UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_app);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "fcm 토큰 발급 실패", task.getException());
                            return;
                        }
                        String token = task.getResult();

                        FcmTokenManager fcmTokenManager = new FcmTokenManager(getApplicationContext());
                        fcmTokenManager.saveFCMToken(token);

                        String a = fcmTokenManager.getFCMToken();
                        Log.v(TAG, "fcm토큰: " + token);
                        Log.v(TAG, "fcm토큰 저장값: " + a);

//                    String msg = getString(R.string.msg_token_fmt, token);
//                    Log.d(TAG, msg);
                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userManager = new UserManager(SplashActivity.this);

                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                finish();

//                if (userManager.getUserId() != null) {
//                    // 이미 로그인 정보가 있으면 홈 화면으로 바로 전환
//                    String userType = userManager.getUserType();
//                    if ("owner".equals(userType)) {
//                        Log.d("LoginActivity", "Navigating to HomeRecruiter");
//                        startActivity(new Intent(SplashActivity.this, HomeOwner.class));
//                    } else if("worker".equals(userType)) {
//                        Log.d("LoginActivity", "Navigating to HomeApplicant");
//                        startActivity(new Intent(SplashActivity.this, HomeWorker.class));
//                    }
//                    finish();
//                } else {
//                    // 로그인 화면을 보여주거나 로그인 액티비티로 전환
//                    Intent intent = new Intent(SplashActivity.this, Login.class);
//                    startActivity(intent);
//                    finish();
//                }

                // 스플래시 액티비티 종료
                finish();
            }
        }, 2000); // 2초 후 MainActivity 실행

    }//onCreate


}
