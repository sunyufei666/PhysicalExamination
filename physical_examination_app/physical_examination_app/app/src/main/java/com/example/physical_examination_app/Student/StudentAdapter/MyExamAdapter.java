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

public class MyExamAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,String>> dataSource;
    private int item_layout;

    private TextView examLocation;
    private TextView examPeople;
    private TextView examEvent;
    private TextView examStatus;
    private TextView examDate;
    private LinearLayout examBackground;

    public MyExamAdapter(Context context, List<Map<String,String>> dataSource,int item_layout){
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

        examLocation.setText(item.get("location"));
        examPeople.setText("人数："+item.get("people"));
        examEvent.setText("项目："+item.get("event").substring(0,30)+"......");
        examStatus.setText(item.get("status"));
        examDate.setText("发布日期："+item.get("date"));
        if(location.equals("东操场")){
            examBackground.setBackground(context.getResources().getDrawable(R.drawable.ground2));
        }

        return convertView;
    }

    //获取视图控件
    private void getViews(View view) {

        examLocation = view.findViewById(R.id.exam_location);
        examPeople = view.findViewById(R.id.exam_people);
        examEvent = view.findViewById(R.id.exam_event);
        examStatus = view.findViewById(R.id.exam_status);
        examDate = view.findViewById(R.id.exam_date);

        examBackground = view.findViewById(R.id.exam_background);

    }



}
