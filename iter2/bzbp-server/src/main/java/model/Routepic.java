package model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "route_pic")
public class Routepic {
	String pid;
	String picname;
	byte[] pic;

	public Routepic() {
	}

	public Routepic(String pid, byte[] pic) {
		this.pid = pid;
		this.pic = pic;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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
