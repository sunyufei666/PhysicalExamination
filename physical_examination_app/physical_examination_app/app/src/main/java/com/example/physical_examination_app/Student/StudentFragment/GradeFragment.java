package com.example.physical_examination_app.Student.StudentFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeFragment extends Fragment {

    private EditText gradeSearch;
    private Spinner searchSpinner;
    private int spinnerPosition;

    private List<Map<String,String>> dataSource;
    private ListView myGradeList;
    private MyGradeAdapter myGradeAdapter;

    private CustomListener customListener = new CustomListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grade_fragment,container,false);

        getViews(view);
        setListView();
        setListener();

        return view;
    }

    //设置监听器
    private void setListener() {

        myGradeList.setOnItemClickListener(customListener);

    }

    //为ListView准备
    private void setListView() {

        dataSource = new ArrayList<>();
        for (int i = 0;i<5;i++){
            Map<String, String> item = new HashMap<>();
            item.put("location","西操场");
            item.put("event","立定跳远、50米跑、坐位体前屈（男）......");
            item.put("status","未完成");
            item.put("date","2020年11月11日");
            dataSource.add(item);
        }
        myGradeAdapter = new MyGradeAdapter(getContext(),dataSource,R.layout.my_grade_item);
        myGradeList.setAdapter(myGradeAdapter);


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

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(getContext(), GradeDetailActivity.class);
            startActivity(intent);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

}
