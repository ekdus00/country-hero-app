package com.example.hero.guide;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;

public class UserGuideActivity extends AppCompatActivity {

    // 뒤로가기 버튼
    LinearLayout backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        // 뒤로가기 버튼 클릭했을 때 이벤트 등록
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 플랫폼 소개 문구 꾸미기(색상, 글씨 크기 일부 변경)
        TextView introduce = findViewById(R.id.introduce_text);
        String introduceText = introduce.getText().toString();
        SpannableStringBuilder builder = new SpannableStringBuilder(introduceText);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(16, true);
        builder.setSpan(sizeSpan, 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorGreenSpan = new ForegroundColorSpan(Color.parseColor("#91b199"));
        builder.setSpan(colorGreenSpan, 0,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        introduce.setText(builder);
    }
}