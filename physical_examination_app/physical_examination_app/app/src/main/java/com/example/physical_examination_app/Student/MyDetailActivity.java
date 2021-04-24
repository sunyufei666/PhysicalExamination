package com.example.physical_examination_app.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.physical_examination_app.R;

import org.w3c.dom.Text;

public class MyDetailActivity extends AppCompatActivity {

    private ImageView returnButton;

    private TextView university;
    private TextView college;
    private TextView major;
    private TextView nickname;
    private TextView name;
    private TextView sex;
    private TextView sno;
    private TextView grade;
    private TextView classes;
    private TextView registerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_detail);

        getViews();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        studentInfoSet();

    }

    //设置学生的信息
    private void studentInfoSet() {

        SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        university.setText(pre.getString("university",""));
        college.setText(pre.getString("college",""));
        major.setText(pre.getString("major",""));
        nickname.setText(pre.getString("nickname",""));
        name.setText(pre.getString("name",""));
        sex.setText(pre.getString("sex",""));
        sno.setText(pre.getString("sno",""));
        grade.setText(pre.getString("grade",""));
        classes.setText(pre.getString("classes",""));
        registerTime.setText(pre.getString("register_time",""));

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);

        university = findViewById(R.id.university);
        college = findViewById(R.id.college);
        major = findViewById(R.id.major);
        nickname = findViewById(R.id.nickname);
        name = findViewById(R.id.name);
        sex = findViewById(R.id.sex);
        sno = findViewById(R.id.sno);
        grade = findViewById(R.id.grade);
        classes = findViewById(R.id.classes);
        registerTime = findViewById(R.id.register_time);

    }

}