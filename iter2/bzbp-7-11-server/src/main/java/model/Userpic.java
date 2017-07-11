package model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_pic")
public class Userpic {
	int uid;
	String picname;
	byte[] pic;

	public Userpic() {
	}

	public Userpic(int uid, byte[] pic) {
		this.uid = uid;
		this.pic = pic;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	public String getPicname() {
		return this.picname;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

}
