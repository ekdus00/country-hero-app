package com.example.hero.login.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;

import android.content.Context;

import com.nhn.android.naverlogin.OAuthLogin;
//import com.nhn.android.naverlogin.OAuthLoginHandler;

public class NaverLogin extends AppCompatActivity {
    private OAuthLogin mOAuthLoginModule;
    private static final String TAG = NaverLogin.class.getSimpleName();
    Context mContext;

//    private ActivityMainBinding binding;
    private String email = "";
    private String gender = "";
    private String name = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        mContext = getApplicationContext();


//        Button login_simple_naver_btn = findViewById(R.id.login_simple_naver_btn);
//        login_simple_naver_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mOAuthLoginModule = OAuthLogin.getInstance();
//                mOAuthLoginModule.init(
//                        mContext
//                        ,getString(R.string.naver_client_id)
//                        ,getString(R.string.naver_client_secret)
//                        ,getString(R.string.naver_client_name)
//                );

//                @SuppressLint("HandlerLeak")
//                OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
//                    @Override
//                    public void run(boolean success) {
//                        if (success) {
//                            String accessToken = mOAuthLoginModule.getAccessToken(mContext);
//                            String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
//                            long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
//                            String tokenType = mOAuthLoginModule.getTokenType(mContext);
//
//                            Log.i("LoginData","accessToken : "+ accessToken);
//                            Log.i("LoginData","refreshToken : "+ refreshToken);
//                            Log.i("LoginData","expiresAt : "+ expiresAt);
//                            Log.i("LoginData","tokenType : "+ tokenType);
//
//                        } else {
//                            String errorCode = mOAuthLoginModule
//                                    .getLastErrorCode(mContext).getCode();
//                            String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
//                            Toast.makeText(mContext, "errorCode:" + errorCode
//                                    + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
//                        }
//                    };
//                };
//
//                mOAuthLoginModule.startOauthLoginActivity(MainActivity.this, mOAuthLoginHandler);
//
//            }
//        });
//
//        login_simple_naver_btn.setOnClickListener(view -> {
//            OAuthLoginHandler oAuthLoginHandler = new OAuthLoginHandler() {
//                @Override
//                public void run(boolean success) {
//                    if (success) {
//                        OAuthLogin oAuthLogin = OAuthLogin.getInstance();
//                        String accessToken = oAuthLogin.getAccessToken(MainActivity.this);
//                        // API 호출 성공 시 유저 정보 가져오기
//                        callProfileApi(accessToken);
//                    } else {
//                        String errorCode = oAuthLogin.getLastErrorCode(MainActivity.this).getCode();
//                        String errorDesc = oAuthLogin.getLastErrorDesc(MainActivity.this);
//                        Log.e(TAG, "네이버 로그인 에러 - 코드: " + errorCode + ", 메시지: " + errorDesc);
//                    }
//                }
//            };
//
//            OAuthLogin.getInstance().init(MainActivity.this, getString(R.string.naver_client_id),
//                    getString(R.string.naver_client_secret), "앱 이름");
//            OAuthLogin.getInstance().startOauthLoginActivity(MainActivity.this, oAuthLoginHandler);
//        });





    }
}