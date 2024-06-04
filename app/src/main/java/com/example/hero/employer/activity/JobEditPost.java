package com.example.hero.employer.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

import com.bumptech.glide.Glide;
import com.example.hero.employer.dto.JobPostEditResponseDTO;
import com.example.hero.employer.dto.JobRequestDTO;
import com.example.hero.employer.dto.JopPostUpdateRequestDTO;
import com.example.hero.etc.AddressActivity;
import com.example.hero.etc.ApiService;
import com.example.hero.R;
import com.example.hero.etc.GsonLocalDateAdapter;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.etc.UserManager;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class JobEditPost extends AppCompatActivity {
    private Context context;
    private TextView job_post_title,
            textView_date_start, textView_date_end, work_salary_value, textView_recruit_start, textView_recruit_end, textView_recruit_number,
            textView_recruit_age, textView_recruit_introduction, textview_work_prefer, address_detail_editView;
    public ImageButton btn_Work_Start, btn_Work_End, btn_Recruit_Start, btn_Recruit_End;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView post_ImageView;
    private Button btn_send;
    public TextView address_main, address_postcode, job_edit_imageName;
    String selectedPreferText = "";
    private Spinner spinnerProvince, spinnerCity, working_time_start, working_time_end, work_crop1, work_crop2;
    private Uri imageUri;
    private RadioButton work_mon, work_tue, work_wen, work_thu, work_fri, work_prefer1, work_prefer2;
    private TokenManager tokenManager;
    private UserManager userManager;
    private ApiService apiService;
    private int jobId;
    private String buttonType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_edit_post);
        context = this;
        tokenManager = new TokenManager(this);
        userManager = new UserManager(this);

        btn_send = findViewById(R.id.job_post_send);
        btn_Work_Start = findViewById(R.id.start_work);
        btn_Work_End = findViewById(R.id.end_work);
        btn_Recruit_Start = findViewById(R.id.start_recruit);
        btn_Recruit_End = findViewById(R.id.end_recruit);

        job_post_title = findViewById(R.id.job_post_title);

        textView_date_start = findViewById(R.id.textView_date_start);
        textView_date_end = findViewById(R.id.textView_date_end);
        textView_recruit_start = findViewById(R.id.textView_recruit_start);
        textView_recruit_end = findViewById(R.id.textView_recruit_end);

        working_time_start = findViewById(R.id.working_time_start);
        working_time_end = findViewById(R.id.working_time_end);
        spinnerProvince = findViewById(R.id.spinner_province);
        spinnerCity= findViewById(R.id.spinner_city);
        work_crop1 = findViewById(R.id.work_crop1);
        work_crop2 = findViewById(R.id.work_crop2);

        work_salary_value = findViewById(R.id.work_salary_value);
        textView_recruit_number = findViewById(R.id.textView_recruit_number);
        textview_work_prefer = findViewById(R.id.textview_work_prefer);
        textView_recruit_age = findViewById(R.id.textView_recruit_age);
        textView_recruit_introduction = findViewById(R.id.textView_recruit_introduction);

        address_detail_editView= findViewById(R.id.address_detail_editView);

        job_edit_imageName = findViewById(R.id.job_edit_imageName);
        post_ImageView = findViewById(R.id.post_image_imageView);

        address_postcode = findViewById(R.id.address_postcode);
        address_main = findViewById(R.id.address_main);

        work_mon = findViewById(R.id.work_mon);
        work_tue = findViewById(R.id.work_tue);
        work_wen = findViewById(R.id.work_wen);
        work_thu = findViewById(R.id.work_thu);
        work_fri = findViewById(R.id.work_fri);
        work_prefer1 = findViewById(R.id.work_prefer1);
        work_prefer2 = findViewById(R.id.work_prefer2);

        jobId = getIntent().getIntExtra("jobId", 0);
        buttonType = getIntent().getStringExtra("buttonType");

        loadCountries();
        loadCropForms();

        getEditJobPost();

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        work_crop1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCropForm = parent.getItemAtPosition(position).toString();
                loadCropTypes(selectedCropForm);  // 농작물 품목 로드
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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

        //공고수정 저장
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobPostUpdate();
            }
        });

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
        btn_Recruit_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textView_recruit_start);
            }
        });
        btn_Recruit_End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textView_recruit_end);
            }
        });

    } //onCreate()

    private void getEditJobPost() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고수정 조회 서버요청
        Call<JobPostEditResponseDTO> call = apiService.getEditJobPost(jobId);
        call.enqueue(new Callback<JobPostEditResponseDTO>() {
            @Override
            public void onResponse(Call<JobPostEditResponseDTO> call, Response<JobPostEditResponseDTO> response) {
                if (response.isSuccessful()) {
                    JobPostEditResponseDTO dto = response.body();
                    job_post_title.setText(dto.getJobName());
                    textView_date_start.setText(dto.getStartWorkDate());
                    textView_date_end.setText(dto.getEndWorkDate());
                    textView_recruit_start.setText(dto.getStartRecruitDate());
                    textView_recruit_end.setText(dto.getEndRecruitDate());

                    String workDay = dto.getWorkDay();
                    if ("월".equals(workDay)) {
                        work_mon.setChecked(true);
                    } else if ("화".equals(workDay)) {
                        work_tue.setChecked(true);
                    } else if ("수".equals(workDay)) {
                        work_wen.setChecked(true);
                    } else if ("목".equals(workDay)) {
                        work_thu.setChecked(true);
                    } else if ("금".equals(workDay)) {
                        work_fri.setChecked(true);
                    }

                    String career = dto.getCareer();
                    if ("need".equals(career)) {
                        work_prefer1.setChecked(true);
                    } else if ("needless".equals(career)) {
                        work_prefer2.setChecked(true);
                    }

                    //스피너 셋
                    setSpinnerSelection1(dto.getCountry());
                    setSpinnerSelection2(dto.getCity());
                    setSpinnerSelection3(dto.getCropForm());
                    setSpinnerSelection4(dto.getCropType());
                    setSpinnerSelection5(dto.getStartWorkTime());
                    setSpinnerSelection6(dto.getEndWorkTime());

                    textview_work_prefer.setText(dto.getSpec());
                    textView_recruit_introduction.setText(dto.getJobIntro());

                    address_detail_editView.setText(dto.getWorkAddressDetail());

                    String workAddress = dto.getWorkAddress();
                    // 괄호를 기준으로 문자열 분리
                    int endOfPostcode = workAddress.indexOf(')');
                    if (endOfPostcode != -1) {
                        String postcode = workAddress.substring(1, endOfPostcode);
                        String address = workAddress.substring(endOfPostcode + 2); // 괄호와 공백 다음 문자부터 시작
                        address_postcode.setText(postcode);
                        address_main.setText(address);
                    }

                    work_salary_value.setText(String.valueOf(dto.getPay()));
                    textView_recruit_number.setText(String.valueOf(dto.getRecruitCount()));
                    textView_recruit_age.setText(String.valueOf(dto.getAge()));

                    job_edit_imageName.setText(dto.getUploadImgFileName());

                    Log.e("tag", "공고수정 조회 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("tag", "공고수정 조회 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "공고수정 조회 서버응답 오류" + response.errorBody().toString());                
                }
            }
            @Override
            public void onFailure(Call<JobPostEditResponseDTO> call, Throwable t) {
                Log.e("tag", "공고수정 조회 서버요청", t);
            }
        });

    }

    private void setSpinnerSelection1(String province) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerProvince.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(province);
            if (position != -1) {
                spinnerProvince.setSelection(position);
            } else {
                Log.e("tag", "해당 지역을 찾을 수 없습니다: " + province);
            }
        }
    }

    private void setSpinnerSelection2(String city) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerCity.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(city);
            if (position != -1) {
                spinnerCity.setSelection(position);
            } else {
                Log.e("tag", "해당 도시를 찾을 수 없습니다: " + city);
            }
        }
    }

    private void setSpinnerSelection3(String crop1) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) work_crop1.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(crop1);
            if (position != -1) {
                work_crop1.setSelection(position);
            } else {
                Log.e("tag", "해당 품목을 찾을 수 없습니다: " + crop1);
            }
        }
    }

    private void setSpinnerSelection4(String crop2) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) work_crop2.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(crop2);
            if (position != -1) {
                work_crop2.setSelection(position);
            } else {
                Log.e("tag", "해당 작물을 찾을 수 없습니다: " + crop2);
            }
        }
    }

    private void setSpinnerSelection5(String time1) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) working_time_start.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(time1);
            if (position != -1) {
                working_time_start.setSelection(position);
            } else {
                Log.e("tag", "해당 작물을 찾을 수 없습니다: " + time1);
            }
        }
    }

    private void setSpinnerSelection6(String time2) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) working_time_end.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(time2);
            if (position != -1) {
                working_time_end.setSelection(position);
            } else {
                Log.e("tag", "해당 작물을 찾을 수 없습니다: " + time2);
            }
        }
    }

    public void jobPostUpdate() {
        //공고제목 에딧
        String text_title = job_post_title.getText().toString();

        //작업지역 스피너
        String text_workProvince = spinnerProvince.getSelectedItem().toString();
        String text_workCity = spinnerCity.getSelectedItem().toString();

        //작업기간 데이트피커
        String text_dateStart = textView_date_start.getText().toString();
        String text_dateEnd = textView_date_end.getText().toString();

        //작업요일 라디오
        String text_workDay = getWorkDay();

        //작업시간 스피너
        String text_workTimeStart = working_time_start.getSelectedItem().toString();
        String text_workTimeEnd = working_time_end.getSelectedItem().toString();

        //급여 에딧
        String text_salaryValue = work_salary_value.getText().toString();
        int pay = Integer.parseInt(text_salaryValue);

        //모집기간 데이트피커
        String text_recruitStart = textView_recruit_start.getText().toString();
        String text_recruitEnd = textView_recruit_end.getText().toString();

        //모집인원 에딧
        String text_recruitNumber = textView_recruit_number.getText().toString();
        int recruitCount = Integer.parseInt(text_recruitNumber);

        //농작물품목 스피너
        String text_workCrop1 = work_crop1.getSelectedItem().toString();
        String text_workCrop2 = work_crop2.getSelectedItem().toString();

        //나이 에딧
        String text_recruitAge = textView_recruit_age.getText().toString();
        int age = Integer.parseInt(text_recruitAge);

        //우대조건 라디오
        String text_preferType = getPrefer();

        //우대조건 에딧
        String text_preferValue = textview_work_prefer.getText().toString();

        //주소
        String text_addressPostcode = address_postcode.getText().toString();
        String text_addressMain = address_main.getText().toString();
        String workAddress = "(" + text_addressPostcode + ") " + text_addressMain;

        String text_addressDetail = address_detail_editView.getText().toString();

        //소개
        String text_introduction = textView_recruit_introduction.getText().toString();

        //이미지
        //imageUri

        JopPostUpdateRequestDTO dto = new JopPostUpdateRequestDTO();

        dto.setJobId(jobId);
        dto.setJobName(text_title);
        dto.setCountry(text_workProvince);
        dto.setCity(text_workCity);
        dto.setStartWorkDate(text_dateStart);
        dto.setEndWorkDate(text_dateEnd);
        dto.setStartWorkTime(text_workTimeStart);
        dto.setEndWorkTime(text_workTimeEnd);
        dto.setWorkDay(text_workDay);
        dto.setPay(pay);
        dto.setStartRecruitDate(text_recruitStart);
        dto.setEndRecruitDate(text_recruitEnd);
        dto.setRecruitCount(recruitCount);
        dto.setCropForm(text_workCrop1);
        dto.setCropType(text_workCrop2);
        dto.setAge(age);
        dto.setCareer(text_preferType);
        dto.setSpec(text_preferValue);
        dto.setJobIntro(text_introduction);
        dto.setWorkAddress(workAddress);
        dto.setWorkAddressDetail(text_addressDetail);

        MultipartBody.Part image = prepareFilePart("uploadImg", imageUri, JobEditPost.this);
//        dto.setUploadImgFile(image);

        Gson gson = GsonLocalDateAdapter.getGson();
        String json = gson.toJson(dto);
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //공고수정 서버요청
        Call<Void> call = apiService.updateJobPost(requestBody, image);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(JobEditPost.this, EmployerStatus.class);
                    startActivity(intent);

                    Log.e("tag", "공고수정 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("tag", "공고수정 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "공고수정 서버응답 오류" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("tag", "공고수정 서버요청", t);
            }
        });
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
            Log.e("tag", "이미지 파일 업로드 오류", e);
            return null;
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), tempFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(partName, tempFile.getName(), requestFile);
//        tempFile.delete();
        return filePart;
    }

    public void loadCountries() {
        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        Call<List<String>> call = apiService.getCountries();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> countries = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobEditPost.this,
                            android.R.layout.simple_spinner_dropdown_item, countries);
                    spinnerProvince.setAdapter(adapter);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobEditPost.this,
                            android.R.layout.simple_spinner_dropdown_item, cities);
                    spinnerCity.setAdapter(adapter);
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

    public void loadCropForms() {
        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        Call<List<String>> call = apiService.getCropForms();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> cropForms = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobEditPost.this,
                            android.R.layout.simple_spinner_dropdown_item, cropForms);
                    work_crop1.setAdapter(adapter);
                } else {
                    Log.e("tag", "품목 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("tag", "품목 서버응답 오류" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("tag", "품목 서버요청 오류", t);
            }
        });
    }

    public void loadCropTypes(String selectedCropForm) {
        ApiService apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        Call<List<String>> call = apiService.getCropTypes(selectedCropForm);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> cropTypes = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobEditPost.this,
                            android.R.layout.simple_spinner_dropdown_item, cropTypes);
                    work_crop2.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("tag", "작물 서버요청 오류", t);
            }
        });
    }


    private void showDatePickerDialog (final TextView textView){
        DatePickerDialog.OnDateSetListener callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String formattedDate = String.format(Locale.getDefault(), "%04d.%02d.%02d", year, monthOfYear + 1, dayOfMonth);
                textView.setText(formattedDate);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, callbackMethod, year, month, day);
        datePickerDialog.show();
    }

    public void onSelectAddressClick (View view){;
        Intent intent = new Intent(JobEditPost.this, AddressActivity.class);
        startActivityForResult(intent, AddressActivity.ADDRESS_REQUEST_CODE);}

    public void onSelectImageClick (View view){;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AddressActivity.ADDRESS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // 주소 데이터를 가져와 표시
                String addressData = data != null ? data.getStringExtra("address") : null;
                String postcodeData = data != null ? data.getStringExtra("postcode") : null;
                address_postcode.setText(postcodeData);
                address_main.setText(addressData);
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                // 이미지를 가져와서 표시
                imageUri = data.getData();
                try {
                    Bitmap bitmap = resizeImage(imageUri, 200, 200);
                    post_ImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private Bitmap resizeImage (Uri imageUri,int targetWidth, int targetHeight) throws IOException {
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

    public String getWorkDay(){

        RadioButton work_mon = (RadioButton)findViewById(R.id.work_mon);
        RadioButton work_tue = (RadioButton)findViewById(R.id.work_tue);
        RadioButton work_wen = (RadioButton)findViewById(R.id.work_wen);
        RadioButton work_thu = (RadioButton)findViewById(R.id.work_thu);
        RadioButton work_fri = (RadioButton)findViewById(R.id.work_fri);

        StringBuilder selectedWorkDayString = new StringBuilder();

        if(work_mon.isChecked()){
            selectedWorkDayString.append("월");
        }
        if(work_tue.isChecked()){
            if(selectedWorkDayString.length() > 0) {
                selectedWorkDayString.append(", ");
            }
            selectedWorkDayString.append("화");
        }
        if(work_wen.isChecked()) {
            if(selectedWorkDayString.length() > 0) {
                selectedWorkDayString.append(", ");
            }
            selectedWorkDayString.append("수");
        }
        if(work_thu.isChecked()) {
            if(selectedWorkDayString.length() > 0) {
                selectedWorkDayString.append(", ");
            }
            selectedWorkDayString.append("목");
        }
        if(work_fri.isChecked()) {
            if(selectedWorkDayString.length() > 0) {
                selectedWorkDayString.append(", ");
            }
            selectedWorkDayString.append("금");
        }

        return selectedWorkDayString.toString();
    }

    public String getPrefer(){
        RadioGroup work_prefer_radioGroup = findViewById(R.id.work_prefer_radioGroup);

        int checkedRadioButtonId = work_prefer_radioGroup.getCheckedRadioButtonId();
        String selectedPreferText = "need";

        if (checkedRadioButtonId == R.id.work_salary1) {
            selectedPreferText = "need";
        } else if (checkedRadioButtonId == R.id.work_salary2) {
            selectedPreferText = "needless";
        }
        return selectedPreferText;
    }

}


