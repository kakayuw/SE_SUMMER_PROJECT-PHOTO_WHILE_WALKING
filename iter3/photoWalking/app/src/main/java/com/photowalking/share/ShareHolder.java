package com.photowalking.share;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

/**
 * Created by lionel on 2017/7/21.
 */

public class ShareHolder {
    public Activity activity;
    public ListView listView;
    public String url;
    public String uid;
    public String uname;
    public int pagenum;

    public ShareHolder(Activity activity, ListView listView, String url, String uid, String uname){
        this.activity = activity;
        this.listView = listView;
        this.url = url;
        this.uid = uid;
        this.uname = uname;
        pagenum = 1;
    }
}
