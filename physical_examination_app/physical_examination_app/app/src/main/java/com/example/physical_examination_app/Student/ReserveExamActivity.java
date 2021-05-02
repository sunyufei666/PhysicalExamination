package com.example.physical_examination_app.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Student.StudentAdapter.ReserveExamAdapter;
import com.example.physical_examination_app.Utils;
import com.example.physical_examination_app.common.ExamDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReserveExamActivity extends AppCompatActivity {

    private ImageView returnButton;

    private List<Map<String,String>> dataSource;
    private ListView myExamList;
    private ReserveExamAdapter reserveExamAdapter;

    private Handler reserveExamHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            try {
                JSONArray jsonArray = new JSONArray(result);
                dataSource = new ArrayList<>();
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Map<String, String> item = new HashMap<>();
                    item.put("id",jsonObject.getString("id"));
                    item.put("location",jsonObject.getString("place"));
                    item.put("people",jsonObject.getString("current_people")+"/"+jsonObject.getString("all_people"));
                    item.put("event",jsonObject.getString("item"));
                    item.put("teacher",jsonObject.getString("teacher"));
                    item.put("date",jsonObject.getString("public_date"));
                    dataSource.add(item);
                }

                reserveExamAdapter = new ReserveExamAdapter(getApplicationContext(),dataSource,R.layout.my_exam_item);
                reserveExamAdapter.notifyDataSetChanged();
                myExamList.setAdapter(reserveExamAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_exam);

        getViews();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getReserveExam();

        myExamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> item = dataSource.get(position);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ExamDetailActivity.class);
                intent.putExtra("id",item.get("id"));
                intent.putExtra("location",item.get("location"));
                intent.putExtra("teacher",item.get("teacher"));
                intent.putExtra("people",item.get("people"));
                intent.putExtra("event",item.get("event"));
                intent.putExtra("status","");
                intent.putExtra("date",item.get("date"));
                intent.setAction("考试预约");
                startActivity(intent);
            }
        });
    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);
        myExamList = findViewById(R.id.reserve_exam_list);

    }

    //向后台请求获得可预约考试
    private void getReserveExam(){
        //向服务器发送数据获得所有考试
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("examController","getReserveExam",
                        "student_id=" + getSharedPreferences("userInfo",MODE_PRIVATE).getString("id",""));
                Message message = new Message();
                message.obj = result;
                reserveExamHandler.sendMessage(message);
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReserveExam();
    }
}