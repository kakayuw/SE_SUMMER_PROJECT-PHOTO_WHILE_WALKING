package dao;

import java.util.List;

import model.User;

public interface UserDao {

	public Integer save(User user);

	public void delete(User user);

	public void update(User user);

	public User getUserById(int id);

	public User getUserByUsername(String username);

	public List<User> getAllUsers();

	public boolean login(String username, String password);

	public boolean duplicate(String username);

}