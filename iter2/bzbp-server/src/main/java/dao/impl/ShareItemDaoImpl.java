package dao.impl;

import java.util.ArrayList;
import java.util.List;

import model.ShareItem;
import model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ShareItemDao;
import javassist.bytecode.LineNumberAttribute;

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
	
	public List<ShareItem> getTopNumber(int number){   
		SQLQuery sqlQuery = getSessionFactory().openSession().createSQLQuery("CALL shareTopNumber(?)");
		sqlQuery.setInteger(0, number);
		sqlQuery.executeUpdate();
        SQLQuery query = getSessionFactory().openSession().createSQLQuery("select * from selected_shareitem");
        //query.executeUpdate();
        List<ShareItem> get_shareitem  =(List<ShareItem>)query.list();
        SQLQuery query2 = getSessionFactory().openSession().createSQLQuery("select * from user");
        //query.executeUpdate();
        List<User> get_user  =(List<User>)query2.list();
//		JSONArray items = JSONArray.fromObject(sqlQuery.list());
//		List<ShareItem> shareItems = new ArrayList<>();
//		for(int i=0; i<items.size(); ++i){
//			ShareItem shareItem = new ShareItem();
//			JSONObject jsonObject = items.getJSONObject(i);
//			shareItem.setComment(jsonObject.getInt("comment"));
//			//shareItems.add(new ShareItem())
//		}
		return get_shareitem;
		
	}
}
