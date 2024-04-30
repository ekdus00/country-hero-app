package com.example.hero;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class AddressActivity extends AppCompatActivity {
    public static final int ADDRESS_REQUEST_CODE = 2928;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_webview);

        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new com.example.hero.AddressActivity.KakaoJavaScriptInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:execKakaoPostcode();");
            }
        });
        webView.loadUrl("http://10.0.2.2:8080/postcode.html");
    }

    public class KakaoJavaScriptInterface {
        @JavascriptInterface
        public void processDATA(String address) {
            Intent intent = new Intent();
            intent.putExtra("address", address);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
