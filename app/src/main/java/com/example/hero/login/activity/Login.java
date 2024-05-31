package com.example.hero.login.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {
    private NaverIdLoginSDK naverIdLoginSDK;
    private FcmTokenManager fcmTokenManager;
    private ApiService apiService;
    private Button login_sendBtn, login_joinBtn, login_logout, buttonOAuthLoginImg;
    private EditText login_id_editText, login_pw_editText;
    private TokenManager tokenManager;
    private UserManager userManager;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        context = this;
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

        //로그아웃
        login_logout = findViewById(R.id.login_logout);
        login_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
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
        naverIdLoginSDK.initialize(this, getString(R.string.naver_client_id), getString(R.string.naver_client_secret), getString(R.string.naver_client_name));
        NidOAuthLoginButton buttonOAuthLoginImg = findViewById(R.id.buttonOAuthLoginImg);
        buttonOAuthLoginImg.setOAuthLogin(new OAuthLoginCallback() {
            @Override
            public void onSuccess() {
                // 로그인 성공시
                // 액세스 토큰 가져오기
                String accessToken = NaverIdLoginSDK.INSTANCE.getAccessToken();
                tokenManager.saveAccessTokens(accessToken);
//                textView.setText(accessToken);
                buttonOAuthLoginImg.setVisibility(View.GONE);
//                String ac = naverIdLoginSDK.getAccessToken();
//                binding.tvRefreshToken.text = NaverIdLoginSDK.getRefreshToken()
//                binding.tvExpires.text = NaverIdLoginSDK.getExpiresAt().toString()
//                binding.tvType.text = NaverIdLoginSDK.getTokenType()
//                binding.tvState.text = NaverIdLoginSDK.getState().toString()
            }

            @Override
            public void onFailure(int httpStatus, @NonNull String message) {
                // 통신 오류
                Log.e("네아로", "onFailure: httpStatus - " + httpStatus + " / message - " + message);
            }

            @Override
            public void onError(int errorCode, @NonNull String message) {
                // 네이버 로그인 중 오류 발생
                Log.e("네아로", "onError: errorCode - " + errorCode + " / message - " + message);
            }
        });


    }//onCreate()

    public void requestNaverLogin(String code, String fcmToken) {
        apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
        fcmToken = fcmTokenManager.getFCMToken();
        Call<NaverLoginResultDTO> call = apiService.naverLoginCallback(code, fcmToken);
        call.enqueue(new Callback<NaverLoginResultDTO>() {
            @Override
            public void onResponse(Call<NaverLoginResultDTO> call, Response<NaverLoginResultDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 로그인 성공 처리
                    NaverLoginResultDTO loginResult = response.body();
                    // JWT 토큰을 저장하고 사용자 홈으로 이동
                } else {
                    // 로그인 실패 처리
                }
            }

            @Override
            public void onFailure(Call<NaverLoginResultDTO> call, Throwable t) {
                // 네트워크 문제나 서버 오류 처리
            }
        });
    }















    private void loginRequest() {
        String join_id = login_id_editText.getText().toString();
        String join_pw = login_pw_editText.getText().toString();

        userManager.saveUserId(join_id);
        boolean isInfoCompleted = userManager.isUserInfoInputCompleted();

        if (!isInfoCompleted) {
            startActivity(new Intent(Login.this, Join.class));
            finish();
        }

        String userType = userManager.getUserType();

        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setUserId(join_id);
        dto.setUserPw(join_pw);

//        FcmTokenManager fcmTokenManager = new FcmTokenManager(getApplicationContext());
//        String fcmToken = fcmTokenManager.getFCMToken();

        ApiService apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
        //로그인, 회원가입, 아이디찾기, 비번재설정, 액세스토큰재발급, 네이버간편로그인, 추가정보입력, 사용자 인증만 레트로핏클라이언트2 사용하기

        //로그인 서버요청
        Call<LoginResultDTO> call = apiService.loginUser(dto);
        call.enqueue(new Callback<LoginResultDTO>() {
            @Override
            public void onResponse(Call<LoginResultDTO> call, Response<LoginResultDTO> response) {
                if (response.isSuccessful()) {
                    Headers headers = response.headers();
//                    String contentType = headers.get("Content-Type");
//
//                    String headerValue = headers.get("Authorization");
//                    if (headerValue != null && headerValue.startsWith("Bearer ")) {
//                        String token = headerValue.replace("Bearer ", "");
//                        tokenManager.saveAccessTokens(token);
//                        //token 변수에는 "Bearer "가 제거된 순수 토큰값만 저장
//                    }
//
//                    String refreshToken = headers.get("Refresh-Token");
//                    //헤더의 리프래시, 액세스토큰 토큰매니저에 저장
//                    tokenManager.saveRefreshTokens(refreshToken);
//                    tokenManager.saveRefreshTokenExpiryTime();

                    String accessToken = headers.get("Authorization").replace("Bearer ", "");
                    String refreshToken = headers.get("Refresh-Token");

                    tokenManager.saveAccessTokens(accessToken);
                    tokenManager.saveRefreshTokens(refreshToken);

                    if ("owner".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeRecruiter");
                        startActivity(new Intent(Login.this, HomeOwner.class));
                    } else if("worker".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeApplicant");
                        startActivity(new Intent(Login.this, HomeWorker.class));
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

    private void logoutUser() {
        tokenManager.clearTokens();
        tokenManager.clearTokens();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }



}

