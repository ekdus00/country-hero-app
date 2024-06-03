package com.example.hero.login.activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient2;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.login.dto.CheckUserRequestDTO;
import com.example.hero.login.dto.FindUserIdRequestDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPwAuth extends AppCompatActivity {
    private Context context;
    private Button sendBtn;
    private EditText auth_name_editText, auth_id_editText, auth_email_editText;
    private Spinner auth_birth_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pw_auth);
        context = this;

        auth_name_editText = findViewById(R.id.auth_name_editText);
        auth_id_editText = findViewById(R.id.auth_id_editText);
        auth_birth_spinner = findViewById(R.id.auth_birth_spinner);
        auth_email_editText = findViewById(R.id.auth_email_editText);

        //사용자 인증 완료
        sendBtn= findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPwAuthRequest();
            }
        });

        //뒤로가기
        Button btn_Back = findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreate()

    private void resetPwAuthRequest() {
        String name = auth_name_editText.getText().toString();
        String id = auth_id_editText.getText().toString();
        String email = auth_email_editText.getText().toString();
        String birth = auth_birth_spinner.getSelectedItem().toString();

        CheckUserRequestDTO dto = new CheckUserRequestDTO();

        dto.setUserEmail(email);
        dto.setUserId(id);
        dto.setUserName(name);
        dto.setUserBirth(birth);

        ApiService apiService = RetrofitClient2.getClient().create(ApiService.class);

        //사용자인증 서버요청
        Call<String> call = apiService.checkUser(dto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String successMessage = response.body();

                    Log.v(TAG, "사용자 인증 문구: " + successMessage);
                    Log.v(TAG, "사용자 인증 문구: " + response.body());

                    if ("User Authentication Successful".equals(successMessage) || "User Authentication Successful".equals(response.body())) {
                        Toast.makeText(context, "사용자 인증에 성공했습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPwAuth.this, ResetPW.class);
                        startActivity(intent);
                        finish();
                        Log.e("tag", "사용자인증 서버응답 성공" + response.code() + ", " + response.message());
                    } else {
                        Toast.makeText(context, "사용자 인증에 실패했습니다", Toast.LENGTH_SHORT).show();
                        Log.e("tag", "사용자인증 서버응답 오류코드" + response.code() + ", " + response.message());
                        Log.e("tag", "사용자인증 서버응답 오류" + response.errorBody().toString());
                    }
                } else {
                    Toast.makeText(context, "사용자 인증에 실패했습니다", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "사용자인증 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "사용자인증 서버응답 오류" + response.errorBody().toString());                        }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("tag", "사용자인증 서버요청 오류", t);
            }
        });
    }
}
