package com.example.scxh.news;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by scxh on 2016/8/11.
 */
public class getHttpTextUntils {
    private HttpURLConnection httpURLConnection;
    /*
     *联网取数据
     */
    public String ReadDataFromStore(String httpDir) {
        URL url= null;
        InputStream is=null;
        String msg=null;
        try {
            url = new URL(httpDir);
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            is=httpURLConnection.getInputStream();
            msg=readIOFile(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(is !=null) {
                is.close();
            }
            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;

    }

    /*
    *从文件读数据
    */
    public String readIOFile(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        isr.close();
        return sb.toString();
    }
}
