package service;

import model.User;

/**
 * 
 * 
 */
public interface UserService {

	/**
	 * user
	 * 
	 */
	public String addUser(User user);

	public String login(User user);
	
	public String updateUser(User user);
	
	public User getUserByUid(int uid);
	
	public void deleteUserByUid(int uid);
}
