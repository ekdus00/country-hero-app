package com.example.hero.matching.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.OnItemClickListenerMatching;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.Token;
import com.example.hero.etc.TokenManager;
import com.example.hero.matching.adapter.MatchingRecommendAdapter;
import com.example.hero.matching.adapter.MatchingRecommendListAdapter;
import com.example.hero.matching.adapter.MentorMenteeAdapter;
import com.example.hero.matching.dto.MatchingListInfoDTO;
import com.example.hero.matching.dto.MentorRecommendationResponseDTO;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchingList extends AppCompatActivity {

    private OnItemClickListenerMatching itemClickListener;
    private String selectId;

    MentorMenteeAdapter mentorMenteeAdapter;
    RecyclerView recyclerViewMentorMentee;

    List<MatchingListInfoDTO> matchingList;
    List<MatchingListInfoDTO> tempMatchingList;
    List<MatchingListInfoDTO> matchingMentorList;
    List<MatchingListInfoDTO> matchingMenteeList;

    TextView txt_count;

    SearchView searchView;

    Button goBoardWriteBtn;

    private final TokenManager tokenManager = new TokenManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_list);

        itemClickListener = userId -> {
            selectId = userId;
            matchingRecomList(userId);
        };

        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
        Call<List<MatchingListInfoDTO>> call = apiService.getMatchingList();

        recyclerViewMentorMentee = findViewById(R.id.recyclerViewMentorMentee);
        TabLayout layout_tab = findViewById(R.id.layout_tab);
        txt_count = findViewById(R.id.txt_count);

        call.enqueue(new Callback<List<MatchingListInfoDTO>>() {
            @Override
            public void onResponse(Call<List<MatchingListInfoDTO>> call, Response<List<MatchingListInfoDTO>> response) {
                if (response.isSuccessful()) {
                    matchingList = response.body();
                    assert matchingList != null;
                    tempMatchingList = new ArrayList<>(matchingList);
                    //처음에는 멘토에 탭이 선택되어있기 때문에 멘토만 모아서 세팅
                    matchingList = tempMatchingList.stream()
                            .filter(t -> t.getWriterType().equals("mentor"))
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
                Intent intent = new Intent(MatchingList.this, MatchingPost.class);
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
                            .filter(t -> t.getWriterType().equals("mentor") && t.getMatchingName().contains(s))
                            .collect(Collectors.toList());
                    if (matchingMentorList.size() == 0) {
                        matchingList.clear();
                        mentorMenteeAdapter.notifyDataSetChanged();
                    } else {
                        matchingList.clear();
                        matchingList.addAll(matchingMentorList);
                        mentorMenteeAdapter.notifyDataSetChanged();
                    }

                    txt_count.setText(matchingList.size() + "개");
                } else if (tabCount == 1) {
                    matchingMenteeList = tempMatchingList.stream()
                            .filter(t -> t.getWriterType().equals("mentee") && t.getMatchingName().contains(s))
                            .collect(Collectors.toList());
                    if (matchingMenteeList.size() == 0) {
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
                                .filter(t -> t.getWriterType().equals("mentor"))
                                .collect(Collectors.toList());
                        if (matchingMentorList.size() == 0) {
                            matchingList.clear();
                            mentorMenteeAdapter.notifyDataSetChanged();
                            break;
                        }

                        matchingList.clear();
                        matchingList.addAll(matchingMentorList);
                        mentorMenteeAdapter.notifyDataSetChanged();

                        txt_count.setText(matchingList.size() + "개");
                        break;
                    }
                    case 1: {
                        matchingMenteeList = tempMatchingList.stream()
                                .filter(t -> t.getWriterType().equals("mentee"))
                                .collect(Collectors.toList());
                        if (matchingMenteeList.size() == 0) {
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
                        break;
                    }
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        matchingRecom();
    }

    public void matchingRecom() {
        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //멘토추천 서버요청
        Call<List<MentorRecommendationResponseDTO>> call = apiService.getMatchingRecom();
        call.enqueue(new Callback<List<MentorRecommendationResponseDTO>>() {
            @Override
            public void onResponse(Call<List<MentorRecommendationResponseDTO>> call, Response<List<MentorRecommendationResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<MentorRecommendationResponseDTO> slides = response.body();

                    ViewPager2 viewPager = findViewById(R.id.pager);
                    MatchingRecommendAdapter adapter = new MatchingRecommendAdapter(slides, itemClickListener);
                    viewPager.setAdapter(adapter);

                    Log.e("tag", "멘토추천 서버요청 성공");

                } else {
                    Log.e("tag", "멘토추천 서버응답 실패" + response.code() + ", " + response.message());
                    Log.e("tag", "멘토추천 서버응답 실패" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<MentorRecommendationResponseDTO>> call, Throwable t) {
                Log.e("tag", "멘토추천 서버요청 실패", t);
            }
        });
    }

    public void matchingRecomList(String selectId) {
        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //멘토추천 리스트 서버요청
        Call<List<MatchingListInfoDTO>> call = apiService.getMatchingRecomList(selectId);
        call.enqueue(new Callback<List<MatchingListInfoDTO>>() {
            @Override
            public void onResponse(Call<List<MatchingListInfoDTO>> call, Response<List<MatchingListInfoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<MatchingListInfoDTO> slides = response.body();

                    ViewPager2 viewPager = findViewById(R.id.pager2);
                    MatchingRecommendListAdapter adapter = new MatchingRecommendListAdapter(slides);
                    viewPager.setAdapter(adapter);

                    Log.e("tag", "멘토추천 리스트 서버요청 성공");

                } else {
                    Log.e("tag", "멘토추천 리스트 서버응답 실패" + response.code() + ", " + response.message());
                    Log.e("tag", "멘토추천 리스트 서버응답 실패" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<MatchingListInfoDTO>> call, Throwable t) {
                Log.e("tag", "멘토추천 리스트 서버요청 실패", t);
            }
        });
    }






}