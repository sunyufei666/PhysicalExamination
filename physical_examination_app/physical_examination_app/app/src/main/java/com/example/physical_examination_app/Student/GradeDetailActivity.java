package com.example.physical_examination_app.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.physical_examination_app.R;

public class GradeDetailActivity extends AppCompatActivity {

    private TextView gradeLocation;
    private TextView gradeStatus;
    private TextView gradeDate;
    private TextView jumpLong;
    private TextView fiftyRun;
    private TextView thousandRun;
    private TextView pullUps;
    private TextView sitBending;
    private TextView lungContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_detail);

        getViews();

    }

    //获取视图控件
    private void getViews() {

        gradeLocation = findViewById(R.id.grade_location);
        gradeStatus = findViewById(R.id.grade_status);
        gradeDate = findViewById(R.id.grade_date);
        jumpLong = findViewById(R.id.jump_long);
        fiftyRun = findViewById(R.id.fifty_run);
        thousandRun = findViewById(R.id.thousand_run);
        pullUps = findViewById(R.id.pull_ups);
        sitBending = findViewById(R.id.sit_bending);
        lungContainer = findViewById(R.id.lung_container);

    }

}