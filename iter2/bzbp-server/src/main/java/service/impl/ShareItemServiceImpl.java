package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.ContacterDao;
import dao.ShareItemDao;
import model.Contacter;
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
		return shareItemDao.getAllShareItems();
	}

	public List<ShareItem> getAllbyUid(int uid) {
		List<Contacter> contacters = contacterDao.getContacterById(uid);
		int size  = contacters.size();
		List<ShareItem> shareItems = new ArrayList<ShareItem>();
		List<ShareItem> add = new ArrayList<ShareItem>();
		for (int i = 0; i < size; i++){ 
			add = shareItemDao.getShareItemByUid(contacters.get(i).getUid2());
			shareItems.addAll(add);
		}
		return shareItems;
	}
	
	public List<ShareItem> getMyAll(int uid){  
		return shareItemDao.getShareItemByUid(uid);
	}


	public int addShareItem(ShareItem shareItem){ 
		return shareItemDao.save(shareItem);
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
}
