package com.example.hero;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeRecruiter extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_recruiter);

        RecyclerView recyclerView = findViewById(R.id.home_recruiter_recyclerView);

        //예시데이터
        ArrayList<String> testDataSet = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet.add("공고제목" + i);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
        recyclerView.setLayoutManager(linearLayoutManager);

        HomeApplicantAdapter customAdapter = new HomeApplicantAdapter(testDataSet);
        recyclerView.setAdapter(customAdapter);



        //두번째 recycleview
        RecyclerView recyclerView2 = findViewById(R.id.home_job_list_recyclerView);

        //예시데이터
        ArrayList<String> testDataSet2 = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet2.add("공고제목" + i);
        }

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager((Context) this);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        HomeJobListAdapter customAdapter2 = new HomeJobListAdapter(testDataSet);
        recyclerView2.setAdapter(customAdapter2);

        Button Button1 = (Button) findViewById(R.id.home_recruiter_title1_btn);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobList.class);
                startActivity(intent);
            }
        });



    }
}
