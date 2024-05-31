package com.example.hero.mypage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.L;
import com.example.hero.R;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.RetrofitClientBusiness;
import com.example.hero.etc.RetrofitClientWithoutAuth;
import com.example.hero.etc.TokenManager;
import com.example.hero.login.activity.Login;
import com.example.hero.mypage.dto.BusinessDataDTO;
import com.example.hero.mypage.dto.BusinessNumberRequest;
import com.example.hero.mypage.dto.BusinessResponseDTO;
import com.example.hero.mypage.dto.OwnerUserInfoResponseDTO;
import com.example.hero.mypage.dto.OwnerUserInfoUpdateRequestDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModifyOwner extends AppCompatActivity {
    private TextView modify_name, modify_birth, modify_email, modify_gender;
    private Button modify_send, modify_farmNum_btn;
    private ApiService apiService;
    private TokenManager tokenManager;
    private EditText modify_farmName, modify_farmNum;
    Context context;
    private String business_auth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_owner);
        tokenManager = new TokenManager(this);
        context = this;

        modify_name = findViewById(R.id.modify_name);
        modify_gender = findViewById(R.id.modify_gender);
        modify_birth = findViewById(R.id.modify_birth);
        modify_email = findViewById(R.id.modify_email);

        modify_farmName = findViewById(R.id.modify_farmName);

        modify_farmNum = findViewById(R.id.modify_farmNum);

        getModifyOwner();

        //사업자 번호 인증
        modify_farmNum_btn = findViewById(R.id.modify_farmNum_btn);
        modify_farmNum_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessNumAuth();
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("회원 정보 수정");

        //회원정보수정 완료
        modify_send = findViewById(R.id.modify_send);
        modify_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(business_auth, "auth")) {
                    modifyOwnerRequest();
                } else {
                    Toast.makeText(context, "사업자 등록번호 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }//onCreate()

    public void BusinessNumAuth() {
        apiService = RetrofitClientBusiness.getClient(context).create(ApiService.class);

        String farm_number = modify_farmNum.getText().toString();

        List<String> businessNumbers = Arrays.asList(farm_number);  // 여기에 실제 사업자 번호 입력
        BusinessNumberRequest request = new BusinessNumberRequest(businessNumbers);

        String service_key = context.getString(R.string.business_auth_key);

        Call<BusinessResponseDTO> call = apiService.checkBusinessStatus(service_key, request);
        call.enqueue(new Callback<BusinessResponseDTO>() {
            @Override
            public void onResponse(Call<BusinessResponseDTO> call, Response<BusinessResponseDTO> response) {
                if (response.isSuccessful()) {
                    BusinessResponseDTO businessData1 = response.body();
                    BusinessDataDTO businessData2 = businessData1.getData().get(0);
                    String statusCode = businessData2.getBusinessStatusCode();

                    if (Objects.equals(statusCode, "01")) {
                        business_auth = "auth";
                        Toast.makeText(context, "사업자 등록번호 인증에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        business_auth = "unableAuth";
                        Toast.makeText(context, "사업자 등록번호 인증에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }

                    Log.e("api", "회원정보수정(구인자) 수정 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "회원정보수정(구인자) 수정 서버응답 오류코드" + response.code() + ", " + response.message());
//                    Log.e("api", "회원정보수정(구인자) 수정 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<BusinessResponseDTO> call, Throwable t) {
                Log.e("api", "회원정보수정(구인자) 수정 서버요청 오류", t);
            }
        });


    }

    public void modifyOwnerRequest() {
        String text_farm_name = modify_farmName.getText().toString();

        OwnerUserInfoUpdateRequestDTO dto = new OwnerUserInfoUpdateRequestDTO();
        dto.setFarmName(text_farm_name);

        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //회원정보수정(구인자) 수정 서버요청
        Call<Void> call = apiService.updateOwnerInfo(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ModifyOwner.this, MyPageOwner.class);
                    startActivity(intent);
                    Log.e("api", "회원정보수정(구인자) 수정 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "회원정보수정(구인자) 수정 서버응답 오류코드" + response.code() + ", " + response.message());
//                    Log.e("api", "회원정보수정(구인자) 수정 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("api", "회원정보수정(구인자) 수정 서버요청 오류", t);
            }
        });

    }

    public void getModifyOwner() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //회원정보수정(구인자) 조회 서버요청
        Call<OwnerUserInfoResponseDTO> call = apiService.getOwnerInfo();
        call.enqueue(new Callback<OwnerUserInfoResponseDTO>() {
            @Override
            public void onResponse(Call<OwnerUserInfoResponseDTO> call, Response<OwnerUserInfoResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OwnerUserInfoResponseDTO dto = response.body();
                    modify_name.setText(dto.getUserName());
                    modify_gender.setText(dto.getGender());
                    modify_farmName.setText(dto.getFarmName());
                    modify_birth.setText(dto.getBirth());
                    modify_email.setText(dto.getMail());

                    Log.e("api", "회원정보수정(구직자) 조회 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "회원정보수정(구직자) 조회 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "회원정보수정(구직자) 조회 서버응답 오류" + response.errorBody().toString());                        }
            }
            @Override
            public void onFailure(Call<OwnerUserInfoResponseDTO> call, Throwable t) {
                Log.e("api", "회원정보수정(구직자) 조회 서버요청 오류", t);
            }
        });

    }




}