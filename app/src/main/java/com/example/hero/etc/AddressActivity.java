package com.example.hero.etc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;

import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {
    public static final int ADDRESS_REQUEST_CODE = 2928;
    private WebView webView;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_webview);
        tokenManager = new TokenManager(this);

        webView = (WebView) findViewById(R.id.web_view);

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(new AddressActivity.KakaoJavaScriptInterface(), "Android");

        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());

        // 웹뷰에서 URL을 로드할 때 HTTP 헤더 추가
        Map<String, String> extraHeaders = new HashMap<>();
        String acToken = tokenManager.getAccessToken();
        extraHeaders.put("Authorization", "Bearer " + acToken);

        webView.loadUrl("http://3.37.68.5:8080/postcode.html", extraHeaders);

    }//onCreate()

    public class KakaoJavaScriptInterface {
        @JavascriptInterface
        public void processDATA(String postcode, String address) {
            Intent intent = new Intent();
            intent.putExtra("address", address);
            intent.putExtra("postcode", postcode);
            setResult(RESULT_OK, intent);
            finish();
        }
    }








}
