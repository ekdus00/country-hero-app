package com.example.hero.etc;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hero.employer.activity.EmployerStatusDetail;
import com.example.hero.job.activity.JobList;
import com.example.hero.job.activity.JobPost;
import com.example.hero.login.activity.Join;
import com.example.hero.login.activity.Login;
import com.example.hero.login.activity.RefreshToken;
import com.example.hero.login.activity.UserTypeSet;
import com.example.hero.mypage.activity.MyPageApplicant;
import com.example.hero.mypage.activity.MyPageRecruiter;
import com.example.hero.R;
import com.example.hero.review.activity.ReviewEmployerPost;
import com.example.hero.review.activity.ReviewPost;
import com.example.hero.home.activity.HomeApplicant;
import com.example.hero.home.activity.HomeRecruiter;
import com.example.hero.job.activity.JobDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button homeOwner = (Button) findViewById(R.id.homeOwner);
        Button homeWorker = (Button) findViewById(R.id.homeWorker);
        Button jobList = (Button) findViewById(R.id.jobList);
        Button token = (Button) findViewById(R.id.token);
        Button jobPost = (Button) findViewById(R.id.jobPost);
        Button jobDetail = (Button) findViewById(R.id.jobDetail);
        Button review1 = (Button) findViewById(R.id.review1);
        Button review2 = (Button) findViewById(R.id.review2);
        Button userType = (Button) findViewById(R.id.userType);
        Button login = (Button) findViewById(R.id.login);
        Button my1 = (Button) findViewById(R.id.my1);

        homeOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeRecruiter.class);
                startActivity(intent);
            }
        });

        homeWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeApplicant.class);
                startActivity(intent);
            }
        });

        jobList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobList.class);
                startActivity(intent);
            }
        });

        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RefreshToken.class);
                startActivity(intent);
            }
        });

        jobPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobPost.class);
                startActivity(intent);
            }
        });

        jobDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobDetail.class);
                startActivity(intent);
            }
        });

        review1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewPost.class);
                startActivity(intent);
            }
        });

        review2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewEmployerPost.class);
                startActivity(intent);
            }
        });

        userType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserTypeSet.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        my1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageRecruiter.class);
                startActivity(intent);
            }
        });

    } //oncreate close




}