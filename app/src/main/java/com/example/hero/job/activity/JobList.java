package com.example.hero.job.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.etc.ApiService;
import com.example.hero.R;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.home.activity.HomeApplicant;
import com.example.hero.home.adapter.JobInfoHomeAdapter;
import com.example.hero.home.adapter.ParticipateInfoHomeAdapter;
import com.example.hero.home.dto.JobInfoHomeDTO;
import com.example.hero.home.dto.WorkerHomeDTO;
import com.example.hero.job.adapter.JobListAdapter;

import com.example.hero.job.dto.JobFilterDTO;
import com.example.hero.job.dto.JobInfoDTO;
import com.example.hero.worker.activity.WorkerStatus;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.widget.Toast;


public class JobList extends AppCompatActivity implements OnItemClickListener {
    RecyclerView recyclerView;
    JobListAdapter adapter;
    private List<JobInfoDTO> jobList = new ArrayList<>();
    private ApiService apiService;
    private OnItemClickListener itemClickListener;
    public TextView job_list_keyword, job_list_sum;
    public Spinner job_list_range;
    private FusedLocationProviderClient locationClient;
    private double userLatitude = 0.0;
    private double userLongitude = 0.0;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Context context;
    private TokenManager tokenManager;
    private String sortType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_list);
        context = this;
        itemClickListener = this;
        tokenManager = new TokenManager(this);

        recyclerView = findViewById(R.id.job_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(JobList.this));
        adapter = new JobListAdapter(jobList, itemClickListener);
        recyclerView.setAdapter(adapter);

        itemClickListener = jobId -> {
            Intent intent = new Intent(JobList.this, JobDetail.class);
            intent.putExtra("jobId", jobId);
            startActivity(intent);
        };

        job_list_keyword = findViewById(R.id.job_list_keyword);
        job_list_sum = findViewById(R.id.job_list_sum);

        //사용자 위도, 경도
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        processIntentData();

        //검색어 입력
        Button search_btn = findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processIntentData();
            }
        });

        //정렬검색
        job_list_range = findViewById(R.id.job_list_range);
        job_list_range.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                processIntentData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때
            }
        });

        //상세조건
        Button search_detail = findViewById(R.id.search_detail);
        search_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobFilter.class);
                startActivity(intent);
            }
        });

    }

    public void jobListRequest(JobFilterDTO jobFilterDTO) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고목록 서버요청
        Call<List<JobInfoDTO>> call = apiService.JobListSend(jobFilterDTO);
        call.enqueue(new Callback<List<JobInfoDTO>>() {
            @Override
            public void onResponse(Call<List<JobInfoDTO>> call, Response<List<JobInfoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    jobList.addAll(response.body());  // 새 데이터를 추가
                    adapter.notifyDataSetChanged();

                    int itemCount = adapter.getItemCount();
                    job_list_sum.setText(itemCount + "개");
                    Log.e("JobList", "공고목록 서버요청 성공");

                } else {
                    Log.e("JobList", "공고목록 서버응답 실패" + response.code() + ", " + response.message());
                    Log.e("JobList", "공고목록 서버응답 실패" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<JobInfoDTO>> call, Throwable t) {
                Log.e("JobList", "공고목록 서버요청 실패", t);
            }
        });
    }

    private void processIntentData() {
        Intent intent = getIntent();
//        if (intent != null) {
//            String userId = intent.getStringExtra("userId");
//            String userType = intent.getStringExtra("userType");
            ArrayList<String> area = intent.getStringArrayListExtra("area");
            ArrayList<String> crop = intent.getStringArrayListExtra("crop");
            Integer payLoe = getIntegerFromIntent(intent, "payLoe");
            Integer payGoe = getIntegerFromIntent(intent, "payGoe");
            LocalDate startWorkDate = getDateFromIntent(intent, "startWorkDate");
            LocalDate endWorkDate = getDateFromIntent(intent, "endWorkDate");

            String keyWord = job_list_keyword.getText().toString();
//            String sortType = job_list_range.getSelectedItem().toString();

            if (job_list_range != null) {
                sortType = job_list_range.getSelectedItem().toString();
                // selectedItem을 처리하는 로직
            } else {
                sortType = "등록일순";
            }

            JobFilterDTO jobFilterDTO = new JobFilterDTO();

//            jobFilterDTO.setUserId(userId);
//            jobFilterDTO.setUserType(userType);
            jobFilterDTO.setArea(area);
            jobFilterDTO.setCrop(crop);
            jobFilterDTO.setStartWorkDate(startWorkDate);
            jobFilterDTO.setEndWorkDate(endWorkDate);
            jobFilterDTO.setPayLoe(payLoe);
            jobFilterDTO.setPayGoe(payGoe);

            jobFilterDTO.setKeyWord(keyWord);
            jobFilterDTO.setSortType(sortType);
            jobFilterDTO.setUserLatitude(userLatitude);
            jobFilterDTO.setUserLongitude(userLongitude);

            jobListRequest(jobFilterDTO);

//            Gson gson = new Gson();
//            String json = gson.toJson(jobFilterDTO);
//            RequestBody requestBody = RequestBody.create(json, okhttp3.MediaType.parse("application/json; charset=utf-8"));
//            JobListSend(requestBody);
//        }
    }

    private Integer getIntegerFromIntent(Intent intent, String key) {
        String valueStr = intent.getStringExtra(key);
        if (valueStr != null && !valueStr.isEmpty()) {
            try {
                return Integer.parseInt(valueStr);
            } catch (NumberFormatException e) {
                Log.e("JobList", "Failed to parse integer value: " + valueStr, e);
            }
        }
        return null;
    }

    private LocalDate getDateFromIntent(Intent intent, String key) {
        String dateStr = intent.getStringExtra(key);
        if (dateStr != null) {
            try {
                return LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                Log.e("JobList", "Error parsing date", e);
            }
        }
        return null;
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        locationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                userLatitude = location.getLatitude();
                userLongitude = location.getLongitude();
            }
        });
    }

    @Override
    public void onItemClick(int jobId) {
        Toast.makeText(this, "Job ID: " + jobId + " clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(JobList.this, JobDetail.class);
        intent.putExtra("jobId", jobId);
        startActivity(intent);
    }



}