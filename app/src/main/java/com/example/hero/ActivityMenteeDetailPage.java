package com.example.hero;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityMenteeDetailPage extends AppCompatActivity {
    LinearLayout backBtn; // 뒤로가기 버튼

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_detail);

        backBtn = findViewById(R.id.back_btn); // 뒤로가기 버튼의 객체(linear layout)를 id로 찾아서 받아옴
        backBtn.setOnClickListener(new View.OnClickListener() { // 뒤로가기 버튼(linear layout)에 클릭 이벤트를 달아줌
            @Override
            public void onClick(View view) {
                onBackPressed();
            } // 클릭하면 뒤로가기 동작이 실행됨
        });
    }
}
