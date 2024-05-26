package com.example.hero_home.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero_home.R;
import com.example.hero_home.adapter.ClipAdapter;
import com.example.hero_home.model.ClipDTO;
import com.example.hero_home.util.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrapActivity extends AppCompatActivity {

    // 뒤로가기 버튼
    LinearLayout backBtn;

    ClipAdapter clipAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);

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
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        Log.d("SCRAP", apiService.toString());
        Call<List<ClipDTO>> call = apiService.getClipList(Token.token);
//        Log.d("SCRAP", call.toString());
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