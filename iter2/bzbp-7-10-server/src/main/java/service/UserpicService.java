package service;

import java.io.File;

public interface UserpicService {

	public void save(int uid, File file);

	public byte[] getPicById(int uid);

	public void usersave(byte[] userpic, int uid);
}
