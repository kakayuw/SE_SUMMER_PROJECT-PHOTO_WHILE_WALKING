package model;

import java.io.InputStream;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_pic")
public class Userpic {
	int uid;
	InputStream pic;

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getUid() {
		return this.uid;
	}

	public void setPic(InputStream pStream) {
		this.pic = pStream;
	}

	public InputStream getPic() {
		return this.pic;
	}
}
