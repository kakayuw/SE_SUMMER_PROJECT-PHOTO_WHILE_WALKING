package service;

import java.util.List;

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
	public Integer addUser(User user);

	public void deleteUser(User user);

	public void updateUser(User user);

	public User getUserById(int id);

	public User getUserByUsername(String username);

	public List<User> getAllUsers();

	public boolean login(String username, String password);

	public boolean duplicate(String username);
}
