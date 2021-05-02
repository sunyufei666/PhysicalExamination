package com.example.physical_examination_app.common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Utils;

import org.w3c.dom.Text;

public class MyDetailActivity extends AppCompatActivity {

    private ImageView returnButton;
    private ImageView myAvatar;

    private TextView introduction;
    private TextView university;
    private TextView college;
    private TextView major;
    private TextView nickname;
    private TextView name;
    private TextView sex;
    private TextView sno;
    private TextView grade;
    private TextView classes;
    private TextView registerTime;

    private TextView snoOrAno;

    private LinearLayout itemUniversityLayout;
    private LinearLayout itemCollegeLayout;
    private LinearLayout itemProfessionLayout;
    private LinearLayout itemNameLayout;
    private LinearLayout itemSexLayout;
    private LinearLayout itemGradeLayout;
    private LinearLayout itemClassLayout;
    private LinearLayout itemRegisterLayout;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_detail);

        activity = this;

        getViews();

        getAvatar();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        studentInfoSet();

    }

    //从后台获取头像
    private void getAvatar() {

        String avatarName = null;
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        if(pre.getString("login_role","").equals("student")){
            avatarName = pre.getString("sno","") + ".jpg";
        }else avatarName = pre.getString("ano","") + ".jpg";

        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).fallback(R.mipmap.app_icon)
                .error(R.drawable.error).diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop();
        Glide.with(activity).load(Utils.AVATAR_PATH + avatarName)
                .thumbnail(Glide.with(activity).load(R.drawable.loading))
                .apply(requestOptions).into(myAvatar);

    }

    //设置学生的信息
    private void studentInfoSet() {

        String action = getIntent().getAction();
        SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if(action.equals("admin")){

            itemUniversityLayout.setVisibility(View.GONE);
            itemCollegeLayout.setVisibility(View.GONE);
            itemProfessionLayout.setVisibility(View.GONE);
            itemNameLayout.setVisibility(View.GONE);
            itemSexLayout.setVisibility(View.GONE);
            itemGradeLayout.setVisibility(View.GONE);
            itemClassLayout.setVisibility(View.GONE);
            itemRegisterLayout.setVisibility(View.GONE);

            snoOrAno.setText("账号");
            sno.setText(pre.getString("ano",""));

        }else if(action.equals("student")){

            university.setText(pre.getString("university",""));
            college.setText(pre.getString("college",""));
            major.setText(pre.getString("major",""));
            name.setText(pre.getString("name",""));
            sex.setText(pre.getString("sex",""));
            sno.setText(pre.getString("sno",""));
            grade.setText(pre.getString("grade",""));
            classes.setText(pre.getString("classes",""));
            registerTime.setText(pre.getString("register_time",""));

        }

        introduction.setText(pre.getString("introduction",""));
        nickname.setText(pre.getString("nickname",""));

    }

    //获取视图控件
    private void getViews() {

        returnButton = findViewById(R.id.return_button);
        myAvatar = findViewById(R.id.my_avatar);

        snoOrAno = findViewById(R.id.sno_or_ano);
        introduction = findViewById(R.id.introduction);
        university = findViewById(R.id.university);
        college = findViewById(R.id.college);
        major = findViewById(R.id.major);
        nickname = findViewById(R.id.nickname);
        name = findViewById(R.id.name);
        sex = findViewById(R.id.sex);
        sno = findViewById(R.id.sno);
        grade = findViewById(R.id.grade);
        classes = findViewById(R.id.classes);
        registerTime = findViewById(R.id.register_time);

        itemUniversityLayout = findViewById(R.id.item_university_layout);
        itemCollegeLayout = findViewById(R.id.item_college_layout);
        itemProfessionLayout = findViewById(R.id.item_profession_layout);
        itemNameLayout = findViewById(R.id.item_name_layout);
        itemSexLayout = findViewById(R.id.item_sex_layout);
        itemGradeLayout = findViewById(R.id.item_grade_layout);
        itemClassLayout = findViewById(R.id.item_class_layout);
        itemRegisterLayout = findViewById(R.id.item_register_layout);

    }

}