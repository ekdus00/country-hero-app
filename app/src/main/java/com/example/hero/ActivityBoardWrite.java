package com.example.hero;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ActivityBoardWrite extends AppCompatActivity {

    LinearLayout backBtn; // 뒤로가기 버튼
    String[] regionals = {"도", "경남"};
    String[] citys = {"시", "창원시", "밀양시", "마산시"};

    ArrayAdapter<String> adapterRegionals;
    ArrayAdapter<String> adapterCitys;

    TextView txt_startdate,txt_enddate;


    Spinner city_spinner,regional_spinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardwrite);

        backBtn = findViewById(R.id.back_btn); // 뒤로가기 버튼의 객체(linear layout)를 id로 찾아서 받아옴
        backBtn.setOnClickListener(new View.OnClickListener() { // 뒤로가기 버튼(linear layout)에 클릭 이벤트를 달아줌
            @Override
            public void onClick(View view) {
                onBackPressed();
            } // 클릭하면 뒤로가기 동작이 실행됨
        });

        //클릭시 기간 선택창
        findViewById(R.id.data_picker).setOnClickListener(v ->{
            DatePickerdialog();
        });

        //뷰 초기화
        city_spinner = findViewById(R.id.city_spinner);
        regional_spinner = findViewById(R.id.regional_spinner);
        txt_startdate = findViewById(R.id.txt_startdate);
        txt_enddate = findViewById(R.id.txt_enddate);

        //스피너 어댑터 초기화
        adapterRegionals = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, regionals);
        adapterCitys = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, citys);

        //스피너 어댑터 설정
        adapterRegionals.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCitys.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //스피너에 어댑터 적용
        city_spinner.setAdapter(adapterCitys);
        regional_spinner.setAdapter(adapterRegionals);

        //도 ,시로 미리 골라놓기
        city_spinner.setSelection(0, false);
        regional_spinner.setSelection(0, false);
    }

    //기간 선택창 띄우는 함수
    private void DatePickerdialog() {

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("기간을 선택해 주세요");
        builder.setTheme(R.style.AppTheme);

        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();

        datePicker.addOnPositiveButtonClickListener(selection -> {

            Long startDate = selection.first;
            Long endDate = selection.second;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
            String startDateString = sdf.format(new Date(startDate));
            String endDateString = sdf.format(new Date(endDate));

            txt_startdate.setText(startDateString);
            txt_enddate.setText(endDateString);

        });
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}

