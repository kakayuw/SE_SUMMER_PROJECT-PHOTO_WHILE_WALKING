package model;

import java.io.Serializable;

public class Saitem {
    private String sid;
	private int uid;
	private String username;
    private String title;
    private int picnum;
    private String starttime;
    private String endtime;
    private int upvote;
    private int comment;
    private String poem;
    private int type;
    
    public Saitem(){
    	
    }
    public Saitem(ShareItem satm) {
    	this.sid = satm.getSid();
    	this.uid = satm.getUid();
    	this.username = satm.getUsername();
    	this.title = satm.getTitle();
    	this.picnum = satm.getPicnum();
    	this.starttime = satm.getStarttime().toString();
    	this.endtime = satm.getEndtime().toString();
    	this.upvote = satm.getUpvote();
    	this.comment = satm.getComment();
    	this.poem = satm.getPoem();
    	this.type = satm.getType();
	}
    
    public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPicnum() {
		return picnum;
	}
	public void setPicnum(int picnum) {
		this.picnum = picnum;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getUpvote() {
		return upvote;
	}
	public void setUpvote(int upvote) {
		this.upvote = upvote;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public String getPoem() {
		return poem;
	}
	public void setPoem(String poem) {
		this.poem = poem;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
} 