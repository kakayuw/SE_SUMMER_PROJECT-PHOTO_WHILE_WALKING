package service.impl;

import java.util.List;

<<<<<<< HEAD
import dao.UserDao;
import model.User;
import service.AppService;
=======

import model.User;
import service.AppService;


import dao.UserDao;
>>>>>>> 23cbde1e67f4144d43181cb3e4c4342acae79aa6

/**
 * @version 1.0
 * 
 */
public class AppServiceImpl implements AppService {
<<<<<<< HEAD
	private UserDao userDao;

=======


	private UserDao userDao;



	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

>>>>>>> 23cbde1e67f4144d43181cb3e4c4342acae79aa6
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

	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public boolean login(String username, String password) {
		return userDao.login(username, password);
	}

	public boolean duplicate(String username) {
		return userDao.duplicate(username);
	}
}
