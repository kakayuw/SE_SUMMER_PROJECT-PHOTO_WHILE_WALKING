package model;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectedShare {
	private String sid;
    private int uid;
    private String username;
    private String title;
    private int picnum;
    private Date starttime;
    private Date endtime;
    private int upvote;
    private int comment;
    private String poem;
    private int type;
    
    public SelectedShare(){
    	
    }
    
    public SelectedShare(String sid, int uid, String username, String title, int picnum, java.util.Date starttime, java.util.Date endtime, int upvote, int comment, String poem,int type){ 
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
    	this.type = type;
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

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
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


	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public String getPoem() {
		return poem;
	}

	public void setPoem(String poem) {
		this.poem = poem;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
