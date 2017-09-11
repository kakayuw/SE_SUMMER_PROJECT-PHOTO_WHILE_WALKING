package com.photowalking.model;

/**
 * Created by liujinxu on 17/7/3.
 */

public class Friend {
    private int uid;
    private String nickname;

    public Friend(int uid, String nickname){
        this.uid = uid;
        this.nickname = nickname;
    }

    public int getUid() {
        return this.uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
