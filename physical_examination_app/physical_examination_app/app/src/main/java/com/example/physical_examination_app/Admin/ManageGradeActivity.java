package com.example.physical_examination_app.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Student.StudentAdapter.MyGradeAdapter;
import com.example.physical_examination_app.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageGradeActivity extends AppCompatActivity {

    private ImageView returnButton;

    private List<Map<String,String>> dataSource;
    private ListView studentGradeList;
    private MyGradeAdapter myGradeAdapter;

    private String id;

    private Handler studentExamHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            try {
                JSONArray jsonArrayAll = new JSONArray(result);
                dataSource = new ArrayList<>();
                for (int i = 0;i<jsonArrayAll.length();i++){
                    JSONObject jsonObject = new JSONArray(jsonArrayAll.getString(i)).getJSONObject(1);
                    JSONObject jsonObject1 = new JSONArray(jsonArrayAll.getString(i)).getJSONObject(0);
                    Map<String, String> item = new HashMap<>();
                    item.put("id",jsonObject1.getString("id"));
                    item.put("location",jsonObject.getString("place"));
                    item.put("teacher",jsonObject.getString("teacher"));
                    item.put("status",jsonObject1.getString("if_pass"));
                    item.put("event",jsonObject.getString("item"));
                    item.put("date",jsonObject.getString("exam_date"));
                    item.put("jump_long",jsonObject1.getString("stand_jump"));
                    item.put("fifty_run",jsonObject1.getString("fifty_meter"));
                    String sex = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("sex","");
                    if(sex.equals("女")){
                        item.put("long_run",jsonObject1.getString("eighthundred_meter"));
                        item.put("extra_event",jsonObject1.getString("sit_up"));
                    }else {
                        item.put("long_run",jsonObject1.getString("onethousand_meter"));
                        item.put("extra_event",jsonObject1.getString("pull_up"));
                    }
                    item.put("sit_bending",jsonObject1.getString("sitting_forward"));
                    item.put("lung_container",jsonObject1.getString("vital_capacity"));
                    dataSource.add(item);
                }

                myGradeAdapter = new MyGradeAdapter(getApplicationContext(),dataSource,R.layout.my_grade_item);
                myGradeAdapter.notifyDataSetChanged();
                studentGradeList.setAdapter(myGradeAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_grade);

        getViews();

        //返回按钮
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        id = getIntent().getStringExtra("id");

        otherOperating();

        studentGradeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> item = dataSource.get(position);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),GradeEditActivity.class);
                intent.putExtra("id",item.get("id"));
                intent.putExtra("pass",item.get("status"));
                intent.putExtra("teacher",item.get("teacher"));
                intent.putExtra("date",item.get("date"));
                intent.putExtra("sex",getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("sex",""));
                intent.putExtra("fifty_run",item.get("fifty_run"));
                intent.putExtra("long_run",item.get("long_run"));
                intent.putExtra("sit_bending",item.get("sit_bending"));
                intent.putExtra("extra_event",item.get("extra_event"));
                intent.putExtra("jump_long",item.get("jump_long"));
                intent.putExtra("lung_container",item.get("lung_container"));
                intent.putExtra("location",item.get("location"));
                startActivity(intent);
            }
        });

    }

    //其他操作
    private void otherOperating() {

        //向服务器发送数据获得所有考试
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("studentExamController","getStudentExamInfo","id="+id);
                Message message = new Message();
                message.obj = result;
                studentExamHandler.sendMessage(message);
            }
        }.start();

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);
        studentGradeList = findViewById(R.id.student_grade_list);

    }

    @Override
    protected void onResume() {
        super.onResume();
        otherOperating();
    }
}