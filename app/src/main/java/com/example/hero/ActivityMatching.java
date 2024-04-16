package com.example.hero;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ActivityMatching extends AppCompatActivity {

    MentorMenteeAdapter mentorMenteeAdapter;

    ArrayList<MentorMenteeData> list;

    TextView txt_count;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        list = new ArrayList<>();
        //멘토 더미데이터 추가
        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));
        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));
        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));
        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));
        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));

        //어댑터 초기화
        mentorMenteeAdapter = new MentorMenteeAdapter(list);

        RecyclerView recyclerViewMentorMentee = findViewById(R.id.recyclerViewMentorMentee);
        TabLayout layout_tab = findViewById(R.id.layout_tab);
        txt_count = findViewById(R.id.txt_count);

        //리싸이클러뷰에 어댑터 설정
        recyclerViewMentorMentee.setAdapter(mentorMenteeAdapter);
        recyclerViewMentorMentee.setLayoutManager(new LinearLayoutManager(this));

        //멘토, 멘티 누를때 이벤트
        layout_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { //탭을 선택했을 때의 이벤트
                switch (tab.getPosition()) {
                    case 0: {
                        list.clear();
                        //멘토 더미데이터 추가
                        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));
                        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));
                        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));
                        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));
                        list.add(new MentorMenteeData("멘토", "test1", "test1", "test1"));
                        break;
                    }
                    case 1: {
                        list.clear();
                        //멘티 더미데이터 추가
                        list.add(new MentorMenteeData("멘티", "test1", "test1", "test1"));
                        list.add(new MentorMenteeData("멘티", "test1", "test1", "test1"));
                        list.add(new MentorMenteeData("멘티", "test1", "test1", "test1"));
                        list.add(new MentorMenteeData("멘티", "test1", "test1", "test1"));
                        list.add(new MentorMenteeData("멘티", "test1", "test1", "test1"));
                        break;
                    }
                }
                //리스트 갯수 표시
                txt_count.setText(list.size() + "개");
                //어댑터에 데이터 변경 알림보내기
                mentorMenteeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
