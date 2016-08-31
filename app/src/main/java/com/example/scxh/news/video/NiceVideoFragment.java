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
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NiceVideoFragment extends Fragment {
    private ListView listView;
    private ConnectionUtils connectionUtils;
    private List<VideoHelper> list;
    private MyFunnyAdapter adapter;
    private List<Nice> lists;
    int pageNo = 0; //页号 ，表示第几页,第一页从0开始
    int pageSize = 10; //页大小，显示每页多少条数据
    String video_type_id = "00850FRB";  //视频类型标识, 此处表示精品视频
    private String httpUrl="http://c.3g.163.com/nc/video/list/"+ video_type_id + "/n/" +pageNo*pageSize+ "-"  +pageSize+ ".html";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_nice_video, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView= (ListView) getView().findViewById(R.id.video_nice);
        connectionUtils=new ConnectionUtils(getContext());
        list=new ArrayList<>();
        lists=new ArrayList<>();
        adapter=new MyFunnyAdapter(getContext());
        listView.setAdapter(adapter);
        setData();

    }
    public void setData(){
        connectionUtils.asycnConnect(httpUrl, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                try {
                    JSONObject jsonObject=new JSONObject(content);
                    JSONArray array=jsonObject.getJSONArray("00850FRB");
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                         String title=object.getString("title");
                         String mp4_url=object.getString("mp4_url");
                         String topicimg=object.getString("cover");
                         String m3u8_url=object.getString("m3u8_url");
                         Nice nice=new Nice();
                         nice.setTitle(title);
                         nice.setMp4_url(mp4_url);
                         nice.setM3u8_url(m3u8_url);
                         nice.setTopicimg(topicimg);
                         lists.add(nice);
                    }
                    adapter.setList(lists);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    class MyFunnyAdapter extends BaseAdapter {
        private List<Nice> list=new ArrayList<>();
        LayoutInflater layoutInflater;
        public MyFunnyAdapter(Context context){
            layoutInflater=LayoutInflater.from(context);
        }
        public void setList(List<Nice> list){
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
            Nice item= (Nice) getItem(position);
            videoViewHolder.textView.setText(item.getTitle());
            videoViewHolder.jcVideoPlayer.setUp(item.getMp4_url(),item.getTitle());
            ImageLoader.getInstance().displayImage(item.getTopicimg(),videoViewHolder.jcVideoPlayer.ivThumb);
            return convertView;
        }

    }
}
