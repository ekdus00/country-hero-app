package com.example.hero.etc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;

public class AddressActivity extends AppCompatActivity {
    public static final int ADDRESS_REQUEST_CODE = 2928;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_webview);

        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new AddressActivity.KakaoJavaScriptInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:execKakaoPostcode();");
            }
        });
        webView.loadUrl("http://3.37.68.5:8080/home/ubuntu/projects/country-hero-server/build/resources/main/static/postcode.html");
    }

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
