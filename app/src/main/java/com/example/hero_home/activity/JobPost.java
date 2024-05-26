package com.example.hero_home.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hero_home.R;

public class JobPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post);
    }

//    public void loadCountries() {
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//
//        Call<List<String>> call = apiService.getCountries();
//        call.enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if (response.isSuccessful()) {
//                    List<String> countries = response.body();
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobPost.this,
//                            android.R.layout.simple_spinner_dropdown_item, countries);
//                    spinnerProvince.setAdapter(adapter);
//                } else {
//                    Log.e("API_CALL", "Response error: " + response.errorBody());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//                Log.e("JobPost", "시/도 에러 발생", t);
//            }
//        });
//    }
}