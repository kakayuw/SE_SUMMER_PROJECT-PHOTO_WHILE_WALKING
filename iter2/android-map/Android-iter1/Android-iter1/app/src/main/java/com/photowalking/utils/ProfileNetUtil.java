package com.photowalking.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import com.photowalking.model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 10911 on 2017/7/11.
 */

public class ProfileNetUtil {
    private OkHttpClient client;
    private Handler handler;
    static final String TAG = "ProfileNetUtils: ";

    static final String url = "/";

    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串数据
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    public ProfileNetUtil() {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());

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
                .post(RequestBody.create(JSON, content))
                .build();
        Log.d(TAG, "after build");
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }


    public User getUserByName(String url,String name) {
        User user = new User();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, name))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String str = response.body().string();
            Gson gson = new Gson();
            user = gson.fromJson(str, User.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public User getUserByUid(String url,String uid) {
        User user = new User();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, uid))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String str = response.body().string();
            Gson gson = new Gson();
            user = gson.fromJson(str, User.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public String sendFriendRequest(User user, String me,String url) {
        String ip = url + me + "/" + Integer.toString(user.getId());
        Request request = new Request.Builder()
                .url(ip)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String str = response.body().string();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public String sendNickname(User user,String nickname,String me,String url) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, me+","+user.getId()+","+nickname))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String str = response.body().string();
            Gson gson = new Gson();
            String res = gson.fromJson(str, String.class);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public String sendEditPost(User user) {
        Gson gson1 = new Gson();
        String jsonstr = gson1.toJson(user);
        try {
        Request request = new Request.Builder()
                .url(UrlPath.updateUserUrl)
                .post(RequestBody.create(JSON, jsonstr))
                .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String str = response.body().string();
            Gson gson = new Gson();
            String res = gson.fromJson(str, String.class);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    public String sendPwdPost(User user,String pwd) {
        Gson gson1 = new Gson();
        String jsonstr = gson1.toJson(user);
        Request request = new Request.Builder()
                .url(UrlPath.chgUserPwd)
                .post(RequestBody.create(JSON, jsonstr+","+pwd))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String str = response.body().string();
            Gson gson = new Gson();
            String res = gson.fromJson(str, String.class);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public void sendPhonePost(String phone) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON,phone))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
