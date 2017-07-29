package service;

import java.util.List;

import model.SelectedShare;
import model.ShareItem;
import model.User;

/**
 * 
 * 
 */
public interface ShareItemService {

	public List<ShareItem> getAll();
	
	public List<SelectedShare> getAll(int pagenum, int size);

	public List<ShareItem> getAllbyUid(int uid);
	
	public List<ShareItem> getMyAll(int uid);
	
	public List<SelectedShare> getFriendAll(int pagenum, int size, int uid);
	
	public List<SelectedShare> getMyAll(int pagenum, int size, int uid);
	
	public void addShareItem(ShareItem shareItem);
	
	public List<ShareItem> getTopNumber(int number);
	
	public ShareItem getBest();
	
	public void changeBest(String sid);
	
	public String upvote(int uid,String sid);
	
	public void cancelUpvote(int uid,String sid);
	
	public String searchUpvote(int uid, String sid);

}
