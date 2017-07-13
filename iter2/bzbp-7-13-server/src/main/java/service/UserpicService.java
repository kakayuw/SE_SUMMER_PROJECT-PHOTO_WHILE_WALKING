package service;

import java.io.File;

import model.Userpic;

public interface UserpicService {

	public void save(int uid, File file);

	public byte[] getPicById(int uid);
	
	public Userpic getUserPicById(int uid);

	public void usersave(byte[] userpic, int uid);
}
