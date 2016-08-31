package com.example.scxh.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private List<String> list_title;                           //tab名称列表
    private List<Fragment> list_fragment;                      //定义要装fragment的列表
    private HotNewsFragment hotNewsFragment;
    private EntertainmentFragment entertainmentFragment;
    private EconomyFragment economyFragment;
    private AthleteFragment athleteFragment;
    private ScienceFragment scienceFragment;
    private TabAdapter adapter;
    private LinearLayout linearLayout;
    private TextView moretxt;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.fragment_news_layout, container, false);
        linearLayout= (LinearLayout) v.findViewById(R.id.linealer);
        moretxt= (TextView) v.findViewById(R.id.tab_more);
        initial(v);
        moretxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(getContext(),view);
                MenuInflater menuInflater=popupMenu.getMenuInflater();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                         @Override
                                                         public boolean onMenuItemClick(MenuItem menuItem) {
                                                             switch (menuItem.getItemId()){
                                                                 case R.id.car:
                                                                     Toast.makeText(getContext(),"汽车已选中",Toast.LENGTH_SHORT).show();
                                                                      return true;
                                                                 case R.id.smile:
                                                                     Toast.makeText(getContext(),"搞笑段子已选中",Toast.LENGTH_SHORT).show();
                                                                     return true;
                                                                 case R.id.house:
                                                                     Toast.makeText(getContext(),"房产已选中",Toast.LENGTH_SHORT).show();
                                                                     return true;
                                                                 case R.id.ticket:
                                                                     Toast.makeText(getContext(),"车票已选中",Toast.LENGTH_SHORT).show();
                                                                     return true;
                                                                 default:return false;
                                                             }
                                                         }
                                                     });

                        menuInflater.inflate(R.menu.pop, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        return v;
    }
    public void initial(View view){
        mViewPager= (ViewPager) view.findViewById(R.id.view_pager);

        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout);

        hotNewsFragment= (HotNewsFragment) HotNewsFragment.newInstance();
        athleteFragment= (AthleteFragment) AthleteFragment.newInstance();
        scienceFragment= (ScienceFragment) ScienceFragment.newInstance();
        economyFragment= (EconomyFragment) EconomyFragment.newInstance();
        entertainmentFragment= (EntertainmentFragment) EntertainmentFragment.newInstance();

        list_fragment=new ArrayList<>();
        list_fragment.add(hotNewsFragment);
        list_fragment.add(economyFragment);
        list_fragment.add(athleteFragment);
        list_fragment.add(scienceFragment);
        list_fragment.add(entertainmentFragment);
        list_title=new ArrayList<>();
        list_title.add("热点");
        list_title.add("财经");
        list_title.add("体育");
        list_title.add("科技");
        list_title.add("娱乐");

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(4)));

        adapter=new TabAdapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
    }
    class TabAdapter extends FragmentStatePagerAdapter{
        private List<Fragment> list_fragment;                  //fragment列表
        private List<String> list_Title;                       //tab名的列表

        public TabAdapter(FragmentManager fm,List<Fragment> list_fragment,List<String> list_Title) {
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

