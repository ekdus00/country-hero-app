package com.example.hero;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.adapter.EmployerDataAdapter;
import com.example.hero.adapter.EmployerStatusAdapterA;
import com.example.hero.adapter.EmployerStatusAdapterB;

import java.util.ArrayList;

public class EmployerStatus extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_status);

        Button employer_status_job_post = (Button) findViewById(R.id.employer_status_job_post);

        employer_status_job_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobPost.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView1 = findViewById(R.id.employer_status_progress_recyclerView);
        //예시데이터
        ArrayList<String> testDataSet1 = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet1.add("아이디" + i);}

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager((Context) this);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        EmployerStatusAdapterA customAdapter1 = new EmployerStatusAdapterA(testDataSet1);
        recyclerView1.setAdapter(customAdapter1);



        RecyclerView recyclerView2 = findViewById(R.id.employer_status_deadline_recyclerView);
        //예시데이터
        ArrayList<String> testDataSet2 = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet2.add("아이디" + i);}

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager((Context) this);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        EmployerStatusAdapterB customAdapter2 = new EmployerStatusAdapterB(testDataSet2);
        recyclerView2.setAdapter(customAdapter2);
    }
}