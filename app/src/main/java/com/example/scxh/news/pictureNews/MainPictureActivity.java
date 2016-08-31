package com.example.scxh.news.pictureNews;

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

public class MainPictureActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<String> list;
    private List<Fragment> array;
    private PerfectFragment perfectFragment;
    private InterestFragment interestFragment;
    private BeatifulFragment beatifulFragment;
    private StoryFragment storyFragment;
    private TabAdapter tabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_picture);
        initial();
    }
    public void initial(){
        viewPager= (ViewPager) findViewById(R.id.pic_main_pager);
        tabLayout= (TabLayout) findViewById(R.id.pic_main_tab);

        perfectFragment=new PerfectFragment();
        beatifulFragment=new BeatifulFragment();
        interestFragment=new InterestFragment();
        storyFragment=new StoryFragment();

        array=new ArrayList<>();
        array.add(perfectFragment);
        array.add(beatifulFragment);
        array.add(interestFragment);
        array.add(storyFragment);

        list=new ArrayList<>();
        list.add("精品");
        list.add("美图");
        list.add("趣图");
        list.add("内涵");

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(list.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(list.get(3)));


        tabAdapter=new TabAdapter(getSupportFragmentManager(),array,list);
        viewPager.setAdapter(tabAdapter);
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
