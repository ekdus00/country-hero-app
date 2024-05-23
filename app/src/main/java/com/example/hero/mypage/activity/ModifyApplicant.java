package com.example.hero.mypage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hero.R;


public class ModifyApplicant extends AppCompatActivity {
    private TextView textView_Work_Start;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_applicant);

        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("회원 정보 수정");

//        textView_Work_Start = findViewById(R.id.textView_date_start);
//        textView_Work_End = findViewById(R.id.textView_date_end);
//        btn_Recruit_Start = findViewById(R.id.start_recruit);
//        btn_Recruit_End = findViewById(R.id.end_recruit);
//        textView_Recruit_Start = findViewById(R.id.textView_recruit_start);
//        textView_Recruit_End = findViewById(R.id.textView_recruit_end);

    }
}
