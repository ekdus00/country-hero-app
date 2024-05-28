package com.example.hero.login.activity;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.login.dto.FindUserIdRequestDTO;
import com.example.hero.login.dto.JoinRequestDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindID extends AppCompatActivity {
    private Context context;
    private Button sendBtn;
    private EditText find_name_editText, find_email_editText;
    private Spinner find_birth_spinner;
    private TextView find_id_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id);
        context = this;

        find_name_editText = findViewById(R.id.find_name_editText);
        find_birth_spinner = findViewById(R.id.find_birth_spinner);
        find_email_editText = findViewById(R.id.find_email_editText);

        find_id_result = findViewById(R.id.find_id_result);

        sendBtn= findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findIDRequest();
            }
        });

    }//onCreate()

    private void findIDRequest() {
        String name = find_name_editText.getText().toString();
        String email = find_email_editText.getText().toString();
        String birth = find_birth_spinner.getSelectedItem().toString();

        FindUserIdRequestDTO dto = new FindUserIdRequestDTO();

        dto.setUserEmail(email);
        dto.setUserName(name);
        dto.setUserBirth(birth);

        ApiService apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);

        //아이디 찾기 서버요청
        Call<String> call = apiService.findUserId(dto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String result = response.body();
                    find_id_result.setText(result);

                    Log.e("tag", "아이디찾기 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("tag", "아이디찾기 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "아이디찾기 서버응답 오류" + response.errorBody().toString());                        }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("tag", "아이디찾기 서버요청 오류", t);
            }
        });
    }
}
