package com.example.hero.etc;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hero.R;
import com.example.hero.login.activity.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends Activity {
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

//                    String msg = getString(R.string.msg_token_fmt, token);
//                    Log.d(TAG, msg);
                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 메인 액티비티로 이동
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                // 스플래시 액티비티 종료
                finish();
            }
        }, 2000); // 2초 후 MainActivity 실행

    }//onCreate


}
