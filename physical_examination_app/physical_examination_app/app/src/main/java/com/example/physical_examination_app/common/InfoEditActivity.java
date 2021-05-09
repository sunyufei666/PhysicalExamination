package com.example.physical_examination_app.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class InfoEditActivity extends AppCompatActivity {

    private ImageView returnButton;
    private TextView barTitle;
    private TextView intentParam;
    private EditText editParam;
    private Button saveButton;
    private Button finishButton;

    private LinearLayout itemConfirmLayout;
    private TextView warningText;
    private EditText editPasswordParam;

    private String action;

    private CustomListener customListener = new CustomListener();

    private Handler infoEditHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            if (result.equals("success")){
                SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pre.edit();
                editor.putString(action,editParam.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(),"信息修改成功！",Toast.LENGTH_SHORT).show();
                finish();
            }else if (result.equals("fail")){
                Toast.makeText(getApplicationContext(),"信息修改失败，请重新输入！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_edit);

        getViews();

        otherOperating();

        setListener();


    }

    //其他页面操作
    private void otherOperating() {

        //设置bar的标题
        Intent intent = getIntent();
        action = intent.getAction();
        barTitle.setText(action);

        SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        switch (action){
            case "简介修改":
                action = "introduction";
                intentParam.setText(pre.getString("introduction",""));
                itemConfirmLayout.setVisibility(View.GONE);
                warningText.setVisibility(View.GONE);
                break;
            case "密码修改":
                action = "password";
                intentParam.setText(intent.getStringExtra("password"));
                break;
            case "昵称修改":
                action = "nickname";
                intentParam.setText(pre.getString("nickname",""));
                itemConfirmLayout.setVisibility(View.GONE);
                warningText.setVisibility(View.GONE);
                break;
        }

    }

    //给控件设置监听器
    private void setListener() {

        returnButton.setOnClickListener(customListener);
        editPasswordParam.addTextChangedListener(customListener);
        saveButton.setOnClickListener(customListener);
        finishButton.setOnClickListener(customListener);

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);
        barTitle = findViewById(R.id.bar_title);
        intentParam = findViewById(R.id.intent_param);
        editParam = findViewById(R.id.edit_param);
        saveButton = findViewById(R.id.save_button);
        finishButton = findViewById(R.id.finish_button);

        itemConfirmLayout = findViewById(R.id.item_confirm_layout);

        warningText = findViewById(R.id.warning_text);
        editPasswordParam = findViewById(R.id.edit_password_confirm);

    }

    //自定义监听器
    class CustomListener implements View.OnClickListener, TextWatcher {

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.return_button:
                    finish();
                    break;
                case R.id.save_button:
                    new Thread(){
                        @Override
                        public void run() {
                            String editParamString = editParam.getText().toString();
                            if (getIntent().getAction().equals("密码修改")){
                                try {
                                    MessageDigest md = MessageDigest.getInstance("MD5");
                                    md.update(editParamString.getBytes());
                                    editParamString = new BigInteger(1, md.digest()).toString(16);
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (editParamString.equals("")){
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(),"请输入修改内容！",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }else if(!editParamString.equals("") && (editPasswordParam.getText().toString().equals("")
                                    && getIntent().getAction().equals("密码修改"))){
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(),"请输入修改内容！",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } else if(getIntent().getAction().equals("密码修改") && ! editParam.getText().toString().equals(editPasswordParam.getText().toString())){
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(),"两次输入的密码应一致！",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }else {
                                SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                String params = "sno="+pre.getString("sno","")+"&&"+"type="+action+"&&"+"param="+editParamString;
                                String result = new Utils().getConnectionResult("studentController","editInformation",params);
                                Message message = new Message();
                                message.obj = result;
                                infoEditHandler.sendMessage(message);
                            }
                        }
                    }.start();
                    break;
                case R.id.finish_button:
                    finish();
                    break;
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String editParamText = editParam.getText().toString();
            String editPasswordParamText = editPasswordParam.getText().toString();
            if(! editParamText.equals(editPasswordParamText) && ! editPasswordParamText.equals("")){
                warningText.setText("两次输入的密码应一致");
                warningText.setTextColor(Color.parseColor("#DC143C"));
                warningText.setVisibility(View.VISIBLE);
            }else if(editPasswordParamText.equals("")){
                warningText.setVisibility(View.INVISIBLE);
            }else {
                warningText.setText("两次密码输入一致");
                warningText.setTextColor(Color.parseColor("#00FF00"));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}