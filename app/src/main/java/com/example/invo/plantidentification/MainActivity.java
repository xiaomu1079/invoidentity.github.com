package com.example.invo.plantidentification;

import android.os.Bundle;

import com.example.invo.plantidentification.ui.util.ToastUtil;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.invo.plantidentification.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /*
    * 当页面被选中时的操作
    * */
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                ToastUtil.showToast(getApplication(),"植物识别");
                break;
            case 1:
                ToastUtil.showToast(getApplication(),"动物识别");
                break;
            case 2:
                ToastUtil.showToast(getApplication(),"菜品识别");
                break;
            case 3:
                ToastUtil.showToast(getApplication(), "车辆识别");
                break;
        }
    }
}