package dao.impl;

import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import model.ShareItem;
import model.User;

import org.hibernate.SQLQuery;
import org.hibernate.cfg.QuerySecondPass;
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
	
	public List<ShareItem> getTopNumber(int number){   
		SQLQuery sqlQuery = getSession().createSQLQuery("CALL shareTopNumber(?)");
		sqlQuery.setInteger(0, number);
		sqlQuery.executeUpdate();
		getSession().close();
		//getSessionFactory().getCurrentSession().close();
        SQLQuery query = getSession().createSQLQuery("select * from selected_shareitem");
        //query.executeUpdate();
        List<ShareItem> get_shareitem  =(List<ShareItem>)query.list();
        getSession().close();
        SQLQuery query2 = getSession().createSQLQuery("select * from user");
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
     //   getSessionFactory().getCurrentSession().close();
  //      if (getSessionFactory().getCurrentSession().isOpen()){
   //     	getSessionFactory().getCurrentSession().close();
   //     }
        getSession().close();
        return get_shareitem;
		
	}
	
	public ShareItem getBest(){   
        SQLQuery query = getSession().createSQLQuery("select sid from bestshare where type = 1");
        //query.executeUpdate();
        List<String> get_bestshare  =(List<String>)query.list();
        String bestsid = "";
        if (get_bestshare.size() > 0){   
        	bestsid = get_bestshare.get(0);
        }

        @SuppressWarnings("unchecked")
		List<ShareItem> shareItems = (List<ShareItem>) getHibernateTemplate().find(
				"from ShareItem as s where s.sid=?", bestsid);
        if (shareItems.size() >0){
        	return shareItems.get(0);
        }
       // if (getSessionFactory().getCurrentSession().isOpen()){
      //  	getSessionFactory().getCurrentSession().close();
      //  }
        getSession().close();
		return null;
		
	}
	
	public void changeBest(String sid){
        SQLQuery sqlquery = getSession().createSQLQuery("update bestshare set type = 0");
        sqlquery.executeUpdate();
        getSession().close();
      //  getSessionFactory().getCurrentSession().close();
        SQLQuery query = getSession().createSQLQuery("insert into bestshare(sid,time,type) values(?,?,1)");
        java.sql.Timestamp date = new java.sql.Timestamp(new Date().getTime()); 
        query.setString(0, sid);
		query.setTimestamp(1, date);
		query.executeUpdate();
		getSession().close();
		//getSessionFactory().getCurrentSession().close();
	}
}
