package action;

import java.util.List;

import dao.UserDao;
import model.User;

public class UserAction extends BaseAction {

	private int uid;
	private String username;
	private String password;
	private String email;
	private String phone;
	private UserDao userDao;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	// method
	public String get() {
		return "success";
	}

	public String getAll() {
		List<User> users = userDao.getAllUsers();
		request().setAttribute("user", users);
		return "success";
	}

}
