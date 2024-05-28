package com.example.hero.mypage.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.mypage.dto.OwnerUserInfoResponseDTO;
import com.example.hero.mypage.dto.WorkerUserInfoResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModifyWorker extends AppCompatActivity {
    private TextView modify_name, modify_gender, modify_birth, modify_email;
    private ApiService apiService;
    private TokenManager tokenManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_worker);
        tokenManager = new TokenManager(this);

        modify_name = findViewById(R.id.modify_name);
        modify_gender = findViewById(R.id.modify_gender);
        modify_birth = findViewById(R.id.modify_birth);
        modify_email = findViewById(R.id.modify_email);

        getModifyOwner();

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

    }//onCreate()

    public void getModifyOwner() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //회원정보수정(구직자) 조회 서버요청
        Call<WorkerUserInfoResponseDTO> call = apiService.getWorkerInfo();
        call.enqueue(new Callback<WorkerUserInfoResponseDTO>() {
            @Override
            public void onResponse(Call<WorkerUserInfoResponseDTO> call, Response<WorkerUserInfoResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WorkerUserInfoResponseDTO dto = response.body();
                    modify_name.setText(dto.getUserName());
                    modify_gender.setText(dto.getGender());
                    modify_birth.setText(dto.getBirth());
                    modify_email.setText(dto.getMail());

                    Log.e("api", "회원정보수정(구직자) 조회 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "회원정보수정(구직자) 조회 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "회원정보수정(구직자) 조회 서버응답 오류" + response.errorBody().toString());                        }
            }
            @Override
            public void onFailure(Call<WorkerUserInfoResponseDTO> call, Throwable t) {
                Log.e("api", "회원정보수정(구직자) 조회 서버요청 오류", t);
            }
        });

    }
}
