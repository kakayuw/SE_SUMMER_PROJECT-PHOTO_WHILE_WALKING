package com.photowalking.model;

/**
 * Created by lionel on 17/8/21.
 */

public class SendPicture {
    private String sid;
    private int idx;
    private byte[] pic;

    public  SendPicture(String sid, int idx, byte[] pic){
        this.sid = sid;
        this.idx = idx;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public byte[] getPic(){
        return this.pic;
    }

    public void setPic(byte[] pic) { this.pic = pic; }

}
