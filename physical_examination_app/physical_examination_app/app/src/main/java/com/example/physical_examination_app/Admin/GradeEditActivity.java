package com.example.physical_examination_app.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Utils;

public class GradeEditActivity extends AppCompatActivity {

    private ImageView returnButton;

    private Button finishButton;
    private Button saveButton;

    private Spinner studentPassSpinner;
    private TextView examTeacher;
    private TextView examDate;
    private EditText editJump;
    private EditText editFiftyRun;
    private TextView longRunName;
    private EditText editLongRun;
    private TextView extraEventName;
    private EditText editExtraEvent;
    private EditText editSitBending;
    private EditText editLungContainer;
    private TextView examLocation;

    private String ifPass;

    private LinearLayout examBackground;

    private CustomListener customListener = new CustomListener();

    private Handler studentGradeHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            if (result.equals("success")){
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(),"修改学生成绩成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"修改学生成绩失败，可能是服务器出现了错误！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_edit);

        getViews();
        setListener();
        otherOperating();

    }

    //给控件设置监听器
    private void setListener() {

        returnButton.setOnClickListener(customListener);
        finishButton.setOnClickListener(customListener);
        saveButton.setOnClickListener(customListener);
        studentPassSpinner.setOnItemSelectedListener(customListener);

    }

    //其他操作
    private void otherOperating() {

        Intent intent = getIntent();
        SpannableString hintString;
        if(intent.getStringExtra("sex").equals("男")){
            hintString = new SpannableString("在此输入1000米成绩");
            longRunName.setText("1000米跑");
            editLongRun.setHint(hintString);
            hintString = new SpannableString("在此输入引体向上成绩");
            extraEventName.setText("引体向上");
            editExtraEvent.setHint(hintString);
        }
        if(intent.getStringExtra("pass").equals("已通过")){
            studentPassSpinner.setSelection(1);
        }
        examTeacher.setText(intent.getStringExtra("teacher"));
        examDate.setText(intent.getStringExtra("date"));
        editJump.setText(intent.getStringExtra("jump_long"));
        editFiftyRun.setText(intent.getStringExtra("fifty_run"));
        editLongRun.setText(intent.getStringExtra("long_run"));
        editExtraEvent.setText(intent.getStringExtra("extra_event"));
        editSitBending.setText(intent.getStringExtra("sit_bending"));
        editLungContainer.setText(intent.getStringExtra("lung_container"));

        examLocation.setText(intent.getStringExtra("location"));
        if(intent.getStringExtra("location").equals("西操场"))
            examBackground.setBackground(getResources().getDrawable(R.drawable.ground2));

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);

        finishButton = findViewById(R.id.finish_button);
        saveButton = findViewById(R.id.save_button);

        studentPassSpinner = findViewById(R.id.student_pass_spinner);
        examTeacher = findViewById(R.id.exam_teacher);
        examDate = findViewById(R.id.exam_date);

        editJump = findViewById(R.id.edit_jump);
        editFiftyRun = findViewById(R.id.edit_fifty_run);
        longRunName = findViewById(R.id.long_run_name);
        editLongRun = findViewById(R.id.edit_long_run);
        extraEventName = findViewById(R.id.extra_event_name);
        editExtraEvent = findViewById(R.id.edit_extra_event);
        editSitBending = findViewById(R.id.edit_sit_bending);
        editLungContainer = findViewById(R.id.edit_lung_container);

        examLocation = findViewById(R.id.exam_location);
        examBackground = findViewById(R.id.exam_background);

    }

    class CustomListener implements View.OnClickListener, AdapterView.OnItemSelectedListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.return_button:
                    finish();
                    break;
                case R.id.finish_button:
                    finish();
                    break;
                case R.id.save_button:
                    updateStudentGrade();
                    break;
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    ifPass = "未通过";
                    break;
                case 1:
                    ifPass = "已通过";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

    //向后台发送请求更新学生的成绩
    private void updateStudentGrade() {

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                String standJump = editJump.getText().toString();
                String fiftyRun = editFiftyRun.getText().toString();
                String longRun = editLongRun.getText().toString();
                String extraEvent = editExtraEvent.getText().toString();
                String sitBending = editSitBending.getText().toString();
                String lungContainer = editLungContainer.getText().toString();
                if(standJump.equals("") || fiftyRun.equals("") || longRun.equals("") || extraEvent.equals("") || sitBending.equals("") ||
                lungContainer.equals("")){
                    Toast.makeText(getApplicationContext(),"输入的信息不能未空！",Toast.LENGTH_SHORT).show();
                }else {
                    String param = "id=" + getIntent().getStringExtra("id") + "&&" + "if_pass=" + ifPass + "&&" + "stand_jump=" +
                            standJump + "&&" + "fifty_meter=" + fiftyRun + "&&" + "long_run=" + longRun + "&&" + "extra_event=" + extraEvent +
                            "&&" + "sit_forward=" + sitBending + "&&" + "vital_capacity=" + lungContainer + "&&" + "sex=" + getIntent().getStringExtra("sex");
                            String result = new Utils().getConnectionResult("studentExamController","updateStudentExam",param);
                    Message message = new Message();
                    message.obj = result;
                    studentGradeHandler.sendMessage(message);
                }
                Looper.loop();
            }
        }.start();

    }

}