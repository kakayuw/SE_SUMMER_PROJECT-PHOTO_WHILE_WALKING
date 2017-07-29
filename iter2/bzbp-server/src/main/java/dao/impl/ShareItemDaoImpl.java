package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.xml.crypto.Data;

import model.ShareItem;
import model.User;
import model.BestShare;
import model.SelectedShare;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.cfg.QuerySecondPass;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ShareItemDao;

public class ShareItemDaoImpl extends HibernateDaoSupport implements ShareItemDao {
	
	public void save(ShareItem shareItem) {
		getHibernateTemplate().save(shareItem);
	}

	public void delete(ShareItem shareItem) {
		getHibernateTemplate().delete(shareItem);
		
	}

	public void update(ShareItem shareItem) {
		getHibernateTemplate().merge(shareItem);		
	}

	@Override
	public ShareItem getShareItemById(String sid) {
		@SuppressWarnings("unchecked")
		List<ShareItem> shareItems = (List<ShareItem>) getHibernateTemplate().find(
				"from ShareItem as s where s.sid=?", sid);
		ShareItem shareItem = shareItems.size() > 0 ? shareItems.get(0) : null;
		return shareItem;
	}

	@Override
	public List<ShareItem> getPublicShareItems() {
		@SuppressWarnings("unchecked")
		List<ShareItem> shareItems = (List<ShareItem>) getHibernateTemplate()
				.find("from ShareItem as s where s.type=1");
		return shareItems;
	}
	
	public List<SelectedShare> getPublicShareItems(int pagenum, int size) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery("call getshare(?,?)");
		query.setInteger(0, pagenum);
		query.setInteger(1, size);
		query.executeUpdate();
		@SuppressWarnings("unchecked")
        List<SelectedShare> get_shareitems = (List<SelectedShare>) getHibernateTemplate().find(
				"from SelectedShare");
        return get_shareitems;
	}
	
	public List<SelectedShare> getFriendShareItems(int pagenum, int size, int uid) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery("call getsharebyuid(?,?,?)");
		query.setInteger(0, pagenum);
		query.setInteger(1, size);
		query.setInteger(2, uid);
		query.executeUpdate();
		@SuppressWarnings("unchecked")
        List<SelectedShare> get_shareitems = (List<SelectedShare>) getHibernateTemplate().find(
				"from SelectedShare");
        return get_shareitems;
	}

	public List<SelectedShare> getMyShareItems(int pagenum, int size, int uid) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery("call getmyshare(?,?,?)");
		query.setInteger(0, pagenum);
		query.setInteger(1, size);
		query.setInteger(2, uid);
		query.executeUpdate();
		@SuppressWarnings("unchecked")
        List<SelectedShare> get_shareitems = (List<SelectedShare>) getHibernateTemplate().find(
				"from SelectedShare");
        return get_shareitems;
	}
	
	@Override
	public List<ShareItem> getShareItemByUid(int uid) {
		@SuppressWarnings("unchecked")
		List<ShareItem> shareItems = (List<ShareItem>) getHibernateTemplate().find(
				"from ShareItem as s where s.uid=?", uid);
		return shareItems;
	}
	
	public List<ShareItem> getFriendShareByUid(int uid) {
		@SuppressWarnings("unchecked")
		List<ShareItem> shareItems = (List<ShareItem>) getHibernateTemplate().find(
				"from ShareItem as s where s.uid=? and s.type=0", uid);
		return shareItems;
	}
	
	public List<ShareItem> getTopNumber(int number){  
		Session session = getSession();
		SQLQuery sqlQuery = session.createSQLQuery("CALL shareTopNumber(?)");
		sqlQuery.setInteger(0, number);
		sqlQuery.executeUpdate();
		SQLQuery query = getSession().createSQLQuery("select * from selected_shareitem");
        List<ShareItem> get_shareitem  =(List<ShareItem>)query.list();
		
		/*
		SQLQuery sqlQuery = getSession().createSQLQuery("CALL shareTopNumber(?)");
		sqlQuery.setInteger(0, number);
		sqlQuery.executeUpdate();
		getSession().close();
        SQLQuery query = getSession().createSQLQuery("select * from selected_shareitem");
        List<ShareItem> get_shareitem  =(List<ShareItem>)query.list();
        getSession().close();
        SQLQuery query2 = getSession().createSQLQuery("select * from user");
        List<User> get_user  =(List<User>)query2.list();

        getSession().close();
        */
        return get_shareitem;
		
	}
	
	public ShareItem getBest(){   
		/*
        SQLQuery query = getSession().createSQLQuery("select sid from bestshare where type = 1");
        //query.executeUpdate();
        List<String> get_bestshare  =(List<String>)query.list();
        String bestsid = "";
        if (get_bestshare.size() > 0){   
        	bestsid = get_bestshare.get(0);
        }
		*/
		@SuppressWarnings("unchecked")
		List<BestShare> bestShares = (List<BestShare>) getHibernateTemplate().find(
				"from BestShare as b where b.type=1");
		String bestsid = "";
		if (bestShares.size() > 0 ){   
			bestsid = bestShares.get(0).getSid();
		}
        @SuppressWarnings("unchecked")
		List<ShareItem> shareItems = (List<ShareItem>) getHibernateTemplate().find(
				"from ShareItem as s where s.sid=?", bestsid);
        if (shareItems.size() >0){
        	return shareItems.get(0);
        }
        //getSession().close();
		return null;
		
	}
	
	public void changeBest(String sid){
		Session session = getSession();
		SQLQuery sqlquery = session.createSQLQuery("update bestshare set type = 0");
        sqlquery.executeUpdate();
        SQLQuery query = session.createSQLQuery("insert into bestshare(sid,time,type) values(?,?,1)");
        java.sql.Timestamp date = new java.sql.Timestamp(new Date().getTime()); 
        query.setString(0, sid);
		query.setTimestamp(1, date);
		query.executeUpdate();
		/*
        SQLQuery sqlquery = getSession().createSQLQuery("update bestshare set type = 0");
        sqlquery.executeUpdate();
        getSession().close();
        SQLQuery query = getSession().createSQLQuery("insert into bestshare(sid,time,type) values(?,?,1)");
        java.sql.Timestamp date = new java.sql.Timestamp(new Date().getTime()); 
        query.setString(0, sid);
		query.setTimestamp(1, date);
		query.executeUpdate();
		getSession().close();
		*/
	}
	
	public String searchUpvote(int uid, String sid){
		Session session = getSession();
		SQLQuery sqlquery = session.createSQLQuery("select * from upvotedetail where uid = ? and sid = ?");
		sqlquery.setInteger(0, uid);
		sqlquery.setString(1, sid);
		List<Object> get_upvote  =(List<Object>)sqlquery.list();
		if (get_upvote.size()>0){
			return "exist";
		} 
		else{ 
			return "nothave";
		}
	}
	
	public void addUpvotedetail(int uid, String sid){  
		Session session = getSession();
		SQLQuery sqlquery = session.createSQLQuery("insert into upvotedetail(uid,sid) values(?,?)");
		sqlquery.setInteger(0, uid);
		sqlquery.setString(1, sid);
		sqlquery.executeUpdate();
	}
	
	public void cancelUpvotedetail(int uid, String sid){  
		Session session = getSession();
		SQLQuery sqlquery = session.createSQLQuery("delete from upvotedetail where uid = ? and sid = ?");
		sqlquery.setInteger(0, uid);
		sqlquery.setString(1, sid);
		sqlquery.executeUpdate();
	}
}
