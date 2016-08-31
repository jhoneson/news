package com.example.scxh.news.video;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jcmodule.JCVideoPlayer;
import com.example.scxh.news.R;
import com.example.scxh.news.httpLoadTxtUntils.ConnectionUtils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntertainmentVideoFragment extends Fragment {
    private ListView listView;
    private ConnectionUtils connectionUtils;
    private List<VideoHelper> list;
    private MyHotAdapter adapter;
    int pageNo = 0; //页号 ，表示第几页,第一页从0开始
    int pageSize = 10; //页大小，显示每页多少条数据
    String video_type_id = "V9LG4CHOR";  //视频类型标识
    private String httpUrl="http://c.3g.163.com/nc/video/list/"+ video_type_id + "/n/"+pageNo*pageSize+"-"+pageSize+".html";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_entertainment_video, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView= (ListView) getView().findViewById(R.id.video_enter);
        connectionUtils=new ConnectionUtils(getContext());
        list=new ArrayList<>();
        adapter=new MyHotAdapter(getContext());
        listView.setAdapter(adapter);
        setData();
    }

    public void setData(){
        connectionUtils.asycnConnect(httpUrl, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                Gson gson=new Gson();
                video video=gson.fromJson(content,video.class);
                list=video.getV9LG4CHOR();
                adapter.setList(list);
            }
        });
    }
    class MyHotAdapter extends BaseAdapter {
        private List<VideoHelper> list=new ArrayList<>();
        LayoutInflater layoutInflater;
        public MyHotAdapter(Context context){
            layoutInflater=LayoutInflater.from(context);
        }
        public void setList(List<VideoHelper> list){
            this.list=list;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VideoViewHolder videoViewHolder;
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.video_item_layout,null);
                TextView title= (TextView) convertView.findViewById(R.id.video_item_dsc);
                JCVideoPlayer player= (JCVideoPlayer) convertView.findViewById(R.id.custom_videoplayer_standard);
                videoViewHolder=new VideoViewHolder();
                videoViewHolder.textView=title;
                videoViewHolder.jcVideoPlayer=player;
                convertView.setTag(videoViewHolder);
            }
            videoViewHolder= (VideoViewHolder) convertView.getTag();
            VideoHelper item= (VideoHelper) getItem(position);
            videoViewHolder.textView.setText(item.getTopicDesc());
            videoViewHolder.jcVideoPlayer.setUp(item.getMp4_url(),item.getTopicDesc());
            ImageLoader.getInstance().displayImage(item.getTopicImg(),videoViewHolder.jcVideoPlayer.ivThumb);
            return convertView;
        }

    }

}
