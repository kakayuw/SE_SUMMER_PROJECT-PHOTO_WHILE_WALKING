package service.impl;

import java.util.List;

import dao.ShareItemDao;
import model.ShareItem;
import service.ShareItemService;

/**
 * @version 1.0
 * 
 */
public class ShareItemServiceImpl implements ShareItemService {
	private ShareItemDao shareItemDao;
	
	public ShareItemDao getShareItemDao() {
		return shareItemDao;
	}

	public void setShareItemDao(ShareItemDao shareItemDao) {
		this.shareItemDao = shareItemDao;
	}

	
	public List<ShareItem> getAll() {
		return shareItemDao.getAllShareItems();
	}



}
