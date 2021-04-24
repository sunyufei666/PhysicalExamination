package com.example.physical_examination_app.Admin.AdminFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.physical_examination_app.Admin.AdminAdapter.ManageExamAdapter;
import com.example.physical_examination_app.Admin.ExamEditActivity;
import com.example.physical_examination_app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageExamFragment extends Fragment {

    private List<Map<String,String>> dataSource;
    private ListView manageExamList;
    private ManageExamAdapter manageExamAdapter;

    private LinearLayout addExamButton;

    private CustomListener customListener = new CustomListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_exam_fragment,container,false);

        getViews(view);
        setListView();
        setListener();

        return view;
    }

    //设置监听器
    private void setListener() {

        addExamButton.setOnClickListener(customListener);

    }

    //设置list控件的数据
    private void setListView() {

        dataSource = new ArrayList<>();
        Map<String, String> item1 = new HashMap<>();
        item1.put("location","西操场");
        Map<String, String> item2 = new HashMap<>();
        item2.put("location","东操场");
        Map<String, String> item3 = new HashMap<>();
        item3.put("location","西操场");
        Map<String, String> item4 = new HashMap<>();
        item4.put("location","东操场");
        dataSource.add(item1);
        dataSource.add(item2);
        dataSource.add(item3);
        dataSource.add(item4);

        manageExamAdapter = new ManageExamAdapter(getContext(),dataSource,R.layout.manage_exam_item);
        manageExamList.setAdapter(manageExamAdapter);

    }

    //获取视图控件
    private void getViews(View view) {

        manageExamList = view.findViewById(R.id.manage_exam_list);

        addExamButton = view.findViewById(R.id.add_exam_button);

    }

    //自定义listener
    class CustomListener implements View.OnClickListener{

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

    }

}
