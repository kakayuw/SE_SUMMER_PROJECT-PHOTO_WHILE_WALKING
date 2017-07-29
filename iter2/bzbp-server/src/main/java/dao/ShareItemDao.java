package dao;

import java.util.List;

import model.SelectedShare;
import model.ShareItem;;

public interface ShareItemDao {

	public void save(ShareItem shareItem);

	public void delete(ShareItem shareItem);

	public void update(ShareItem shareItem);

	public ShareItem getShareItemById(String sid);
	
	public List<ShareItem> getPublicShareItems();
	
	public List<SelectedShare> getPublicShareItems(int pagenum, int size);
	
	public List<SelectedShare> getFriendShareItems(int pagenum, int size, int uid);
	
	public List<SelectedShare> getMyShareItems(int pagenum, int size, int uid);

	public List<ShareItem> getShareItemByUid(int uid);
	
	public List<ShareItem> getFriendShareByUid(int uid);
	
	public List<ShareItem> getTopNumber(int number);
	
	public ShareItem getBest();
	
	public void changeBest(String sid);
	
	public String searchUpvote(int uid, String sid);
	
	public void addUpvotedetail(int uid, String sid);
	
	public void cancelUpvotedetail(int uid, String sid);
}
