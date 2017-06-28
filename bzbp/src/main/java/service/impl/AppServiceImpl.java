package service.impl;

import java.util.List;

import dao.UserDao;
import model.User;
import service.AppService;

/**
 * @author seniyuting
 * @version 1.0
 * 
 */
public class AppServiceImpl implements AppService {
	private UserDao userDao;

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
