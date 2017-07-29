package service;

import java.util.List;

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
	
	public User getUserByUsername(String username);
	
	public void deleteUserByUid(int uid);
	
	public List<User> searchUser(String idname);
}
