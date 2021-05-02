package com.example.physical_examination_app.Student.StudentFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Student.GradeDetailActivity;
import com.example.physical_examination_app.Student.StudentAdapter.MyExamAdapter;
import com.example.physical_examination_app.Student.StudentAdapter.MyGradeAdapter;
import com.example.physical_examination_app.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeFragment extends Fragment {

    private EditText gradeSearch;
    private Spinner searchSpinner;
    private String type;

    private List<Map<String,String>> dataSource;
    private ListView myGradeList;
    private MyGradeAdapter myGradeAdapter;

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
                    item.put("status",jsonObject1.getString("if_pass"));
                    item.put("event",jsonObject.getString("item"));
                    item.put("date",jsonObject.getString("exam_date"));
                    item.put("jump_long",jsonObject1.getString("stand_jump"));
                    item.put("fifty_run",jsonObject1.getString("fifty_meter"));
                    String sex = getContext().getSharedPreferences("userInfo",Context.MODE_PRIVATE).getString("sex","");
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
                myGradeAdapter = new MyGradeAdapter(getContext(),dataSource,R.layout.my_grade_item);
                myGradeAdapter.notifyDataSetChanged();
                myGradeList.setAdapter(myGradeAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grade_fragment,container,false);

        getViews(view);
        setListView("getStudentExamInfo","");
        setListener();

        return view;
    }

    //设置监听器
    private void setListener() {

        myGradeList.setOnItemClickListener(customListener);
        searchSpinner.setOnItemSelectedListener(customListener);
        gradeSearch.addTextChangedListener(customListener);

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

    //获得视图控件
    private void getViews(View view) {

        myGradeList = view.findViewById(R.id.my_grade_list);
        gradeSearch = view.findViewById(R.id.grade_search);
        searchSpinner = view.findViewById(R.id.search_spinner);

    }

    //自定义监听器
    class CustomListener implements AdapterView.OnItemClickListener, TextWatcher, AdapterView.OnItemSelectedListener {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String param = "&&" + "type=" + type + "&&" + "value=" + gradeSearch.getText().toString();
            setListView("getStudentExamInfoByType",param);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> item = dataSource.get(position);
            Intent intent = new Intent();
            intent.setClass(getContext(), GradeDetailActivity.class);
            intent.putExtra("grade_status",item.get("status"));
            intent.putExtra("grade_teacher",item.get("teacher"));
            intent.putExtra("grade_date",item.get("date"));
            intent.putExtra("jump_long",item.get("jump_long"));
            intent.putExtra("fifty_run",item.get("fifty_run"));
            intent.putExtra("long_run",item.get("long_run"));
            intent.putExtra("extra_event",item.get("extra_event"));
            intent.putExtra("sit_bending",item.get("sit_bending"));
            intent.putExtra("lung_container",item.get("lung_container"));
            intent.putExtra("grade_location",item.get("location"));
            intent.setAction("考试信息");
            startActivity(intent);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    type = "place";
                    break;
                case 1:
                    type = "teacher";
                    break;
                case 2:
                    type = "if_pass";
                    break;
                case 3:
                    type = "exam_date";
                    break;
            }
            String param = "&&" + "type=" + type + "&&" + "value=" + gradeSearch.getText().toString();
            setListView("getStudentExamInfoByType",param);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setListView("getStudentExamInfo","");
    }

}
