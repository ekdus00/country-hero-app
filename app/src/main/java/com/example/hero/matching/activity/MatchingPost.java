package com.example.hero.matching.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.example.hero.R;
import com.example.hero.employer.activity.EmployerStatus;
import com.example.hero.employer.activity.JobEditPost;
import com.example.hero.employer.dto.JobPostEditResponseDTO;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.LocalDateAdapter;
import com.example.hero.etc.LocalTimeAdapter;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.example.hero.matching.dto.MatchingDetailResponseDTO;
import com.example.hero.matching.dto.MatchingPostCreateRequestDTO;
import com.example.hero.matching.dto.MatchingPostEditResponseDTO;
import com.example.hero.matching.dto.MatchingPostUpdateRequestDTO;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchingPost extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private LinearLayout backBtn; // 뒤로가기 버튼
    private String[] origin_regionals = {"시/도",};
    private String[] regionals;
    private String[] origin_citys = {"시/군/구"};
    private String[] citys;
    private ArrayAdapter<String> adapterRegionals;
    private ArrayAdapter<String> adapterCitys;
    private TextView textView_date_start, textView_date_end, image_name;
    private String startDateStr, endDateStr;
    private Button postMatchingBtn;
    private EditText eduContent;
    private ImageButton selectImage;
    private EditText title;
    private EditText snsUrl;
    private Uri imageUri;
    private String selectedRadioItem;
    private Spinner city_spinner, regional_spinner;
    int matchingId;
    private TokenManager tokenManager;
    private UserManager userManager;
    private ApiService apiService;
    public ImageButton btn_Work_Start, btn_Work_End;
    private boolean isEdit;
    private String selectCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_post);
        tokenManager = new TokenManager(this);
        userManager = new UserManager(this);

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);
        matchingId = intent.getIntExtra("matchingId", -1);

        title = findViewById(R.id.title);
        snsUrl = findViewById(R.id.sns_url);
        eduContent = findViewById(R.id.txt_edu_content);
        btn_Work_Start = findViewById(R.id.start_work);
        btn_Work_End = findViewById(R.id.end_work);
        textView_date_start = findViewById(R.id.textView_date_start);
        textView_date_end = findViewById(R.id.textView_date_end);

        postMatchingBtn = findViewById(R.id.post_matching);

        //뷰 초기화
        city_spinner = findViewById(R.id.city_spinner);
        regional_spinner = findViewById(R.id.regional_spinner);

        image_name = findViewById(R.id.image_name);

        btn_Work_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textView_date_start);
            }
        });
        btn_Work_End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textView_date_end);
            }
        });

        loadCountries();

        getEditMatchingPost();
        regional_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();
                loadCities(selectedCountry);  // 도시 목록 로드
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때
            }
        });

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        selectImage = findViewById(R.id.select_image);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectImageClick();
            }
        });

        postMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEdit && matchingId != -1) {

                    MultipartBody.Part image = prepareFilePart("uploadImg", imageUri, MatchingPost.this);
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                            .create();

                    String json = gson.toJson(new MatchingPostUpdateRequestDTO(matchingId, title.getText().toString(), regional_spinner.getSelectedItem().toString(), city_spinner.getSelectedItem().toString(),
                            textView_date_start.getText().toString(), textView_date_end.getText().toString(), snsUrl.getText().toString(), eduContent.getText().toString()));

                    RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

                    apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
                    Call<MatchingDetailResponseDTO> call = apiService.matchingPostEdit(requestBody, image);
                    call.enqueue(new Callback<MatchingDetailResponseDTO>() {
                        @Override
                        public void onResponse(Call<MatchingDetailResponseDTO> call, Response<MatchingDetailResponseDTO> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(MatchingPost.this, MatchingList.class);
                                startActivity(intent);

                            } else {
                                try {
                                    assert response.errorBody() != null;
                                    Log.e("API_CALL", "Response error: " + response.errorBody().string());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<MatchingDetailResponseDTO> call, Throwable t) {
                            Log.e("BOARDWRITE", "스크랩 에러 발생", t);
                        }
                    });

                } else {

                    MultipartBody.Part image = prepareFilePart("uploadImg", imageUri, MatchingPost.this);
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                            .create();

                    String json = gson.toJson(new MatchingPostCreateRequestDTO(userManager.getUserId(), userManager.getUserType(), regional_spinner.getSelectedItem().toString(), city_spinner.getSelectedItem().toString(),
                            title.getText().toString(), getType() , textView_date_start.getText().toString(), textView_date_end.getText().toString(), eduContent.getText().toString(), snsUrl.getText().toString()));

                    RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

                    apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
                    Call<Void> call = apiService.matchingPost(requestBody, image);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(MatchingPost.this, MatchingList.class);
                                startActivity(intent);
                            } else {
                                try {
                                    assert response.errorBody() != null;
                                    Log.e("API_CALL", "Response error: " + response.errorBody().string());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("BOARDWRITE", "스크랩 에러 발생", t);
                        }
                    });
                }


            }
        });

    } //onCreate()

    private void getEditMatchingPost() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고수정 조회 서버요청
        Call<MatchingPostEditResponseDTO> call = apiService.getMatchingPostEditInfo(matchingId);
        call.enqueue(new Callback<MatchingPostEditResponseDTO>() {
            @Override
            public void onResponse(Call<MatchingPostEditResponseDTO> call, Response<MatchingPostEditResponseDTO> response) {
                if (response.isSuccessful()) {
                    MatchingPostEditResponseDTO dto = response.body();
                        title.setText(dto.getMatchingName());

                        setSpinnerSelection1(dto.getCountry());
                        setSpinnerSelection2(dto.getCity());

                        textView_date_start.setText(dto.getStartEduDate());
                        textView_date_end.setText(dto.getEndEduDate());
                        eduContent.setText(dto.getEduContent());
                        snsUrl.setText(dto.getSnsUrl());
                        image_name.setText(dto.getUploadImgFileName());

                    Log.e("tag", "공고수정 조회 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("tag", "공고수정 조회 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "공고수정 조회 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<MatchingPostEditResponseDTO> call, Throwable t) {
                Log.e("tag", "공고수정 조회 서버요청", t);
            }
        });
    }

    public void loadCountries() {
        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        Call<List<String>> call = apiService.getCountries();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> countries = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MatchingPost.this,
                            android.R.layout.simple_spinner_dropdown_item, countries);
                    regional_spinner.setAdapter(adapter);
                } else {
                    Log.e("tag", "시/도 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "시/도 서버응답 오류" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("tag", "시/도 서버요청 오류", t);
            }
        });
    }

    public void loadCities(String country) {
        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        Call<List<String>> call = apiService.getCitiesByCountry(country);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> cities = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MatchingPost.this,
                            android.R.layout.simple_spinner_dropdown_item, cities);
                    city_spinner.setAdapter(adapter);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("tag", "시/군/구 서버응답 오류" + response.errorBody());
                    } catch (IOException e) {
                        Log.e("tag", "시/군/구 서버응답 오류", e);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("tag", "시/군/구 서버요청 오류", t);
            }
        });
    }

    public void onSelectImageClick() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                // 이미지를 가져와서 표시
                imageUri = data.getData();
                try {
                    Bitmap bitmap = resizeImage(imageUri, 200, 200);
                    selectImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private Bitmap resizeImage(Uri imageUri, int targetWidth, int targetHeight) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, options);

        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        int scaleFactor = Math.min(imageWidth / targetWidth, imageHeight / targetHeight);
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, options);
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri, Context context) {
        File cacheDir = context.getCacheDir();
        File tempFile = new File(cacheDir, "upload.jpg");
        Log.d("File Check", "File exist before stream: " + tempFile.exists());

        try {
            if (!tempFile.exists() && tempFile.createNewFile()) {
                Log.d("File Creation", "File created successfully.");
            }
        } catch (IOException e) {
            Log.e("File Creation", "Error creating file", e);
            return null;
        }

        try (InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
             OutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            Log.e("File I/O", "Error processing file", e);
            return null;
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), tempFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(partName, tempFile.getName(), requestFile);
//        tempFile.delete();
        return filePart;
    }

    private void showDatePickerDialog (final TextView textView){
        DatePickerDialog.OnDateSetListener callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                if (textView != null) {
                    textView.setText(formattedDate);
                } else {
                    Log.e("DatePicker", "TextView is null");
                }
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, callbackMethod, year, month, day);
        datePickerDialog.show();
    }

    public String getType(){
        RadioGroup writer_type = findViewById(R.id.writer_type);

        int checkedRadioButtonId = writer_type.getCheckedRadioButtonId();
        String selectedPreferText = "mentor";

        if (checkedRadioButtonId == R.id.radio_mentor) {
            selectedPreferText = "mentor";
        } else if (checkedRadioButtonId == R.id.radio_mentee) {
            selectedPreferText = "mentee";
        }
        return selectedPreferText;
    }

    private void setSpinnerSelection2(String city) {
        Log.e("tag", "해당 도시를 찾을 수 없습니다: " + city);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) city_spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(city);
            if (position != -1) {
                city_spinner.setSelection(position);
            } else {
                Log.e("tag", "해당 도시를 찾을 수 없습니다: " + city);
            }
        }
    }

    private void setSpinnerSelection1(String province) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) regional_spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(province);
            if (position != -1) {
                regional_spinner.setSelection(position);
            } else {
                Log.e("tag", "해당 지역을 찾을 수 없습니다: " + province);
            }
        }
    }

}