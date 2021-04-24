package com.example.physical_examination_app.Admin.AdminFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.physical_examination_app.Admin.AdminAdapter.ManageExamAdapter;
import com.example.physical_examination_app.Admin.AdminAdapter.ManageStudentAdapter;
import com.example.physical_examination_app.Admin.StudentEditActivity;
import com.example.physical_examination_app.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageStudentFragment extends Fragment {

    private List<Map<String,String>> dataSource;
    private ListView manageStudentList;
    private ManageStudentAdapter manageStudentAdapter;

    private LinearLayout addStudentButton;

    private CustomListener customListener = new CustomListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_student_fragment,container,false);

        getViews(view);
        setListView();
        setListener();

        return view;
    }

    //给控件设置监听器
    private void setListener() {

        addStudentButton.setOnClickListener(customListener);

    }

    //设置ListView中的数据
    private void setListView() {

        dataSource = new ArrayList<>();
        for(int i = 0;i<10;i++){
            Map<String, String> item = new HashMap<>();
            item.put("student_name","杨锡涛");
            dataSource.add(item);
        }

        manageStudentAdapter = new ManageStudentAdapter(getContext(),dataSource,R.layout.manage_student_item);
        manageStudentList.setAdapter(manageStudentAdapter);

    }

    //获取视图控件
    private void getViews(View view) {

        manageStudentList = view.findViewById(R.id.manage_student_list);

        addStudentButton = view.findViewById(R.id.add_student_button);


    }

    //自定义监听器
    class CustomListener implements View.OnClickListener{

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

    }

}
