package com.example.physical_examination_app.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.physical_examination_app.Admin.AdminMainActivity;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Student.StudentMainActivity;

public class LoginActivity extends AppCompatActivity {

    private Button studentLoginButton;
    private Button adminLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setStatusBar();
        getViews();
        setButtons();
    }

    //设置button的点击事件
    private void setButtons() {

        studentLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), UserLoginActivity.class);
                intent.setAction("student");
                startActivity(intent);
            }
        });

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), UserLoginActivity.class);
                intent.setAction("admin");
                startActivity(intent);
            }
        });

    }

    //获得button的视图控件
    private void getViews() {
        studentLoginButton = findViewById(R.id.student_login_button);
        adminLoginButton = findViewById(R.id.admin_login_button);
    }

    //修改状态栏的状态
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色

        }
    }

}