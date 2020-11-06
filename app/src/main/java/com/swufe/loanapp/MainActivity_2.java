package com.swufe.loanapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity_2 extends AppCompatActivity implements View.OnClickListener{
    ViewPager viewPager;
    MyPageAdapter pageAdapter;

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_2);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //这两句和上面不能位置写反了
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_4:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
