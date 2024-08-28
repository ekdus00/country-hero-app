package com.example.hero.login.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.home.activity.HomeOwner;
import com.example.hero.home.activity.HomeWorker;
import com.example.hero.login.dto.NaverLoginResultDTO;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NaverWebView extends AppCompatActivity {
    private static final String TAG = "WebViewFragment";
    private WebView webView;
    private TokenManager tokenManager;
    private UserManager userManager;
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_webview);
        tokenManager = new TokenManager(this);
        userManager = new UserManager(this);

        // WebView 초기화
        webView = (WebView) findViewById(R.id.web_view);

        // WebView 설정
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if (uri.toString().startsWith("http://3.37.68.5:8080/naver/callback")) {
                    String code = uri.getQueryParameter("code");
                    if (code != null) {
                        Log.v(TAG, "코드값 추출: " + code);
                        // 여기서 code 값을 사용하여 필요한 작업을 수행
                        requestNaverLogin2(code);
                    }
                    return true;
                }
                return false;
            }
        });

        // 네이버 로그인 페이지 로드
        String clientId = getString(R.string.naver_client_id);
        String redirectUri = "http://3.37.68.5:8080/naver/callback";
        String url = "https://nid.naver.com/oauth2.0/authorize?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code";
        webView.loadUrl(url);

    } //onCreate()


    public void requestNaverLogin2(String code) {
        ApiService apiService = RetrofitClientWithoutAuth.getClient3().create(ApiService.class);
        Call<NaverLoginResultDTO> call = apiService.naverLoginCallback(code);
        call.enqueue(new Callback<NaverLoginResultDTO>() {
            @Override
            public void onResponse(Call<NaverLoginResultDTO> call, Response<NaverLoginResultDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Headers headers = response.headers();
                    NaverLoginResultDTO dto = response.body();

                    String userType =  dto.getUserType();
                    userManager.saveUserType(userType);
                    Log.v(TAG, "유저타입응답: " + userType);

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

                    if (userManager.getUserType() == null) {
                        Intent intent = new Intent(NaverWebView.this, UserTypeSet.class);
                        intent.putExtra("loginType", "Naver");
                        startActivity(intent);
                    }

                    if ("owner".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeRecruiter");
                        startActivity(new Intent(NaverWebView.this, HomeOwner.class));
                    } else if("worker".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeApplicant");
                        startActivity(new Intent(NaverWebView.this, HomeWorker.class));
                    }

                    Log.e("login", "네이버로그인 서버응답 성공" + response.code() + ", " + response.message());

                } else if (response.body() == null && "text/plain".equals(response.headers().get("Content-Type"))) {
                    // 텍스트 응답을 처리하는 로직
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

                    if (userManager.getUserType() == null) {
                        Intent intent = new Intent(NaverWebView.this, UserTypeSet.class);
                        intent.putExtra("loginType", "Naver");
                        startActivity(intent);
                    }

                    String userType = userManager.getUserType();

                    if ("owner".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeRecruiter");
                        startActivity(new Intent(NaverWebView.this, HomeOwner.class));
                    } else if("worker".equals(userType)) {
                        Log.d("LoginActivity", "Navigating to HomeApplicant");
                        startActivity(new Intent(NaverWebView.this, HomeWorker.class));
                    }

                } else {
                    Headers headers = response.headers();

                    if (headers.get("Authorization") != null) {
                        String accessToken = headers.get("Authorization").replace("Bearer ", "");
                        String refreshToken = headers.get("Refresh-Token");
                        tokenManager.saveAccessTokens(accessToken);
                        tokenManager.saveRefreshTokens(refreshToken);
                    }

                    // 400 에러일 경우의 처리 로직 추가
                    if (response.code() == 400) {
                        // 에러 화면으로 이동하는 인텐트 생성
                        Intent intent = new Intent(NaverWebView.this, UserTypeSet.class);
                        intent.putExtra("loginType", "Naver");
                        startActivity(intent);
                    }

                    if (userManager.getUserType() == null) {
                        Intent intent = new Intent(NaverWebView.this, UserTypeSet.class);
                        intent.putExtra("loginType", "Naver");
                        startActivity(intent);
                    }

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
}
