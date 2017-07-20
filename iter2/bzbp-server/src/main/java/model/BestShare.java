package model;

public class BestShare {
	private String sid;
	private java.util.Date time;
	private int type;
	
	public BestShare(){
		
	}
	
	public BestShare(String sid, java.util.Date time, int type){  
		this.sid = sid;
		this.time = time;
		this.type = type;
	}
    
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public java.util.Date getTime() {
		return time;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	
	
}
