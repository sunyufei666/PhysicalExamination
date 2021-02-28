package com.example.physical_examination_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.physical_examination_app.admin.AdminActivity;
import com.example.physical_examination_app.student.StudentActivity;

public class UserLoginActivity extends AppCompatActivity {

    private String role;
    private EditText userName;
    private EditText userPassword;
    private ImageView userImage;
    private Button loginButton;

    private Handler loginHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            if(result.equals("success")){
                Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                if(role.equals("student")){
                    intent.setClass(getApplicationContext(),StudentActivity.class);
                    startActivity(intent);
                }else if(role.equals("admin")){
                    intent.setClass(getApplicationContext(), AdminActivity.class);
                    startActivity(intent);
                }
            }else if(result.equals("fail")){
                Toast.makeText(getApplicationContext(),"登录失败，请输入正确的信息！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        setStatusBar();
        getViews();

        //根据action判断是学生登录还是管理登录
        Intent intent = getIntent();
        role = intent.getAction();

        setViewsAndGetResult();

    }

    //根据传过来的action值改变视图的内容
    private void setViewsAndGetResult() {
        SpannableString hintString;
        if(role.equals("student")){
            //动态设置hint的值
            hintString = new SpannableString("学号");
            //动态设置输入框上的图片
            userImage.setImageResource(R.mipmap.student_login);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //创建线程访问后台，返回登录结果
                    new Thread(){
                        @Override
                        public void run() {
                            String result = new Utils().getConnectionResult("loginController","userLogin",
                                    "username="+userName.getText().toString()+
                                            "&&password="+userPassword.getText().toString()+
                                            "&&role="+role);
                            Message message = new Message();
                            message.obj = result;
                            loginHandler.sendMessage(message);
                        }
                    }.start();
                }
            });

        }else if(role.equals("admin")){
            hintString = new SpannableString("账号");
            userImage.setImageResource(R.mipmap.admin_login);
        }
    }

    //获取视图控件
    private void getViews() {
        userName = findViewById(R.id.user_name);
        userPassword = findViewById(R.id.user_password);
        userImage = findViewById(R.id.user_image);
        loginButton = findViewById(R.id.login_button);
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