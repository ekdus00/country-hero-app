package com.example.hero.clip.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hero.R;
import com.example.hero.clip.adapter.ClipAdapter;
import com.example.hero.clip.dto.ClipDTO;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClipActivity extends AppCompatActivity {

    // 뒤로가기 버튼
    LinearLayout backBtn;

    ClipAdapter clipAdapter;
    RecyclerView recyclerView;

    private final TokenManager tokenManager = new TokenManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ClipAdapter.OffsetItemDecoration itemDecoration = new ClipAdapter.OffsetItemDecoration(20);
        recyclerView.addItemDecoration(itemDecoration);

        // 뒤로가기 버튼을 클릭했을때 이벤트 등록
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loadClipList();
    }

    public void loadClipList() {
        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
        Call<List<ClipDTO>> call = apiService.getClipList();
        call.enqueue(new Callback<List<ClipDTO>>() {
            @Override
            public void onResponse(Call<List<ClipDTO>> call, Response<List<ClipDTO>> response) {
                if (response.isSuccessful()) {
                    List<ClipDTO> clipList = response.body();
                    assert clipList != null;
                    clipAdapter = new ClipAdapter(clipList);
                    recyclerView.setAdapter(clipAdapter);
                    clipAdapter.notifyDataSetChanged();

                    Log.d("SCRAP", clipList.toString());
                } else {
                    Log.e("API_CALL", "Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ClipDTO>> call, Throwable t) {
                Log.e("SCRAP", "스크랩 에러 발생", t);
            }
        });
    }
}