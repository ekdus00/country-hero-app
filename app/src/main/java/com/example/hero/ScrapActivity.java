package com.example.hero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrapActivity extends AppCompatActivity {

    // 뒤로가기 버튼
    LinearLayout backBtn;
    ClipAdapter clipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);

        // 뒤로가기 버튼을 클릭했을때 이벤트 등록
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadScrapList();
    }

    private void loadScrapList() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<Map<String, Response>> call = apiService.getScrapList();
        call.enqueue(new Callback<Map<String, Response>>() {
            @Override
            public void onResponse(Call<Map<String, Response>> call, Response<Map<String, Response>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ClipDTO> list = new ArrayList<ClipDTO>();
                    for(ClipDTO data: (ClipDTO[])response.body().values().toArray()) {
                        list.add(data);
                    }
                    clipAdapter = new ClipAdapter(list, getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Response>> call, Throwable t) {
                onBackPressed();
            }
        });
    }
}