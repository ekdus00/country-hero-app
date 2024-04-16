package com.example.hero;

//import static com.example.hero.JobList.requestQueue;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


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

    private EditText editText;
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

    String selectedWorkDayText = "";
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_post);

        Button btn_Back = findViewById(R.id.btn_back);
        btn_send = findViewById(R.id.job_post_send);
        loadProvinces();

//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                makePostRequest();
//            }
//        });

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
        work_crop1 = findViewById(R.id.work_crop1);
        work_crop2= findViewById(R.id.work_crop2);

        job_post_title = findViewById(R.id.job_post_title);
        textView_date_start = findViewById(R.id.textView_date_start);
        textView_date_end = findViewById(R.id.textView_date_end);
        working_time_start = findViewById(R.id.working_time_start);
        working_time_end = findViewById(R.id.working_time_end);
        work_salary_value = findViewById(R.id.work_salary_value);
        textView_recruit_start = findViewById(R.id.textView_recruit_start);
        textView_recruit_end = findViewById(R.id.textView_recruit_end);
        textView_recruit_number = findViewById(R.id.textView_recruit_number);
        work_crop1 = findViewById(R.id.work_crop1);
        work_crop2 = findViewById(R.id.work_crop2);
        textView_recruit_age = findViewById(R.id.textView_recruit_age);
        textView_recruit_introduction = findViewById(R.id.textView_recruit_introduction);

        working_time_start = findViewById(R.id.working_time_start);
        working_time_end= findViewById(R.id.working_time_end);

        ArrayAdapter<CharSequence> adapterStart = ArrayAdapter.createFromResource(this,
                R.array.work_time1, android.R.layout.simple_spinner_item);
        adapterStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        working_time_start.setAdapter(adapterStart);

        ArrayAdapter<CharSequence> adapterEnd = ArrayAdapter.createFromResource(this,
                R.array.work_time2, android.R.layout.simple_spinner_item);
        adapterEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        working_time_end.setAdapter(adapterEnd);

        ArrayAdapter<CharSequence> adapterProvince = ArrayAdapter.createFromResource(this,
                R.array.work_time2, android.R.layout.simple_spinner_item);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapterProvince);

        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this,
                R.array.work_time2, android.R.layout.simple_spinner_item);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapterCity);

        ArrayAdapter<CharSequence> adapterCrop1 = ArrayAdapter.createFromResource(this,
                R.array.work_time2, android.R.layout.simple_spinner_item);
        adapterCrop1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        work_crop1.setAdapter(adapterCrop1);

        ArrayAdapter<CharSequence> adapterCrop2 = ArrayAdapter.createFromResource(this,
                R.array.work_time2, android.R.layout.simple_spinner_item);
        adapterCrop2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        work_crop2.setAdapter(adapterCrop2);



        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobPostSend();
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

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvince = parent.getItemAtPosition(position).toString();
                updateCities(selectedProvince);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    } //onCreate()

    void JobPostSend() {
        Log.w("JobPostRequest","공고작성 폼 보냄");
        try {
            String post_title = job_post_title.getText().toString();
            String textView_date_start = job_post_title.getText().toString();
            String textView_date_end = job_post_title.getText().toString();
            String work_salary_value = job_post_title.getText().toString();
            String textView_recruit_start = job_post_title.getText().toString();
            String textView_recruit_end = job_post_title.getText().toString();
            String textView_recruit_number = job_post_title.getText().toString();
            String textView_recruit_age = job_post_title.getText().toString();
            String textView_recruit_introduction = job_post_title.getText().toString();

            workStartTime = working_time_start.getSelectedItem().toString();
            workEndTime = working_time_end.getSelectedItem().toString();
            spinnerProvince = working_time_start.getSelectedItem().toString();
            spinnerCity = working_time_end.getSelectedItem().toString();
            work_crop1 = working_time_start.getSelectedItem().toString();
            work_crop2 = working_time_end.getSelectedItem().toString();
            
            //+사진, 주소

            selectedWorkDayText = getWorkDay();
            selectedSalaryText = getSalary();
            selectedPreferText = getPrefer();

            CustomTask task = new CustomTask();
            String result = task.execute(post_title, spinnerProvince, spinnerCity, textView_date_start, textView_date_end, workStartTime, workEndTime, work_salary_value, textView_recruit_start, textView_recruit_end, textView_recruit_number, work_crop1, work_crop2, textView_recruit_age, textView_recruit_introduction, selectedWorkDayText, selectedSalaryText, selectedPreferText).get();
            Log.w("받은값", result);

            Intent intent = new Intent(JobPost.this, JobPost.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("서버주소");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
//                sendMsg = "공고명"+strings[0]+"작업지역1"+strings[1]+"작업지역2"+strings[2]+"작업기간1"+strings[3]+"작업기간2"+strings[4]+"작업시간1"+strings[5]+"작업시간2"+strings[6]+"작업지역1"+strings[1]+"작업지역1"+strings[1]+"작업지역1"+strings[1]+"작업지역1"+strings[1]+"작업지역1"+strings[1]+"작업지역1"+strings[1]+"작업지역1"+strings[1];
                osw.write(sendMsg);
                osw.flush();
                
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {    // 에러
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 서버에서 보낸 값 리턴
            return receiveMsg;
        }
    }

    private void loadProvinces() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "서버주소";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    ArrayList<String> country = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            country.add(response.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, country);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProvince.setAdapter(adapter);
                },
                error -> {
                    // Handle error
                });

        queue.add(jsonArrayRequest);
    }

    private void updateCities(String province) {
        String url = "서버주소" + Uri.encode(province);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    ArrayList<String> city = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            city.add(response.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, city);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCity.setAdapter(adapter);
                },
                error -> {
                    // Handle error
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }

    private void loadCrop1() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "서버주소";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    ArrayList<String> country = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            country.add(response.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, country);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProvince.setAdapter(adapter);
                },
                error -> {
                    // Handle error
                });

        queue.add(jsonArrayRequest);
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

    public void onSelectImageClick (View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);}

    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = resizeImage(selectedImageUri, 200, 200);
                post_ImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
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


