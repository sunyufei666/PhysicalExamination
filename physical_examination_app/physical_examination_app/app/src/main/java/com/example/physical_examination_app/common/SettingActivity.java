package com.example.physical_examination_app.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.physical_examination_app.ActivityCollector;
import com.example.physical_examination_app.Admin.StudentEditActivity;
import com.example.physical_examination_app.ScreenUtils;
import com.example.physical_examination_app.Student.MyNewsActivity;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Utils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SettingActivity extends AppCompatActivity {

    private ImageView returnButton;
    private ImageView myAvatar;

    private LinearLayout itemIntroductionLayout;
    private LinearLayout itemDetailLayout;
    private LinearLayout itemPasswordLayout;
    private LinearLayout itemNicknameLayout;
    private LinearLayout itemNewsLayout;
    private LinearLayout itemDownLayout;

    private CustomListener customListener = new CustomListener();

    private Activity activity;
    private AlertDialog dialog;
    private String passwordEditString;

    private Handler confirmHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            if (result.equals("fail") || result == null){
                Toast.makeText(getApplicationContext(),"输入密码错误，请重新操作！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"恭喜你，身份验证成功！",Toast.LENGTH_SHORT).show();
                Intent passwordIntent = new Intent();
                passwordIntent.setAction("密码修改");
                passwordIntent.putExtra("password",passwordEditString);
                passwordIntent.setClass(getApplicationContext(),InfoEditActivity.class);
                startActivity(passwordIntent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActivityCollector.addActivity(this);
        activity = this;

        getViews();
        getAvatar();

        if(getSharedPreferences("userInfo",MODE_PRIVATE).getString("login_role","").equals("admin"))
            itemNewsLayout.setVisibility(View.GONE);

        setListener();

    }

    //从后台获取头像
    private void getAvatar() {

        String avatarName = null;
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        if(pre.getString("login_role","").equals("student")){
            avatarName = pre.getString("sno","") + ".jpg";
        }else avatarName = pre.getString("ano","") + ".jpg";

        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).fallback(R.mipmap.app_icon)
                .error(R.drawable.error).diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop();
        Glide.with(activity).load(Utils.AVATAR_PATH + avatarName)
                .thumbnail(Glide.with(activity).load(R.drawable.loading))
                .apply(requestOptions).into(myAvatar);

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
            SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
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
                    if(pre.getString("login_role","").equals("admin")){
                        detailIntent.setAction("admin");
                        detailIntent.putExtra("id",pre.getString("id",""));
                        detailIntent.putExtra("introduction",pre.getString("introduction",""));
                        detailIntent.putExtra("nickname",pre.getString("nickname",""));
                        detailIntent.putExtra("ano",pre.getString("ano",""));
                    } else {
                        detailIntent.setAction("student");
                        detailIntent.putExtra("id",pre.getString("id",""));
                        detailIntent.putExtra("university",pre.getString("university",""));
                        detailIntent.putExtra("college",pre.getString("college",""));
                        detailIntent.putExtra("nickname",pre.getString("nickname",""));
                        detailIntent.putExtra("name",pre.getString("name",""));
                        detailIntent.putExtra("sno",pre.getString("sno",""));
                        detailIntent.putExtra("grade",pre.getString("grade",""));
                        detailIntent.putExtra("class",pre.getString("classes",""));
                        detailIntent.putExtra("sex",pre.getString("sex",""));
                        detailIntent.putExtra("introduction",pre.getString("introduction",""));
                        detailIntent.putExtra("profession",pre.getString("major",""));
                        detailIntent.putExtra("register_time",pre.getString("register_time",""));
                    }
                    detailIntent.setClass(getApplicationContext(), StudentEditActivity.class);
                    startActivity(detailIntent);
                    break;
                case R.id.item_password_layout:
                    showPasswordDialog();
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
                    showDialog();
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

    //显示自定义密码对话框
    private void showPasswordDialog() {

        View view = LayoutInflater.from(activity).inflate(R.layout.custom_confirm_password_dialog,null,false);
        dialog = new AlertDialog.Builder(activity).setView(view).create();

        //调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        //dialog.setCancelable(false);
        //调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
        dialog.setCanceledOnTouchOutside(false);

        EditText dialogEdit = view.findViewById(R.id.dialog_edit);
        Button dialogCancel = view.findViewById(R.id.dialog_cancel);
        Button dialogConfirm = view.findViewById(R.id.dialog_confirm);

        //取消按钮监听
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确认按钮监听
        dialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordEditString = dialogEdit.getText().toString();
                dialog.dismiss();
                getPasswordConfirm();
            }
        });

        //dialog消失监听
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setScreenBgLight();
            }
        });

        setScreenBgDarken();
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(ScreenUtils.getScreenWidth(activity)*5/6,ScreenUtils.getScreenHeight(activity)/3);
//        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.dialogWindowAnim);

    }

    //显示自定义注销对话框
    private void showDialog() {

        View view = LayoutInflater.from(activity).inflate(R.layout.custom_confirm_dialog,null,false);
        AlertDialog dialog = new AlertDialog.Builder(activity).setView(view).create();

        //调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        //dialog.setCancelable(false);
        //调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
        dialog.setCanceledOnTouchOutside(false);

        TextView dialogText = view.findViewById(R.id.dialog_text);
        Button dialogCancel = view.findViewById(R.id.dialog_cancel);
        Button dialogConfirm = view.findViewById(R.id.dialog_confirm);

        //设置dialog需要确定的任务
        dialogText.setText("确定要进行注销操作吗？");
        //取消按钮监听
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确认按钮监听
        dialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String avatarName = null;
                SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                if(pre.getString("login_role","").equals("student")){
                    avatarName = pre.getString("sno","");
                }else avatarName = pre.getString("ano","");
                File file = new File(getFilesDir().getAbsolutePath() + "/avatar/" + avatarName +".jpg");
                file.delete();

                SharedPreferences.Editor editor = pre.edit();
                editor.clear();
                editor.commit();

                Toast.makeText(getApplicationContext(),"注销成功！",Toast.LENGTH_SHORT).show();

                ActivityCollector.finishAll();
            }
        });

        //dialog消失监听
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setScreenBgLight();
            }
        });

        setScreenBgDarken();
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(ScreenUtils.getScreenWidth(activity)*4/5,ScreenUtils.getScreenHeight(activity)/4);
//        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.dialogWindowAnim);

    }

    // 设置屏幕背景变暗
    private void setScreenBgDarken() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
    }

    // 设置屏幕背景变亮
    private void setScreenBgLight() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        lp.dimAmount = 1.0f;
        getWindow().setAttributes(lp);
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

    //向后台确认登录账户
    private void getPasswordConfirm(){
        //创建线程访问后台，返回登录结果
        new Thread(){
            @Override
            public void run() {
                String md5Password = null;
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(passwordEditString.getBytes());
                    md5Password = new BigInteger(1, md.digest()).toString(16);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                String result = new Utils().getConnectionResult("loginController","userLogin",
                        "username=" + pre.getString("sno","") +
                                "&&password=" + md5Password + "&&role=" + pre.getString("login_role",""));
                Message message = new Message();
                message.obj = result;
                confirmHandler.sendMessage(message);
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAvatar();
    }
}