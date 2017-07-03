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
}
