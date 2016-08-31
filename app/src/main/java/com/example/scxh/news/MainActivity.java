package com.example.scxh.news;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity {
    private SlidingMenu slidingMenu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       if(isHaveNet()==true){
           Toast.makeText(this,"网络连接正常",Toast.LENGTH_SHORT).show();
       }else {
           dialog();
       }
        if(Build.VERSION.SDK_INT>=21) {             //5.0以上才支持此功能
            View decoreView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decoreView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame_layout,NewsFragment.newInstance())
                .commit();

        setBehindContentView(R.layout.sliding_menu_layout);                //工具包里的布局，不可更改
        getSupportFragmentManager().beginTransaction()
                .add(R.id.sliding_menu_layout,MyFragment.newInstance())    //工具包里的布局的ID，不可更改
                .commit();
        slidingMenu=getSlidingMenu();
        slidingMenu.setSlidingEnabled(true);                              //设置可以划出
        slidingMenu.setBehindOffsetRes(R.dimen.activity_margin);                            //可以划开的最大角度
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);      //从边上开始划
        slidingMenu.setMode(SlidingMenu.LEFT);                            //从左侧划出
    }

    public boolean isHaveNet(){
        ConnectivityManager cm= (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm==null){
            return false;
        }
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if((networkInfo==null) || !networkInfo.isAvailable()){
            return false;
        }
        return true;
    }
    protected void dialog() {
          AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
          builder.setMessage("确认联网了吗？");
          builder.setTitle("提示");
          builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   MainActivity.this.finish();
                  }
              });
         builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                  }
             });
         builder.create().show();
       }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
