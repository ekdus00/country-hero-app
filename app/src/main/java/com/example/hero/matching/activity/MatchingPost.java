package com.example.hero.matching.activity;

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
import com.example.hero.etc.ApiService;
import com.example.hero.etc.LocalDateAdapter;
import com.example.hero.etc.LocalTimeAdapter;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.matching.dto.MatchingPostCreateRequestDTO;
import com.google.android.material.datepicker.MaterialDatePicker;
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

    LinearLayout backBtn; // 뒤로가기 버튼
    String[] origin_regionals = {"시/도",};
    String[] regionals;
    String[] origin_citys = {"시/군/구"};
    String[] citys;

    ArrayAdapter<String> adapterRegionals;
    ArrayAdapter<String> adapterCitys;

    TextView txt_startdate, txt_enddate;
    String startDateStr, endDateStr;

    Button postMatchingBtn;

    EditText eduContent;

    ImageButton selectImage;

    EditText title;
    EditText snsUrl;

    private Uri imageUri;
    private String selectedRadioItem;


    Spinner city_spinner, regional_spinner;

    private TokenManager tokenManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_post);
        tokenManager = new TokenManager(this);

        title = findViewById(R.id.title);
        snsUrl = findViewById(R.id.sns_url);
        eduContent = findViewById(R.id.txt_edu_content);
        RadioGroup rdgGroup = findViewById(R.id.writer_type);
        rdgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radio_mentor) {
                    selectedRadioItem = "mentor";
                } else if (checkedId == R.id.radio_mentee) {
                    selectedRadioItem = "mentee";
                }
            }
        });

        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
        Call<List<String>> call = apiService.getCountries();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    Log.d("BOARDWRITE", response.body().toString());
                    List<String> newList = new ArrayList<>(Arrays.asList(origin_regionals));
                    // addAll() 메서드를 호출하여 두 번째 배열의 값을 추가
                    newList.addAll(new ArrayList<>(response.body()));

                    // List 객체의 값을 배열에 할당
                    regionals = new String[newList.size()];
                    regionals = newList.toArray(regionals);

                    //스피너 어댑터 초기화
                    adapterRegionals = new ArrayAdapter<>(MatchingPost.this, android.R.layout.simple_spinner_item, regionals);

                    //스피너 어댑터 설정
                    adapterRegionals.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    //스피너에 어댑터 적용
                    regional_spinner.setAdapter(adapterRegionals);

                    regional_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i == 0) return;
                            ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
                            Call<List<String>> call = apiService.getCitiesByCountry(regional_spinner.getSelectedItem().toString());
                            call.enqueue(
                                    new Callback<List<String>>() {
                                        @Override
                                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                            List<String> newList = new ArrayList<>(Arrays.asList(origin_citys));
                                            // addAll() 메서드를 호출하여 두 번째 배열의 값을 추가
                                            assert response.body() != null;
                                            newList.addAll(new ArrayList<>(response.body()));

                                            // List 객체의 값을 배열에 할당
                                            citys = new String[newList.size()];
                                            citys = newList.toArray(citys);

                                            adapterCitys = new ArrayAdapter<>(MatchingPost.this, android.R.layout.simple_spinner_item, citys);
                                            adapterCitys.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            city_spinner.setAdapter(adapterCitys);
                                        }

                                        @Override
                                        public void onFailure(Call<List<String>> call, Throwable t) {
                                            Log.e("BOARDWRITE", "BOARDWRITE 에러 발생", t);
                                        }
                                    }
                            );
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    //도 ,시로 미리 골라놓기
                    city_spinner.setSelection(0, false);
                    regional_spinner.setSelection(0, false);

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
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("BOARDWRITE", "BOARDWRITE 에러 발생", t);
            }
        });

        backBtn = findViewById(R.id.back_btn); // 뒤로가기 버튼의 객체(linear layout)를 id로 찾아서 받아옴
        backBtn.setOnClickListener(new View.OnClickListener() { // 뒤로가기 버튼(linear layout)에 클릭 이벤트를 달아줌
            @Override
            public void onClick(View view) {
                onBackPressed();
            } // 클릭하면 뒤로가기 동작이 실행됨
        });

        selectImage = findViewById(R.id.select_image);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectImageClick();
            }
        });


        postMatchingBtn = findViewById(R.id.post_matching);
        postMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                MultipartBody.Part image = prepareFilePart("uploadImg", imageUri, MatchingPost.this);

//                jobPostDTO.setUploadImgFile(image);

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                        .create();


                String json = gson.toJson(new MatchingPostCreateRequestDTO("tester100", "구인자", regional_spinner.getSelectedItem().toString(), city_spinner.getSelectedItem().toString(),
                        title.getText().toString(), selectedRadioItem, startDateStr, endDateStr, eduContent.getText().toString(), snsUrl.getText().toString()));
                RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

                ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);
                Call<Void> call = apiService.matchingPost(requestBody, image);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {

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


                onBackPressed();
            }
        });

        //클릭시 기간 선택창
        findViewById(R.id.data_picker).setOnClickListener(v -> {
            DatePickerdialog();
        });

        //뷰 초기화
        city_spinner = findViewById(R.id.city_spinner);
        regional_spinner = findViewById(R.id.regional_spinner);
        txt_startdate = findViewById(R.id.txt_startdate);
        txt_enddate = findViewById(R.id.txt_enddate);
    }

    //기간 선택창 띄우는 함수
    private void DatePickerdialog() {

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("기간을 선택해 주세요");
        builder.setTheme(R.style.Theme_Hero);

        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();

        datePicker.addOnPositiveButtonClickListener(selection -> {

            Long startDate = selection.first;
            Long endDate = selection.second;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
            String startDateString = sdf.format(new Date(startDate));
            String endDateString = sdf.format(new Date(endDate));

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            startDateStr = sdf1.format(new Date(startDate));
            endDateStr = sdf1.format(new Date(endDate));

            txt_startdate.setText(startDateString);
            txt_enddate.setText(endDateString);

        });
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    public void onSelectImageClick() {
        ;
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
}