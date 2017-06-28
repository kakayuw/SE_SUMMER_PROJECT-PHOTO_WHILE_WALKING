package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.UserDao;
import model.User;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	public Integer save(User user) {
		return (Integer) getHibernateTemplate().save(user);
	}

	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

	public void update(User user) {
		getHibernateTemplate().merge(user);
	}

	public User getUserById(int id) {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate().find("from User as u where u.id=?", id);
		User user = users.size() > 0 ? users.get(0) : null;
		return user;
	}

	public User getUserByUsername(String username) {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate().find("from User as u where u.username=?", username);
		User user = users.size() > 0 ? users.get(0) : null;
		return user;
	}

	public List<User> getAllUsers() {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate().find("from User");
		return users;
	}

	public boolean login(String username, String password) {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate()
				.find("from User as u where u.username=? and u.password=?", username, password);
		if (users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean duplicate(String username) {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate().find("from User as u where u.username=?", username);
		if (users.size() > 0) { // duplicate
			return true;
		} else {
			return false;
		}
	}

}
