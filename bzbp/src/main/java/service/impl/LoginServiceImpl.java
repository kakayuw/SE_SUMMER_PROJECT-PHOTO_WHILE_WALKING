package service.impl;


import service.LoginService;
import dao.LoginDao;
import dao.impl.LoginDaoImpl;

public class LoginServiceImpl implements LoginService {
	private LoginDao loginDao;
	
	public void setLoginDao(LoginDaoImpl loginDao) {
		this.loginDao = loginDao;
	}
	
	public boolean login_ok(String username, String password){
		return loginDao.login_ok(username,password);
	}

}
