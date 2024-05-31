package com.example.hero.job.activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hero.employer.activity.EmployerStatus;
import com.example.hero.employer.activity.JobEditPost;
import com.example.hero.etc.ApiService;
import com.example.hero.R;
import com.example.hero.etc.OnButtonClickListener;
import com.example.hero.etc.OnCommentClickListener;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.home.activity.HomeOwner;
import com.example.hero.job.adapter.JobCommentAdapter;
import com.example.hero.job.dto.JobInfoDTO;
import com.example.hero.job.dto.JobPostCommentDeleteRequestDTO;
import com.example.hero.job.dto.JobPostCommentRequestDTO;
import com.example.hero.job.dto.JobPostCommentResponseDTO;
import com.example.hero.job.dto.JobDetailResponseDTO;
import com.example.hero.job.dto.JobPostCommentUpdateRequestDTO;
import com.example.hero.job.dto.ParticipateRequestDTO;
import com.naver.maps.map.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.Marker;

public class JobDetail extends AppCompatActivity implements OnMapReadyCallback {
    private ApiService apiService;
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
    public EditText job_comment_content;
    public Button job_comment_send;
    public Button job_comment_editBtn;
    public Button job_comment_deleteBtn;
    public ImageView job_detail_image;
    public RecyclerView job_comment_recyclerView;
    private int jobId;
    private Context context;
    private TokenManager tokenManager;
    private double latitude = 37.5670135; // 초기 위도 값을 0으로 설정
    private double longitude = 126.9783740;
    private RatingBar job_detail_cropLevel;
    JobCommentAdapter adapter;
    private OnCommentClickListener buttonClickListener;
    private List<JobPostCommentResponseDTO> commentList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail);
        context = this;
        tokenManager = new TokenManager(this);

        jobId = getIntent().getIntExtra("jobId", 0);
        jobDetailRequest();
        commentRequest();

        buttonClickListener = (commentId, buttonType) -> {
            switch (buttonType) {
                case CHILD:
                    // MODIFY 버튼 클릭 이벤트 처리
                    Log.d(TAG, "CHILD" + commentId);
                    onChildComment(commentId);
                    break;
                case EDIT:
                    // DEADLINE 버튼 클릭 이벤트 처리
                    Log.d(TAG, "EDIT" + commentId);
                    onEditComment(commentId);
                    break;
                case DELETE:
                    // DEADLINE 버튼 클릭 이벤트 처리
                    Log.d(TAG, "DELETE" + commentId);
                    commentDelete(commentId);
                    break;
            }
        };

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
        job_detail_image = findViewById(R.id.job_detail_image);
        job_detail_cropLevel = findViewById(R.id.job_detail_cropLevel);

        //질문하기
        job_comment_content = findViewById(R.id.job_comment_content);
        job_comment_send = findViewById(R.id.job_comment_send);

        job_comment_editBtn = findViewById(R.id.job_comment_editBtn);
        job_comment_deleteBtn = findViewById(R.id.job_comment_deleteBtn);

        job_comment_recyclerView = findViewById(R.id.job_comment_recyclerView);
        job_comment_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JobCommentAdapter(commentList, buttonClickListener);
        job_comment_recyclerView.setAdapter(adapter);

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //구인자정보 리스너
        Button job_detail_employer_btn = findViewById(R.id.job_detail_employer_btn);
        job_detail_employer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployerData.class);
                startActivity(intent);
            }
        });

        //지원하기 리스너
        Button job_detail_participate = findViewById(R.id.job_detail_participate);
        job_detail_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                participateRequest();
            }
        });

        //공고댓글 작성 리스너
        job_comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentCreate();
            }
        });


    } //oncreate()

    public void onChildComment(int commentId) {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("대댓글 작성하기");
        builder.setView(editText);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedComment = editText.getText().toString();
                commentCreateChild(commentId, updatedComment);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void onEditComment(int commentId) {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("댓글 수정하기");
        builder.setView(editText);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedComment = editText.getText().toString();
                commentUpdate(commentId, updatedComment);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void commentRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고댓글 목록조회 요청
        Call<List<JobPostCommentResponseDTO>> call2 = apiService.getJobPostCommentList(jobId);
        call2.enqueue(new Callback<List<JobPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<JobPostCommentResponseDTO>> call, Response<List<JobPostCommentResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<JobPostCommentResponseDTO> comments = response.body();
                    adapter = new JobCommentAdapter(comments, buttonClickListener);
                    job_comment_recyclerView.setAdapter(adapter);
                } else {
                    Log.e("api", "공고상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "공고상세 댓글 서버응답 오류" + response.errorBody().toString());                }
            }
            @Override
            public void onFailure(Call<List<JobPostCommentResponseDTO>> call, Throwable t) {
                Log.e("api", "공고상세 댓글 서버요청 오류", t);
            }
        });
    }

    private void commentCreate() {
        String content = job_comment_content.getText().toString();

        JobPostCommentRequestDTO dto = new JobPostCommentRequestDTO();
        dto.setJobId(jobId);
        dto.setCommentContent(content);

        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고댓글 작성 요청
        Call<Void> call2 = apiService.createJobPostComment(dto);
        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(JobDetail.this, JobDetail.class);
                    intent.putExtra("jobId", jobId);
                    startActivity(intent);
//                    startActivity(new Intent(JobDetail.this, JobDetail.class));
                    Log.e("api", "공고상세 댓글 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "공고상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "공고상세 댓글 서버응답 오류" + response.errorBody().toString());                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("api", "공고상세 댓글 서버요청 오류", t);
            }
        });
    }

    private void commentCreateChild(int parentId, String content) {
        JobPostCommentRequestDTO dto = new JobPostCommentRequestDTO();

        dto.setCommentParent(parentId);
        dto.setJobId(jobId);
        dto.setCommentContent(content);

        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고 자식댓글 작성 요청
        Call<Void> call2 = apiService.createJobPostComment(dto);
        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                } else {
                    Log.e("api", "공고상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "공고상세 댓글 서버응답 오류" + response.errorBody().toString());                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("api", "공고상세 댓글 서버요청 오류", t);
            }
        });
    }

    private void commentUpdate(int commentId, String content) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        JobPostCommentUpdateRequestDTO dto = new JobPostCommentUpdateRequestDTO();

        dto.setCommentId(commentId);
        dto.setJobId(jobId);
        dto.setCommentContent(content);

        //공고댓글 수정 요청
        Call<List<JobPostCommentResponseDTO>> call2 = apiService.updateJobPostComment(dto);
        call2.enqueue(new Callback<List<JobPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<JobPostCommentResponseDTO>> call, Response<List<JobPostCommentResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<JobPostCommentResponseDTO> comments = response.body();
                    adapter = new JobCommentAdapter(comments, buttonClickListener);
                    job_comment_recyclerView.setAdapter(adapter);
                } else {
                    Log.e("api", "공고상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "공고상세 댓글 서버응답 오류" + response.errorBody().toString());                }
            }
            @Override
            public void onFailure(Call<List<JobPostCommentResponseDTO>> call, Throwable t) {
                Log.e("api", "공고상세 댓글 서버요청 오류", t);
            }
        });
    }

    private void commentDelete(int commentId) {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        JobPostCommentDeleteRequestDTO dto = new JobPostCommentDeleteRequestDTO();

        dto.setCommentId(commentId);
        dto.setJobId(jobId);

        //공고댓글 삭제 요청
        Call<List<JobPostCommentResponseDTO>> call2 = apiService.deleteJobPostComment(dto);
        call2.enqueue(new Callback<List<JobPostCommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<JobPostCommentResponseDTO>> call, Response<List<JobPostCommentResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<JobPostCommentResponseDTO> comments = response.body();
                    adapter = new JobCommentAdapter(comments, buttonClickListener);
                    job_comment_recyclerView.setAdapter(adapter);
                } else {
                    Log.e("api", "공고상세 댓글 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "공고상세 댓글 서버응답 오류" + response.errorBody().toString());                }
            }
            @Override
            public void onFailure(Call<List<JobPostCommentResponseDTO>> call, Throwable t) {
                Log.e("api", "공고상세 댓글 서버요청 오류", t);
            }
        });
    }

    private void jobDetailRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고상세정보 요청
        Call<JobDetailResponseDTO> call = apiService.getJobDetail(jobId);
        call.enqueue(new Callback<JobDetailResponseDTO>() {
            @Override
            public void onResponse(Call<JobDetailResponseDTO> call, Response<JobDetailResponseDTO> response) {
                if (response.isSuccessful()) {
                    JobDetailResponseDTO jobDetails = response.body();
                    // 화면에 job 상세 정보 표시
                    job_detail_title.setText(jobDetails.getJobName());
                    job_detail_day.setText(jobDetails.getCreatedJobDate());
                    job_detail_salary.setText(String.valueOf(jobDetails.getPay()));
                    job_detail_address_country.setText(jobDetails.getCountry());
                    job_detail_address_city.setText(jobDetails.getCity());
                    job_detail_workDate_start.setText(jobDetails.getStartWorkDate());
                    job_detail_workDate_end.setText(jobDetails.getEndWorkDate());
                    job_detail_workDay.setText(jobDetails.getWorkDay());
                    job_detail_workTime_start.setText(jobDetails.getStartWorkTime());
                    job_detail_workTime_end.setText(jobDetails.getEndWorkTime());
                    job_detail_crop.setText(jobDetails.getCropType());
                    job_detail_recruitDate_start.setText(jobDetails.getStartRecruitDate());
                    job_detail_recruitDate_end.setText(jobDetails.getEndRecruitDate());
                    job_detail_recruitNumber.setText(String.valueOf(jobDetails.getRecruitCount()));
                    job_detail_age.setText(String.valueOf(jobDetails.getAge()));
                    job_detail_recruiterName.setText(jobDetails.getUserName());

                    job_detail_addressDetail.setText(jobDetails.getWorkAddressDetail());

                    job_detail_preference.setText(jobDetails.getSpec());
                    job_detail_content.setText(jobDetails.getJobIntro());

                    Double valueLatitude = jobDetails.getWorkLatitude();
                    Double valueLongitude = jobDetails.getWorkLongitude();

                    if (valueLatitude == null || valueLongitude == null) {
                        Log.e("Map", "위경도 값 null, 기본 위치로 설정: 서울시청");
                    } else {
                        latitude = valueLatitude.doubleValue();
                        longitude = valueLongitude.doubleValue();
                    }

                    double rating = jobDetails.getCropLevel();
                    job_detail_cropLevel.setRating((float) rating);

                    String imageData = jobDetails.getJobImgFile();

                    if (imageData != null && imageData.length() > 0) {
                        // Base64 문자열을 바이트 배열로 디코드
                        byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
                        // 디코드된 바이트 배열을 Bitmap으로 변환
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        // Bitmap을 ImageView에 설정
                        job_detail_image.setImageBitmap(bitmap);
                    } else {
                        // 이미지가 없을 경우 기본 이미지 설정
                        job_detail_image.setImageResource(R.drawable.start_app);
                    }

                } else {
                    Log.e("api", "공고상세 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "공고상세 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<JobDetailResponseDTO> call, Throwable t) {
                Log.e("api", "공고상세 서버요청 오류", t);
            }
        });
    }

    private void participateRequest() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        ParticipateRequestDTO dto = new ParticipateRequestDTO();
        dto.setJobId(jobId);

        //공고지원 서버요청
        Call<Void> call = apiService.JobDetailParticipate(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
//                    Void ResponseDTO = response.body();
                    //지원현황으로 이동
                    
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





    public void onMapReady(NaverMap naverMap) {
        this.naverMap = naverMap;

        // 서버에서 받은 위치로 카메라 이동
        LatLng location = new LatLng(latitude, longitude);
        naverMap.moveCamera(CameraUpdate.scrollTo(location));

        Marker marker = new Marker();
        marker.setPosition(location);
        marker.setMap(naverMap);
    }








}
