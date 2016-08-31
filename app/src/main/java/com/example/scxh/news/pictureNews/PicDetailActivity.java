package com.example.scxh.news.pictureNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.scxh.news.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PicDetailActivity extends AppCompatActivity {
    private TextView title,content,toolbar;
    private ImageView imageView,imgBack;
    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);
        toolbar= (TextView) findViewById(R.id.mians_text);
        title= (TextView) findViewById(R.id.picture_titlt);
        content= (TextView) findViewById(R.id.picture_content);
        imageView= (ImageView) findViewById(R.id.picture_img);
        imgBack= (ImageView) findViewById(R.id.backs_btn);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        Glide.with(this).load(bundle.getString("link")).override(400,200).into(imageView);
        mAttacher=new PhotoViewAttacher(imageView);  //双击或手势图片变大缩小
        title.setText(bundle.getString("lead"));
        content.setText(Html.fromHtml(bundle.getString("content")));

    }
}
