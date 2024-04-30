package com.example.hero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageRecruiter extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_recruiter);

        Button my_page_Modify_Btn = findViewById(R.id.myPage_modify_btn);
//        Button my_page_employer_status_btn = findViewById(R.id.myPage_employer_status_btn);
        Button myPage_review_btn = findViewById(R.id.myPage_review_btn);


        my_page_Modify_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModifyRecruiter.class);
                startActivity(intent);
            }
        });

//        my_page_employer_status_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), EmployerStatus.class);
//                startActivity(intent);
//            }
//        });

        myPage_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewEmployerList.class);
                startActivity(intent);
            }
        });

    }
}
