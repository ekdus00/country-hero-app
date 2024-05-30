package com.example.hero.setting.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hero.faq.activity.FAQActivity;
import com.example.hero.userguide.activity.UserGuideActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hero.databinding.ActivitySettingBinding;

import com.example.hero.R;

import org.w3c.dom.Text;

public class SettingActivity extends AppCompatActivity {


    LinearLayout backBtn;
    TextView userGuideBtn;
    TextView faqBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userGuideBtn = findViewById(R.id.user_guide_btn);
        userGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UserGuideActivity.class);
                startActivity(intent);
            }
        });
        faqBtn = findViewById(R.id.faq_btn);
        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, FAQActivity.class);
                startActivity(intent);
            }
        });

    }
}