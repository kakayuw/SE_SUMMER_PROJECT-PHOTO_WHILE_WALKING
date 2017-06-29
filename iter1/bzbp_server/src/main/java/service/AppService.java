package service;

import model.User;

/**
 * 
 * 
 */
public interface AppService {

	/**
	 * user
	 * 
	 */
	public String addUser(User user);

	public String login(User user);
}
