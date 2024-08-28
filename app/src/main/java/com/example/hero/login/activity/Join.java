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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.activity.JobFilter;
import com.example.hero.job.activity.JobList;
import com.example.hero.job.dto.JobPostCreateRequestDTO;
import com.example.hero.login.dto.JoinRequestDTO;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Join extends AppCompatActivity {
    private Context context;
    private Button join_sendBtn;
    private EditText join_id_editText, join_pw_editText, join_email_editText, join_name_editText;
    private Spinner spinner_join_birth;
    private RadioGroup radio_join_gender;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        tokenManager = new TokenManager(this);
        context = this;

        join_id_editText = findViewById(R.id.join_id_editText);
        join_pw_editText = findViewById(R.id.join_pw_editText);
        spinner_join_birth = findViewById(R.id.spinner_join_birth);
        radio_join_gender = findViewById(R.id.radio_join_gender);
        join_email_editText = findViewById(R.id.join_email_editText);
        join_name_editText = findViewById(R.id.join_name_editText);

        //회원가입 완료
        join_sendBtn= findViewById(R.id.join_sendBtn);
        join_sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinRequest();
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

    private void joinRequest() {
        String join_id = join_id_editText.getText().toString();
        String join_pw = join_pw_editText.getText().toString();
        String join_email = join_email_editText.getText().toString();
        String join_name = join_name_editText.getText().toString();
        String join_birth = spinner_join_birth.getSelectedItem().toString();
        String join_gender = getScoreFromRadioGroup(radio_join_gender);

        JoinRequestDTO dto = new JoinRequestDTO();

        dto.setUserId(join_id);
        dto.setUserName(join_name);
        dto.setUserPw(join_pw);
        dto.setEmail(join_email);
        dto.setGender(join_gender);
        dto.setBirth(join_birth);

        ApiService apiService = RetrofitClientWithoutAuth.getClient().create(ApiService.class);

        //회원가입 서버요청
        Call<Void> call = apiService.joinUser(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    Headers headers = response.headers();

                    String accessToken = headers.get("Authorization").replace("Bearer ", "");
                    String refreshToken = headers.get("Refresh-Token");

                    tokenManager.saveAccessTokens(accessToken);
                    tokenManager.saveRefreshTokens(refreshToken);

                    String a = tokenManager.getAccessToken();
                    String b = tokenManager.getRefreshToken();
                    long c = tokenManager.getAccessExpirationTime();
                    long d = tokenManager.getRefreshExpirationTime();

                    Log.v(TAG, "액세스토큰: " + a);
                    Log.v(TAG, "리프레시토큰: " + b);
                    Log.v(TAG, "액세스토큰 남은시간: " + c);
                    Log.v(TAG, "리프레시토큰 남은시간: " + d);

                    Intent intent = new Intent(Join.this, UserTypeSet.class);
                    intent.putExtra("userId", join_id);
                    intent.putExtra("loginType", "join");
                    startActivity(intent);

                    Log.e("tag", "회원가입 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Toast.makeText(Join.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("tag", "회원가입 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "회원가입 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("tag", "회원가입 서버요청 오류", t);
            }
        });
    }

    private String getScoreFromRadioGroup(RadioGroup group) {
        int radioButtonID = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(radioButtonID);
        String radioString = radioButton.getTag().toString();
        return radioString;
    }
}
