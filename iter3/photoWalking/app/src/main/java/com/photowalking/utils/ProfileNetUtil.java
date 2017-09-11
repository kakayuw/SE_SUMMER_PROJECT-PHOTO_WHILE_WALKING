package com.photowalking.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import com.photowalking.model.User;

import okhttp3.FormBody;
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
    static final String TAG = "ProfileNetUtils: ";

    static String exception = "IOException";

    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    public ProfileNetUtil() {
        client = new OkHttpClient();

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
        return  sendPost(request);
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
            response.close();
            user = gson.fromJson(str, User.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public User getUserByUid(String url,String uid) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, uid))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String str = response.body().string();
            Gson gson = new Gson();
            response.close();
            return gson.fromJson(str, User.class);
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public String sendFriendRequest(String fid, String me, String url) {
        String ip = url + me + "/" + fid;
        Request request = new Request.Builder()
                .url(ip)
                .get()
                .build();
        return  sendPost(request);
    }

    public String addFriendRequest(int uida, int uidb, String remarka, String remarkb) {
        String ip = UrlPath.addFriUrl+uida+"/"+uidb+"/"+remarka+"/"+remarkb;
        Request request = new Request.Builder()
                .url(ip)
                .get()
                .build();
        return  sendPost(request);
    }

    public String chgRemarkRequest(String uida, String uidb, String remark){
        String ip = UrlPath.chgRemarkUrl;
        RequestBody formbody = new FormBody.Builder().add("uid1",uida).add("uid2",uidb)
                .add("remark",remark).build();
        Request request = new Request.Builder()
                .url(ip)
                .post(formbody)
                .build();
        return  sendPost(request);
    }

    public String sendEditPost(String uid, String name, String email) {
        RequestBody formbody = new FormBody.Builder().add("uid",uid).add("name",name)
                .add("email",email).build();
        Request request = new Request.Builder()
                .url(UrlPath.updateUserUrl)
                .post(formbody)
                .build();
        return  sendPost(request);
    }

    public String sendPwdPost(String uid, String opwd, String npwd) {
        RequestBody formbody = new FormBody.Builder().add("uid",uid).add("opwd",opwd)
                .add("npwd",npwd).build();
        Request request = new Request.Builder()
                .url(UrlPath.chgUserPwd)
                .post(formbody)
                .build();
        return  sendPost(request);
    }

    public String sendPhonePost(String uid,String phone) {
        RequestBody formbody = new FormBody.Builder().add("uid",uid).add("phone",phone).build();
        Request request = new Request.Builder()
                .url(UrlPath.chgPhone)
                .post(formbody)
                .build();
        return  sendPost(request);
    }

    private String sendPost(Request request){
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String str =response.body().string();
            response.close();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return exception;
        }
    }

}
