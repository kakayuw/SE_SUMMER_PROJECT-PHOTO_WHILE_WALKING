package model;

import java.security.Timestamp;
import java.sql.Date;
import java.sql.Time;

public class ShareItem {
	private int sid;
    private int uid;
    private String username;
    private String title;
    private int picnum;
    private java.sql.Timestamp starttime;
    private java.sql.Timestamp endtime;
    private int upvote;
    private int comment;
    private String poem;
    
    public ShareItem(){
    	
    }
    
    public ShareItem(int sid, int uid, String username, String title, int picnum, java.sql.Timestamp starttime, java.sql.Timestamp endtime, int upvote, int comment, String poem){ 
    	this.sid = sid;
    	this.uid = uid;
    	this.username = username;
    	this.title = title;
    	this.picnum = picnum;
    	this.starttime = starttime;
    	this.setEndtime(endtime);
    	this.upvote = upvote;
    	this.comment = comment;
    	this.poem = poem;
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

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getUpvote() {
		return upvote;
	}

	public void setUpvote(int upvote) {
		this.upvote = upvote;
	}


	public java.sql.Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(java.sql.Timestamp starttime) {
		this.starttime = starttime;
	}

	public String getPoem() {
		return poem;
	}

	public void setPoem(String poem) {
		this.poem = poem;
	}

	public java.sql.Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(java.sql.Timestamp endtime) {
		this.endtime = endtime;
	}
}
