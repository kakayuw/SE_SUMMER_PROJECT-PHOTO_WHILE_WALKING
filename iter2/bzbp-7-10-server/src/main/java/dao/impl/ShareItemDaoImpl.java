package dao.impl;

import java.util.List;

import model.ShareItem;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ShareItemDao;

public class ShareItemDaoImpl extends HibernateDaoSupport implements ShareItemDao {

	public Integer save(ShareItem shareItem) {
		return (Integer) getHibernateTemplate().save(shareItem);
	}

	public void delete(ShareItem shareItem) {
		getHibernateTemplate().delete(shareItem);
		
	}

	public void update(ShareItem shareItem) {
		getHibernateTemplate().merge(shareItem);		
	}

	@Override
	public ShareItem getShareItemById(int sid) {
		@SuppressWarnings("unchecked")
		List<ShareItem> shareItems = (List<ShareItem>) getHibernateTemplate().find(
				"from ShareItem as s where s.sid=?", sid);
		ShareItem shareItem = shareItems.size() > 0 ? shareItems.get(0) : null;
		return shareItem;
	}

	@Override
	public List<ShareItem> getAllShareItems() {
		@SuppressWarnings("unchecked")
		List<ShareItem> shareItems = (List<ShareItem>) getHibernateTemplate()
				.find("from ShareItem");
		return shareItems;
	}

	@Override
	public List<ShareItem> getShareItemByUid(int uid) {
		@SuppressWarnings("unchecked")
		List<ShareItem> shareItems = (List<ShareItem>) getHibernateTemplate().find(
				"from ShareItem as s where s.uid=?", uid);
		return shareItems;
	}
	
}
