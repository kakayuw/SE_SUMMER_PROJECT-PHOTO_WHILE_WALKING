package action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import com.mysql.jdbc.ReflectiveStatementInterceptorAdapter;
import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import dao.ProfileDao;
import dao.UserDao;
import model.Contacter;
import model.User;
import net.sf.json.JSONArray;
import service.UserService;
import service.UserpicService;
import service.ContacterService;

public class UserAction extends BaseAction{

	private int uid;
	private String username;
	private String password;
	private String email;
	private String phone;
	private UserDao userDao;
	private UserService userService;
	private ContacterService contacterService;
	private UserpicService userpicService;

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
		try {
			this.username = java.net.URLDecoder.decode(username,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public ContacterService getContacterService() {
		return contacterService;
	}

	public void setContacterService(ContacterService contacterService) {
		this.contacterService = contacterService;
	}
	
	public UserpicService getUserpicService() {
		return userpicService;
	}

	public void setUserpicService(UserpicService userpicService) {
		this.userpicService = userpicService;
	}
	//method
	public String get(){
		return "success";
	}
	
	public String getAll(){
		List<User> users = userDao.getAllUsers();
		request().setAttribute("user", users);
		return "success";
	}

	public String addUser(){
		
		User user = new User(username, password, email, phone);
		return userService.addUser(user);
	}
	
	public String updateUser(){
		User user = userService.getUserByUid(uid);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhone(phone);
		return userService.updateUser(user);
	}

	public String deleteUser(){
		userService.deleteUserByUid(uid);
		return SUCCESS;
	}

	public void getFriends(){
		List<Contacter> contacters = contacterService.getContacterById(uid);
		try {
			JSONArray jsonArray = JSONArray.fromObject(contacters);
			response().setCharacterEncoding("utf-8");
			response().getWriter().println(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File pic;

	public void setPic(File pic) {
		this.pic = pic;
	}

	
	public void pic() throws IOException {
		int uid = -1;
		HttpServletRequest request = ServletActionContext.getRequest();
		if (!"".equals(request.getParameter("uid")) && request.getParameter("uid") != null) {
			uid = Integer.parseInt(request.getParameter("uid"));
		}
		if (uid == -1)
			return;
		byte[] pic = userpicService.getPicById(uid);
		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream outputStream = response.getOutputStream();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment; filename=\"acc.jpg\"");
		outputStream.write(pic);
		outputStream.flush();
		outputStream.close();
	}

	private static int uuid = 2;
	
	public String signup() {
		if (pic != null)
			userpicService.save(uuid, pic);
		else {
			System.out.println("asdfasdf");
		}
		uuid++;
		return "test";
	}

}
