package com.example.physical_examination_app.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.physical_examination_app.Admin.AdminMainActivity;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Student.StudentMainActivity;
import com.example.physical_examination_app.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
            SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor = pre.edit();
            if(result.equals("fail") || result==null){
                Toast.makeText(getApplicationContext(),"登录失败，请输入正确的信息！",Toast.LENGTH_SHORT).show();
            }else{
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject json = jsonArray.getJSONObject(0);
                    Intent intent = new Intent();
                    Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT).show();
                    if(role.equals("student")){
                        editor.putString("id",json.getString("id"));
                        editor.putString("sno",json.getString("sno"));
                        editor.putString("nickname",json.getString("nickname"));
                        editor.putString("sex",json.getString("sex"));
                        editor.putString("university",json.getString("university"));
                        editor.putString("college",json.getString("college"));
                        editor.putString("name",json.getString("name"));
                        editor.putString("grade",json.getString("grade"));
                        editor.putString("classes",json.getString("classes"));
                        editor.putString("introduction",json.getString("introduction"));
                        editor.putString("major",json.getString("major"));
                        editor.putString("register_time",json.getString("register_time"));
                        editor.putString("login_role","student");
                        editor.commit();
                        intent.setClass(getApplicationContext(), StudentMainActivity.class);
                        startActivity(intent);
                        finish();
                    }else if(role.equals("admin")){
                        editor.putString("id",json.getString("id"));
                        editor.putString("ano",json.getString("ano"));
                        editor.putString("nickname",json.getString("nickname"));
                        editor.putString("introduction",json.getString("introduction"));
                        editor.putString("login_role","admin");
                        editor.commit();
                        intent.setClass(getApplicationContext(), AdminMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

        if(role.equals("student")){
            //动态设置hint的值
            SpannableString hintString = new SpannableString("学号");
            userName.setHint(hintString);
            //动态设置输入框上的图片
            userImage.setImageResource(R.mipmap.student_login);
        }else if(role.equals("admin")){
            userImage.setImageResource(R.mipmap.admin_login);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoginResult();
            }
        });

    }

    //获取登录结果
    private void getLoginResult(){
        //创建线程访问后台，返回登录结果
        new Thread(){
            @Override
            public void run() {
                String md5Password = null;
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(userPassword.getText().toString().getBytes());
                    md5Password = new BigInteger(1, md.digest()).toString(16);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                String result = new Utils().getConnectionResult("loginController","userLogin",
                        "username=" + userName.getText().toString() +
                                "&&password=" + md5Password + "&&role="+role);
                Message message = new Message();
                message.obj = result;
                loginHandler.sendMessage(message);
            }
        }.start();
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