package service.impl;

import java.util.List;
import java.util.regex.Pattern;

import dao.UserDao;
import model.User;
import service.UserService;

/**
 * @version 1.0
 * 
 */
public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * user
	 * 
	 */
	public String addUser(User user) {
		User u = userDao.getUserByEmail(user.getEmail());
		User u2 = userDao.getUserByUsername(user.getUsername());
		if (u == null && u2 == null) {
			userDao.save(user);
			return "success";
		}
		else
			return "failed";
	}

	public String login(User user) {
		User u = userDao.getUserByUsername(user.getUsername());
		if (u != null && u.getPassword().equals(user.getPassword())) {
			return "u"+Integer.toString(u.getUid());
		}
		else
			return "failed";
	}

	@Override
	public String updateUser(User user) {
		userDao.update(user);
			return "success";	
	}

	@Override
	public User getUserByUid(int uid) {
		return userDao.getUserById(uid);
	}
	
	@Override
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public void deleteUserByUid(int uid) {
		User user = userDao.getUserById(uid);
		userDao.delete(user);
	}

	@Override
	public List<User> searchUser(String idname){
		if(isInteger(idname)){
			return userDao.searchUserByBoth(idname);
		}else{
			return userDao.searchUserByName(idname);
		}
	}

	private static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
  	}
}
