package com.example.hero.login.activity;

import static android.content.ContentValues.TAG;
import static android.system.Os.connect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.FcmTokenManager;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.home.activity.HomeWorker;
import com.example.hero.home.activity.HomeOwner;
import com.example.hero.login.dto.LoginRequestDTO;
import com.example.hero.login.dto.LoginResultDTO;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.hero.login.dto.NaverLoginResultDTO;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.oauth.NidOAuthBehavior;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.view.NidOAuthLoginButton;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfileMap;
import com.navercorp.nid.profile.data.NidProfileResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {
    private ApiService apiService;
    private Button login_sendBtn, login_joinBtn, login_logout, buttonOAuthLoginImg;
    private EditText login_id_editText, login_pw_editText;
    private TokenManager tokenManager;
    private UserManager userManager;
    private ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        tokenManager = new TokenManager(this);
        userManager = new UserManager(this);

        login_id_editText = findViewById(R.id.login_id_editText);
        login_pw_editText = findViewById(R.id.login_pw_editText);


        //로그인 완료
        login_sendBtn = findViewById(R.id.login_sendBtn);
        login_sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest();
            }
        });

        //회원가입
        login_joinBtn = findViewById(R.id.login_joinBtn);
        login_joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                startActivity(intent);
            }
        });

        //아이디찾기
        Button login_id_findBtn = findViewById(R.id.login_id_findBtn);
        login_id_findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, FindID.class));
            }
        });

        //비밀번호 재설정
        Button login_pw_resetBtn = findViewById(R.id.login_pw_resetBtn);
        login_pw_resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ResetPwAuth.class));
            }
        });

        //네이버 로그인
        Button naver = findViewById(R.id.naver);
        naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NaverWebView.class);
                startActivity(intent);
            }
        });

    }//onCreate()


    private void loginRequest() {
        String id = login_id_editText.getText().toString();
        String pw = login_pw_editText.getText().toString();

        userManager.saveUserId(id);

        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setUserId(id);
        dto.setUserPw(pw);

        ApiService apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);

        //일반 로그인 서버요청
        Call<LoginResultDTO> call = apiService.loginUser(dto);
        call.enqueue(new Callback<LoginResultDTO>() {
            @Override
            public void onResponse(Call<LoginResultDTO> call, Response<LoginResultDTO> response) {
                if (response.isSuccessful()) {
                    LoginResultDTO dto = response.body();
                    String userType =  dto.getUserType();

                    Log.v(TAG, "유저타입응답: " + userType);
                    userManager.saveUserType(userType);

                    Headers headers = response.headers();

                    String accessToken = headers.get("Authorization").replace("Bearer ", "");
                    String refreshToken = headers.get("Refresh-Token");

                    tokenManager.saveAccessTokens(accessToken);
                    tokenManager.saveRefreshTokens(refreshToken);

                    String a = tokenManager.getAccessToken();
                    String b = tokenManager.getRefreshToken();
                    long c = tokenManager.getAccessExpirationTime();
                    long d = tokenManager.getRefreshExpirationTime();

                    Log.v(TAG, "액세스토큰: " + a);
                    Log.v(TAG, "리프레시토큰: " + b);
                    Log.v(TAG, "액세스토큰 남은시간: " + c);
                    Log.v(TAG, "리프레시토큰 남은시간: " + d);

                    if ("owner".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeRecruiter");
                        startActivity(new Intent(Login.this, HomeOwner.class));
                    } else if("worker".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeApplicant");
                        startActivity(new Intent(Login.this, HomeWorker.class));
                    }

                    Log.e("login", "로그인 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Toast.makeText(Login.this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();

                    Log.e("login", "로그인 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("login", "로그인 서버응답 오류" + response.errorBody().toString());                        }
            }
            @Override
            public void onFailure(Call<LoginResultDTO> call, Throwable t) {
                Log.e("login", "로그인 서버요청 오류", t);
            }
        });
    }



}

