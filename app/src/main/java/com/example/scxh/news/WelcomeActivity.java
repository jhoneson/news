package com.example.scxh.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    private TextView mHextView;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if(Build.VERSION.SDK_INT>=21) {             //5.0以上才支持此功能
            View decoreView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decoreView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
            ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();

        mHextView = (TextView) findViewById(R.id.welcome_txt);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
               int type=msg.arg1;
                switch (type){
                    case 0:
                        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        String count = (String) msg.obj;
                        mHextView.setText(count);
                        break;
                }
            }
        };

        // TODO: 2016/6/13 在子线程里发送消息
        Message message = Message.obtain();
        message.arg1 = 0;    // TODO: 2016/6/13 标识1
        mHandler.sendMessageDelayed(message, 4000);
        new Thread(new Runnable() {
            int i;
            @Override
            public void run() {
                for (i = 3; i > 0; i--) {
                    Message message = Message.obtain();
                    message.arg1 = 1;        // TODO: 2016/6/13 标识2
                    message.obj = i + "";
                    mHandler.sendMessage(message);
                    SystemClock.sleep(1000);
                }
            }
        }).start();
    }
}
