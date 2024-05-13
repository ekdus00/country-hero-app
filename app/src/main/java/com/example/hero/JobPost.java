package com.example.hero;

//import static com.example.hero.JobList.requestQueue;

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

import com.example.hero.dto.JobPostCreateRequestDTO;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class JobPost extends AppCompatActivity {
    private TextView textView_Work_Start;
    private TextView textView_Work_End;
    public ImageButton btn_Work_Start;
    public ImageButton btn_Work_End;
    private TextView textView_Recruit_Start;
    private TextView textView_Recruit_End;
    public ImageButton btn_Recruit_Start;
    public ImageButton btn_Recruit_End;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView post_ImageView;

    private Button btn_send;

    public TextView job_post_title;
    public TextView textView_date_start;
    public TextView textView_date_end;
    public TextView work_salary_value;
    public TextView textView_recruit_start;
    public TextView textView_recruit_end;
    public TextView textView_recruit_number;
    public TextView textView_recruit_age;
    public TextView textView_recruit_introduction;
    public TextView textview_work_prefer;

    public TextView address_main;
    public TextView address_postcode;

    public TextView address_detail_editView;

    String selectedSalaryText = "";
    String selectedPreferText = "";

    private Spinner spinnerProvince;
    private Spinner spinnerCity;
    private Spinner working_time_start;
    private Spinner working_time_end;
    private Spinner work_crop1;
    private Spinner work_crop2;

    String workStartTime = "";
    String workEndTime = "";

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_post);

        Button btn_Back = findViewById(R.id.btn_back);
        btn_send = findViewById(R.id.job_post_send);

        btn_Work_Start = findViewById(R.id.start_work);
        btn_Work_End = findViewById(R.id.end_work);

        textView_Work_Start = findViewById(R.id.textView_date_start);
        textView_Work_End = findViewById(R.id.textView_date_end);
        btn_Recruit_Start = findViewById(R.id.start_recruit);
        btn_Recruit_End = findViewById(R.id.end_recruit);
        textView_Recruit_Start = findViewById(R.id.textView_recruit_start);
        textView_Recruit_End = findViewById(R.id.textView_recruit_end);

        post_ImageView = findViewById(R.id.post_image_imageView);

        spinnerProvince = findViewById(R.id.spinner_province);
        spinnerCity= findViewById(R.id.spinner_city);

        job_post_title = findViewById(R.id.job_post_title);

        textView_date_start = findViewById(R.id.textView_date_start);
        textView_date_end = findViewById(R.id.textView_date_end);

        working_time_start = findViewById(R.id.working_time_start);
        working_time_end = findViewById(R.id.working_time_end);

        work_salary_value = findViewById(R.id.work_salary_value);

        textView_recruit_start = findViewById(R.id.textView_recruit_start);
        textView_recruit_end = findViewById(R.id.textView_recruit_end);

        textView_recruit_number = findViewById(R.id.textView_recruit_number);
        textview_work_prefer = findViewById(R.id.textview_work_prefer);

        work_crop1 = findViewById(R.id.work_crop1);
        work_crop2 = findViewById(R.id.work_crop2);

        textView_recruit_age = findViewById(R.id.textView_recruit_age);
        textView_recruit_introduction = findViewById(R.id.textView_recruit_introduction);

        address_detail_editView= findViewById(R.id.address_detail_editView);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //공고제목 에딧
                String text_title = job_post_title.getText().toString();

                //작업지역 스피너
                String text_workProvince = spinnerProvince.getSelectedItem().toString();
                String text_workCity = spinnerCity.getSelectedItem().toString();

                //작업기간 데이트피커

                String text_dateStart = textView_date_start.getText().toString();
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate startWorkDate = LocalDate.parse(text_dateStart, formatter1);

                String text_dateEnd = textView_date_end.getText().toString();
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate endWorkDate = LocalDate.parse(text_dateEnd, formatter2);

                //작업요일 라디오
                String text_workDay = getWorkDay();

                //작업시간 스피너
                String text_workTimeStart = working_time_start.getSelectedItem().toString();
                DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime startWorkTime = LocalTime.parse(text_workTimeStart, formatter3);

                String text_workTimeEnd = working_time_end.getSelectedItem().toString();
                DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime endWorkTime = LocalTime.parse(text_workTimeEnd, formatter4);

                //급여 에딧
                String text_salaryValue = work_salary_value.getText().toString();
                int pay = Integer.parseInt(text_salaryValue);

                //모집기간 데이트피커
                String text_recruitStart = textView_recruit_start.getText().toString();
                DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate startRecruitDate = LocalDate.parse(text_recruitStart, formatter5);

                String text_recruitEnd = textView_recruit_end.getText().toString();
                DateTimeFormatter formatter6 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate endRecruitDate = LocalDate.parse(text_recruitEnd, formatter6);

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

                JobPostCreateRequestDTO jobPostDTO = new JobPostCreateRequestDTO();

                jobPostDTO.setUserId("user123");
                jobPostDTO.setUserType("구인자");
                jobPostDTO.setJobName(text_title);
                jobPostDTO.setCountry(text_workProvince);
                jobPostDTO.setCity(text_workCity);
                jobPostDTO.setStartWorkDate(startWorkDate);
                jobPostDTO.setEndWorkDate(endWorkDate);
                jobPostDTO.setStartWorkTime(startWorkTime);
                jobPostDTO.setEndWorkTime(endWorkTime);
                jobPostDTO.setWorkDay(text_workDay);
                jobPostDTO.setPay(pay);
                jobPostDTO.setStartRecruitDate(startRecruitDate);
                jobPostDTO.setEndRecruitDate(endRecruitDate);
                jobPostDTO.setRecruitCount(recruitCount);
                jobPostDTO.setCropForm(text_workCrop1);
                jobPostDTO.setCropType(text_workCrop2);
                jobPostDTO.setAge(age);
                jobPostDTO.setCareer(text_preferType);
                jobPostDTO.setSpec(text_preferValue);
                jobPostDTO.setJobIntro(text_introduction);
                jobPostDTO.setWorkAddress(workAddress);
                jobPostDTO.setWorkAddressDetail(text_addressDetail);
                jobPostDTO.setUploadImgFile(imageUri);

                Gson gson = new Gson();
                String json = gson.toJson(jobPostDTO);
                RequestBody requestBody = RequestBody.create(json, okhttp3.MediaType.parse("application/json; charset=utf-8"));

                ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

                MultipartBody.Part body = prepareFilePart("image", imageUri, JobPost.this);
                Call<ResponseBody> call = apiService.JobPostSend(requestBody, body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                        } else {
                            Log.e("JobList", "공고작성 서버응답 오류코드" + response.code() + ", " + response.message());
                            Log.e("JobPost", "공고작성 서버응답 오류" + response.errorBody());                        }
                        }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("JobPost", "공고작성 서버요청 오류", t);
                    }
                });
            }
        });

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

        loadCountries();
        loadCropForms();

        ArrayAdapter<CharSequence> adapterStart = ArrayAdapter.createFromResource(this,
                R.array.work_time1, android.R.layout.simple_spinner_item);
        adapterStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        working_time_start.setAdapter(adapterStart);

        ArrayAdapter<CharSequence> adapterEnd = ArrayAdapter.createFromResource(this,
                R.array.work_time2, android.R.layout.simple_spinner_item);
        adapterEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        working_time_end.setAdapter(adapterEnd);

        address_postcode = findViewById(R.id.address_postcode);
        address_main = findViewById(R.id.address_main);

//        address_btn.setOnClickListener(v -> {
//            Intent intent = new Intent(JobPost.this, AddressActivity.class);
//            startActivityForResult(intent, AddressActivity.ADDRESS_REQUEST_CODE);
//        });

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_Work_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textView_Work_Start);
            }
        });
        btn_Work_End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textView_Work_End);
            }
        });
        btn_Recruit_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textView_Recruit_Start);
            }
        });
        btn_Recruit_End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textView_Recruit_End);
            }
        });

    } //onCreate()


    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri, Context context) {
        File cacheDir = context.getCacheDir();
        File tempFile = new File(cacheDir, "upload.jpg");
        Log.d("File Check", "File exist before stream: " + tempFile.exists());

        try {
            // 파일이 존재하지 않는 경우, 새로운 파일을 생성합니다.
            if (!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if (created) {
                    Log.d("File Creation", "File created successfully.");
                } else {
                    Log.e("File Creation", "Failed to create file.");
                }
            }
        } catch (IOException e) {
            Log.e("File Creation", "Error creating file", e);
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
            e.printStackTrace();
        }
        // 파일을 RequestBody로 변환
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), tempFile);

//        // 파일 사용 후 삭제
//        tempFile.delete();

        // MultipartBody.Part 생성 및 반환
        return MultipartBody.Part.createFormData(partName, tempFile.getName(), requestFile);
    }


//    private Map<String, RequestBody> prepareTextMap(
//            String text_title, String text_workProvince, String text_workCity, String text_dateStart, String text_dateEnd, String text_workDay, String text_workTimeStart, String text_workTimeEnd,
//            String text_salaryType, String text_salaryValue, String text_recruitStart, String text_recruitEnd, String text_recruitNumber,
//            String text_workCrop1, String text_workCrop2, String text_recruitAge, String text_preferType, String text_preferValue,
//            String text_addressMain, String text_addressDetail, String text_introduction) {
//
//            Map<String, RequestBody> data = new HashMap<>();
//
//            data.put("key_text_title", RequestBody.create(MediaType.parse("text/plain"), text_title));
//            data.put("key_text_workProvince", RequestBody.create(MediaType.parse("text/plain"), text_workProvince));
//            data.put("key_text_workCity", RequestBody.create(MediaType.parse("text/plain"), text_workCity));
//
//            data.put("key_text_dateStart", RequestBody.create(MediaType.parse("text/plain"), text_dateStart));
//            data.put("key_text_dateEnd", RequestBody.create(MediaType.parse("text/plain"), text_dateEnd));
//            data.put("key_text_workDay", RequestBody.create(MediaType.parse("text/plain"), text_workDay));
//            data.put("key_text_workTimeStart", RequestBody.create(MediaType.parse("text/plain"), text_workTimeStart));
//            data.put("key_text_salaryType", RequestBody.create(MediaType.parse("text/plain"), text_salaryType));
//            data.put("key_text_workTimeEnd", RequestBody.create(MediaType.parse("text/plain"), text_workTimeEnd));
//            data.put("key_text_salaryValue", RequestBody.create(MediaType.parse("text/plain"), text_salaryValue));
//            data.put("key_text_recruitStart", RequestBody.create(MediaType.parse("text/plain"), text_recruitStart));
//            data.put("key_text_recruitEnd", RequestBody.create(MediaType.parse("text/plain"), text_recruitEnd));
//            data.put("key_text_recruitNumber", RequestBody.create(MediaType.parse("text/plain"), text_recruitNumber));
//
//            data.put("key_text_workCrop1", RequestBody.create(MediaType.parse("text/plain"), text_workCrop1));
//            data.put("key_text_workCrop2", RequestBody.create(MediaType.parse("text/plain"), text_workCrop2));
//
//            data.put("key_text_recruitAge", RequestBody.create(MediaType.parse("text/plain"), text_recruitAge));
//            data.put("key_text_preferType", RequestBody.create(MediaType.parse("text/plain"), text_preferType));
//            data.put("key_text_preferValue", RequestBody.create(MediaType.parse("text/plain"), text_preferValue));
//            data.put("key_text_addressMain", RequestBody.create(MediaType.parse("text/plain"), text_addressMain));
//            data.put("key_text_addressDetail", RequestBody.create(MediaType.parse("text/plain"), text_addressDetail));
//            data.put("key_text_introduction", RequestBody.create(MediaType.parse("text/plain"), text_introduction));
//
//            return data;
//    }

//    public void JobPostSend(JobPostCreateRequestDTO ) {
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        MultipartBody.Part filePart = prepareFilePart("image", imageUri);
//
//        Call<Void> call = apiService.JobPostSend(requestBody, body);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    System.out.println("Data uploaded successfully");
//                } else {
//                    System.out.println("Upload failed: " + response.message());
//                }
//            }
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                System.out.println("Error: " + t.getMessage());
//            }
//        });
//    }

    public void loadCountries() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<String>> call = apiService.getCountries();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> countries = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobPost.this,
                            android.R.layout.simple_spinner_dropdown_item, countries);
                    spinnerProvince.setAdapter(adapter);
                } else {
                    Log.e("JobList", "시/도 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("JobPost", "시/도 서버응답 오류" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("JobPost", "시/도 서버요청 오류", t);
            }
        });
    }

    public void loadCities(String country) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<String>> call = apiService.getCitiesByCountry(country);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> cities = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobPost.this, android.R.layout.simple_spinner_dropdown_item, cities);
                    spinnerCity.setAdapter(adapter);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("JobPost", "시/군/구 서버응답 오류" + response.errorBody());
                    } catch (IOException e) {
                        Log.e("JobPost", "시/군/구 서버응답 오류", e);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("JobPost", "시/군/구 서버요청 오류", t);
            }
        });
    }

    public void loadCropForms() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<String>> call = apiService.getCropForms();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> cropForms = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobPost.this, android.R.layout.simple_spinner_dropdown_item, cropForms);
                    work_crop1.setAdapter(adapter);
                } else {
                    Log.e("JobList", "품목 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("JobPost", "품목 서버응답 오류" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("JobPost", "품목 서버요청 오류", t);
            }
        });
    }

    public void loadCropTypes(String selectedCropForm) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<String>> call = apiService.getCropTypes(selectedCropForm);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> cropTypes = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobPost.this, android.R.layout.simple_spinner_dropdown_item, cropTypes);
                    work_crop2.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("JobPost", "작물 서버요청 오류", t);
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
        Intent intent = new Intent(JobPost.this, AddressActivity.class);
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

    public String getSalary(){
        RadioGroup work_salary_radioGroup = findViewById(R.id.work_salary_radioGroup);

        int checkedRadioButtonId = work_salary_radioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.work_salary1) {
            selectedSalaryText = "일급";
        } else if (checkedRadioButtonId == R.id.work_salary2) {
            selectedSalaryText = "협의";
        }
        return selectedSalaryText;
    }

    public String getPrefer(){
        RadioGroup work_prefer_radioGroup = findViewById(R.id.work_prefer_radioGroup);

        int checkedRadioButtonId = work_prefer_radioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.work_salary1) {
            selectedPreferText = "필요";
        } else if (checkedRadioButtonId == R.id.work_salary2) {
            selectedPreferText = "무관";
        }
        return selectedPreferText;
    }

}


