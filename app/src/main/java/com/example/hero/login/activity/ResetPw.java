package com.example.hero.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.login.dto.ResetUserPwRequestDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPw extends AppCompatActivity {
    private Context context;
    private Button sendBtn;
    private EditText reset_id_editText, reset_new_pw_editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pw);
        context = this;

        reset_id_editText = findViewById(R.id.reset_id_editText);
        reset_new_pw_editText = findViewById(R.id.reset_new_pw_editText);

        //비밀번호 재설정 완료
        sendBtn= findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findIDRequest();
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

    private void findIDRequest() {
        String id = reset_id_editText.getText().toString();
        String pw = reset_new_pw_editText.getText().toString();

        ResetUserPwRequestDTO dto = new ResetUserPwRequestDTO();

        dto.setUserId(id);
        dto.setUserPw(pw);

        ApiService apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);

        //비밀번호 재설정 서버요청
        Call<Void> call = apiService.resetUserPw(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "비밀번호 재설정에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPw.this, Login.class);
                    startActivity(intent);
                    Log.e("tag", "비밀번호 재설정 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Toast.makeText(ResetPw.this, "비밀번호 재설정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "비밀번호 재설정 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "비밀번호 재설정 서버응답 오류" + response.errorBody().toString());                        }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("tag", "비밀번호 재설정 서버요청 오류", t);
            }
        });
    }
}
