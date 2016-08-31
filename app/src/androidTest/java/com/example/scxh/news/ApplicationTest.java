package com.example.scxh.news;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.example.scxh.news.httpLoadTxtUntils.ConnectionUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    public void getData(String url){
        new ConnectionUtils(getContext()).asycnConnect(url, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                JSONObject jsonObject=new JSONObject();
                try {
                    JSONObject outs=jsonObject.getJSONObject("data");
                    JSONArray array=outs.getJSONArray("list");
                    String tltle=array.getString(2);
                    Log.e("title>>>>>","=="+tltle);
                    String imgsrc=array.getString(6);
                    Log.e("imgsrc>>>>>","=="+imgsrc);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}