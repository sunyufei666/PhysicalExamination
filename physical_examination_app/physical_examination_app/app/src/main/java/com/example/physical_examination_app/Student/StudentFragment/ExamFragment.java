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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Student.ExamDetailActivity;
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
    private int spinnerPosition;

    private List<Map<String,String>> dataSource;
    private ListView myExamList;
    private MyExamAdapter myExamAdapter;

    private CustomListener customListener = new CustomListener();

    private Handler userExamHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
//            try {
//                JSONArray jsonArrayAll = new JSONArray(result);
//                dataSource = new ArrayList<>();
//                for (int i = 0;i<jsonArrayAll.length();i++){
//                    JSONObject jsonObject = new JSONArray(jsonArrayAll.getString(i)).getJSONObject(1);
//                    Log.e("sadsadsadsadasd",jsonObject.toString());
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_fragment,container,false);

        getViews(view);

        setListView();
        setListener();

        return view;
    }

    //给控件设置自定义的监听器
    private void setListener() {

        myExamList.setOnItemClickListener(customListener);
        examSearch.addTextChangedListener(customListener);
        searchSpinner.setOnItemSelectedListener(customListener);

    }

    //为ListView准备
    private void setListView() {

        SharedPreferences pre = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        //向服务器发送数据获得所有考试
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("studentExamController","getStudentExamInfo","id="+pre.getString("id",""));
                Message message = new Message();
                message.obj = result;
                userExamHandler.sendMessage(message);

            }
        }.start();

        dataSource = new ArrayList<>();
        for (int i = 0;i<5;i++){
            Map<String, String> item = new HashMap<>();
            item.put("location","西操场");
            item.put("people","50/80");
            item.put("event","立定跳远、50米跑、坐位体前屈（男）......");
            item.put("status","未满");
            item.put("date","2020年11月11日");
            dataSource.add(item);
        }

        myExamAdapter = new MyExamAdapter(getContext(),dataSource,R.layout.my_exam_item);
        myExamList.setAdapter(myExamAdapter);

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
            intent.putExtra("people",item.get("people"));
            intent.putExtra("event",item.get("event"));
            intent.putExtra("status",item.get("status"));
            intent.putExtra("date",item.get("date"));
            startActivity(intent);

        }

        //spinner接口里的方法实现
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            spinnerPosition = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

}
