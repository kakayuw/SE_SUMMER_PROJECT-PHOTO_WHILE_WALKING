package service.impl;

import java.util.List;


import model.User;
import service.AppService;


import dao.UserDao;

/**
 * @version 1.0
 * 
 */
public class AppServiceImpl implements AppService {


	private UserDao userDao;



	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * user
	 * 
	 */
	public Integer addUser(User user) {
		return userDao.save(user);
	}

	public void deleteUser(User user) {
		userDao.delete(user);
	}

	public void updateUser(User user) {
		userDao.update(user);
	}

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}
	
	public User getUserByUsername(String username){
		return userDao.getUserByUsername(username);
	}
	

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
	public boolean login_ok(String username, String password){
			return userDao.login_ok(username,password);		
	}
	
	public boolean duplicate_username(String username){
		return userDao.duplicate_username(username);
	}
}
