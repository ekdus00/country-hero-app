package com.example.hero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MyPageApplicant extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_applicant);

        Button my_page_Modify_Btn = findViewById(R.id.myPage_modify_btn);
        Button my_page_Resume_Btn = findViewById(R.id.myPage_resume_btn);

        my_page_Modify_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModifyApplicant.class);
                startActivity(intent);
            }
        });

        my_page_Resume_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResumePost.class);
                startActivity(intent);
            }
        });

    }
}