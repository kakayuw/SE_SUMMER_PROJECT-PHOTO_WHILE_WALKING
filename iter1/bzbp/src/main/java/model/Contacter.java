package model;

public class Contacter {

	private int fid;
	private int uid1;
	private int uid2;
	private String remark;
	private String auth;
	
	public Contacter() {
	}

	public Contacter(int uid1, int uid2, String remark, String auth) {
		this.uid1 = uid1;
		this.uid2 = uid2;
		this.remark = remark;
		this.auth = auth;
	}
	
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public int getUid1() {
		return uid1;
	}
	public void setUid1(int uid1) {
		this.uid1 = uid1;
	}
	public int getUid2() {
		return uid2;
	}
	public void setUid2(int uid2) {
		this.uid2 = uid2;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}



	
}