package com.example.hero.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.FcmTokenManager;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.SplashActivity;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.home.activity.HomeApplicant;
import com.example.hero.home.activity.HomeRecruiter;
import com.example.hero.home.dto.EmployInfoHomeDTO;
import com.example.hero.home.dto.JobInfoHomeDTO;
import com.example.hero.home.dto.OwnerHomeDTO;
import com.example.hero.home.dto.ParticipateInfoHomeDTO;
import com.example.hero.home.dto.WorkerHomeDTO;
import com.example.hero.login.dto.JWTPayloadDTO;
import com.example.hero.login.dto.JoinRequestDTO;
import com.example.hero.login.dto.LoginRequestDTO;
import com.example.hero.login.dto.LoginResultDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private Context context;
    private Button login_sendBtn, login_joinBtn;
    private EditText login_id_editText, login_pw_editText;
    private TokenManager tokenManager;
    private UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        context = this;
        tokenManager = new TokenManager(this);
        userManager = new UserManager(this);

        login_id_editText = findViewById(R.id.login_id_editText);
        login_pw_editText = findViewById(R.id.login_pw_editText);
        login_sendBtn = findViewById(R.id.login_sendBtn);
        login_sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest();
            }
        });

        login_joinBtn = findViewById(R.id.login_joinBtn);
        login_joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                startActivity(intent);
            }
        });

    }//onCreate()

    private void loginRequest() {
        String join_id = login_id_editText.getText().toString();
        String join_pw = login_pw_editText.getText().toString();

        userManager.saveUserId(join_id);
        boolean isInfoCompleted = userManager.isUserInfoInputCompleted();

        if (!isInfoCompleted) {
            // 정보 입력이 완료된 경우, 메인 화면으로 이동
            startActivity(new Intent(Login.this, Join.class));
            finish();
        }

        String userType = userManager.getUserType();

        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setUserId(join_id);
        dto.setUserPw(join_pw);

        FcmTokenManager fcmTokenManager = new FcmTokenManager(getApplicationContext());
        String fcmToken = fcmTokenManager.getFCMToken();

        ApiService apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
        //로그인, 회원가입, 아이디찾기, 비번재설정, 액세스토큰재발급, 네이버간편로그인, 추가정보입력, 사용자 인증만 레트로핏클라이언트2 사용하기

        //로그인 서버요청
        Call<LoginResultDTO> call = apiService.loginUser(dto, fcmToken);
        call.enqueue(new Callback<LoginResultDTO>() {
            @Override
            public void onResponse(Call<LoginResultDTO> call, Response<LoginResultDTO> response) {
                if (response.isSuccessful()) {
                    Headers headers = response.headers();
                    String contentType = headers.get("Content-Type");

                    String headerValue = headers.get("Authorization");
                    if (headerValue != null && headerValue.startsWith("Bearer ")) {
                        String token = headerValue.replace("Bearer ", "");
                        tokenManager.saveAccessTokens(token);
                        //token 변수에는 "Bearer "가 제거된 순수 토큰값만 저장
                    }

                    String refreshToken = headers.get("Refresh-Token");
                    //헤더의 리프래시, 액세스토큰 토큰매니저에 저장
                    tokenManager.saveRefreshTokens(refreshToken);
                    tokenManager.saveExpiryTimeTokens();

                    if ("owner".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeRecruiter");
                        startActivity(new Intent(Login.this, HomeRecruiter.class));
                    } else if("worker".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeApplicant");
                        startActivity(new Intent(Login.this, HomeApplicant.class));
                    }

                    Log.e("login", "로그인 서버응답 성공" + response.code() + ", " + response.message());
                } else {
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

