package com.chensd.tablayout;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HashMap<Class, CraftFragment> mFragments = new HashMap<>();
    private Class[] mFragmentClass = {TestFragment.class, TestFragment2.class, TestFragment23.class};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(myFragmentAdapter);
        tab.setupWithViewPager(viewPager);
        tab.setTabTextColors(Color.GRAY, Color.BLUE);
        tab.setSelectedTabIndicatorColor(Color.BLUE);
    }

    private CraftFragment getFragment(int pos){

        Class clzs = mFragmentClass[pos];
        CraftFragment craftFragment = mFragments.get(clzs);

        if(craftFragment == null){

            try {
                craftFragment = (CraftFragment) clzs.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("name", clzs.getSimpleName());
                craftFragment.setArguments(bundle);
                mFragments.put(clzs, craftFragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return mFragments.get(clzs);
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter{

        String[] titles = {"第一页","第二页","第三页"};

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public int getCount() {
            return mFragmentClass.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
