package com.example.hero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.adapter.JobCommentAdapter;
import com.example.hero.adapter.JobListAdapter;
import com.example.hero.dto.JobDetailRequestDTO;
import com.example.hero.dto.JobPostCommentResponseDTO;
import com.example.hero.dto.JobPostEditResponseDTO;
import com.google.gson.Gson;
import com.naver.maps.map.OnMapReadyCallback;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.geometry.LatLng;

public class JobDetail extends AppCompatActivity implements OnMapReadyCallback {
    private ApiService ApiService;
    public TextView job_detail_title;
    public TextView job_detail_day;
    public TextView job_detail_salary;
    public TextView job_detail_address_country;
    public TextView job_detail_address_city;
    public TextView job_detail_workDate_start;
    public TextView job_detail_workDate_end;
    public TextView job_detail_workDay;
    public TextView job_detail_workTime_start;
    public TextView job_detail_workTime_end;
    public TextView job_detail_crop;
    public TextView job_detail_recruitDate_start;
    public TextView job_detail_recruitDate_end;
    public TextView job_detail_recruitNumber;
    public TextView job_detail_age;
    public TextView job_detail_recruiterName;
    public TextView job_detail_addressDetail;
    public TextView job_detail_preference;
    public TextView job_detail_content;
    private NaverMap naverMap;
    JobCommentAdapter adapter;
    public EditText job_comment_content;
    public Button job_comment_send;
    public Button job_comment_editBtn;
    public Button job_comment_deleteBtn;



    public RecyclerView home_job_comment_recyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail);

        //공고상세정보
        job_detail_title = findViewById(R.id.job_detail_title);
        job_detail_day = findViewById(R.id.job_detail_day);
        job_detail_salary = findViewById(R.id.job_detail_salary);
        job_detail_address_country = findViewById(R.id.job_detail_address_country);
        job_detail_address_city = findViewById(R.id.job_detail_address_city);
        job_detail_workDate_start = findViewById(R.id.job_detail_workDate_start);
        job_detail_workDate_end = findViewById(R.id.job_detail_workDate_end);
        job_detail_workDay = findViewById(R.id.job_detail_workDay);
        job_detail_workTime_start = findViewById(R.id.job_detail_workTime_start);
        job_detail_workTime_end = findViewById(R.id.job_detail_workTime_end);
        job_detail_crop = findViewById(R.id.job_detail_crop);
        job_detail_recruitDate_start = findViewById(R.id.job_detail_recruitDate_start);
        job_detail_recruitDate_end = findViewById(R.id.job_detail_recruitDate_end);
        job_detail_recruitNumber = findViewById(R.id.job_detail_recruitNumber);
        job_detail_age = findViewById(R.id.job_detail_age);
        job_detail_recruiterName = findViewById(R.id.job_detail_recruiterName);
        job_detail_addressDetail = findViewById(R.id.job_detail_addressDetail);
        job_detail_preference = findViewById(R.id.job_detail_preference);
        job_detail_content = findViewById(R.id.job_detail_content);

        //질문하기
        job_comment_content = findViewById(R.id.job_comment_content);
        job_comment_send = findViewById(R.id.job_comment_send);

        job_comment_editBtn = findViewById(R.id.job_comment_editBtn);
        job_comment_deleteBtn = findViewById(R.id.job_comment_deleteBtn);

        home_job_comment_recyclerView = findViewById(R.id.home_job_comment_recyclerView);
        home_job_comment_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        
        //질문하기
        //구인자정보 리스너


        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button job_detail_employer_btn = findViewById(R.id.job_detail_employer_btn);
        job_detail_employer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployerData.class);
                startActivity(intent);
            }
        });

        int jobId = getIntent().getIntExtra("jobId", 0);

        JobDetailRequestDTO jobDetailDTO = new JobDetailRequestDTO();

        jobDetailDTO.setJobId(jobId);
        jobDetailDTO.setUserId("keh223377");
        jobDetailDTO.setUserType("구인자");

        Gson gson = new Gson();
        String json = gson.toJson(jobDetailDTO);
        RequestBody requestBody = RequestBody.create(json, okhttp3.MediaType.parse("application/json; charset=utf-8"));

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        //공고상세정보 요청
        Call<JobPostEditResponseDTO> call = apiService.getJobDetail(requestBody);
        call.enqueue(new Callback<JobPostEditResponseDTO>() {
            @Override
            public void onResponse(Call<JobPostEditResponseDTO> call, Response<JobPostEditResponseDTO> response) {
                if (response.isSuccessful()) {
                    JobPostEditResponseDTO jobDetails = response.body();
                    // 화면에 job 상세 정보 표시
                    job_detail_title.setText(jobDetails.getJobName());
//                    job_detail_day.setText(jobDetails.getJobDescription());
                    job_detail_salary.setText(jobDetails.getPay());
                    job_detail_address_country.setText(jobDetails.getCountry());
                    job_detail_address_city.setText(jobDetails.getCity());


                    LocalDate date1 = jobDetails.getStartWorkDate();
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate1 = date1.format(formatter1);
                    job_detail_workDate_start.setText(formattedDate1);

                    LocalDate date2 = jobDetails.getEndWorkDate();
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate2 = date2.format(formatter2);
                    job_detail_workDate_end.setText(formattedDate2);

                    job_detail_workDay.setText(jobDetails.getWorkDay());

                    LocalTime time1 = jobDetails.getStartWorkTime();
                    DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate5 = time1.format(formatter5);
                    job_detail_workTime_start.setText(formattedDate5);

                    LocalTime time2 = jobDetails.getStartWorkTime();
                    DateTimeFormatter formatter6 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate6 = time2.format(formatter6);
                    job_detail_workTime_end.setText(formattedDate6);

                    job_detail_crop.setText(jobDetails.getCropType());

                    LocalDate date3 = jobDetails.getStartRecruitDate();
                    DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate3 = date3.format(formatter3);
                    job_detail_recruitDate_start.setText(formattedDate3);

                    LocalDate date4 = jobDetails.getEndRecruitDate();
                    DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate4 = date4.format(formatter4);
                    job_detail_recruitDate_end.setText(formattedDate4);

                    job_detail_recruitNumber.setText(jobDetails.getRecruitCount());

                    job_detail_age.setText(jobDetails.getAge());
                    job_detail_recruiterName.setText(jobDetails.getUserName());
                    job_detail_addressDetail.setText(jobDetails.getWorkAddress());
                    job_detail_preference.setText(jobDetails.getSpec());
                    job_detail_content.setText(jobDetails.getJobIntro());

                    //네이버지도 위도, 경도
//                    if (naverMap != null && jobDetails != null) {
//                        LatLng position = new LatLng(jobDetails.getLatitude(), jobDetails.getLongitude());
//                        naverMap.moveCamera(CameraUpdate.scrollTo(position));
//                    }

                } else {
                    // 오류 처리
                }
            }

            @Override
            public void onFailure(Call<JobPostEditResponseDTO> call, Throwable t) {
                // 네트워크 오류 처리
            }
        });

        //공고댓글목록 요청
        Call<List<JobPostCommentResponseDTO>> call2 = apiService.getJobPostCommentList(jobId);
        call2.enqueue(new Callback<List<JobPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<JobPostCommentResponseDTO>> call, Response<List<JobPostCommentResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<JobPostCommentResponseDTO> comments = response.body();
                    adapter = new JobCommentAdapter(comments);
                    home_job_comment_recyclerView.setAdapter(adapter);
                } else {
                    // 요청 실패 처리
                }
            }

            @Override
            public void onFailure(Call<List<JobPostCommentResponseDTO>> call, Throwable t) {
                // 네트워크 에러 등의 오류 처리
            }
        });

        job_comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployerData.class);
                startActivity(intent);
            }
        });


        job_detail_employer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployerData.class);
                startActivity(intent);
            }
        });


        job_detail_employer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployerData.class);
                startActivity(intent);
            }
        });


        job_detail_employer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployerData.class);
                startActivity(intent);
            }
        });


    } //oncreate()

    public void onMapReady(NaverMap naverMap) {
        this.naverMap = naverMap;
        // 위치 예시: 서울 시청
        LatLng location = new LatLng(37.5670135, 126.9783740);
        naverMap.moveCamera(CameraUpdate.scrollTo(location));
    }








}
