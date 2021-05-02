package com.example.physical_examination_app.Admin;

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
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.physical_examination_app.ActivityCollector;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.ScreenUtils;
import com.example.physical_examination_app.Utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ExamEditActivity extends AppCompatActivity {

    private ImageView returnButton;
    private TextView barTitle;
    private TextView releaseDate;
    private TextView reservePeopleText;
    private EditText editTeacherName;
    private EditText editExamDate;
    private EditText editReservePeople;
    private Spinner examLocationSpinner;

    private Button finishButton;
    private Button saveButton;

    private LinearLayout itemReleaseLayout;
    private LinearLayout itemEventLayout;
    private LinearLayout examBackground;

    private CustomListener customListener = new CustomListener();

    private String action;
    private String location;

    private Activity activity;

    private Handler examHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            if(msg.what == 0){
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(),"保存考试信息成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"保存考试信息失败，可能是服务器出现了错误！",Toast.LENGTH_SHORT).show();
                }
            }else if(msg.what == 1){
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(),"删除考试信息成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"删除考试信息失败，可能是服务器出现了错误！",Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_edit);

        activity = this;

        getViews();
        otherOperating();
        setListener();

    }

    //其他操作
    private void otherOperating() {

        Intent intent = getIntent();
        action = intent.getAction();
        if(action.equals("edit")){

            String[] people = intent.getStringExtra("people").split("/");
            barTitle.setText("考试信息修改");
            releaseDate.setText(intent.getStringExtra("release_date"));
            editTeacherName.setText(intent.getStringExtra("teacher"));
            editExamDate.setText(intent.getStringExtra("exam_date"));
            editReservePeople.setText(people[1]);
            if(intent.getStringExtra("location").equals("东操场")) examLocationSpinner.setSelection(0);
            else examLocationSpinner.setSelection(1);

            finishButton.setBackgroundColor(Color.parseColor("#DC143C"));
            finishButton.setText("删除");
            finishButton.setTextColor(Color.parseColor("#FFFFFF"));

        }else if(action.equals("add")){
            barTitle.setText("添加考试");
            itemReleaseLayout.setVisibility(View.GONE);
            itemEventLayout.setVisibility(View.GONE);
        }

    }

    //设置listener
    private void setListener() {

        returnButton.setOnClickListener(customListener);
        finishButton.setOnClickListener(customListener);
        examLocationSpinner.setOnItemSelectedListener(customListener);
        saveButton.setOnClickListener(customListener);

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);
        barTitle = findViewById(R.id.bar_title);
        releaseDate = findViewById(R.id.release_date);
        reservePeopleText = findViewById(R.id.edit_people_text);
        editTeacherName = findViewById(R.id.edit_teacher_name);
        editExamDate = findViewById(R.id.edit_exam_date);
        editReservePeople = findViewById(R.id.edit_reserve_people);
        examLocationSpinner = findViewById(R.id.exam_location_spinner);

        finishButton = findViewById(R.id.finish_button);
        saveButton = findViewById(R.id.save_button);

        examBackground = findViewById(R.id.exam_background);
        itemReleaseLayout = findViewById(R.id.item_release_layout);
        itemEventLayout = findViewById(R.id.item_event_layout);

    }

    //自定义listener
    class CustomListener implements View.OnClickListener, AdapterView.OnItemSelectedListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.return_button:
                    finish();
                    break;
                case R.id.finish_button:
                    if(action.equals("edit")) showDialog();
                    else if(action.equals("add")) finish();
                    break;
                case R.id.save_button:
                    getResultFromServer();
                    break;
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (position){
                case 0:
                    location = "东操场";
                    examBackground.setBackground(getResources().getDrawable(R.drawable.ground2));
                    break;
                case 1:
                    location = "西操场";
                    examBackground.setBackground(getResources().getDrawable(R.drawable.ground1));
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

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
                deleteExam();
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

    //向后台发送请求删除考试
    private void deleteExam() {

        new Thread(){
            @Override
            public void run() {
                String param = "id=" + getIntent().getStringExtra("id");
                String result = new Utils().getConnectionResult("examController","deleteExam",param);
                Message message = new Message();
                message.obj = result;
                message.what = 1;
                examHandler.sendMessage(message);
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

    //向后台发送数据进行插入考试操作
    private void getResultFromServer(){
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                if(editTeacherName.getText().toString().equals("") || editReservePeople.getText().toString().equals("") ||
                editExamDate.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"输入的信息不能为空！",Toast.LENGTH_SHORT).show();
                }else {
                    String param = null;
                    String result = null;
                    if(action.equals("edit")){
                        param = "id=" + getSharedPreferences("userInfo",MODE_PRIVATE).getString("id","")
                                + "&&" + "teacher=" + editTeacherName.getText().toString() + "&&" +"exam_date=" +
                                editExamDate.getText().toString() + "&&" + "all_people=" + editReservePeople.getText().toString() +
                                "&&" + "place=" + location;
                        result = new Utils().getConnectionResult("examController","updateExam",param);
                    }else if(action.equals("add")){
                        Calendar c = Calendar.getInstance();
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        int month = c.get(Calendar.MONTH);
                        int year = c.get(Calendar.YEAR);
                        String currentDate = year + "-" + (month+1) + "-" + day;
                        param = "teacher=" + editTeacherName.getText().toString() + "&&" + "exam_date=" + editExamDate.getText().toString() +
                                "&&" + "all_people=" + editReservePeople.getText().toString() + "&&" + "place=" + location + "&&" + "public_date=" + currentDate;
                        result = new Utils().getConnectionResult("examController","insertExam",param);
                    }
                    Message message = new Message();
                    message.obj = result;
                    message.what = 0;
                    examHandler.sendMessage(message);
                }
                Looper.loop();
            }
        }.start();
    }

}