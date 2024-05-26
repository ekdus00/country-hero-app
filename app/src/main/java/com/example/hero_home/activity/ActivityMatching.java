package com.example.hero_home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero_home.R;
import com.example.hero_home.adapter.MentorMenteeAdapter;
import com.example.hero_home.model.MatchingListInfoDTO;
import com.example.hero_home.util.Token;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMatching extends AppCompatActivity {

    MentorMenteeAdapter mentorMenteeAdapter;
    RecyclerView recyclerViewMentorMentee;

    List<MatchingListInfoDTO> matchingList;
    List<MatchingListInfoDTO> tempMatchingList;
    List<MatchingListInfoDTO> matchingMentorList;
    List<MatchingListInfoDTO> matchingMenteeList;

    TextView txt_count;

    SearchView searchView;

    Button goBoardWriteBtn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<MatchingListInfoDTO>> call = apiService.getMatchingList(Token.token);

        recyclerViewMentorMentee = findViewById(R.id.recyclerViewMentorMentee);
        TabLayout layout_tab = findViewById(R.id.layout_tab);
        txt_count = findViewById(R.id.txt_count);

//        Log.d("SCRAP", call.toString());
        call.enqueue(new Callback<List<MatchingListInfoDTO>>() {
            @Override
            public void onResponse(Call<List<MatchingListInfoDTO>> call, Response<List<MatchingListInfoDTO>> response) {
                if (response.isSuccessful()) {
                    matchingList = response.body();
                    assert matchingList != null;
                    tempMatchingList = new ArrayList<>(matchingList);
                    //처음에는 멘토에 탭이 선택되어있기 때문에 멘토만 모아서 세팅
                    matchingList = tempMatchingList.stream()
                            .filter(t -> t.getWriterType().equals("mentor") )
                            .collect(Collectors.toList());
                    mentorMenteeAdapter = new MentorMenteeAdapter(matchingList);
                    recyclerViewMentorMentee.setAdapter(mentorMenteeAdapter);
                    mentorMenteeAdapter.notifyDataSetChanged();
                    //리스트 갯수 표시
                    txt_count.setText(matchingList.size() + "개");
                    Log.d("MATCHING", matchingList.toString());
                } else {
                    Log.e("API_CALL", "Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<MatchingListInfoDTO>> call, Throwable t) {
                Log.e("MATCHING", "에러 발생", t);
            }
        });

        goBoardWriteBtn = findViewById(R.id.go_board_write_btn);
        goBoardWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMatching.this, ActivityBoardWrite.class);
                startActivity(intent);
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                int tabCount = layout_tab.getSelectedTabPosition();
                if (tabCount == 0) {
                    matchingMentorList = tempMatchingList.stream()
                            .filter(t -> t.getWriterType().equals("mentor") && t.getMatchingName().contains(s) )
                            .collect(Collectors.toList());
                    if(matchingMentorList.size() == 0) {
                        matchingList.clear();
                        mentorMenteeAdapter.notifyDataSetChanged();
                    } else {
                        matchingList.clear();
                        matchingList.addAll(matchingMentorList);
                        mentorMenteeAdapter.notifyDataSetChanged();
                    }

                    txt_count.setText(matchingList.size() + "개");
                } else if (tabCount == 1){
                    matchingMenteeList = tempMatchingList.stream()
                            .filter(t -> t.getWriterType().equals("mentee") && t.getMatchingName().contains(s) )
                            .collect(Collectors.toList());
                    if(matchingMenteeList.size() == 0) {
                        matchingList.clear();
                        mentorMenteeAdapter.notifyDataSetChanged();
                    } else {
                        matchingList.clear();
                        matchingList.addAll(matchingMenteeList);
                        mentorMenteeAdapter.notifyDataSetChanged();
                    }

                    txt_count.setText(matchingList.size() + "개");
                }
                return true;
            }
        });

        //리싸이클러뷰에 어댑터 설정
        recyclerViewMentorMentee.setAdapter(mentorMenteeAdapter);
        recyclerViewMentorMentee.setLayoutManager(new LinearLayoutManager(this));

        //멘토, 멘티 누를때 이벤트
        layout_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { //탭을 선택했을 때의 이벤트
                switch (tab.getPosition()) {
                    case 0: {
                        matchingMentorList = tempMatchingList.stream()
                                .filter(t -> t.getWriterType().equals("mentor") )
                                .collect(Collectors.toList());
                        if(matchingMentorList.size() == 0) {
                            matchingList.clear();
                            mentorMenteeAdapter.notifyDataSetChanged();
                            break;
                        }

                        matchingList.clear();
                        matchingList.addAll(matchingMentorList);
                        mentorMenteeAdapter.notifyDataSetChanged();

                        txt_count.setText(matchingList.size() + "개");

//                        list.clear();
                        //멘토 더미데이터 추가
//                        list.add(new MentorMenteeDTO("멘토", "test1", "test1", "test1"));
//                        list.add(new MentorMenteeDTO("멘토", "test2", "test1", "test1"));
//                        list.add(new MentorMenteeDTO("멘토", "test3", "test1", "test1"));
//                        list.add(new MentorMenteeDTO("멘토", "test4", "test1", "test1"));
//                        list.add(new MentorMenteeDTO("멘토", "test5", "test1", "test1"));
                        break;
                    }
                    case 1: {
                        matchingMenteeList = tempMatchingList.stream()
                                .filter(t -> t.getWriterType().equals("mentee") )
                                .collect(Collectors.toList());
                        if(matchingMenteeList.size() == 0) {
                            matchingList.clear();
                            Log.d("MATCHING", "tempMatchingList: " + tempMatchingList.size());
                            Log.d("MATCHING", "matchingList: " + matchingList.size());
                            mentorMenteeAdapter.notifyDataSetChanged();
                            break;
                        }
                        matchingList.clear();
                        matchingList.addAll(matchingMenteeList);
                        mentorMenteeAdapter.notifyDataSetChanged();

                        txt_count.setText(matchingList.size() + "개");
//                        list.clear();
                        //멘티 더미데이터 추가
//                        list.add(new MentorMenteeDTO("멘티", "test1", "test1", "test1"));
//                        list.add(new MentorMenteeDTO("멘티", "test2", "test1", "test1"));
//                        list.add(new MentorMenteeDTO("멘티", "test3", "test1", "test1"));
//                        list.add(new MentorMenteeDTO("멘티", "test4", "test1", "test1"));
//                        list.add(new MentorMenteeDTO("멘티", "test5", "test1", "test1"));
                        break;
                    }
                }
//                //어댑터에 데이터 변경 알림보내기
//                mentorMenteeAdapter.notifyDataSetChanged();
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
