package com.example.physical_examination_app.Student.StudentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.physical_examination_app.R;

import java.util.List;
import java.util.Map;

public class MyGradeAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,String>> dataSource;
    private int item_layout;

    private TextView gradeLocation;
    private TextView gradeEvent;
    private TextView gradeStatus;
    private TextView gradeDate;

    private LinearLayout gradeBackground;

    public MyGradeAdapter(Context context, List<Map<String,String>> dataSource, int item_layout){
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

        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(item_layout,null);
        }

        getViews(convertView);
        Map<String, String> item = dataSource.get(position);
        String location = item.get("location");
        gradeLocation.setText(location);
        gradeStatus.setText(item.get("status"));
        gradeEvent.setText("项目："+item.get("event"));
        gradeDate.setText("考试日期："+item.get("date"));

        if(position%3==1){
            gradeBackground.setBackground(context.getResources().getDrawable(R.drawable.jump));
        }else if(position%3==2){
            gradeBackground.setBackground(context.getResources().getDrawable(R.drawable.bar));
        }

        return convertView;
    }

    //获取视图控件
    private void getViews(View view) {

        gradeLocation = view.findViewById(R.id.grade_location);
        gradeStatus = view.findViewById(R.id.grade_status);
        gradeEvent = view.findViewById(R.id.grade_event);
        gradeDate = view.findViewById(R.id.grade_date);

        gradeBackground = view.findViewById(R.id.grade_background);

    }



}
