package com.example.physical_examination_app.Student.StudentFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.common.ExamDetailActivity;
import com.example.physical_examination_app.Student.StudentAdapter.MyExamAdapter;
import com.example.physical_examination_app.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamFragment extends Fragment {

    private EditText examSearch;
    private Spinner searchSpinner;
    private String type;

    private List<Map<String,String>> dataSource;
    private ListView myExamList;
    private MyExamAdapter myExamAdapter;

    private CustomListener customListener = new CustomListener();

    private Handler userExamHandler = new Handler(){
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
                    item.put("location",jsonObject.getString("place"));
                    item.put("teacher",jsonObject.getString("teacher"));
                    item.put("people",jsonObject.getString("current_people")+"/"+jsonObject.getString("all_people"));
                    item.put("event",jsonObject.getString("item"));
                    item.put("status",jsonObject1.getString("if_complete"));
                    item.put("release_date",jsonObject.getString("public_date"));
                    item.put("date",jsonObject.getString("exam_date"));
                    dataSource.add(item);
                }

                myExamAdapter = new MyExamAdapter(getContext(),dataSource,R.layout.my_exam_item);
                myExamAdapter.notifyDataSetChanged();
                myExamList.setAdapter(myExamAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_fragment,container,false);

        getViews(view);

        setListView("getStudentExamInfo","");
        setListener();

        return view;
    }

    //给控件设置自定义的监听器
    private void setListener() {

        myExamList.setOnItemClickListener(customListener);
        searchSpinner.setOnItemSelectedListener(customListener);
        examSearch.addTextChangedListener(customListener);

    }

    //为ListView准备
    private void setListView(String method, String param) {

        SharedPreferences pre = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        //向服务器发送数据获得所有考试
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("studentExamController",method,"id="+pre.getString("id","")+param);
                Message message = new Message();
                message.obj = result;
                userExamHandler.sendMessage(message);
            }
        }.start();

    }

    //获取视图控件
    private void getViews(View view) {

        myExamList = view.findViewById(R.id.my_exam_list);

        examSearch = view.findViewById(R.id.exam_search);
        searchSpinner = view.findViewById(R.id.search_spinner);

    }

    //自定义监听器
    class CustomListener implements AdapterView.OnItemClickListener, TextWatcher , AdapterView.OnItemSelectedListener {

        //监听点击
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String param = "&&" + "type=" + type + "&&" + "value=" + examSearch.getText().toString();
            setListView("getStudentExamInfoByType",param);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        //每一项的点击实现
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> item = dataSource.get(position);
            Intent intent = new Intent();
            intent.setClass(getContext(), ExamDetailActivity.class);
            intent.putExtra("location",item.get("location"));
            intent.putExtra("teacher",item.get("teacher"));
            intent.putExtra("people",item.get("people"));
            intent.putExtra("event",item.get("event"));
            intent.putExtra("status",item.get("status"));
            intent.putExtra("date",item.get("date"));
            intent.putExtra("release_date",item.get("release_date"));
            intent.setAction("考试详情");
            startActivity(intent);
        }

        //spinner接口里的方法实现
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    type = "place";
                    break;
                case 1:
                    type = "if_complete";
                    break;
                case 2:
                    type = "exam_date";
                    break;
            }
            String param = "&&" + "type=" + type + "&&" + "value=" + examSearch.getText().toString();
            setListView("getStudentExamInfoByType",param);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

}
