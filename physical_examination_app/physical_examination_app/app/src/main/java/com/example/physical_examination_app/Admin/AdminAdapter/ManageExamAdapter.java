package com.example.physical_examination_app.Admin.AdminAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.physical_examination_app.R;

import java.util.List;
import java.util.Map;

public class ManageExamAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,String>> dataSource;
    private int item_layout;

    private TextView examLocation;
    private TextView examPeople;
    private TextView examEvent;
    private TextView examTeacher;
    private TextView releaseDate;

    private LinearLayout examBackground;

    public ManageExamAdapter(Context context, List<Map<String,String>> dataSource,int item_layout){
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
            convertView = LayoutInflater.from(context).inflate(item_layout,parent,false);
        }

        getViews(convertView);
        Map<String, String> item = dataSource.get(position);
        String location = item.get("location");
        examLocation.setText(location);
        examPeople.setText("人数："+item.get("people"));
        examEvent.setText(item.get("event").substring(0,30)+"......");
        examTeacher.setText("负责教师："+item.get("teacher"));
        releaseDate.setText("发布日期："+item.get("public_date"));
        if(location.equals("东操场")){
            examBackground.setBackground(context.getResources().getDrawable(R.drawable.ground2));
        }


        return convertView;
    }

    //获取视图控件
    private void getViews(View view) {

        examLocation = view.findViewById(R.id.exam_location);
        examTeacher = view.findViewById(R.id.exam_teacher);
        examPeople = view.findViewById(R.id.exam_people);
        examEvent = view.findViewById(R.id.exam_event);
        releaseDate = view.findViewById(R.id.release_date);

        examBackground = view.findViewById(R.id.exam_background);

    }


}
