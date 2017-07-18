package model;

public class Comment {

	private int cid;
	private int sid;
	private String username;
	private String comment;


	public Comment() {
	}

	public Comment(int cid, int sid, String username, String comment) {
		this.cid = cid;
		this.sid = sid;
		this.username = username;
		this.comment = comment;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	
}