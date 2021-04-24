package com.example.physical_examination_app.Admin.AdminAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.physical_examination_app.R;

import java.util.List;
import java.util.Map;

public class ManageStudentAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,String>> dataSource;
    private int item_layout;

    private TextView studentName;
    private TextView collegeName;
    private TextView professionName;
    private TextView introductionText;
    private TextView gradeAndClass;
    private TextView studentId;

    public ManageStudentAdapter(Context context, List<Map<String,String>> dataSource,int item_layout){
        this.context = context;
        this.dataSource = dataSource;
        this.item_layout = item_layout;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null==convertView){
            convertView = LayoutInflater.from(context).inflate(item_layout,null);
        }

        getViews(convertView);

        Map<String, String> item = dataSource.get(position);
        studentName.setText(item.get("student_name"));

        return convertView;
    }

    //获取视图控件
    private void getViews(View view) {

        studentName = view.findViewById(R.id.student_name);
        collegeName = view.findViewById(R.id.college_name);
        professionName = view.findViewById(R.id.profession_name);
        introductionText = view.findViewById(R.id.introduction_text);
        gradeAndClass = view.findViewById(R.id.grade_and_class);
        studentId = view.findViewById(R.id.student_id);

    }

}
