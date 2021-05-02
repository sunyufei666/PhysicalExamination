package com.example.physical_examination_app.Admin.AdminAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.physical_examination_app.R;

import java.util.List;
import java.util.Map;

public class ManageNewsAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,String>> dataSource;
    private int item_layout;

    private TextView newsDate;
    private TextView newsContent;
    private ImageView newsAlert;

    public ManageNewsAdapter(Context context, List<Map<String,String>> dataSource,int item_layout){
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

        String content = item.get("news_content");
        if (content.length() > 40) newsContent.setText(content.substring(0,40)+"......");
        else newsContent.setText(content);
        newsDate.setText(item.get("news_date"));

        if(item.get("if_view").equals("0")) newsAlert.setVisibility(View.VISIBLE);
        else if(item.get("if_view").equals("1")) newsAlert.setVisibility(View.INVISIBLE);

        return convertView;
    }


    private void getViews(View view) {

        newsDate = view.findViewById(R.id.news_date);
        newsContent = view.findViewById(R.id.news_content);
        newsAlert = view.findViewById(R.id.news_alert);

    }

}
