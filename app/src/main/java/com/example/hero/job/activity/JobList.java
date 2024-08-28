package com.example.hero.job.activity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.etc.ApiService;
import com.example.hero.R;
import com.example.hero.etc.OnButtonClickListener;
import com.example.hero.etc.OnItemClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.home.activity.HomeOwner;
import com.example.hero.home.activity.HomeWorker;
import com.example.hero.job.adapter.JobListAdapter;

import com.example.hero.job.dto.JobFilterDTO;
import com.example.hero.job.dto.JobInfoDTO;
import com.example.hero.job.dto.ParticipateRequestDTO;
import com.example.hero.login.activity.Login;
import com.example.hero.matching.activity.MatchingList;
import com.example.hero.mypage.activity.MyPageOwner;
import com.example.hero.mypage.activity.MyPageWorker;
import com.example.hero.worker.activity.WorkerStatus;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.util.FusedLocationSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;


public class JobList extends AppCompatActivity {
    RecyclerView recyclerView;
    JobListAdapter adapter;
    private List<JobInfoDTO> jobList = new ArrayList<>();
    private ApiService apiService;
    private OnItemClickListener itemClickListener;
    private OnButtonClickListener buttonClickListener;
    public TextView job_list_keyword, job_list_sum;
    public Spinner job_list_range;
    private double userLatitude = 0.0;
    private double userLongitude = 0.0;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Context context;
    private TokenManager tokenManager;
    private UserManager userManager;
    private String sortType;
    private int jobId;
    private FusedLocationSource locationSource;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    double longitude; // 위도
    double latitude; // 경도
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    String provider;
    private LocationManager locationManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_list);
        context = this;
        tokenManager = new TokenManager(this);
        userManager = new UserManager(this);

        locationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        GetUserLocation();

        job_list_keyword = findViewById(R.id.job_list_keyword);
        job_list_sum = findViewById(R.id.job_list_sum);

        processIntentData();

        itemClickListener = jobId -> {
            Intent intent = new Intent(JobList.this, JobDetail.class);
            intent.putExtra("jobId", jobId);
            startActivity(intent);
        };

        buttonClickListener = jobId -> {
            participateRequest(jobId);
        };

        recyclerView = findViewById(R.id.job_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(JobList.this));
        adapter = new JobListAdapter(jobList, itemClickListener, buttonClickListener);
        recyclerView.setAdapter(adapter);

        //검색어 입력
        ImageButton search_btn = findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processIntentData();
            }
        });

        //검색 취소
        ImageButton search_cancel = findViewById(R.id.search_cancel);
        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job_list_keyword.setText("");
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    if (userManager.getUserType().equals("owner")) {
                        startActivity(new Intent(JobList.this, HomeOwner.class));
                        finish();
                        return true;
                    } else {
                        startActivity(new Intent(JobList.this, HomeWorker.class));
                        finish();
                        return true;
                    }

                } else if (id == R.id.nav_search) {
                    startActivity(new Intent(JobList.this, JobList.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_matching) {
                    startActivity(new Intent(JobList.this, MatchingList.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_mypage) {
                    if (userManager.getUserType().equals("owner")) {
                        startActivity(new Intent(JobList.this, MyPageOwner.class));
                        finish();
                        return true;
                    } else {
                        startActivity(new Intent(JobList.this, MyPageWorker.class));
                        finish();
                        return true;
                    }
                }
                return false;
            }
        });

    }//onCreate()

    public void jobListRequest(JobFilterDTO jobFilterDTO) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고목록 서버요청
        Call<List<JobInfoDTO>> call = apiService.JobListSend(jobFilterDTO);
        call.enqueue(new Callback<List<JobInfoDTO>>() {
            @Override
            public void onResponse(Call<List<JobInfoDTO>> call, Response<List<JobInfoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    jobList.clear();  // 기존 목록을 지우고
                    jobList.addAll(response.body());  // 새 데이터를 추가
                    adapter.notifyDataSetChanged();

                    int itemCount = adapter.getItemCount();
                    job_list_sum.setText("총 " + itemCount + "개");

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

        ArrayList<String> area = intent.getStringArrayListExtra("area");
        ArrayList<String> crop = intent.getStringArrayListExtra("crop");
        Integer payGoe = getIntegerFromIntent(intent, "payLoe");
        Integer payLoe = getIntegerFromIntent(intent, "payGoe");
        LocalDate startWorkDate = getDateFromIntent(intent, "startWorkDate");
        LocalDate endWorkDate = getDateFromIntent(intent, "endWorkDate");

        String startWorkDateStr = formatDate(startWorkDate);
        String endWorkDateStr = formatDate(endWorkDate);

        String keyWord = job_list_keyword.getText().toString();

        if (job_list_range != null) {
            String selectedItem = job_list_range.getSelectedItem().toString();
            switch (selectedItem) {
                case "최신순":
                    sortType = "latest";
                    break;
                case "마감임박순":
                    sortType = "close";
                    break;
                case "거리순":
                    sortType = "distance";
                    break;
                default:
                    break;
            }
        } else {
            sortType = "latest";
        }

        JobFilterDTO jobFilterDTO = new JobFilterDTO();

        jobFilterDTO.setArea(area);
        jobFilterDTO.setCrop(crop);
        jobFilterDTO.setStartWorkDate(startWorkDateStr);
        jobFilterDTO.setEndWorkDate(endWorkDateStr);
        jobFilterDTO.setPayLoe(payLoe);
        jobFilterDTO.setPayGoe(payGoe);
        jobFilterDTO.setKeyWord(keyWord);
        jobFilterDTO.setSortType(sortType);
        jobFilterDTO.setUserLatitude(longitude);
        jobFilterDTO.setUserLongitude(latitude);

        Log.d("JobFilterDTO", "Filter Data: " + jobFilterDTO.toString());

        jobListRequest(jobFilterDTO);
    }

    @SuppressLint("MissingPermission")
    private void GetUserLocation() {
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled="+ isGPSEnabled);
        Log.d("Main", "isNetworkEnabled="+ isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // 현재 위치의 위도 경도 받아오기
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Log.d(TAG, "onLocationChanged: latitude =" + latitude);
                Log.d(TAG, "onLocationChanged: longitude =" + longitude);

            }
        };
        // 위치 업데이트 요청
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,locationListener);

        // 마지막으로 알려진 위치 가져오기
        provider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);

        if(lastKnownLocation != null) {
            latitude = lastKnownLocation.getLatitude();
            longitude = lastKnownLocation.getLongitude();
            Log.d(TAG, "GetUserLoction: lastKnownLocation != null");
            Log.d(TAG, "GetUserLoction: latitude =" + latitude);
            Log.d(TAG, "GetUserLoction: longitude =" + longitude);
        }
    }

    private void participateRequest(int jobId) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        ParticipateRequestDTO dto = new ParticipateRequestDTO();
        dto.setJobId(jobId);

        //공고지원 서버요청
        Call<Void> call = apiService.JobDetailParticipate(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(JobList.this, WorkerStatus.class);
                    startActivity(intent);
                    finish();

                } else {
                    Log.e("api", "지원하기 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "지원하기 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("api", "지원하기 서버요청 오류", t);
            }
        });
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

    private String formatDate(LocalDate date) {
        if (date == null) {
            return null;  // 또는 기본값으로 적절한 문자열을 반환할 수 있습니다.
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

}