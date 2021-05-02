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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.physical_examination_app.Admin.AdminAdapter.ManageExamAdapter;
import com.example.physical_examination_app.Admin.AdminAdapter.ManageStudentAdapter;
import com.example.physical_examination_app.Admin.ExamEditActivity;
import com.example.physical_examination_app.Admin.StudentEditActivity;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Utils;
import com.example.physical_examination_app.common.ExamDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageStudentFragment extends Fragment {

    private EditText studentSearch;
    private Spinner searchSpinner;

    private List<Map<String,String>> dataSource;
    private ListView manageStudentList;
    private ManageStudentAdapter manageStudentAdapter;

    private LinearLayout addStudentButton;

    private CustomListener customListener = new CustomListener();

    private String type;

    private Handler studentHandler = new Handler(){
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
                    item.put("name",jsonObject.getString("name"));
                    item.put("grade",jsonObject.getString("grade"));
                    item.put("class",jsonObject.getString("classes"));
                    item.put("introduction",jsonObject.getString("introduction"));
                    item.put("sno",jsonObject.getString("sno"));
                    item.put("nickname",jsonObject.getString("nickname"));
                    item.put("sex",jsonObject.getString("sex"));
                    item.put("university",jsonObject.getString("university"));
                    item.put("college",jsonObject.getString("college"));
                    item.put("profession",jsonObject.getString("major"));
                    item.put("register_time",jsonObject.getString("register_time"));
                    dataSource.add(item);
                }

                manageStudentAdapter = new ManageStudentAdapter(getContext(),dataSource,R.layout.manage_student_item);
                manageStudentAdapter.notifyDataSetChanged();
                manageStudentList.setAdapter(manageStudentAdapter);

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_student_fragment,container,false);

        getViews(view);
        setListView("");
        setListener();

        return view;
    }

    //给控件设置监听器
    private void setListener() {

        studentSearch.addTextChangedListener(customListener);
        searchSpinner.setOnItemSelectedListener(customListener);
        manageStudentList.setOnItemClickListener(customListener);
        addStudentButton.setOnClickListener(customListener);


    }

    //设置ListView中的数据
    private void setListView(String param) {

        //向服务器发送数据获得所有学生
        new Thread(){
            @Override
            public void run() {
                String result = null;
                if (param.equals("")){
                    result = new Utils().getConnectionResult("studentController","getAllStudent");
                }else {
                    result = new Utils().getConnectionResult("studentController","getStudentByType",param);
                }
                Message message = new Message();
                message.obj = result;
                studentHandler.sendMessage(message);
            }
        }.start();

    }

    //获取视图控件
    private void getViews(View view) {

        manageStudentList = view.findViewById(R.id.manage_student_list);

        studentSearch = view.findViewById(R.id.student_search);
        searchSpinner = view.findViewById(R.id.search_spinner);

        addStudentButton = view.findViewById(R.id.add_student_button);


    }

    //自定义listener
    class CustomListener implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher, AdapterView.OnItemClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_student_button:
                    Intent intent = new Intent();
                    intent.setClass(getContext(), StudentEditActivity.class);
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
                    type = "name";
                    break;
                case 1:
                    type = "sno";
                    break;
                case 2:
                    type = "grade";
                    break;
                case 3:
                    type = "classes";
                    break;
                case 4:
                    type = "college";
                    break;
                case 5:
                    type = "major";
                    break;
            }
            String param = "type=" + type + "&&" + "value=" + studentSearch.getText().toString();
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
            String param = "type=" + type + "&&" + "value=" + studentSearch.getText().toString();
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
            intent.setClass(getContext(), StudentEditActivity.class);
            intent.putExtra("id",item.get("id"));
            intent.putExtra("university",item.get("university"));
            intent.putExtra("college",item.get("college"));
            intent.putExtra("profession",item.get("profession"));
            intent.putExtra("nickname",item.get("nickname"));
            intent.putExtra("name",item.get("name"));
            intent.putExtra("sex",item.get("sex"));
            intent.putExtra("sno",item.get("sno"));
            intent.putExtra("grade",item.get("grade"));
            intent.putExtra("class",item.get("class"));
            intent.putExtra("introduction",item.get("introduction"));
            intent.putExtra("register_time",item.get("register_time"));
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
