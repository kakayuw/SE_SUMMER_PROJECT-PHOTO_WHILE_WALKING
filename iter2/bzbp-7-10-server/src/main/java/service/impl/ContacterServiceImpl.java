package service.impl;

import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import dao.ContacterDao;
import model.Contacter;
import service.ContacterService;
import model.User;
import dao.UserDao;

public class ContacterServiceImpl implements ContacterService {

	private ContacterDao contacterDao;
	private UserDao userDao;

	public void setContacterDao(ContacterDao contacterDao) {
		this.contacterDao = contacterDao;
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public void save(Contacter contacter) {
		contacterDao.save(contacter);
	}

	public void delete(int uid1, int uid2) {
		Contacter contacter1 = contacterDao.getContacterByIds(uid1, uid2);
		Contacter contacter2 = contacterDao.getContacterByIds(uid2, uid1);
		if (contacter1 != null){ 		
			contacterDao.delete(contacter1);
		}
		if (contacter2 != null){  
			contacterDao.delete(contacter2);
		}
	}

	public List<Contacter> getContacterById(int uid) {
		return contacterDao.getContacterById(uid);
	}

	public void update(Contacter contacter) {
		contacterDao.update(contacter);
	}

	public String addfriend(int uid1, int uid2) {
		String name1 = (userDao.getUserById(uid1)).getUsername();
		String name2 = (userDao.getUserById(uid2)).getUsername();
		Contacter addContacter1 = new Contacter(uid1, uid2, name2, (short)1);
		if (contacterDao.duplicate(addContacter1) == 1) {
			return "failed";
		}
		else{
			contacterDao.save(addContacter1);
			Contacter addContacter2 = new Contacter(uid2, uid1, name1, (short)1);
			contacterDao.save(addContacter2);
			return "success";
		}
	}


}
