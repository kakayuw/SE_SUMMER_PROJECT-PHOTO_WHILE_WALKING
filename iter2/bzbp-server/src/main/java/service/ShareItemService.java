package service;

import java.util.List;

import model.ShareItem;
import model.User;

/**
 * 
 * 
 */
public interface ShareItemService {

	public List<ShareItem> getAll();

	public List<ShareItem> getAllbyUid(int uid);
	
	public List<ShareItem> getMyAll(int uid);
	
	public void addShareItem(ShareItem shareItem);
	
	public List<ShareItem> getTopNumber(int number);
	
	public ShareItem getBest();
	
	public void changeBest(String sid);
	
	public void upvote(String sid);
	
	public void cancelUpvote(String sid);

}
