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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.ScreenUtils;
import com.example.physical_examination_app.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudentEditActivity extends AppCompatActivity {

    private ImageView returnButton;

    private Spinner studentSexSpinner;

    private TextView barTitle;
    private TextView username;
    private EditText editUniversity;
    private EditText editCollege;
    private EditText editProfession;
    private EditText editNickname;
    private EditText editName;
    private EditText editSno;
    private EditText editGrade;
    private EditText editClass;
    private EditText editIntroduction;

    private LinearLayout itemRegisterTime;
    private LinearLayout itemIntroductionLayout;
    private LinearLayout itemManageStudentGrade;

    private LinearLayout itemUniversityLayout;
    private LinearLayout itemCollegeLayout;
    private LinearLayout itemProfessionLayout;
    private LinearLayout itemNameLayout;
    private LinearLayout itemSexLayout;
    private LinearLayout itemGradeLayout;
    private LinearLayout itemClassLayout;

    private CustomListener customListener = new CustomListener();

    private Button finishButton;
    private Button saveButton;

    private String sex;
    private String sno = "";
    private String action;

    private Activity activity;

    private Handler studentHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            Log.e("asdsadasd",result);
            if (msg.what == 0){
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(),"保存学生信息成功！",Toast.LENGTH_SHORT).show();
                    sendPicture(editSno.getText().toString());
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"保存学生信息失败，可能是服务器出现了错误！",Toast.LENGTH_SHORT).show();
                }
            }else if (msg.what == 1){
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(),"修改信息成功！",Toast.LENGTH_SHORT).show();
                    if(action.equals("student")) saveStudentNewInfo();
                    else if(action.equals("admin")) {
                        renameOrDeletePicture("rename");
                        saveAdminNewInfo();
                    }
                    else renameOrDeletePicture("rename");
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"修改信息失败，可能是服务器出现了错误！",Toast.LENGTH_SHORT).show();
                }
            }else if (msg.what == 2){
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(),"删除学生信息成功！",Toast.LENGTH_SHORT).show();
                    renameOrDeletePicture("delete");
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"删除学生信息失败，可能是服务器出现了错误！",Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);

        activity = this;

        getViews();
        setListener();
        otherOperating();

    }

    //系统的其他操作
    private void otherOperating() {

        Intent intent = getIntent();
        action = intent.getAction();
        if(action.equals("edit") || action.equals("student")){

            barTitle.setText("修改学生信息");
            sno += intent.getStringExtra("sno");
            editUniversity.setText(intent.getStringExtra("university"));
            editCollege.setText(intent.getStringExtra("college"));
            editProfession.setText(intent.getStringExtra("profession"));
            editNickname.setText(intent.getStringExtra("nickname"));
            editName.setText(intent.getStringExtra("name"));
            editSno.setText(intent.getStringExtra("sno"));
            editGrade.setText(intent.getStringExtra("grade"));
            editClass.setText(intent.getStringExtra("class"));

            if(intent.getStringExtra("sex").equals("男"))
                studentSexSpinner.setSelection(0);
            else if(intent.getStringExtra("sex").equals("女"))
                studentSexSpinner.setSelection(1);

            editIntroduction.setText(intent.getStringExtra("introduction"));

            if(action.equals("edit")){
                finishButton.setBackgroundColor(Color.parseColor("#DC143C"));
                finishButton.setText("删除");
                finishButton.setTextColor(Color.parseColor("#FFFFFF"));
            }else if(action.equals("student")){

                itemManageStudentGrade.setVisibility(View.GONE);
                editSno.setFocusable(false);
                editSno.setTextColor(Color.parseColor("#A9A9A9"));

            }

        }else if(action.equals("add")){

            barTitle.setText("添加学生");
            itemIntroductionLayout.setVisibility(View.GONE);
            editIntroduction.setText("暂无");
            itemRegisterTime.setVisibility(View.GONE);
            itemManageStudentGrade.setVisibility(View.GONE);

        }else if(action.equals("admin")){

            sno += intent.getStringExtra("ano");

            barTitle.setText("信息修改");
            username.setText("账号");
            editSno.setHint("在此输入账号");

            itemUniversityLayout.setVisibility(View.GONE);
            itemCollegeLayout.setVisibility(View.GONE);
            itemProfessionLayout.setVisibility(View.GONE);
            itemNameLayout.setVisibility(View.GONE);
            itemSexLayout.setVisibility(View.GONE);
            itemGradeLayout.setVisibility(View.GONE);
            itemClassLayout.setVisibility(View.GONE);
            itemRegisterTime.setVisibility(View.GONE);
            itemManageStudentGrade.setVisibility(View.GONE);

            editNickname.setText(intent.getStringExtra("nickname"));
            editSno.setText(intent.getStringExtra("ano"));
            editIntroduction.setText(intent.getStringExtra("introduction"));


        }


    }

    //给控件设置自定义的listener
    private void setListener() {

        returnButton.setOnClickListener(customListener);
        finishButton.setOnClickListener(customListener);
        saveButton.setOnClickListener(customListener);
        studentSexSpinner.setOnItemSelectedListener(customListener);

        itemManageStudentGrade.setOnClickListener(customListener);
        itemManageStudentGrade.setOnTouchListener(customListener);

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);
        studentSexSpinner = findViewById(R.id.student_sex_spinner);

        barTitle = findViewById(R.id.bar_title);
        username = findViewById(R.id.user_name);
        editUniversity = findViewById(R.id.edit_university);
        editCollege = findViewById(R.id.edit_college);
        editProfession = findViewById(R.id.edit_profession);
        editNickname = findViewById(R.id.edit_nickname);
        editName = findViewById(R.id.edit_name);
        editSno = findViewById(R.id.edit_sno);
        editGrade = findViewById(R.id.edit_grade);
        editClass = findViewById(R.id.edit_class);
        editIntroduction = findViewById(R.id.edit_introduction);

        itemUniversityLayout = findViewById(R.id.item_university_layout);
        itemCollegeLayout = findViewById(R.id.item_college_layout);
        itemProfessionLayout = findViewById(R.id.item_profession_layout);
        itemNameLayout = findViewById(R.id.item_name_layout);
        itemSexLayout = findViewById(R.id.item_sex_layout);
        itemGradeLayout = findViewById(R.id.item_grade_layout);
        itemClassLayout = findViewById(R.id.item_class_layout);

        itemIntroductionLayout = findViewById(R.id.item_introduction_layout);
        itemRegisterTime = findViewById(R.id.item_register_time);
        itemManageStudentGrade = findViewById(R.id.item_manage_student_grade);

        finishButton = findViewById(R.id.finish_button);
        saveButton = findViewById(R.id.save_button);

    }

    //自定义listener
    class CustomListener implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.return_button:
                    finish();
                    break;
                case R.id.finish_button:
                    if(action.equals("edit")) showDialog();
                    else if(action.equals("add") || action.equals("student") || action.equals("admin"))
                        finish();
                    break;
                case R.id.save_button:
                    getResultFromServer();
                    break;
                case R.id.item_manage_student_grade:
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),ManageGradeActivity.class);
                    intent.putExtra("id",getIntent().getStringExtra("id"));
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (position){
                case 0:
                    sex = "男";
                    break;
                case 1:
                    sex = "女";
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (v.getId()){
                case R.id.item_manage_student_grade:
                    touchUpAndDownEvent(itemManageStudentGrade,event);
                    break;
            }

            return false;
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
        dialogText.setText("确定要删除该学生吗？");
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
                deleteStudent();
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

    //向后台发送请求删除学生
    private void deleteStudent() {

        new Thread(){
            @Override
            public void run() {
                String param = "id=" + getIntent().getStringExtra("id");
                String result = new Utils().getConnectionResult("studentController","deleteStudent",param);
                Message message = new Message();
                message.obj = result;
                message.what = 2;
                studentHandler.sendMessage(message);
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

    //向服务器发送请求
    private void getResultFromServer(){

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                String university = editUniversity.getText().toString();
                String college = editCollege.getText().toString();
                String profession = editProfession.getText().toString();
                String nickname = editNickname.getText().toString();
                String name = editName.getText().toString();
                String sno = editSno.getText().toString();
                String grade = editGrade.getText().toString();
                String classes = editClass.getText().toString();
                String introduction = editIntroduction.getText().toString();
                if(university.equals("") || college.equals("") || profession.equals("") || nickname.equals("") ||
                        name.equals("") || sno.equals("") || grade.equals("") || classes.equals("") || introduction.equals("")){

                    if(action.equals("admin") && !nickname.equals("") && !sno.equals("") && !introduction.equals("")){
                        String param = "id=" + getIntent().getStringExtra("id") + "&&" + "nickname=" +
                                nickname + "&&" + "ano=" + sno + "&&" + "introduction=" + introduction;
                        String result = new Utils().getConnectionResult("adminController","editAdmin",param);
                        Message message = new Message();
                        message.obj = result;
                        message.what = 1;
                        studentHandler.sendMessage(message);
                    }else Toast.makeText(getApplicationContext(),"输入的信息不能为空！",Toast.LENGTH_SHORT).show();

                }else {
                    String param = null;
                    String result = null;
                    Message message = new Message();
                    if(action.equals("edit") || action.equals("student")){
                        param = "id=" + getIntent().getStringExtra("id") + "&&" + "university=" + university + "&&" + "college=" +
                                college + "&&" + "major=" + profession + "&&" + "nickname=" + nickname + "&&" + "name=" + name + "&&" +
                                "sno=" + sno + "&&" + "grade=" + grade + "&&" + "classes=" + classes + "&&" + "introduction=" + introduction + "&&" +
                                "sex=" + sex;
                        result = new Utils().getConnectionResult("studentController","updateStudent",param);
                        message.obj = result;
                        message.what = 1;
                    }else if(action.equals("add")){

                        Calendar c = Calendar.getInstance();
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        int month = c.get(Calendar.MONTH);
                        int year = c.get(Calendar.YEAR);

                        String md5Password = null;
                        try {
                            MessageDigest md = MessageDigest.getInstance("MD5");
                            md.update(sno.getBytes());
                            md5Password = new BigInteger(1, md.digest()).toString(16);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        String currentDate = year + "-" + (month+1) + "-" + day;
                        param = "university=" + university + "&&" + "college=" + college + "&&" + "major=" + profession + "&&" + "nickname="
                                + nickname + "&&" + "name=" + name + "&&" + "sno=" + sno + "&&" + "grade=" + grade + "&&" + "classes="
                                + classes + "&&" + "sex=" + sex + "&&" + "register_time=" + currentDate + "&&" + "password=" + md5Password;
                        result = new Utils().getConnectionResult("studentController","insertStudent",param);
                        message.obj = result;
                        message.what = 0;
                    }

                    studentHandler.sendMessage(message);
                }

                Looper.loop();
            }
        }.start();

    }

    //头像的发送
    private void sendPicture(String username){
        String path = getFilesDir().getAbsolutePath()+"/avatar/app_icon.png";
        String url = "http://"+ Utils.ip + ":8080/" + Utils.project + "/studentController/getPicture";

        File file = new File(path);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",username + ".jpg",
                        RequestBody.create(MediaType.parse("application/octet-stream"),file)).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    //头像的重命名或删除
    private void renameOrDeletePicture(String operating){

        new Thread(){
            @Override
            public void run() {
                if(operating.equals("delete")){
                    new Utils().getConnectionResult("studentController","deletePicture",
                            "fileName=" + editSno.getText().toString() + ".jpg");
                }else if(operating.equals("rename")){
                    new Utils().getConnectionResult("studentController","renamePicture",
                            "editBefore=" + sno + ".jpg" + "&&" +
                                    "editAfter=" + editSno.getText().toString() + ".jpg");
                }
            }
        }.start();

    }

    //保存学生最新信息
    private void saveStudentNewInfo(){
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("nickname",editNickname.getText().toString());
        editor.putString("sex",sex);
        editor.putString("university",editUniversity.getText().toString());
        editor.putString("college",editCollege.getText().toString());
        editor.putString("name",editName.getText().toString());
        editor.putString("grade",editGrade.getText().toString());
        editor.putString("classes",editClass.getText().toString());
        editor.putString("introduction",editIntroduction.getText().toString());
        editor.putString("major",editProfession.getText().toString());
        editor.commit();
    }

    //保存管理员最新信息
    private void saveAdminNewInfo(){
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("nickname",editNickname.getText().toString());
        editor.putString("ano",editSno.getText().toString());
        editor.putString("introduction",editIntroduction.getText().toString());
        editor.commit();
    }

}