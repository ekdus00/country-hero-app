package com.example.hero.mypage.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.GsonLocalDateAdapter;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.activity.JobPost;
import com.example.hero.job.dto.JobPostCreateRequestDTO;
import com.example.hero.mypage.dto.OwnerUserInfoUpdateRequestDTO;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModifyRecruiter extends AppCompatActivity {
    private TextView modify_name, modify_recruiter_name, modify_birth, modify_email;
    private RadioGroup radioGroupModify;
    private Button modify_send;
    private ApiService apiService;
    private TokenManager tokenManager = new TokenManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_recruiter);

        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("회원 정보 수정");

        modify_name = findViewById(R.id.modify_name);
        radioGroupModify = findViewById(R.id.radioGroupModify);
        modify_recruiter_name = findViewById(R.id.modify_recruiter_name);
        modify_birth = findViewById(R.id.modify_birth);
        modify_email = findViewById(R.id.modify_email);

        //회원정보수정 완료
        modify_send = findViewById(R.id.modify_send);
        modify_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyRecruiterRequest();
            }
        });

    }//onCreate()

    public void modifyRecruiterRequest() {
        String text_recruiter_name = modify_recruiter_name.getText().toString();

        OwnerUserInfoUpdateRequestDTO dto = new OwnerUserInfoUpdateRequestDTO();
        dto.setFarmName(text_recruiter_name);

        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //회원정보수정(구인자) 서버요청
        Call<Void> call = apiService.updateOwnerInfo(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("api", "회원정보수정(구인자) 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "회원정보수정(구인자) 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "회원정보수정(구인자) 서버응답 오류" + response.errorBody().toString());                        }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("api", "회원정보수정(구인자) 서버요청 오류", t);
            }
        });

    }




}