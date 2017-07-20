package dao;

import java.util.List;

import model.ShareItem;;

public interface ShareItemDao {

	public void save(ShareItem shareItem);

	public void delete(ShareItem shareItem);

	public void update(ShareItem shareItem);

	public ShareItem getShareItemById(String sid);
	
	public List<ShareItem> getPublicShareItems();

	public List<ShareItem> getShareItemByUid(int uid);
	
	public List<ShareItem> getFriendShareByUid(int uid);
	
	public List<ShareItem> getTopNumber(int number);
	
	public ShareItem getBest();
	
	public void changeBest(String sid);
}
