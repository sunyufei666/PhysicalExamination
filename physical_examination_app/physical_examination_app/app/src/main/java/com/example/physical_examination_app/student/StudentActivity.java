package com.example.physical_examination_app.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.physical_examination_app.MyAvatarActivity;
import com.example.physical_examination_app.R;

public class StudentActivity extends AppCompatActivity {

    private LinearLayout myAvatar;

    private MyListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        getViews();
        setListener();

    }

    //获取视图控件
    private void getViews() {
        myAvatar = findViewById(R.id.my_avatar);
    }

    //设置监听器
    private void setListener() {
        myListener = new MyListener();

        //同时设置两个事件监听器
        myAvatar.setOnClickListener(myListener);
        myAvatar.setOnTouchListener(myListener);
    }

    //自定义点击事件监听器
    class MyListener implements View.OnClickListener, View.OnTouchListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.my_avatar:
                    Intent avatarIntent = new Intent();
                    avatarIntent.setClass(getApplicationContext(), MyAvatarActivity.class);
                    startActivity(avatarIntent);
                    break;
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()){
                case R.id.my_avatar:
                    touchUpAndDownEvent(myAvatar,event);
                    break;
            }
            return false;
        }
    }

    //设置触摸的按下和抬起时背景的颜色
    private void touchUpAndDownEvent(View view,MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                break;
            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }

}