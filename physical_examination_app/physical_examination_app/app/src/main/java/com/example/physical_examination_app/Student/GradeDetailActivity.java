package com.example.physical_examination_app.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.physical_examination_app.R;

public class GradeDetailActivity extends AppCompatActivity {

    private ImageView returnButton;

    private TextView gradeLocation;
    private TextView gradeTeacher;
    private TextView gradeStatus;
    private TextView gradeDate;
    private TextView jumpLong;
    private TextView fiftyRun;

    private TextView longRunName;
    private TextView extraEventName;
    private TextView longRun;
    private TextView extraEvent;

    private TextView sitBending;
    private TextView lungContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_detail);

        getViews();

        otherOperating();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //其他操作
    private void otherOperating() {

        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        String sex = pre.getString("sex","");
        if (sex.equals("女")){
            longRunName.setText("800米跑");
            extraEventName.setText("仰卧起坐");
        }else {
            longRunName.setText("1000米跑");
            extraEventName.setText("引体向上");
        }

        Intent intent = getIntent();
        gradeStatus.setText(intent.getStringExtra("grade_status"));
        gradeTeacher.setText(intent.getStringExtra("grade_teacher"));
        gradeDate.setText(intent.getStringExtra("grade_date"));
        jumpLong.setText(intent.getStringExtra("jump_long"));
        fiftyRun.setText(intent.getStringExtra("fifty_run"));
        longRun.setText(intent.getStringExtra("long_run"));
        extraEvent.setText(intent.getStringExtra("extra_event"));
        sitBending.setText(intent.getStringExtra("sit_bending"));
        lungContainer.setText(intent.getStringExtra("lung_container"));
        gradeLocation.setText(intent.getStringExtra("grade_location"));

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);

        gradeLocation = findViewById(R.id.grade_location);
        gradeTeacher = findViewById(R.id.grade_teacher);
        gradeStatus = findViewById(R.id.grade_status);
        gradeDate = findViewById(R.id.grade_date);
        jumpLong = findViewById(R.id.jump_long);
        fiftyRun = findViewById(R.id.fifty_run);

        longRunName = findViewById(R.id.long_run_name);
        extraEventName = findViewById(R.id.extra_event_name);
        longRun = findViewById(R.id.long_run);
        extraEvent = findViewById(R.id.extra_event);

        sitBending = findViewById(R.id.sit_bending);
        lungContainer = findViewById(R.id.lung_container);

    }

}