package com.example.physical_examination_app.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.physical_examination_app.Admin.AdminFragment.ManageExamFragment;
import com.example.physical_examination_app.Admin.AdminFragment.ManageStudentFragment;
import com.example.physical_examination_app.common.ActivityCollector;
import com.example.physical_examination_app.common.MyselfFragment;
import com.example.physical_examination_app.common.FragmentAdapter;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.common.UserLoginActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminMainActivity extends AppCompatActivity {

    private ManageExamFragment manageExamFragment;
    private ManageStudentFragment manageStudentFragment;
    private MyselfFragment myselfFragment;

    private LinearLayout manageExamTab;
    private LinearLayout manageStudentTab;
    private LinearLayout managerMyselfTab;
    private ImageView manageExamImg;
    private ImageView manageStudentImg;
    private ImageView managerMyselfImg;
    private TextView manageExamText;
    private TextView manageStudentText;
    private TextView managerMyselfText;

    private ViewPager viewPager;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private FragmentAdapter fragmentAdapter;

    private Resources resources;
    private CustomListener customListener = new CustomListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        ActivityCollector.addActivity(this);

        //获得资源实例
        resources = getResources();

        getViews();
        setFragment();
        setCustomListener();

    }

    //设置自定义的Listener
    private void setCustomListener() {

        viewPager.addOnPageChangeListener(customListener);

        manageExamTab.setOnClickListener(customListener);
        manageStudentTab.setOnClickListener(customListener);
        managerMyselfTab.setOnClickListener(customListener);

    }


    //准备Fragment的所有事项
    private void setFragment() {

        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();

        manageExamFragment = new ManageExamFragment();
        manageStudentFragment = new ManageStudentFragment();
        myselfFragment = new MyselfFragment();

        fragmentList.add(0, manageExamFragment);
        fragmentList.add(1, manageStudentFragment);
        fragmentList.add(2, myselfFragment);

        fragmentAdapter = new FragmentAdapter(fragmentManager, fragmentList);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);

    }

    //获取视图控件
    private void getViews() {

        viewPager = findViewById(R.id.viewPager);

        manageExamTab = findViewById(R.id.manage_exam_tab);
        manageStudentTab = findViewById(R.id.manage_student_tab);
        managerMyselfTab = findViewById(R.id.manager_myself_tab);

        manageExamImg = findViewById(R.id.manage_exam_img);
        manageStudentImg = findViewById(R.id.manage_student_img);
        managerMyselfImg = findViewById(R.id.manager_myself_img);

        manageExamText = findViewById(R.id.manage_exam_text);
        manageStudentText = findViewById(R.id.manage_student_text);
        managerMyselfText = findViewById(R.id.manager_myself_text);

    }

    //自定义监听器
    class CustomListener implements View.OnClickListener, ViewPager.OnPageChangeListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.manage_exam_tab:
                    viewPager.setCurrentItem(0);
                    changeTabStatus(R.drawable.manage_exam_blue,R.drawable.student_grey,R.drawable.myself_grey,
                            "#06c8fb","#bfbfbf","#bfbfbf");
                    break;
                case R.id.manage_student_tab:
                    viewPager.setCurrentItem(1);
                    changeTabStatus(R.drawable.manage_exam_grey,R.drawable.student_blue,R.drawable.myself_grey,
                            "#bfbfbf","#06c8fb","#bfbfbf");
                    break;
                case R.id.manager_myself_tab:
                    viewPager.setCurrentItem(2);
                    changeTabStatus(R.drawable.manage_exam_grey,R.drawable.student_grey,R.drawable.myself_blue,
                            "#bfbfbf","#bfbfbf","#06c8fb");
                    break;

            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    changeTabStatus(R.drawable.manage_exam_blue,R.drawable.student_grey,R.drawable.myself_grey,
                            "#06c8fb","#bfbfbf","#bfbfbf");
                    break;
                case 1:
                    changeTabStatus(R.drawable.manage_exam_grey,R.drawable.student_blue,R.drawable.myself_grey,
                            "#bfbfbf","#06c8fb","#bfbfbf");
                    break;
                case 2:
                    changeTabStatus(R.drawable.manage_exam_grey,R.drawable.student_grey,R.drawable.myself_blue,
                            "#bfbfbf","#bfbfbf","#06c8fb");
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

    //改变底部标签栏
    private void changeTabStatus(int tab1Img,int tab2Img,int tab3Img,String tab1Color,String tab2Color,String tab3Color){

        manageExamImg.setImageDrawable(resources.getDrawable(tab1Img));
        manageStudentImg.setImageDrawable(resources.getDrawable(tab2Img));
        managerMyselfImg.setImageDrawable(resources.getDrawable(tab3Img));

        manageExamText.setTextColor(Color.parseColor(tab1Color));
        manageStudentText.setTextColor(Color.parseColor(tab2Color));
        managerMyselfText.setTextColor(Color.parseColor(tab3Color));

    }



}