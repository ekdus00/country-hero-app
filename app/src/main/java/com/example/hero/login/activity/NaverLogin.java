package com.example.hero.login.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;

import android.content.Context;

import com.example.hero.etc.ApiService;
import com.example.hero.etc.FcmTokenManager;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.RetrofitClientWithoutAuth;

import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.oauth.NidOAuthBehavior;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfileMap;
import com.navercorp.nid.profile.data.NidProfileResponse;

public class NaverLogin extends AppCompatActivity {
    Context mContext;
    private NaverIdLoginSDK naverIdLoginSDK;
    private ApiService apiService;
    private FcmTokenManager fcmTokenManager;
    private TokenManager tokenManager;
    private UserManager userManager;
    private String email = "";
    private String gender = "";
    private String name = "";
    private Button buttonOAuthLoginImg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_main);
//        mContext = getApplicationContext();

        naverIdLoginSDK.initialize(this, getString(R.string.naver_client_id), getString(R.string.naver_client_secret), getString(R.string.naver_client_name));
        buttonOAuthLoginImg = findViewById(R.id.buttonOAuthLoginImg);

//        binding.buttonOAuthLoginImg.setOAuthLogin(launcher);
////        binding.buttonOAuthLoginImg.setOAuthLogin(oauthLoginCallback);
//
//        binding.buttonOAuthLoginImg.setOAuthLoginCallback(object : OAuthLoginCallback {
//            override fun onError(errorCode: Int, message: String) {
//                Toast.makeText(applicationContext, "네이버 아이디 로그인 에러입니다. $message (오류코드: $errorCode)", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onFailure(httpStatus: Int, message: String) {
//                Toast.makeText(applicationContext, "네이버 아이디 로그인 실패입니다. $message (오류코드: $httpStatus)", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onSuccess() {
//                Toast.makeText(applicationContext, "네이버 아이디 로그인 성공입니다.", Toast.LENGTH_LONG).show()
//            }
//
//        })







    }//onCreate()




}