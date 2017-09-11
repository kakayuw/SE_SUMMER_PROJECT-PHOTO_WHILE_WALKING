package com.photowalking.model;

/**
 * Created by liujinxu on 17/8/11.
 */

public class Comment {

    private int cid;
    private String sid;
    private Integer uida;
    private Integer uidb;
    private String unamea;
    private String unameb;
    private String comment;

    public Comment(String sid, Integer uida, Integer uidb, String unamea, String unameb, String comment) {
        this.sid = sid;
        this.uida = uida;
        this.uidb = uidb;
        this.unamea = unamea;
        this.unameb = unameb;
        this.comment = comment;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getUida() {
        return uida;
    }

    public void setUida(Integer uida) {
        this.uida = uida;
    }

    public Integer getUidb() {
        return uidb;
    }

    public void setUidb(Integer uidb) {
        this.uidb = uidb;
    }

    public String getUnamea() {
        return unamea;
    }

    public void setUnamea(String unamea) {
        this.unamea = unamea;
    }

    public String getUnameb() {
        return unameb;
    }

    public void setUnameb(String unameb) {
        this.unameb = unameb;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
