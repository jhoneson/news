package com.example.scxh.news.pictureNews;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.scxh.news.R;
import com.example.scxh.news.httpLoadTxtUntils.ConnectionUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeatifulFragment extends Fragment {
    private ListView mListView;
    private ConnectionUtils connectionUtils;
    private PictureBeatyAdapter adapter;
    private List<Helper> helperList;
    private String path="http://api.sina.cn/sinago/list.json?channel=hdpic_pretty&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p=";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beatiful, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView= (ListView) getView().findViewById(R.id.pic_beautiful_list);
        connectionUtils=new ConnectionUtils(getContext());
        helperList=new ArrayList<>();
        adapter=new PictureBeatyAdapter(getContext());
        mListView.setAdapter(adapter);
        getData(path);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ids=helperList.get(position).getId();
                String pic_url="http://api.sina.cn/sinago/article.json?postt=hdpic_hdpic_toutiao_4&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&id="+ids;
                connectionUtils.asycnConnect(pic_url, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
                    @Override
                    public void execute(String content){

                        JSONObject jsonObject= null;
                        String URL=null;
                        try {
                            jsonObject = new JSONObject(content);
                            JSONObject jsonObject1=jsonObject.getJSONObject("data");
                            JSONArray array=jsonObject1.getJSONArray("pics");
                            for(int i=0;i< array.length();i++){
                                JSONObject jsonObject2=array.getJSONObject(i);
                                URL=jsonObject2.getString("kpic");
                            }

                            String title=jsonObject1.getString("lead");
                            String contents=jsonObject1.getString("content");
                            Bundle bundle=new Bundle();
                            bundle.putString("link",URL);
                            bundle.putString("lead",title);
                            bundle.putString("content",contents);
                            Intent intent=new Intent(getContext(),PicDetailActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
    public void getData(String url){
        connectionUtils.asycnConnect(url, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                try {
                    JSONObject jsonObject=new JSONObject(content);
                    JSONObject object=jsonObject.getJSONObject("data");
                    JSONArray array=object.getJSONArray("list");
                    int length=array.length();
                    for(int i=0;i<length;i++){
                        Helper helper=new Helper();
                        JSONObject object1= (JSONObject) array.get(i);
                        helper.setTitle(object1.getString("long_title"));
                        helper.setUrl(object1.getString("kpic"));
                        helper.setId(object1.getString("id"));
                        helperList.add(helper);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.setList(helperList);
            }
        });
    }
    class PictureBeatyAdapter extends BaseAdapter {
        private Context context;
        private List<Helper> list=new ArrayList<>();
        LayoutInflater layoutInflater;
        public PictureBeatyAdapter(Context context){
            layoutInflater=LayoutInflater.from(context);
            this.context=context;
        }
        public void setList(List<Helper> list){
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
            ViewHolder viewHolder;
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.item_picture_layout,null);
                ImageView imageView= (ImageView) convertView.findViewById(R.id.item_picture_img);
                TextView textView= (TextView) convertView.findViewById(R.id.item_picture_txt);
                viewHolder=new ViewHolder();
                viewHolder.icon=imageView;
                viewHolder.textView=textView;
                convertView.setTag(viewHolder);
            }
            viewHolder= (ViewHolder) convertView.getTag();
            Helper Lists = (Helper) getItem(position);
            Glide.with(context).load(Lists.getUrl()).override(600,200).into(viewHolder.icon);
            viewHolder.textView.setText(Lists.getTitle());
            return convertView;
        }
        class ViewHolder{
            ImageView icon;
            TextView textView;
        }
    }
}
