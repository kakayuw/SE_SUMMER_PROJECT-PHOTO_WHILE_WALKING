package service;

import java.util.List;

import model.Routepic;

public interface RoutepicService {

	public void save(String pid, byte[] routepic);

	public List<byte[]> getPicsById(String pid);
	
	public Routepic getUserPicById(String pid);

	public void usersave(byte[] userpic, String pid);
}
