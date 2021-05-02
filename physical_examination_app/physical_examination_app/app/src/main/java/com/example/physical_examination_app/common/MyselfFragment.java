package com.example.physical_examination_app.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.physical_examination_app.Admin.ManageGradeActivity;
import com.example.physical_examination_app.Admin.ManageNewsActivity;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Student.ReserveExamActivity;
import com.example.physical_examination_app.Utils;
import com.google.android.material.appbar.AppBarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class MyselfFragment extends Fragment {

    private AppBarLayout appBarLayout;
    private LinearLayout settingLayout;

    private ImageView myAvatar;
    private TextView myNickname;
    private TextView introductionText;
    private TextView proverbText;
    private TextView proverbAuthor;
    private TextView proverbTextBottom;
    private TextView proverbAuthorBottom;

    private ImageView itemThirdImage;
    private TextView itemThirdText;

    private LinearLayout itemAvatarLayout;
    private LinearLayout itemDetailLayout;
    private LinearLayout itemReserveLayout;
    private LinearLayout itemSettingLayout;

    private CustomListener customListener = new CustomListener();

    private Handler proverbHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.obj + "";
            try {
                JSONObject json = new JSONObject(result);
                proverbText.setText("      “" + json.getString("content") + "”");
                proverbAuthor.setText("————  " + json.getString("author"));
                proverbTextBottom.setText("      “" + json.getString("content") + "”");
                proverbAuthorBottom.setText("————  " + json.getString("author"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myself_fragment,container,false);

        getViews(view);

        getAvatar();

        otherOperating();
        setListener();


        return view;
    }

    //从后台获取头像
    private void getAvatar() {

        String avatarName = null;
        SharedPreferences pre = getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        if(pre.getString("login_role","").equals("student")){
            avatarName = pre.getString("sno","") + ".jpg";
        }else avatarName = pre.getString("ano","") + ".jpg";

        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).fallback(R.mipmap.app_icon)
                .error(R.drawable.error).diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop();
        Glide.with(getActivity()).load(Utils.AVATAR_PATH + avatarName)
                .thumbnail(Glide.with(getActivity()).load(R.drawable.loading))
                .apply(requestOptions).into(myAvatar);
    }

    //其他操作
    private void otherOperating() {

        SharedPreferences pre = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);

        //当登录角色为管理员时，将个人页的预约考试换为成绩管理，包括图标和文字
        if(pre.getString("login_role","").equals("admin")){
            itemThirdImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.item_grade));
            itemThirdText.setText("消息管理");
        }

        //获取用户的昵称和简介并展示
        myNickname.setText(pre.getString("nickname",""));
        introductionText.setText(pre.getString("introduction",""));

        getProverb();

    }


    //获取谚语
    private void getProverb() {

        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("adminController","getProverb");
                Message message = new Message();
                message.obj = result;
                proverbHandler.sendMessage(message);
            }
        }.start();

    }

    //获取视图控件
    private void getViews(View view) {

        appBarLayout = view.findViewById(R.id.app_bar);
        settingLayout = view.findViewById(R.id.setting_layout);

        myAvatar = view.findViewById(R.id.my_avatar);
        myNickname = view.findViewById(R.id.my_nickname);
        introductionText = view.findViewById(R.id.introduction_text);
        proverbText = view.findViewById(R.id.proverb_text);
        proverbAuthor = view.findViewById(R.id.proverb_author);

        itemThirdImage = view.findViewById(R.id.myself_third_item_image);
        itemThirdText = view.findViewById(R.id.myself_third_item_text);

        proverbTextBottom = view.findViewById(R.id.proverb_text_bottom);
        proverbAuthorBottom = view.findViewById(R.id.proverb_author_bottom);

        itemAvatarLayout = view.findViewById(R.id.item_avatar_layout);
        itemDetailLayout = view.findViewById(R.id.item_detail_layout);
        itemReserveLayout = view.findViewById(R.id.item_reserve_layout);
        itemSettingLayout = view.findViewById(R.id.item_setting_layout);

    }

    //给控件设置监听器
    private void setListener() {

        appBarLayout.addOnOffsetChangedListener(customListener);

        settingLayout.setOnClickListener(customListener);
        itemAvatarLayout.setOnClickListener(customListener);
        itemAvatarLayout.setOnTouchListener(customListener);
        itemDetailLayout.setOnClickListener(customListener);
        itemDetailLayout.setOnTouchListener(customListener);
        itemReserveLayout.setOnClickListener(customListener);
        itemReserveLayout.setOnTouchListener(customListener);
        itemSettingLayout.setOnClickListener(customListener);
        itemSettingLayout.setOnTouchListener(customListener);

    }

    //自定义监听器
    class CustomListener implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener, View.OnTouchListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting_layout:
                    Intent intent = new Intent();
                    intent.setClass(getContext(), SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.item_avatar_layout:
                    Intent intent1 = new Intent();
                    intent1.setClass(getContext(), MyAvatarActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.item_detail_layout:
                    Intent intent2 = new Intent();
                    if (getContext().getSharedPreferences("userInfo", MODE_PRIVATE).getString("login_role","").equals("student"))
                        intent2.setAction("student");
                    else intent2.setAction("admin");
                    intent2.setClass(getContext(), MyDetailActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.item_reserve_layout:
                    Intent intent3 = new Intent();
                    if (getContext().getSharedPreferences("userInfo", MODE_PRIVATE).getString("login_role","").equals("student"))
                        intent3.setClass(getContext(), ReserveExamActivity.class);
                    else intent3.setClass(getContext(), ManageNewsActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.item_setting_layout:
                    Intent intent4 = new Intent();
                    intent4.setClass(getContext(), SettingActivity.class);
                    startActivity(intent4);
                    break;
            }

        }


        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            if(verticalOffset<-400){
                if(verticalOffset<-500){

                }

                introductionText.setVisibility(View.INVISIBLE);
            }else introductionText.setVisibility(View.VISIBLE);

        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (v.getId()){
                case R.id.item_avatar_layout:
                    touchUpAndDownEvent(itemAvatarLayout,event);
                    break;
                case R.id.item_detail_layout:
                    touchUpAndDownEvent(itemDetailLayout,event);
                    break;
                case R.id.item_reserve_layout:
                    touchUpAndDownEvent(itemReserveLayout,event);
                    break;
                case R.id.item_setting_layout:
                    touchUpAndDownEvent(itemSettingLayout,event);
                    break;
            }
            return false;
        }

    }

    //设置触摸的按下和抬起时背景的颜色
    private void touchUpAndDownEvent(View view,MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                break;
            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pre = getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        introductionText.setText(pre.getString("introduction",""));
        myNickname.setText(pre.getString("nickname",""));

        getAvatar();
    }

}
