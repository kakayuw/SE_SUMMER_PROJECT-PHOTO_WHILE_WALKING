package service;

import java.util.List;

<<<<<<< HEAD
=======

>>>>>>> 23cbde1e67f4144d43181cb3e4c4342acae79aa6
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
