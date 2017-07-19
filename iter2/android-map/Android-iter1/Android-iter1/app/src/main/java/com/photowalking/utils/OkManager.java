package com.photowalking.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liujinxu on 17/6/30.
 */

public class OkManager<T> {
    private OkHttpClient client;
    private Handler handler;
    static final String TAG = "okmanagerActicity:";

    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType STREAM = MediaType.parse("application/octet-stream");

    public OkManager() {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());

    }

    /**
     * 向服务器上传File
     *
     * @param url
     * @param file
     */
    public String uploadFile(String url, File file) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(STREAM, file))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "internal error";
        }
    }

    /**
     * 从服务器获取File
     *
     * @param url
     */
    public void downloadFile(String url,String destFile) {
        Log.e(TAG, "ceate file: "+destFile);
        File file = new File(destFile);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            InputStream is = null;
            byte[] buf = new byte[2048];
            int len = 0;
            FileOutputStream fos = null;
            try {
                long total = response.body().contentLength();
                Log.e(TAG, "total------>" + total);
                long current = 0;
                is = response.body().byteStream();
                fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.flush();
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器提交String请求
     *
     * @param url
     * @param content
     */
    public String sendStringByPost(String url, String content) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            while(response.code()!=200);
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "internal error";
        }
    }


    public List<T> getAll(String url, Class<T> E){
        List<T> lists = new ArrayList<T>();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            while(response.code()!=200)
                Log.e(TAG, "get friends......");
            String str = response.body().string();
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(str).getAsJsonArray();
            Gson gson = new Gson();
            for(JsonElement jsonElement : jsonArray) {
                Log.v(TAG, jsonElement.toString());
                T item = gson.fromJson(jsonElement, E);
                lists.add(item);
            }
            return lists;
        } catch (IOException e) {
            e.printStackTrace();
            return  lists;
        }
    }


    public static boolean checkNetwork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager==null)
            return false;
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }


}
