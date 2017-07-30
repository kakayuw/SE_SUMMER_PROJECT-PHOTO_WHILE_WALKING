package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.ContacterDao;
import dao.ShareItemDao;
import model.Contacter;
import model.SelectedShare;
import model.ShareItem;
import service.ShareItemService;

/**
 * @version 1.0
 * 
 */
public class ShareItemServiceImpl implements ShareItemService {
	private ShareItemDao shareItemDao;
	private ContacterDao contacterDao;
	
	public ShareItemDao getShareItemDao() {
		return shareItemDao;
	}

	public void setShareItemDao(ShareItemDao shareItemDao) {
		this.shareItemDao = shareItemDao;
	}

	public ContacterDao getContacterDao() {
		return contacterDao;
	}

	public void setContacterDao(ContacterDao contacterDao) {
		this.contacterDao = contacterDao;
	}

	public List<ShareItem> getAll() {
		return shareItemDao.getPublicShareItems();
	}
	
	public List<SelectedShare> getAll(int pagenum, int size){ 
		return shareItemDao.getPublicShareItems(pagenum, size);
	}
	
	
	public List<ShareItem> getAllbyUid(int uid) {
		List<Contacter> contacters = contacterDao.getContacterById(uid);
		int size  = contacters.size();
		List<ShareItem> shareItems = new ArrayList<ShareItem>();
		List<ShareItem> add = new ArrayList<ShareItem>();
		for (int i = 0; i < size; i++){ 
			add = shareItemDao.getFriendShareByUid(contacters.get(i).getUid2());
			shareItems.addAll(add);
		}
		return shareItems;
	}
	
	public List<SelectedShare> getMyAll(int pagenum, int size, int uid){
		return shareItemDao.getMyShareItems(pagenum, size, uid);
	}
	
	public List<SelectedShare> getFriendAll(int pagenum, int size, int uid){  
		return shareItemDao.getFriendShareItems(pagenum, size, uid);
	}
	
	public List<ShareItem> getMyAll(int uid){  
		return shareItemDao.getShareItemByUid(uid);
	}


	public void addShareItem(ShareItem shareItem){ 
		shareItemDao.save(shareItem);
	}

	public List<ShareItem> getTopNumber(int number){  
		return shareItemDao.getTopNumber(number);
	}
	
	public ShareItem getBest(){  
		return shareItemDao.getBest();
	}
	
	public void changeBest(String sid){  
		shareItemDao.changeBest(sid);
	}
	
	public String upvote(int uid,String sid){       
		if (shareItemDao.searchUpvote(uid, sid).equals("nothave")){
			ShareItem shareItem = shareItemDao.getShareItemById(sid);
			shareItem.setUpvote(shareItem.getUpvote()+1);
			shareItemDao.update(shareItem);
			shareItemDao.addUpvotedetail(uid, sid);
			return "success";
		}
		return "duplicate";
	}
	
	public void cancelUpvote(int uid,String sid){  
		ShareItem shareItem = shareItemDao.getShareItemById(sid);
		shareItem.setUpvote(shareItem.getUpvote()-1);
		shareItemDao.update(shareItem);
		shareItemDao.cancelUpvotedetail(uid, sid);
	}
	
	public String searchUpvote(int uid, String sid){
		return shareItemDao.searchUpvote(uid, sid);
	}
}
