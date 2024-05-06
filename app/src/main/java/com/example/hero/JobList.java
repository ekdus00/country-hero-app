package com.example.hero;

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

import com.example.hero.adapter.JobListAdapter;

import com.example.hero.object.JobFilterDTO;
import com.example.hero.object.JobInfoDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Map;
import android.Manifest;


public class JobList extends AppCompatActivity {
    RecyclerView recyclerView;
    JobListAdapter adapter;
    private ApiService ApiService;
    public TextView job_list_keyword;
    public Spinner job_list_range;
    private FusedLocationProviderClient locationClient;
    private double userLatitude = 0.0;
    private double userLongitude = 0.0;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_list);

        Button search_detail = findViewById(R.id.search_detail);
        Button search_btn = findViewById(R.id.search_btn);

        recyclerView = findViewById(R.id.job_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 초기에 빈 어댑터 설정
        adapter = new JobListAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        job_list_keyword = findViewById(R.id.job_list_keyword);
        job_list_range = findViewById(R.id.job_list_range);

        //사용자 위도, 경도
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        processIntentData();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processIntentData();
            }
        });

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

        search_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobFilter.class);
                startActivity(intent);
            }
        });

    }

    public void JobListSend(JobFilterDTO jobFilterDTO) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        Map<String, RequestBody> textData = prepareTextMap(text_workProvince, text_workCity, text_crop1, text_crop2, text_salaryType, text_salaryValue, text_workStart, text_workEnd, text_job_list_keyword, text_job_list_range);

        Call<List<JobInfoDTO>> call = apiService.JobListSend(jobFilterDTO);
        call.enqueue(new Callback<List<JobInfoDTO>>() {
            @Override
            public void onResponse(Call<List<JobInfoDTO>> call, Response<List<JobInfoDTO>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Data uploaded successfully");
                    Log.e("JobList", "공고목록 서버요청 성공");

//                    List<JobInfoDTO> jobs = response.body();
//                    adapter = new JobListAdapter(jobs);
//                    recyclerView.setAdapter(adapter);
                } else {
                    System.out.println("Upload failed: " + response.message());
                    Log.e("JobList", "공고목록 서버응답 실패" + response.code() + ", " + response.message());
                    Log.e("JobList", "공고목록 서버응답 실패" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<JobInfoDTO>> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
                Log.e("JobList", "공고목록 서버요청 실패", t);
            }
        });
    }

    private void processIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            String userId = intent.getStringExtra("userId");
            String userType = intent.getStringExtra("userType");
            ArrayList<String> area = intent.getStringArrayListExtra("area");
            ArrayList<String> crop = intent.getStringArrayListExtra("crop");
            Integer payLoe = getIntegerFromIntent(intent, "payLoe");
            Integer payGoe = getIntegerFromIntent(intent, "payGoe");
            LocalDate startWorkDate = getDateFromIntent(intent, "startWorkDate");
            LocalDate endWorkDate = getDateFromIntent(intent, "endWorkDate");

            String keyWord = job_list_keyword.getText().toString();
            String sortType = job_list_range.getSelectedItem().toString();

            JobFilterDTO jobFilterDTO = new JobFilterDTO();
            jobFilterDTO.setUserId(userId);
            jobFilterDTO.setUserType(userType);
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

            JobListSend(jobFilterDTO);
        }
    }

    private Integer getIntegerFromIntent(Intent intent, String key) {
        String valueStr = intent.getStringExtra(key);
        if (valueStr != null && !valueStr.isEmpty()) {
            try {
                return Integer.parseInt(valueStr);
            } catch (NumberFormatException e) {
                Log.e("MainActivity", "Failed to parse integer value: " + valueStr, e);
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
                Log.e("MainActivity", "Error parsing date", e);
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



//    private Map<String, RequestBody> prepareTextMap(String text_workProvince, String text_workCity, String text_crop1, String text_crop2,
//                                                    String text_salaryType, String text_salaryValue, String text_workStart,
//                                                    String text_workEnd, String text_job_list_keyword, String text_job_list_range) {
//
//        Map<String, RequestBody> data = new HashMap<>();
//
//        data.put("workProvince", createRequestBody(text_workProvince));
//        data.put("workCity", createRequestBody(text_workCity));
//        data.put("crop1", createRequestBody(text_crop1));
//        data.put("crop2", createRequestBody(text_crop2));
//        data.put("salaryType", createRequestBody(text_salaryType));
//        data.put("salaryValue", createRequestBody(text_salaryValue));
//        data.put("workStart", createRequestBody(text_workStart));
//        data.put("workEnd", createRequestBody(text_workEnd));
//        data.put("job_list_keyword", createRequestBody(text_job_list_keyword));
//        data.put("job_list_range", createRequestBody(text_job_list_range));
//
//        return data;
//    }
//
//    public Map<String, String> prepareQueryMap(JobFilterDTO jobFilter) {
//        Map<String, String> queryMap = new HashMap<>();
//
//        if (jobFilter.getWorkProvince() != null) {
//            queryMap.put("workProvince", jobFilter.getWorkProvince());
//        }
//        if (jobFilter.getWorkCity() != null) {
//            queryMap.put("workCity", jobFilter.getWorkCity());
//        }
//        if (jobFilter.getCrop1() != null) {
//            queryMap.put("crop1", jobFilter.getCrop1());
//        }
//        if (jobFilter.getCrop2() != null) {
//            queryMap.put("crop2", jobFilter.getCrop2());
//        }
//        // 추가적인 필드도 이와 같이 처리
//        return queryMap;
//    }


//    private RequestBody createRequestBody(String value) {
//        return value != null ? RequestBody.create(value, MediaType.parse("text/plain")) : RequestBody.create("", MediaType.parse("text/plain"));
//    }


//
//    private void getJobList() {
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//
//        Call<List<JobListDTO>> call = apiService.getJobList();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String startDateString = dateFormat.format(new Date());
//
////        JobListDTO jobListRequest = new JobListDTO(
////                123, // id
////                "토마토 재배", // jobName
////                "경북", // country
////                "경주시", // city
////                "채소", // cropForm
////                "토마토", // cropType
////                dateFormat.format(new Date()),
////                dateFormat.format(new Date()),
////                dateFormat.format(new Date()),
////                dateFormat.format(new Date()),
////                100000 // pay
////        );
//
////        Call<List<JobListDTO>> call = apiService.getJobList(
////                "경주시", // city
////                "경북",   // country
////                "채소",   // cropForm
////                "토마토", // cropType
////                dateFormat.format(new Date()), // endRecruitDate
////                dateFormat.format(new Date()), // endWorkDate
////                123,    // id
////                "토마토 재배", // jobName
////                100000, // pay
////                dateFormat.format(new Date()), // startRecruitDate
////                dateFormat.format(new Date())  // startWorkDate
////        );
//
////        JobListDTO jobListRequest = new JobListDTO();
////        Call<List<JobListDTO>> call = ApiService.getJobList(jobListRequest);
////        Call<List<JobListDTO>> call = ApiService.getJobList(
////                jobListRequest.getCity(),
////                jobListRequest.getCountry(),
////                jobListRequest.getCropForm(),
////                jobListRequest.getCropType(),
////                jobListRequest.getEndRecruitDate(),
////                jobListRequest.getEndWorkDate(),
////                jobListRequest.getId(),
////                jobListRequest.getJobName(),
////                jobListRequest.getPay(),
////                jobListRequest.getStartRecruitDate(),
////                jobListRequest.getStartWorkDate()
////        );
//        call.enqueue(new Callback<List<JobListDTO>>() {
//            @Override
//            public void onResponse(Call<List<JobListDTO>> call, Response<List<JobListDTO>> response) {
//                if (response.isSuccessful()) {
//                    List<JobListDTO> jobs = response.body();
//                    adapter = new JobListAdapter(jobs);
//                    recyclerView.setAdapter(adapter);
//                }
//            }
//            @Override
//            public void onFailure(Call<List<JobListDTO>> call, Throwable t) {
//                Log.e("JobList", "공고목록 서버요청 실패", t);
//            }
//        });
//    }

}