package com.example.physical_examination_app.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.physical_examination_app.Admin.AdminAdapter.ManageNewsAdapter;
import com.example.physical_examination_app.Admin.NewsEditActivity;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyNewsActivity extends AppCompatActivity {

    private ImageView returnButton;

    private List<Map<String, String>> dataSource;
    private ListView myNewsList;
    private ManageNewsAdapter manageNewsAdapter;

    private Handler newsHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            if(msg.what == 0){
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    dataSource = new ArrayList<>();
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Map<String, String> item = new HashMap<>();
                        item.put("id",jsonObject.getString("id"));
                        item.put("news_date",jsonObject.getString("news_date"));
                        item.put("news_content",jsonObject.getString("news_content"));
                        item.put("if_view",jsonObject.getString("if_view"));
                        dataSource.add(item);
                    }

                    manageNewsAdapter = new ManageNewsAdapter(getApplicationContext(),dataSource,R.layout.news_item);
                    manageNewsAdapter.notifyDataSetChanged();
                    myNewsList.setAdapter(manageNewsAdapter);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);

        returnButton = findViewById(R.id.return_button);
        myNewsList = findViewById(R.id.my_news_list);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getNews();

        myNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String, String> item = dataSource.get(position);
                if(item.get("if_view").equals("0")){
                    addViewNews(item.get("id"));
                }
                Intent intent = new Intent();
                intent.putExtra("id",item.get("id"));
                intent.putExtra("news_date",item.get("news_date"));
                intent.putExtra("news_content",item.get("news_content"));
                intent.setAction("view");
                intent.setClass(getApplicationContext(), NewsEditActivity.class);
                startActivity(intent);

            }
        });

    }

    //添加查看消息
    private void addViewNews(String newsId) {

        new Thread(){
            @Override
            public void run() {
                String param = "student_id=" + getSharedPreferences("userInfo",MODE_PRIVATE).getString("id","") + "&&" + "news_id=" + newsId;
                String result = new Utils().getConnectionResult("newsController","viewNews",param);
                Message message = new Message();
                message.obj = result;
                message.what = 1;
                newsHandler.sendMessage(message);
            }
        }.start();

    }

    //向服务器发送请求获取消息
    private void getNews() {

        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("newsController","getNewsByStudentId","student_id=" +
                        getSharedPreferences("userInfo",MODE_PRIVATE).getString("id",""));
                Message message = new Message();
                message.obj = result;
                message.what = 0;
                newsHandler.sendMessage(message);
            }
        }.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNews();
    }
}