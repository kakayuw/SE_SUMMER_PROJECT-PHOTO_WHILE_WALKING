package dao;

import java.util.List;

import model.ShareItem;;

public interface ShareItemDao {

	public Integer save(ShareItem shareItem);

	public void delete(ShareItem shareItem);

	public void update(ShareItem shareItem);

	public ShareItem getShareItemById(int sid);
	
	public List<ShareItem> getAllShareItems();

}
