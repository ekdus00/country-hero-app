package com.example.hero.login.activity;

import static android.content.ContentValues.TAG;
import static android.system.Os.connect;

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

        executorService = Executors.newSingleThreadExecutor();

        //네이버 로그인
        NaverIdLoginSDK.INSTANCE.initialize(this, getString(R.string.naver_client_id), getString(R.string.naver_client_secret), getString(R.string.naver_client_name));
        NidOAuthLoginButton buttonOAuthLoginImg = findViewById(R.id.buttonOAuthLoginImg);
        buttonOAuthLoginImg.setOAuthLogin(new OAuthLoginCallback() {
            @Override
            public void onSuccess() {
                // 로그인 성공시
                // 액세스 토큰 가져오기
                String accessToken = NaverIdLoginSDK.INSTANCE.getAccessToken();
                Log.v(TAG, "액세스토큰: " + accessToken);

                userManager.saveJoinType("naver");
                buttonOAuthLoginImg.setVisibility(View.GONE);

//                binding.tvRefreshToken.text = NaverIdLoginSDK.getRefreshToken()
//                binding.tvExpires.text = NaverIdLoginSDK.getExpiresAt().toString()
//                binding.tvType.text = NaverIdLoginSDK.getTokenType()
//                binding.tvState.text = NaverIdLoginSDK.getState().toString()

                executorService.execute(() -> {
                    String responseBody = getUserProfile(accessToken);
                    Log.i("프로필 가져오기", responseBody);

                    if (responseBody != null && !responseBody.isEmpty()) {
                        runOnUiThread(() -> updateProfile(responseBody));
                    }
                });

                requestNaverLogin2(accessToken);

//                requestNaverLogin();

//                //최초 네이버 로그인일 경우
//                if (userManager.getUserType() == null) {
//                    Intent intent = new Intent(Login.this, UserTypeSet.class);
//                    intent.putExtra("loginType", "naver");  // 실제 사용자 ID를 넘겨줌
//                    startActivity(intent);
//                } else {
//                    // 기존 로그인 처리 로직
//                    requestNaverLogin();
//                }
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

    private String getUserProfile(String accessToken) {
        String apiURL = "https://openapi.naver.com/v1/nid/me";
        String authorization = "Bearer " + accessToken; // Bearer 다음에 공백 추가

        HttpURLConnection con = connect(apiURL);
        try {
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", authorization);

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    private void updateProfile(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject response = jsonObject.getJSONObject("response");
            String id = response.getString("id");

            String email = response.getString("email");
            String[] parts = email.split("@"); // "@" 기호를 기준으로 문자열을 분리합니다.
            String userId = parts[0];

            Log.v(TAG, "네이버이메일: " + email);
            Log.v(TAG, "아이디값 추출: " + userId);

            userManager.saveUserId(userId);

        } catch (JSONException e) {
            Log.e("네아로", "JSON 파싱 오류", e);
        }
    }
    public void requestNaverLogin2(String code) {
//        if (userManager.getUserType() == null) {
//            Intent intent = new Intent(Login.this, UserTypeSet.class);
//            startActivity(intent);
//        }
        Log.v(TAG, "코드값: " + code);

        apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
        Call<NaverLoginResultDTO> call = apiService.naverLoginCallback(code);
        call.enqueue(new Callback<NaverLoginResultDTO>() {
            @Override
            public void onResponse(Call<NaverLoginResultDTO> call, Response<NaverLoginResultDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
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

                    String userType = userManager.getUserType();

                    if (userManager.getUserType() == null) {
                        Intent intent = new Intent(Login.this, UserTypeSet.class);
                        startActivity(intent);
                    }

                    if ("owner".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeRecruiter");
                        startActivity(new Intent(Login.this, HomeOwner.class));
                    } else if("worker".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeApplicant");
                        startActivity(new Intent(Login.this, HomeWorker.class));
                    }
                    Log.e("login", "네이버로그인 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("login", "네이버로그인 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("login", "네이버로그인 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<NaverLoginResultDTO> call, Throwable t) {
                Log.e("login", "네이버로그인 서버요청 오류", t);
            }
        });
    }

//    public void requestNaverLogin() {
//        if (userManager.getUserType() == null) {
//            Intent intent = new Intent(Login.this, UserTypeSet.class);
//            startActivity(intent);
//        }

//        apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
//        Call<NaverLoginResultDTO> call = apiService.naverLoginCallback();
//        call.enqueue(new Callback<NaverLoginResultDTO>() {
//            @Override
//            public void onResponse(Call<NaverLoginResultDTO> call, Response<NaverLoginResultDTO> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Headers headers = response.headers();
//
//                    String accessToken = headers.get("Authorization").replace("Bearer ", "");
//                    String refreshToken = headers.get("Refresh-Token");
//
//                    tokenManager.saveAccessTokens(accessToken);
//                    tokenManager.saveRefreshTokens(refreshToken);
//
//                    String a = tokenManager.getAccessToken();
//                    String b = tokenManager.getRefreshToken();
//                    long c = tokenManager.getAccessExpirationTime();
//                    long d = tokenManager.getRefreshExpirationTime();
//
//                    Log.v(TAG, "액세스토큰: " + a);
//                    Log.v(TAG, "리프레시토큰: " + b);
//                    Log.v(TAG, "액세스토큰 남은시간: " + c);
//                    Log.v(TAG, "리프레시토큰 남은시간: " + d);
//
//                    String userType = userManager.getUserType();
//
//                    if ("owner".equals(userType)) {
//                        Log.d("LoginActivity", "Navigating to HomeRecruiter");
//                        startActivity(new Intent(Login.this, HomeOwner.class));
//                    } else if("worker".equals(userType)) {
//                        Log.d("LoginActivity", "Navigating to HomeApplicant");
//                        startActivity(new Intent(Login.this, HomeWorker.class));
//                    }
//                    Log.e("login", "네이버로그인 서버응답 성공" + response.code() + ", " + response.message());
//                } else {
//                    Log.e("login", "네이버로그인 서버응답 오류코드" + response.code() + ", " + response.message());
//                    Log.e("login", "네이버로그인 서버응답 오류" + response.errorBody().toString());
//                }
//            }
//            @Override
//            public void onFailure(Call<NaverLoginResultDTO> call, Throwable t) {
//                Log.e("login", "네이버로그인 서버요청 오류", t);
//            }
//        });
//    }

    private void loginRequest() {
        String id = login_id_editText.getText().toString();
        String pw = login_pw_editText.getText().toString();

        userManager.saveUserId(id);

        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setUserId(id);
        dto.setUserPw(pw);

        ApiService apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);

        //로그인 서버요청
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

