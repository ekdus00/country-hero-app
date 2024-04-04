package com.example.hero;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FAQActivity extends AppCompatActivity {

    // 각 질문 별  접었다가 펼치는 버튼들.
    ImageView collapseBtn1;
    ImageView collapseBtn2;
    ImageView collapseBtn3;
    ImageView collapseBtn4;

    // 답변 텍스트 뷰들.
    TextView answer1;
    TextView answer2;
    TextView answer3;
    TextView answer4;

    // 뒤로가기 버튼.
    LinearLayout backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 접었다 펼치는 버튼 뷰 접근할 수  있도록 변수에 담음.
        collapseBtn1 = findViewById(R.id.collapse_btn1);
        collapseBtn2 = findViewById(R.id.collapse_btn2);
        collapseBtn3 = findViewById(R.id.collapse_btn3);
        collapseBtn4 = findViewById(R.id.collapse_btn4);

        // 질문 답변 텍스트 뷰 접근할 수  있도록 변수에 담음.
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        // 뒤로 가기 버튼 클릭했을 때  이벤트 등록
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 질문 1에 대한 답변 접었다 펼치는 버튼 클릭했을 때  이벤트 등록.
        collapseBtn1.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int isVisible = answer1.getVisibility();
                if(isVisible == View.VISIBLE) {
                    collapseBtn1.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_drop_up));
                    answer1.setVisibility(View.GONE);
                } else {
                    collapseBtn1.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_drop_down));
                    answer1.setVisibility(View.VISIBLE);
                }
            }
        });

        // 질문 2에 대한 답변 접었다 펼치는 버튼 클릭했을 때  이벤트 등록.
        collapseBtn2.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int isVisible = answer2.getVisibility();
                if(isVisible == View.VISIBLE) {
                    collapseBtn2.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_drop_up));
                    answer2.setVisibility(View.GONE);
                } else {
                    collapseBtn2.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_drop_down));
                    answer2.setVisibility(View.VISIBLE);
                }
            }
        });
        // 질문 3에 대한 답변 접었다 펼치는 버튼 클릭했을 때  이벤트 등록.
        collapseBtn3.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int isVisible = answer3.getVisibility();
                if(isVisible == View.VISIBLE) {
                    collapseBtn3.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_drop_up));
                    answer3.setVisibility(View.GONE);
                } else {
                    collapseBtn3.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_drop_down));
                    answer3.setVisibility(View.VISIBLE);
                }
            }
        });
        
        // 질문 4에 대한 답변 접었다 펼치는 버튼 클릭했을 때  이벤트 등록.
        collapseBtn4.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int isVisible = answer4.getVisibility();
                if(isVisible == View.VISIBLE) {
                    collapseBtn4.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_drop_up));
                    answer4.setVisibility(View.GONE);
                } else {
                    collapseBtn4.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_drop_down));
                    answer4.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}