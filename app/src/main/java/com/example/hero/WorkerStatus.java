package com.example.hero;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.adapter.EmployerStatusAdapterA;
import com.example.hero.adapter.EmployerStatusAdapterB;
import com.example.hero.adapter.WorkerStatusAdapterA;
import com.example.hero.adapter.WorkerStatusAdapterB;

import java.util.ArrayList;

public class WorkerStatus extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_status);

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
        textView.setText("지원현황");



        RecyclerView recyclerView1 = findViewById(R.id.worker_status_apply_recyclerView);
        //예시데이터
        ArrayList<String> testDataSet1 = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet1.add("아이디" + i);}

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager((Context) this);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        WorkerStatusAdapterA customAdapter1 = new WorkerStatusAdapterA(testDataSet1);
        recyclerView1.setAdapter(customAdapter1);



        RecyclerView recyclerView2 = findViewById(R.id.worker_status_approval_recyclerView);
        //예시데이터
        ArrayList<String> testDataSet2 = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet2.add("아이디" + i);}

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager((Context) this);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        WorkerStatusAdapterB customAdapter2 = new WorkerStatusAdapterB(testDataSet2);
        recyclerView2.setAdapter(customAdapter2);



        RecyclerView recyclerView3 = findViewById(R.id.worker_status_defer_recyclerView);
        //예시데이터
        ArrayList<String> testDataSet3 = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet3.add("아이디" + i);}

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager((Context) this);
        recyclerView3.setLayoutManager(linearLayoutManager3);

        WorkerStatusAdapterB customAdapter3 = new WorkerStatusAdapterB(testDataSet2);
        recyclerView3.setAdapter(customAdapter3);


        RecyclerView recyclerView4 = findViewById(R.id.worker_status_past_recyclerView);
        //예시데이터
        ArrayList<String> testDataSet4 = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet4.add("아이디" + i);}

        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager((Context) this);
        recyclerView4.setLayoutManager(linearLayoutManager4);

        WorkerStatusAdapterB customAdapter4 = new WorkerStatusAdapterB(testDataSet2);
        recyclerView4.setAdapter(customAdapter4);

    }
}
