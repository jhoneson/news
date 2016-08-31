package com.example.scxh.news.httpLoadTxtUntils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.scxh.news.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class NewsDetailActivity extends AppCompatActivity {
    private TextView textView,titleTxt,timeTxt,sourseTxt;
    private ScrollView scrollView;
    private ImageView imageView,icon;
    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        icon= (ImageView) findViewById(R.id.back_btn);
        titleTxt= (TextView) findViewById(R.id.detail_title);
        textView= (TextView) findViewById(R.id.detail_txt);
        timeTxt= (TextView) findViewById(R.id.detail_ptime);
        sourseTxt= (TextView) findViewById(R.id.detail_sourse);
        imageView= (ImageView) findViewById(R.id.detail_img);
        scrollView= (ScrollView) findViewById(R.id.item_txt_scroll);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String content=bundle.getString("BODY");
        String title=bundle.getString("ALT");
        String src=bundle.getString("SRC");
        if(src!=null)
            Glide.with(this).load(src).into(imageView);
            mAttacher = new PhotoViewAttacher(imageView);
        if(title!=null){
            titleTxt.setText(Html.fromHtml(title));
        }else {
            titleTxt.setText("新闻抢先看");
        }
        if(content!=null){
            textView.setText(Html.fromHtml(content));
        }else {
            textView.setText("暂无内容");
        }

        timeTxt.setText(Html.fromHtml(bundle.getString("PTIME")));
        sourseTxt.setText(bundle.getString("SOURSE"));
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
