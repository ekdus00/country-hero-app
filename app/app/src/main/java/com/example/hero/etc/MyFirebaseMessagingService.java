package com.example.hero.etc;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //token을 서버로 전송
        Log.d("FCM Token", "Refreshed token: " + token);

        // 서버에 토큰 전송 로직을 추가할 수 있습니다.
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //수신한 메시지를 처리
    }

    public void sendRegistrationToServer(String token) {
        // API 서버에 요청을 보낼 OkHttpClient 인스턴스 생성
        OkHttpClient client = new OkHttpClient();
        // 서버의 토큰 등록 URL
        String url = "http://3.37.68.5:8080/";

        // POST 요청을 구성하기 위한 RequestBody 생성
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                "{\"token\":\"" + token + "\"}");

        // POST 요청 구성
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // 요청 실행
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("FCM Token", "Failed to register token: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("FCM Token", "Token registered successfully");
            }
        });
    }

}