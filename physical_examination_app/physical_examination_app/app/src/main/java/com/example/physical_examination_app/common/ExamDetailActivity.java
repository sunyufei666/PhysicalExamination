package com.example.physical_examination_app.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Utils;

public class ExamDetailActivity extends AppCompatActivity {

    private ImageView returnButton;

    private TextView barTitle;
    private TextView examLocation;
    private TextView examTeacher;
    private TextView examPeople;
    private TextView examEvent;
    private TextView examStatus;
    private TextView examDate;
    private TextView releaseDate;

    private Button finishButton;
    private Button reserveButton;

    private LinearLayout itemStatusLayout;
    private LinearLayout itemExamLayout;
    private LinearLayout examBackground;
    private LinearLayout itemButtonLayout;

    private Handler reserveExamHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            if (result.equals("success")){
                Toast.makeText(getApplicationContext(),"预约成功，请您在规定的时间进行考试！",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"预约失败，系统可能出现了错误，请您稍后再试！",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };

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

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向服务器发送数据请求进行预约
                new Thread(){
                    @Override
                    public void run() {

                        String param = "student_id=" + getSharedPreferences("userInfo",MODE_PRIVATE).getString("id","")
                                + "&&" + "exam_id=" + getIntent().getStringExtra("id");
                        String result = new Utils().getConnectionResult("studentExamController","studentReserveExam",param);
                        Message message = new Message();
                        message.obj = result;
                        reserveExamHandler.sendMessage(message);
                    }
                }.start();
            }
        });


        setExamInfoText();

    }

    //设置考试信息
    private void setExamInfoText() {

        Intent intent = getIntent();
        String action = intent.getAction();
        String location = intent.getStringExtra("location");
        if(action.equals("考试详情")){
            releaseDate.setText(intent.getStringExtra("release_date"));
            examDate.setText(intent.getStringExtra("date"));
        }else if(action.equals("考试预约")){
            itemExamLayout.setVisibility(View.GONE);
            releaseDate.setText(intent.getStringExtra("date"));
        }
        if(location.equals("东操场")){
            examBackground.setBackground(getResources().getDrawable(R.drawable.ground2));
        }
        examLocation.setText(location);
        examTeacher.setText(intent.getStringExtra("teacher"));
        examPeople.setText(intent.getStringExtra("people"));
        examEvent.setText(intent.getStringExtra("event"));

        barTitle.setText(action);

        if(intent.getStringExtra("status").equals("")){
            itemButtonLayout.setVisibility(View.VISIBLE);
            itemStatusLayout.setVisibility(View.GONE);
        }else {
            examStatus.setText(intent.getStringExtra("status"));
        }

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);

        barTitle = findViewById(R.id.bar_title);
        examLocation = findViewById(R.id.exam_location);
        examTeacher = findViewById(R.id.exam_teacher);
        examPeople = findViewById(R.id.exam_people);
        examEvent = findViewById(R.id.exam_event);
        examStatus = findViewById(R.id.exam_status);
        examDate = findViewById(R.id.exam_date);
        releaseDate = findViewById(R.id.release_date);

        finishButton = findViewById(R.id.finish_button);
        reserveButton = findViewById(R.id.reserve_button);

        itemStatusLayout = findViewById(R.id.item_status_layout);
        itemExamLayout = findViewById(R.id.item_exam_layout);
        examBackground = findViewById(R.id.exam_background);
        itemButtonLayout = findViewById(R.id.item_button_layout);

    }

}