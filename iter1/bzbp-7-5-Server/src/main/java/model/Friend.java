package model;

public class Friend {
	private int uid;
	private String nickname;
	
	public Friend(){	
	}
	
	public Friend(int uid, String nickname) {
		this.uid = uid;
		this.nickname = nickname;
	}
	
	public Friend(Contacter contacter){ 
		this.uid = contacter.getUid2();
		this.nickname = contacter.getRemark();
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
