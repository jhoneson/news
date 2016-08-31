package com.example.scxh.news;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.scxh.news.httpLoadTxtUntils.ConnectionUtils;
import com.example.scxh.news.httpLoadTxtUntils.NewsDetailActivity;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AthleteFragment extends Fragment {
    private ListView listView;
    private SliderLayout sliderLayout;
    private ConnectionUtils connectionUtils;
    private List<First> list;
    private AtheleteListAdapter adapter;
    int pageNo = 0; //页号 ，表示第几页,第一页从0开始
    int pageSize = 20; //页大小，显示每页多少条数据
    String news_type_id = "T1348649079062";  //新闻类型标识, 此处表示娱乐新闻
    private String httpUrl="http://c.m.163.com/nc/article/list/"+news_type_id +"/"+pageNo*pageSize+ "-" +pageSize+ ".html";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_athlete, container, false);
    }
    public static Fragment newInstance(){
       AthleteFragment fragment=new AthleteFragment();
        return fragment;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.news_athlete_list);
        View v1 = LayoutInflater.from(getContext()).inflate(R.layout.item_diy_layout, null);
        sliderLayout = (SliderLayout) v1.findViewById(R.id.tests_slider_layout);
        connectionUtils = new ConnectionUtils(getContext());
        adapter=new AtheleteListAdapter(getContext());
        listView.addHeaderView(v1);
        listView.setAdapter(adapter);
        getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                First first= (First) adapterView.getAdapter().getItem(i);
                String docid=first.getDocid();
                String ItemUrl="http://c.m.163.com/nc/article/"+docid +"/full.html";
                getItemData(ItemUrl,docid);

            }
        });
    }
    public void getItemData(String url, final String doc){
        connectionUtils.asycnConnect(url, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content){
                try {
                    JSONObject jsonObject=new JSONObject(content);
                    JSONObject jsonObject1=jsonObject.getJSONObject(doc);
                    String ptime=jsonObject1.getString("ptime");
                    String sourse=jsonObject1.getString("source");
                    String body=jsonObject1.getString("body");
                    JSONArray array=jsonObject1.getJSONArray("img");
                    String alt=null;
                    String src=null;
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject2=array.getJSONObject(i);
                        alt=jsonObject2.getString("alt");
                        src=jsonObject2.getString("src");
                    }
                    Intent intent = new Intent(getContext(),NewsDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("ALT",alt);
                    bundle.putString("PTIME",ptime);
                    bundle.putString("SOURSE",sourse);
                    bundle.putString("BODY",body);
                    bundle.putString("SRC",src);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    onDestroy();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sliderLayout != null) {
            sliderLayout.stopAutoCycle();
            sliderLayout = null;
        }
    }

    public void getData() {
        connectionUtils.asycnConnect(httpUrl, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                getHeaderView(content);
                Gson gson=new Gson();
                News news=gson.fromJson(content,News.class);
                list=news.getT1348649079062();
                adapter.SetData(list);
            }

        });
    }

    public void getHeaderView(String content) {
        JSONObject jsonObject;
        JSONArray ads;
        try {
            jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray(news_type_id);
            JSONObject Item = jsonArray.getJSONObject(0);
            ads = Item.getJSONArray("ads");
            int length = ads.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
                String imgsrc = jsonObjectItem.getString("imgsrc");
                String Title = jsonObjectItem.getString("title");
                TextSliderView textSliderView = new TextSliderView(getContext());
                textSliderView.description(Title)
                        .image(imgsrc)
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                sliderLayout.addSlider(textSliderView);
            }
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class AtheleteListAdapter extends BaseAdapter {
        private List<First> list = new ArrayList<>();
        LayoutInflater layoutInflater;

        public AtheleteListAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void SetData(List<First> list) {
            this.list = list;
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
        public View getView(int i, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.layout, null);
                ImageView icon = (ImageView) convertView.findViewById(R.id.item_image);
                TextView Title = (TextView) convertView.findViewById(R.id.item_title);
                TextView Content = (TextView) convertView.findViewById(R.id.item_content);
                ProgressBar progress= (ProgressBar) convertView.findViewById(R.id.item_progress);

                viewHolder = new ViewHolder();
                viewHolder.title = Title;
                viewHolder.icon = icon;
                viewHolder.content = Content;
                viewHolder.progressBar=progress;

                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            First item = (First) getItem(i);
            viewHolder.progressBar.setProgress(ProgressBar.VISIBLE);
            final ViewHolder finalViewHolder = viewHolder;
            Picasso.with(getContext()).load(item.getImgsrc()).into(viewHolder.icon, new Callback() {
                @Override
                public void onSuccess() {
                    finalViewHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
            viewHolder.title.setText(item.getTitle());
            viewHolder.content.setText(item.getDigest());
            return convertView;
        }

        class ViewHolder {
            ProgressBar progressBar;
            ImageView icon;
            TextView title;
            TextView content;
        }
    }
}
