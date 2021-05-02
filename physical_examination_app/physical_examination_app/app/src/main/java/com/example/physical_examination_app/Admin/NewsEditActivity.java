package com.example.physical_examination_app.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.ScreenUtils;
import com.example.physical_examination_app.Utils;

import java.util.Calendar;

public class NewsEditActivity extends AppCompatActivity {

    private TextView barTitle;
    private TextView alertText;
    private ImageView returnButton;
    private EditText newsEdit;
    private Button finishButton;
    private Button saveButton;
    private TextView newsPublicDate;

    private AlertDialog dialog;

    private String action;
    private Activity activity;

    private LinearLayout itemPublicDateLayout;
    private LinearLayout itemButtonLayout;

    private Handler newsHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            if(msg.what == 1){
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(),"保存消息成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"保存消息失败，可能是服务器出现了错误！",Toast.LENGTH_SHORT).show();
                }
            }else if(msg.what == 0){
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(),"删除消息成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"删除消息失败，可能是服务器出现了错误！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_edit);

        activity = this;

        getViews();

        otherOperating();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(action.equals("edit")){
                    showDialog();
                }else finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNews("save");
            }
        });

    }

    //显示自定义注销对话框
    private void showDialog() {

        View view = LayoutInflater.from(activity).inflate(R.layout.custom_confirm_dialog,null,false);
        dialog = new AlertDialog.Builder(activity).setView(view).create();

        //调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        //dialog.setCancelable(false);
        //调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
        dialog.setCanceledOnTouchOutside(false);

        TextView dialogText = view.findViewById(R.id.dialog_text);
        Button dialogCancel = view.findViewById(R.id.dialog_cancel);
        Button dialogConfirm = view.findViewById(R.id.dialog_confirm);

        //设置dialog需要确定的任务
        dialogText.setText("确定要删除该考试吗？");
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
                editNews("delete");
                dialog.dismiss();
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

    //向后台发送请求删除消息
    private void editNews(String operation) {

        new Thread(){
            @Override
            public void run() {
                new Thread(){
                    @Override
                    public void run() {
                        Looper.prepare();
                        String result = null;
                        String param = null;
                        Message message = new Message();
                        if(newsEdit.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(),"输入信息不能为空！",Toast.LENGTH_SHORT).show();
                        }else {
                            if(operation.equals("delete")){
                                param = "id=" + getIntent().getStringExtra("id");
                                result = new Utils().getConnectionResult("newsController","deleteNews",param);
                                message.obj = result;
                                message.what = 0;
                                newsHandler.sendMessage(message);
                            }else if(operation.equals("save")){
                                if(action.equals("edit")){
                                    param = "id=" + getIntent().getStringExtra("id") + "&&" + "news_content=" + newsEdit.getText().toString();
                                    result = new Utils().getConnectionResult("newsController","updateNews",param);
                                }else if(action.equals("add")){
                                    Calendar c = Calendar.getInstance();
                                    int day = c.get(Calendar.DAY_OF_MONTH);
                                    int month = c.get(Calendar.MONTH);
                                    int year = c.get(Calendar.YEAR);
                                    String currentDate = year + "-" + (month+1) + "-" + day;
                                    param = "news_date=" + currentDate + "&&" + "news_content=" + newsEdit.getText().toString();
                                    result = new Utils().getConnectionResult("newsController","insertNews",param);
                                }
                                message.obj = result;
                                message.what = 1;
                                newsHandler.sendMessage(message);
                            }
                        }
                        Looper.loop();
                    }
                }.start();
            }
        }.start();

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

    //其他操作
    private void otherOperating() {

        action = getIntent().getAction();
        if(action.equals("edit")){
            barTitle.setText("消息修改");
            newsPublicDate.setText(getIntent().getStringExtra("news_date"));
            newsEdit.setText(getIntent().getStringExtra("news_content"));
            finishButton.setBackgroundColor(Color.parseColor("#DC143C"));
            finishButton.setText("删除");
            finishButton.setTextColor(Color.parseColor("#FFFFFF"));
        }else if(action.equals("add")){
            barTitle.setText("消息添加");
            itemPublicDateLayout.setVisibility(View.GONE);
        }else if(action.equals("view")){
            barTitle.setText("消息查看");
            newsPublicDate.setText(getIntent().getStringExtra("news_date"));
            newsEdit.setText(getIntent().getStringExtra("news_content"));
            newsEdit.setBackgroundColor(Color.parseColor("#FFFFFF"));
            newsEdit.setFocusable(false);
            alertText.setVisibility(View.GONE);
            itemButtonLayout.setVisibility(View.INVISIBLE);
        }

    }

    //获取视图控件
    private void getViews() {

        barTitle = findViewById(R.id.bar_title);
        alertText = findViewById(R.id.alert_text);
        returnButton = findViewById(R.id.return_button);
        newsEdit = findViewById(R.id.edit_news);
        finishButton = findViewById(R.id.finish_button);
        saveButton = findViewById(R.id.save_button);
        newsPublicDate = findViewById(R.id.news_public_date);

        itemPublicDateLayout = findViewById(R.id.item_public_date_layout);
        itemButtonLayout = findViewById(R.id.item_button_layout);

    }

}