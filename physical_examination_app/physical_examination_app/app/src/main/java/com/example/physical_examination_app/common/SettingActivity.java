package com.example.physical_examination_app.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.physical_examination_app.Student.MyDetailActivity;
import com.example.physical_examination_app.Student.MyNewsActivity;
import com.example.physical_examination_app.common.MyAvatarActivity;
import com.example.physical_examination_app.R;

public class SettingActivity extends AppCompatActivity {

    private TextView introductionText;

    private ImageView returnButton;
    private ImageView myAvatar;

    private LinearLayout itemIntroductionLayout;
    private LinearLayout itemDetailLayout;
    private LinearLayout itemPasswordLayout;
    private LinearLayout itemNicknameLayout;
    private LinearLayout itemNewsLayout;
    private LinearLayout itemDownLayout;

    private CustomListener customListener = new CustomListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActivityCollector.addActivity(this);

        getViews();
        setListener();
        otherOperating();


    }

    //程序相关其他操作
    private void otherOperating() {

        SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        introductionText.setText(pre.getString("introduction",""));

    }

    //给控件设置监听器
    private void setListener() {

        myAvatar.setOnClickListener(customListener);
        returnButton.setOnClickListener(customListener);

        itemIntroductionLayout.setOnClickListener(customListener);
        itemIntroductionLayout.setOnTouchListener(customListener);
        itemDetailLayout.setOnClickListener(customListener);
        itemDetailLayout.setOnTouchListener(customListener);
        itemPasswordLayout.setOnClickListener(customListener);
        itemPasswordLayout.setOnTouchListener(customListener);
        itemNicknameLayout.setOnClickListener(customListener);
        itemNicknameLayout.setOnTouchListener(customListener);
        itemNewsLayout.setOnClickListener(customListener);
        itemNewsLayout.setOnTouchListener(customListener);
        itemDownLayout.setOnClickListener(customListener);
        itemDownLayout.setOnTouchListener(customListener);

    }

    //获取视图控件
    private void getViews() {

        introductionText = findViewById(R.id.introduction_text);
        returnButton = findViewById(R.id.return_button);
        myAvatar = findViewById(R.id.my_avatar);

        itemIntroductionLayout = findViewById(R.id.item_introduction_layout);
        itemDetailLayout = findViewById(R.id.item_detail_layout);
        itemPasswordLayout = findViewById(R.id.item_password_layout);
        itemNicknameLayout = findViewById(R.id.item_nickname_layout);
        itemNewsLayout = findViewById(R.id.item_news_layout);
        itemDownLayout = findViewById(R.id.item_down_layout);

    }

    //自定义监听器
    class CustomListener implements View.OnClickListener, View.OnTouchListener{

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.my_avatar:
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MyAvatarActivity.class);
                    startActivity(intent);
                    break;
                case R.id.return_button:
                    finish();
                    break;
                case R.id.item_introduction_layout:
                    Intent introductionIntent = new Intent();
                    introductionIntent.setAction("简介修改");
                    introductionIntent.setClass(getApplicationContext(),InfoEditActivity.class);
                    startActivity(introductionIntent);
                    break;
                case R.id.item_detail_layout:
                    Intent detailIntent = new Intent();
                    detailIntent.setAction("详情修改");
                    detailIntent.setClass(getApplicationContext(), MyDetailActivity.class);
                    startActivity(detailIntent);
                    break;
                case R.id.item_password_layout:
                    Intent passwordIntent = new Intent();
                    passwordIntent.setAction("密码修改");
                    passwordIntent.putExtra("password","123456789");
                    passwordIntent.setClass(getApplicationContext(),InfoEditActivity.class);
                    startActivity(passwordIntent);
                    break;
                case R.id.item_nickname_layout:
                    Intent nicknameIntent = new Intent();
                    nicknameIntent.setAction("昵称修改");
                    nicknameIntent.setClass(getApplicationContext(),InfoEditActivity.class);
                    startActivity(nicknameIntent);
                    break;
                case R.id.item_news_layout:
                    Intent newsIntent = new Intent();
                    newsIntent.setClass(getApplicationContext(), MyNewsActivity.class);
                    startActivity(newsIntent);
                    break;
                case R.id.item_down_layout:
                    SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pre.edit();
                    editor.clear();
                    editor.commit();
                    ActivityCollector.finishAll();
                    break;
            }

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (v.getId()){
                case R.id.item_introduction_layout:
                    touchUpAndDownEvent(itemIntroductionLayout,event);
                    break;
                case R.id.item_detail_layout:
                    touchUpAndDownEvent(itemDetailLayout,event);
                    break;
                case R.id.item_password_layout:
                    touchUpAndDownEvent(itemPasswordLayout,event);
                    break;
                case R.id.item_nickname_layout:
                    touchUpAndDownEvent(itemNicknameLayout,event);
                    break;
                case R.id.item_news_layout:
                    touchUpAndDownEvent(itemNewsLayout,event);
                    break;
                case R.id.item_down_layout:
                    touchUpAndDownEvent(itemDownLayout,event);
                    break;
            }

            return false;
        }
    }

    //设置触摸的按下和抬起时背景的颜色
    private void touchUpAndDownEvent(View view, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                break;
            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        introductionText.setText(pre.getString("introduction",""));
    }
}