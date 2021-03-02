package com.example.physical_examination_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MyAvatarActivity extends AppCompatActivity {

    private ImageView iconAlbum;
    private ImageView userAvatar;
    private ImageView iconBack;

    private PopupWindow pop;

    private TextView avatarCamera;
    private TextView avatarAlbum;
    private TextView popDismiss;

    private MyListener myListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_avatar);

        getViews();
        setListener();
        albumClickListener();

    }

    //获取视图控件
    private void getViews() {
        iconAlbum = findViewById(R.id.icon_album);
        userAvatar = findViewById(R.id.user_avatar);
        iconBack = findViewById(R.id.icon_back);
    }

    //设置监听器
    private void setListener() {
        myListener = new MyListener();
        iconBack.setOnClickListener(myListener);
    }

    //相册的点击事件
    private void albumClickListener() {
        iconAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置弹出窗口，并赋值给view
                View popView = getLayoutInflater().inflate(R.layout.album_popup,null);
                //设置弹出窗口的位置
                pop = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, 450,true);
                //设置弹出窗口的动画
                pop.setAnimationStyle(R.style.popwin_anim_style);
                pop.showAsDropDown(getWindow().getDecorView());

                //获取弹出窗口的视图控件并且设置监听器
                getPopupWindowViewsAndSetListener(popView);
                //设置透明阴影
                final WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);

                //设置弹出窗口的消失事件
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
            }
        });
    }

    //获取弹出窗口的视图控件并且设置监听器
    private void getPopupWindowViewsAndSetListener(View v){
        //获取视图控件元素
        avatarCamera = v.findViewById(R.id.avatar_camera);
        avatarAlbum = v.findViewById(R.id.avatar_album);
        popDismiss = v.findViewById(R.id.pop_dismiss);

        //同时设置点击和触摸监听器
        avatarCamera.setOnClickListener(myListener);
        avatarCamera.setOnTouchListener(myListener);
        avatarAlbum.setOnClickListener(myListener);
        avatarAlbum.setOnTouchListener(myListener);
        popDismiss.setOnClickListener(myListener);
        popDismiss.setOnTouchListener(myListener);
    }

    //自定义监听事件
    class MyListener implements View.OnClickListener,View.OnTouchListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.icon_back:
                    finish();
                    break;
                case R.id.avatar_camera:
                    break;
                case R.id.avatar_album:
                    break;
                case R.id.pop_dismiss:
                    pop.dismiss();
                    break;
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()){
                case R.id.avatar_camera:
                    touchUpAndDownEvent(avatarCamera,event);
                    break;
                case R.id.avatar_album:
                    touchUpAndDownEvent(avatarAlbum,event);
                    break;
                case R.id.pop_dismiss:
                    touchUpAndDownEvent(popDismiss,event);
                    break;
            }
            //需要返回false才能同时触发点击和触摸两个监听器
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