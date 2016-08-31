package com.example.scxh.news.video;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.scxh.news.R;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> list;
    private List<Fragment> arrays;
    private EntertainmentVideoFragment entertainmentFragment;
    private HotVideoFragment hotVideoFragment;
    private NiceVideoFragment niceVideoFragment;
    private FunnyFragment funnyFragment;
    private TabAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);
        initial();
    }
    public void initial(){
        tabLayout= (TabLayout) findViewById(R.id.video_tab);
        viewPager= (ViewPager) findViewById(R.id.video_pager);

        hotVideoFragment=new HotVideoFragment();
        funnyFragment=new FunnyFragment();
        niceVideoFragment=new NiceVideoFragment();
        entertainmentFragment=new EntertainmentVideoFragment();

        arrays=new ArrayList<>();
        arrays.add(hotVideoFragment);
        arrays.add(entertainmentFragment);
        arrays.add(funnyFragment);
        arrays.add(niceVideoFragment);

        list=new ArrayList<>();
        list.add("热点");
        list.add("娱乐");
        list.add("搞笑");
        list.add("精品");
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(list.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(list.get(3)));

        adapter=new TabAdapter(getSupportFragmentManager(),arrays,list);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    class TabAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> list_fragment;                  //fragment列表
        private List<String> list_Title;                       //tab名的列表

        public TabAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
            super(fm);
            this.list_fragment=list_fragment;
            this.list_Title=list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return list_Title.get(position);
        }
    }
}
