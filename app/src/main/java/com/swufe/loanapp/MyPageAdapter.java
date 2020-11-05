package com.swufe.loanapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//用于处理ViewPager和各Fragment之间的协调，向ViewPager提供显示的Fragment对象
public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new FirstFragment();
        }else{
            return new SecondFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //此处直接用Title加上位置返回，实际应用中可以根据position确定返回的字符串
        if (position == 0){
            return "购物车";
        }else {
            return "我的";
        }
    }
}
