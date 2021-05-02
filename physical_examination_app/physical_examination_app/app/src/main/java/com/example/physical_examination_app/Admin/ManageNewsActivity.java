package com.example.physical_examination_app.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.physical_examination_app.Admin.AdminAdapter.ManageNewsAdapter;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageNewsActivity extends AppCompatActivity {

    private ImageView returnButton;
    private LinearLayout addNewsButton;

    private List<Map<String,String>> dataSource;
    private ListView newsList;
    private ManageNewsAdapter manageNewsAdapter;

    private CustomListener customListener = new CustomListener();

    private Handler newsHandler = new Handler(){
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
                    item.put("news_date",jsonObject.getString("news_date"));
                    item.put("news_content",jsonObject.getString("news_content"));
                    item.put("if_view","2");
                    dataSource.add(item);
                }

                manageNewsAdapter = new ManageNewsAdapter(getApplicationContext(),dataSource,R.layout.news_item);
                manageNewsAdapter.notifyDataSetChanged();
                newsList.setAdapter(manageNewsAdapter);

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_news);

        getViews();
        setListener();
        otherOperating();

    }

    //给控件设置监听器
    private void setListener() {

        returnButton.setOnClickListener(customListener);
        newsList.setOnItemClickListener(customListener);
        addNewsButton.setOnClickListener(customListener);

    }

    //其他操作
    private void otherOperating() {

        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("newsController","getAllNews");
                Message message = new Message();
                message.obj = result;
                newsHandler.sendMessage(message);
            }
        }.start();

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);
        newsList = findViewById(R.id.manage_news_list);
        addNewsButton = findViewById(R.id.add_news_button);

    }

    //自定义listener
    class CustomListener implements View.OnClickListener, AdapterView.OnItemClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.return_button:
                    finish();
                    break;
                case R.id.add_news_button:
                    Intent intent = new Intent();
                    intent.setAction("add");
                    intent.setClass(getApplicationContext(),NewsEditActivity.class);
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Map<String, String> item = dataSource.get(position);
            Intent intent = new Intent();
            intent.putExtra("id",item.get("id"));
            intent.putExtra("news_date",item.get("news_date"));
            intent.putExtra("news_content",item.get("news_content"));
            intent.setAction("edit");
            intent.setClass(getApplicationContext(),NewsEditActivity.class);
            startActivity(intent);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        otherOperating();
    }

}