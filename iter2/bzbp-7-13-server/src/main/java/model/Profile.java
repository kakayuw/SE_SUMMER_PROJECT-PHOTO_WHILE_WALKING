package model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection="profile")
public class Profile {
	int uid;
	String intro;
	String autograph;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	
}
