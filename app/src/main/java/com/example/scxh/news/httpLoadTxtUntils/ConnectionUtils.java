package com.example.scxh.news.httpLoadTxtUntils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * HTTP请求工具类
 * 请求地址 url
 * 请求方法  Get Post
 * 请求参数
 * 1.联网操作
 * 2.异步操作
 * 3.回调操作
 */
public class ConnectionUtils {
    public static final String SHAREPATER_CACHE_NAME = "com.ittx.httpcache";
    private Context mContext;
    private static boolean isCache = true; //

    public enum Method {
        GET, POST
    }

    public enum Cache {
        TRUE, FALSE
    }

    public ConnectionUtils(Context context) {
        mContext = context;
    }

    public interface HttpConnectionInterface {
        void execute(String content);
    }

    /**
     * 无参无缓存请求
     *
     * @param httpUrl
     * @param method
     * @param httpConnectionInterface
     */
    public void asycnConnect(final String httpUrl, final Method method, final HttpConnectionInterface httpConnectionInterface) {
        asycnConnect(httpUrl, null, method, Cache.FALSE, httpConnectionInterface);
    }

    /**
     * 无参有缓存
     *
     * @param httpUrl
     * @param method
     * @param httpConnectionInterface
     */
    public void asycnConnect(final String httpUrl, final Method method, Cache isCache, final HttpConnectionInterface httpConnectionInterface) {
        asycnConnect(httpUrl, null, method, isCache, httpConnectionInterface);
    }

    /**
     * 有参无缓存
     *
     * @param httpUrl
     * @param paramtMap
     * @param method
     * @param httpConnectionInterface
     */
    public void asycnConnect(final String httpUrl, final HashMap<String, String> paramtMap, final Method method, final HttpConnectionInterface httpConnectionInterface) {
        asycnConnect(httpUrl, paramtMap, method, Cache.FALSE, httpConnectionInterface);
    }

    /**
     * 有参有缓存请求
     * 异步联网获取Http响应字符串数据
     *
     * @param httpUrl
     * @param paramtMap
     * @param method
     * @param httpConnectionInterface
     */
    public void asycnConnect(final String httpUrl, final HashMap<String, String> paramtMap, final Method method, final Cache isCache, final HttpConnectionInterface httpConnectionInterface) {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String httpUrl = params[0];
                return doHttpConnection(httpUrl, paramtMap, method, isCache);
            }

            @Override
            protected void onPostExecute(String content) {
                httpConnectionInterface.execute(content);
            }
        }.execute(httpUrl);
    }

    public String doHttpConnection(String httpUrl, HashMap<String, String> paramtMap, Method method, Cache isCache) {
        if (method == Method.GET) {
            if (paramtMap != null) {
                String paramUrl = "?";
                paramUrl = doParamterHttp(paramUrl, paramtMap); //?userName=admin1&passWord=1234566
                httpUrl = httpUrl + paramUrl; // requestUrl = http://192.168.5.11:8080/webapp/login?userName=admin1&passWord=1234566
            }
            return doGetPostHttp(httpUrl, paramtMap, method, isCache);

        } else {
            return doGetPostHttp(httpUrl, paramtMap, method, isCache);
        }

    }

    public String doGetPostHttp(String httpUrl, HashMap<String, String> paramtMap, Method method, Cache isCache) {
        String message = "";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(httpUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (method == Method.GET) {
                urlConnection.setRequestMethod("GET");  //请求方法 GET POST
            } else {
                urlConnection.setRequestMethod("POST");  //请求方法 GET POST
            }
            urlConnection.setConnectTimeout(10000); //连接超时时间
            urlConnection.setReadTimeout(15000);    //读数据超时
            urlConnection.connect();

            if (method == Method.POST) {
                // post请求的参数
                if (paramtMap != null) {
                    String data = doParamterHttp("", paramtMap); //userName=admin&passWord=1234566
                    // 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
                    OutputStream out = urlConnection.getOutputStream();// 获得一个输出流,向服务器写数据
                    out.write(data.getBytes());
                    out.flush();
                    out.close();
                }
            }

            int code = urlConnection.getResponseCode();  //状态行:状态码 200 OK
            Log.e("", "code :" + code);
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConnection.getInputStream();
                message = readInput(is);
                if (isCache == Cache.TRUE)
                    mContext.getSharedPreferences(SHAREPATER_CACHE_NAME, Context.MODE_PRIVATE).edit().putString(httpUrl, message).commit(); //添加缓存

            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            String cacheContent = mContext.getSharedPreferences(SHAREPATER_CACHE_NAME, Context.MODE_PRIVATE).getString(httpUrl, null);
            return cacheContent;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return message;
    }

    /**
     * paramUrl = "";
     * ?user=admin&psw=1234
     * user=admin&psw=1234
     *
     * @param paramUrl
     * @param paramtMap
     * @return
     */
    public String doParamterHttp(String paramUrl, HashMap<String, String> paramtMap) {
        for (String key : paramtMap.keySet()) {
            String value = paramtMap.get(key);
            paramUrl = paramUrl + key + "=" + value + "&";
        }
        paramUrl = paramUrl.substring(0, paramUrl.length() - 1);
        return paramUrl;
    }

    /**
     * 输入流转字符串
     *
     * @param is
     * @return
     */
    public String readInput(InputStream is) {
        Reader reader = new InputStreamReader(is);  //字节转字符流
        BufferedReader br = new BufferedReader(reader); //字符缓存流

        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                reader.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
