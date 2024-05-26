package com.example.hero_home.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hero_home.R;
import com.example.hero_home.adapter.ExperienceAdapter;
import com.example.hero_home.model.ExperienceDTO;

import java.util.ArrayList;

public class ActivityResumeWrite extends AppCompatActivity {


    ExperienceAdapter experienceAdapter;

    ArrayList<ExperienceDTO> experienceList;

    private RecyclerView experience_list;

    LinearLayout backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_write);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        experience_list = findViewById(R.id.experience_list);

        experienceList = new ArrayList<>();

        //리스트에 더미데이터 넣기
        experienceList.add(new ExperienceDTO("test1"));
        experienceList.add(new ExperienceDTO("test2"));
        experienceList.add(new ExperienceDTO("test3"));
        experienceList.add(new ExperienceDTO("test4"));

        //어댑터 초기화
        experienceAdapter = new ExperienceAdapter(experienceList);

        //리싸이클러뷰에 어댑터 설정
        experience_list.setAdapter(experienceAdapter);
        experience_list.setLayoutManager(new LinearLayoutManager(this));

    }
}