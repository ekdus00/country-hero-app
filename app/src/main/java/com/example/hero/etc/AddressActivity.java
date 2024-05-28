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

public class AddressActivity extends AppCompatActivity {
    public static final int ADDRESS_REQUEST_CODE = 2928;
    public WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_webview);
        initWebView(); // Call the initWebView method here
    }

    public void initWebView() {
        // WebView 설정
        webView = (WebView)findViewById(R.id.web_view);

        // javascript 를 사용할 수 있게 셋팅.
        webView.getSettings().setJavaScriptEnabled(true);

        // javascript 의 window.open 허용.
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // "TestApp" 이름이 중요합니다. javascript 에서 호출되는 이름과 동일해야 합니다.
        webView.addJavascriptInterface(new AddressActivity.KakaoJavaScriptInterface(), "Android");

//        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("WebViewClient", "URL: " + request.getUrl().toString());
                if (request.getUrl().toString().startsWith("http:") || request.getUrl().toString().startsWith("https:")) {
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
                startActivity(intent);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.e("WebViewClient", "Error: " + error.getDescription().toString());
                // Handle the error
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e("WebViewClient", "SSL Error: " + error.toString());
                handler.proceed(); // SSL 에러가 발생해도 계속 진행
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebViewClient", "Page loaded: " + url);
//                webView.loadUrl("javascript:sample3_execDaumPostcode();");
            }
        });

//        webView.loadUrl("http://3.37.68.5:8080/home/ubuntu/projects/country-hero-server/build/resources/main/static/postcode.html");
        webView.loadUrl("http://3.37.68.5:8080/postcode.html");
    }

//
//    @SuppressLint("SetJavaScriptEnabled")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.address_webview);
//
//        WebView webView = findViewById(R.id.web_view);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//
//        webView.addJavascriptInterface(new AddressActivity.KakaoJavaScriptInterface(), "Android");
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
//                Log.d("WebViewActivity", request.getUrl().toString());
//                if( request.getUrl().toString().startsWith("http:") || request.getUrl().toString().startsWith("https:") ) {
//
//                }
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
//                startActivity( intent );
//                return true;
//            }
////            @Override
////            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
////                handler.proceed(); // SSL 에러가 발생해도 계속 진행
////            }
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                Log.e("tag", "html 로드2");
////                webView.loadUrl("http://3.37.68.5:8080/postcode.html");
////                return true;
////            }
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                Log.e("tag", "html 로드1");
//                webView.loadUrl("javascript:sample3_execDaumPostcode();");
//            }
//        });
//        webView.loadUrl("http://3.37.68.5:8080/postcode.html");
////        webView.loadUrl("https://postcode.map.daum.net/");
////        webView.loadUrl("http://3.37.68.5:8080/postcode.html");
//
////        Log.e("tag", "html 로드2");
////        webView.loadUrl("https://postcode.map.daum.net/");
////        webView.loadUrl("http://3.37.68.5:8080/home/ubuntu/projects/country-hero-server/build/resources/main/static/postcode.html");
//
////        webView.loadUrl("http://3.37.68.5:8080/postcode.html");
////        String url = "http://3.37.68.5:8080/postcode.html";
////        Log.d("AddressActivity", "Loading URL: " + url);
////        webView.loadUrl(url);
////        webView.loadUrl("http://3.37.68.5:8080/postcode.html");
////        Log.e("tag", "html 로드2");
//    }

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
