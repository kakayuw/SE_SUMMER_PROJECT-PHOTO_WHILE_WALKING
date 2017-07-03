package service.impl;

import java.util.List;

import dao.ContacterDao;
import model.Contacter;
import service.ContacterService;

public class ContacterServiceImpl implements ContacterService {

	private ContacterDao contacterDao;

	public void setContacterDao(ContacterDao contacterDao) {
		this.contacterDao = contacterDao;
	}

	public void save(Contacter contacter) {
		contacterDao.save(contacter);
	}

	public void delete(Contacter contacter) {
		contacterDao.delete(contacter);
	}

	public List<Contacter> getContacterById(int uid) {
		return contacterDao.getContacterById(uid);
	}

	public void update(Contacter contacter) {
		contacterDao.update(contacter);
	}

}
