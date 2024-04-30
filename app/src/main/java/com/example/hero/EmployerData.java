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

import com.example.hero.adapter.EmployerDataAdapter;

import java.util.ArrayList;

public class EmployerData extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_data);

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
        textView.setText("(구인자 이름)");

        RecyclerView recyclerView = findViewById(R.id.employer_comment_recyclerView);

        //예시데이터
        ArrayList<String> testDataSet = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet.add("아이디" + i);}

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
        recyclerView.setLayoutManager(linearLayoutManager);

        EmployerDataAdapter customAdapter = new EmployerDataAdapter(testDataSet);
        recyclerView.setAdapter(customAdapter);

    }
}
