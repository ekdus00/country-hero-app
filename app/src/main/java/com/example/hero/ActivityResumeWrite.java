package com.example.hero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ActivityResumeWrite extends AppCompatActivity {

    ExperienceAdapter adapter;
    ArrayList<ExperienceData> list;

    RecyclerView recyclerView;

    LinearLayout backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_write);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        list = new ArrayList<>();

        list.add(new ExperienceData("test1"));
        list.add(new ExperienceData("test2"));
        list.add(new ExperienceData("test3"));
        list.add(new ExperienceData("test4"));

        adapter = new ExperienceAdapter(list);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}