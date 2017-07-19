package com.photowalking.model;

/**
 * Created by liujinxu on 17/7/11.
 */

public class SendBytes {
    private byte[] pic;
    private int uid;
    private String picname;

    public byte[] getPic(){
        return this.pic;
    }

    public void setPic(byte[] pic) { this.pic = pic; }

    public int getUid(){
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPicname() {
        return this.picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

}
