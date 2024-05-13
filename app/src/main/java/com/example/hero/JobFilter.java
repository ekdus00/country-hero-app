package com.example.hero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobFilter extends AppCompatActivity{
    private TextView textView_Work_Start;
    private TextView textView_Work_End;
    public ImageButton btn_Work_Start;
    public ImageButton btn_Work_End;
    private Spinner filter_area_region;
    private Spinner filter_area_district;
    private Spinner filter_crop1;
    private Spinner filter_crop2;
    private TextView filter_salary_low;
    private TextView filter_salary_high;
    private TextView filter_period_start;
    private TextView filter_period_end;
    public Button filter_send_btn;
    String selectedSalaryText = "";
    public RadioGroup radioGroupSalary;
    private ArrayList<String> area = new ArrayList<>();
    private ArrayList<String> crop = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_filter);

        btn_Work_Start = findViewById(R.id.filter_start_image);
        btn_Work_End = findViewById(R.id.filter_end_image);
        textView_Work_Start = findViewById(R.id.filter_period_start);
        textView_Work_End = findViewById(R.id.filter_period_end);

        Button filter_back_btn = findViewById(R.id.filter_back_btn);

        loadCountries();
        loadCropForms();

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

        filter_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filter_area_region = findViewById(R.id.filter_area_region);
        filter_area_district = findViewById(R.id.filter_area_district);
        filter_crop1 = findViewById(R.id.filter_crop1);
        filter_crop2 = findViewById(R.id.filter_crop2);

        filter_salary_low = findViewById(R.id.filter_salary_low);
        filter_salary_high = findViewById(R.id.filter_salary_high);

        filter_period_start = findViewById(R.id.filter_period_start);
        filter_period_end = findViewById(R.id.filter_period_end);

        radioGroupSalary = findViewById(R.id.radioGroupSalary);

        filter_send_btn = findViewById(R.id.filter_send_btn);

        filter_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedAreaA = filter_area_region.getSelectedItem().toString();
                String selectedAreaB = filter_area_district.getSelectedItem().toString();
                area.add(selectedAreaA);
                area.add(selectedAreaB);

                String selectedCropA = filter_crop1.getSelectedItem().toString();
                String selectedCropB = filter_crop2.getSelectedItem().toString();
                crop.add(selectedCropA);
                crop.add(selectedCropB);

                Intent intent = new Intent(JobFilter.this, JobList.class);

                // 사용자 아이디, 타입
//                intent.putExtra("userId", filter_area_region.getSelectedItem().toString());
//                intent.putExtra("userType", filter_area_district.getSelectedItem().toString());
                
                //임시
                intent.putExtra("userId", "keh223377");
                intent.putExtra("userType", "구인자");

                intent.putStringArrayListExtra("area", area);
                intent.putStringArrayListExtra("crop", crop);

                intent.putExtra("payLoe", filter_salary_low.getText().toString());
                intent.putExtra("payGoe", filter_salary_high.getText().toString());

                intent.putExtra("startWorkDate", filter_period_start.getText().toString());
                intent.putExtra("endWorkDate", filter_period_end.getText().toString());

                startActivity(intent);

            }
        });

        filter_area_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        filter_crop1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCropForm = parent.getItemAtPosition(position).toString();
                loadCropTypes(selectedCropForm);  // 농작물 품목 로드
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button resetButton = findViewById(R.id.filter_reset_btn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetJobFilter();
            }
        });

    }

    public void loadCountries() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<String>> call = apiService.getCountries();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> countries = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobFilter.this,
                            android.R.layout.simple_spinner_dropdown_item, countries);
                    filter_area_region.setAdapter(adapter);
                } else {
                    Log.e("API_CALL", "Response error: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("JobFilter", "시/도 에러 발생", t);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobFilter.this, android.R.layout.simple_spinner_dropdown_item, cities);
                    filter_area_district.setAdapter(adapter);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("API_CALL", "Response error: " + errorBody);
                    } catch (IOException e) {
                        Log.e("API_CALL", "Error reading error body", e);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("API_CALL", "Failed to fetch cities", t);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobFilter.this, android.R.layout.simple_spinner_dropdown_item, cropForms);
                    filter_crop1.setAdapter(adapter);
                } else {
                    Log.e("API_CALL", "Response error: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("API_CALL", "Error fetching crop forms", t);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JobFilter.this, android.R.layout.simple_spinner_dropdown_item, cropTypes);
                    filter_crop2.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("API_CALL", "Error fetching crop types", t);
            }
        });
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

    private void resetJobFilter() {
        filter_area_region.setSelection(0);
        filter_area_district.setSelection(0);
        filter_crop1.setSelection(0);
        filter_crop2.setSelection(0);

        radioGroupSalary.clearCheck();

        filter_salary_low.setText("");
        filter_salary_high.setText("");

        filter_period_start.setText("");
        filter_period_end.setText("");
    }

}
