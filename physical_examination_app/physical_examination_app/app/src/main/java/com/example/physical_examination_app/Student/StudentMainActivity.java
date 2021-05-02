package com.example.physical_examination_app.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Student.StudentFragment.ExamFragment;
import com.example.physical_examination_app.Student.StudentFragment.GradeFragment;
import com.example.physical_examination_app.ActivityCollector;
import com.example.physical_examination_app.Utils;
import com.example.physical_examination_app.common.MyselfFragment;
import com.example.physical_examination_app.common.FragmentAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StudentMainActivity extends AppCompatActivity {

    private ExamFragment examFragment;
    private GradeFragment gradeFragment;
    private MyselfFragment myselfFragment;

    private LinearLayout examTab;
    private LinearLayout gradeTab;
    private LinearLayout myselfTab;
    private ImageView examImg;
    private ImageView gradeImg;
    private ImageView myselfImg;
    private TextView examText;
    private TextView gradeText;
    private TextView myselfText;

    private ViewPager viewPager;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private FragmentAdapter fragmentAdapter;

    private Resources resources;
    private CustomListener customListener = new CustomListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        ActivityCollector.addActivity(this);

        //获得资源实例
        resources = getResources();

        getViews();
        setFragment();
        setCustomListener();

    }

    //准备Fragment的所有事项
    private void setFragment() {

        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();

        examFragment = new ExamFragment();
        gradeFragment = new GradeFragment();
        myselfFragment = new MyselfFragment();

        fragmentList.add(0, examFragment);
        fragmentList.add(1, gradeFragment);
        fragmentList.add(2, myselfFragment);

        fragmentAdapter = new FragmentAdapter(fragmentManager, fragmentList);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);

    }

    //获得视图控件
    private void getViews() {

        viewPager = findViewById(R.id.viewPager);

        examTab = findViewById(R.id.exam_tab);
        gradeTab = findViewById(R.id.grade_tab);
        myselfTab = findViewById(R.id.myself_tab);

        examImg = findViewById(R.id.exam_img);
        gradeImg = findViewById(R.id.grade_img);
        myselfImg = findViewById(R.id.myself_img);

        examText = findViewById(R.id.exam_text);
        gradeText = findViewById(R.id.grade_text);
        myselfText = findViewById(R.id.myself_text);

    }

    //设置自定义的Listener
    private void setCustomListener() {

        viewPager.addOnPageChangeListener(customListener);

        examTab.setOnClickListener(customListener);
        gradeTab.setOnClickListener(customListener);
        myselfTab.setOnClickListener(customListener);

    }

    //自定义Listener
    class CustomListener implements View.OnClickListener, ViewPager.OnPageChangeListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.exam_tab:
                    viewPager.setCurrentItem(0);
                    changeTabStatus(R.drawable.exam_blue,R.drawable.grade_grey,R.drawable.myself_grey,
                            "#06c8fb","#bfbfbf","#bfbfbf");
                    break;
                case R.id.grade_tab:
                    viewPager.setCurrentItem(1);
                    changeTabStatus(R.drawable.exam_grey,R.drawable.grade_blue,R.drawable.myself_grey,
                            "#bfbfbf","#06c8fb","#bfbfbf");
                    break;
                case R.id.myself_tab:
                    viewPager.setCurrentItem(2);
                    changeTabStatus(R.drawable.exam_grey,R.drawable.grade_grey,R.drawable.myself_blue,
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
                    changeTabStatus(R.drawable.exam_blue,R.drawable.grade_grey,R.drawable.myself_grey,
                            "#06c8fb","#bfbfbf","#bfbfbf");
                    break;
                case 1:
                    changeTabStatus(R.drawable.exam_grey,R.drawable.grade_blue,R.drawable.myself_grey,
                            "#bfbfbf","#06c8fb","#bfbfbf");
                    break;
                case 2:
                    changeTabStatus(R.drawable.exam_grey,R.drawable.grade_grey,R.drawable.myself_blue,
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

        examImg.setImageDrawable(resources.getDrawable(tab1Img));
        gradeImg.setImageDrawable(resources.getDrawable(tab2Img));
        myselfImg.setImageDrawable(resources.getDrawable(tab3Img));

        examText.setTextColor(Color.parseColor(tab1Color));
        gradeText.setTextColor(Color.parseColor(tab2Color));
        myselfText.setTextColor(Color.parseColor(tab3Color));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String avatarName = null;
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);

        if(pre.getString("login_role","").equals("student")){
            avatarName = pre.getString("sno","");
        }else avatarName = pre.getString("ano","");
        File file = new File(getFilesDir().getAbsolutePath() + "/avatar/" + avatarName +".jpg");
        file.delete();

        SharedPreferences.Editor editor = pre.edit();
        editor.clear();
        editor.commit();
    }

}