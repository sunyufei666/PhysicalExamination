package com.example.physical_examination_app.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.physical_examination_app.R;

public class ExamDetailActivity extends AppCompatActivity {

    private ImageView returnButton;

    private TextView examLocation;
    private TextView examPeople;
    private TextView examEvent;
    private TextView examStatus;
    private TextView examDate;

    private LinearLayout examBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);

        getViews();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setExamInfoText();

    }

    //设置考试信息
    private void setExamInfoText() {

        Intent intent = getIntent();
        examLocation.setText(intent.getStringExtra("location"));
        examPeople.setText(intent.getStringExtra("people"));
        examEvent.setText(intent.getStringExtra("event"));
        examStatus.setText(intent.getStringExtra("status"));
        examDate.setText(intent.getStringExtra("date"));

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);

        examLocation = findViewById(R.id.exam_location);
        examPeople = findViewById(R.id.exam_people);
        examEvent = findViewById(R.id.exam_event);
        examStatus = findViewById(R.id.exam_status);
        examDate = findViewById(R.id.exam_date);

        examBackground = findViewById(R.id.exam_background);

    }

}