package dao.impl;

import java.util.List;

import model.User;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.LoginDao;

public class LoginDaoImpl extends HibernateDaoSupport implements LoginDao{

	public boolean login_ok(String username, String password){
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate().find(
				"from User as u where u.username=? and u.password=?", username, password);
		if (users.size() > 0){
			return true;
		}
		else{
			return false;
		}
		
	}
}
