package com.example.hero.resume.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hero.R;
import com.example.hero.employer.activity.JobEditPost;
import com.example.hero.etc.ApiService;
import com.example.hero.etc.RetrofitClient;
import com.example.hero.etc.TokenManager;
import com.example.hero.job.activity.JobPost;
import com.example.hero.mypage.activity.ModifyOwner;
import com.example.hero.mypage.activity.MyPageOwner;
import com.example.hero.mypage.activity.MyPageWorker;
import com.example.hero.mypage.dto.OwnerUserInfoResponseDTO;
import com.example.hero.mypage.dto.OwnerUserInfoUpdateRequestDTO;
import com.example.hero.resume.adapter.CareerAdapter;
import com.example.hero.resume.dto.ResumeEditResponseDTO;
import com.example.hero.resume.dto.ResumeUpdateRequestDTO;
import com.example.hero.review.adatper.ReviewEmployerListAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumePost extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView resume_image_imageView;
    private EditText resume_career_edit, resume_info;
    private TextView resume_review_result, resume_name, resume_post_imageName;
    private TokenManager tokenManager;
    private ApiService apiService;
    private Uri imageUri;
    private CareerAdapter adapter;
    private Button resume_career_btn, resume_send;
    private RecyclerView resume_career_recyclerView;
    private List<String> resumePostList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_post);
        tokenManager = new TokenManager(this);
        resume_career_btn = findViewById(R.id.resume_career_btn);

        resume_info = findViewById(R.id.resume_info);
        resume_image_imageView = findViewById(R.id.resume_image_imageView);
        resume_post_imageName = findViewById(R.id.resume_post_imageName);
        resume_review_result = findViewById(R.id.resume_review_result);
        resume_name = findViewById(R.id.resume_name);

        resume_career_edit = findViewById(R.id.resume_career_edit);
        resume_career_recyclerView = findViewById(R.id.resume_career_recyclerView);

        adapter = new CareerAdapter(resumePostList);
        resume_career_recyclerView.setAdapter(adapter);
        resume_career_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resume_career_btn.setOnClickListener(v -> {
            Log.d("ResumePost", "Button Clicked");
            String text = resume_career_edit.getText().toString();
            if (!text.isEmpty()) {

                resumePostList.add(text);
                adapter.notifyDataSetChanged();
                resume_career_edit.setText("");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("이력서 관리");

        resumeGet();

        //이력서 수정 완료
        Button resume_send = findViewById(R.id.resume_send);
        resume_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumePost();
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

    public void resumePost() {
        String text_resume_info = resume_info.getText().toString();

        MultipartBody.Part image = prepareFilePart("uploadImg", imageUri, ResumePost.this);

        ResumeUpdateRequestDTO dto = new ResumeUpdateRequestDTO();
        dto.setEtcCareer(resumePostList);
        dto.setUserIntro(text_resume_info);

        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //이력서관리 수정 서버요청
        Call<Void> call = apiService.updateResume(dto, image);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ResumePost.this, MyPageWorker.class);
                    startActivity(intent);

                    Log.e("api", "이력서관리 수정 서버응답 성공" + response.code() + ", " + response.message());
                } else {
                    Log.e("api", "이력서관리 수정 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "회원정보수정(구인자) 수정 서버응답 오류" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("api", "이력서관리 수정 서버요청 오류", t);
            }
        });

    }

    public void resumeGet() {
        apiService = RetrofitClient.getClient(tokenManager).create(ApiService.class);

        //이력서관리 조회 서버요청
        Call<ResumeEditResponseDTO> call = apiService.getResume();
        call.enqueue(new Callback<ResumeEditResponseDTO>() {
            @Override
            public void onResponse(Call<ResumeEditResponseDTO> call, Response<ResumeEditResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResumeEditResponseDTO dto = response.body();
                    resume_name.setText(dto.getUserName());
                    resume_review_result.setText(String.valueOf(dto.getTotalReviewScore()));
                    resume_info.setText(dto.getUserIntro());

                    List<String> list = dto.getEtcCareer();
//                    resumePostList.clear();  // 기존 목록을 지우고
                    resumePostList.addAll(list);  // 새 데이터를 추가
                    adapter.notifyDataSetChanged();

                    resume_post_imageName.setText(dto.getUploadImgFileName());

                    Log.e("api", "이력서관리 조회 서버응답 성공" + response.code() + ", " + response.message());

                } else {
                    Log.e("api", "이력서관리 조회 서버응답 오류코드" + response.code() + ", " + response.message());
                    Log.e("api", "이력서관리 조회 서버응답 오류" + response.errorBody().toString());                        }
            }
            @Override
            public void onFailure(Call<ResumeEditResponseDTO> call, Throwable t) {
                Log.e("api", "이력서관리 조회 서버요청 오류", t);
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

    public void onSelectImageClick (View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);}

    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = resizeImage(imageUri, 200, 200);
                resume_image_imageView.setImageBitmap(bitmap);
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