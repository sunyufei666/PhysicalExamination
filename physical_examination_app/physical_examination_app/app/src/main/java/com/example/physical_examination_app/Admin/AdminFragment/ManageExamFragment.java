package com.example.physical_examination_app.Admin.AdminFragment;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.physical_examination_app.Admin.AdminAdapter.ManageExamAdapter;
import com.example.physical_examination_app.Admin.ExamEditActivity;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.common.ExamDetailActivity;
import com.example.physical_examination_app.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageExamFragment extends Fragment {

    private EditText examSearch;
    private Spinner searchSpinner;

    private List<Map<String,String>> dataSource;
    private ListView manageExamList;
    private ManageExamAdapter manageExamAdapter;

    private LinearLayout addExamButton;

    private CustomListener customListener = new CustomListener();

    private String type;

    private Handler userExamHandler = new Handler(){
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
                    item.put("public_date",jsonObject.getString("public_date"));
                    item.put("exam_date",jsonObject.getString("exam_date"));
                    dataSource.add(item);
                }

                manageExamAdapter = new ManageExamAdapter(getContext(),dataSource,R.layout.manage_exam_item);
                manageExamAdapter.notifyDataSetChanged();
                manageExamList.setAdapter(manageExamAdapter);

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_exam_fragment,container,false);

        getViews(view);
        setListener();
        setListView("");

        return view;
    }

    //设置监听器
    private void setListener() {

        examSearch.addTextChangedListener(customListener);
        searchSpinner.setOnItemSelectedListener(customListener);
        manageExamList.setOnItemClickListener(customListener);
        addExamButton.setOnClickListener(customListener);

    }

    //设置list控件的数据
    private void setListView(String param) {

        //向服务器发送数据获得所有考试
        new Thread(){
            @Override
            public void run() {
                String result = null;
                if (param.equals("")){
                    result = new Utils().getConnectionResult("examController","getAllExam");
                }else {
                    result = new Utils().getConnectionResult("examController","getExamByType",param);
                }
                Message message = new Message();
                message.obj = result;
                userExamHandler.sendMessage(message);
            }
        }.start();

    }

    //获取视图控件
    private void getViews(View view) {

        manageExamList = view.findViewById(R.id.manage_exam_list);

        examSearch = view.findViewById(R.id.exam_search);
        searchSpinner = view.findViewById(R.id.search_spinner);
        addExamButton = view.findViewById(R.id.add_exam_button);

    }

    //自定义listener
    class CustomListener implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher, AdapterView.OnItemClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_exam_button:
                    Intent intent = new Intent();
                    intent.setClass(getContext(), ExamEditActivity.class);
                    intent.setAction("add");
                    startActivity(intent);
                    break;
            }
        }

        //spinner选择监听
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
                    type = "public_date";
                    break;
            }
            String param = "type=" + type + "&&" + "value=" + examSearch.getText().toString();
            setListView(param);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        //输入框监听
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String param = "type=" + type + "&&" + "value=" + examSearch.getText().toString();
            setListView(param);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        //adapter点击监听
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> item = dataSource.get(position);
            Intent intent = new Intent();
            intent.setClass(getContext(), ExamEditActivity.class);
            intent.putExtra("id",item.get("id"));
            intent.putExtra("location",item.get("location"));
            intent.putExtra("teacher",item.get("teacher"));
            intent.putExtra("people",item.get("people"));
            intent.putExtra("exam_date",item.get("exam_date"));
            intent.putExtra("release_date",item.get("public_date"));
            intent.setAction("edit");
            startActivity(intent);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setListView("");
    }
}
