package com.example.hero.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.login.dto.ExtraInfoDTO;
import com.example.hero.login.dto.LoginRequestDTO;
import com.example.hero.login.dto.LoginResultDTO;
import com.example.hero.review.dto.OwnerReviewInfoDTO;

import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTypeSet extends AppCompatActivity {
    private Context context;
    private Button user_type_owner, user_type_worker;
    private ApiService apiService;
    private String user_type_result;
    private String userId;
    private UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_user_type);
        context = this;
        userManager = new UserManager(this);

        userId = getIntent().getStringExtra("userId");

        user_type_owner = findViewById(R.id.user_type_owner);
        user_type_worker = findViewById(R.id.user_type_worker);
        user_type_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_type_result = "owner";
                userTypeRequest();
            }
        });
        user_type_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_type_result = "worker";
                userTypeRequest();
            }
        });

    }//onCreate()


    private void userTypeRequest() {
        ExtraInfoDTO dto = new ExtraInfoDTO();

        dto.setUserId(userId);
        dto.setUserType(user_type_result);

        userManager.saveUserId(userId);
        userManager.saveUserType(user_type_result);

        //추가정보입력 서버요청
        apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);
        Call<Void> call = apiService.setUserType(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    userManager.saveUserInfoInputCompleted(true);

                    Intent intent = new Intent(UserTypeSet.this, Login.class);
                    startActivity(intent);
                    finish();

                    Log.e("api", "추가정보입력 서버응답 성공" + response.code() + ", " + response.message());
                    
                } else {
                    Log.e("HTTP_ERROR", "Status Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("NETWORK_ERROR", "Failed to connect to the server", t);
            }
        });

    }



}
