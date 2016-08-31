package com.example.scxh.news;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.scxh.news.pictureNews.MainPictureActivity;
import com.example.scxh.news.video.VideoActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener{
    private Button picBtn,videoBtn,weatherBtn,moreBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }
    public static Fragment newInstance(){
        MyFragment fragment=new MyFragment();
        return fragment;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        picBtn= (Button) getView().findViewById(R.id.item_pic_myFragment);
        videoBtn= (Button) getView().findViewById(R.id.item_video_myFragment);
        weatherBtn= (Button) getView().findViewById(R.id.item_weather_myFragment);
        moreBtn= (Button) getView().findViewById(R.id.item_more_myFragment);
        picBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);
        weatherBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_pic_myFragment:
                startActivity(new Intent(getContext(), MainPictureActivity.class));
                break;
            case R.id.item_video_myFragment:
                startActivity(new Intent(getContext(), VideoActivity.class));
                break;
            case R.id.item_weather_myFragment:
                Toast.makeText(getContext(),"天气模块正在开发中",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_more_myFragment:
                Toast.makeText(getContext(),"更多模块正在开发中",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
