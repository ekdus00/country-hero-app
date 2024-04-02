package com.example.hero;

import static com.example.hero.JobList.requestQueue;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
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
    private Button sendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_post);

        editText = findViewById(R.id.job_post_title);
        sendButton = findViewById(R.id.job_post_send);

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

        Button btn_Back = findViewById(R.id.btn_back);

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

//        makePostRequest();

//        if (requestQueue == null){
//            requestQueue = Volley.newRequestQueue(getApplicationContext());
//        }

    }

    public void makePostRequest() {
        String url = "서버주소";

        JSONObject jsonBody = new JSONObject();
        try {
            //임시 JSON 객체
            jsonBody.put("key", "value");
            jsonBody.put("key2", "value2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        println("응답->" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러->" + error.getMessage());
                    }
                }
        );

        request.setShouldCache(false);
        requestQueue.add(request);
        println("요청 보냄");
    }

    public void println(String data) {
        editText.append(data + "/n");
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
    }


