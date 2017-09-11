package com.photowalking.model;

/**
 * Created by lionel on 2017/8/20.
 */

public class SendMsg{
    String title;
    int uid;
    public SendMsg(String title, int uid) {
        this.title = title;
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
}
