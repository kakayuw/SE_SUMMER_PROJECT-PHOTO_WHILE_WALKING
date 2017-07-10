package service.impl;

import java.util.List;

import dao.AdminDao;
import model.Admin;
import service.AdminService;;

public class AdminServiceImpl implements AdminService {

	private AdminDao adminDao;

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public Boolean check(Admin admin) {
		Admin adm = adminDao.getAdminByName(admin.getUsername());
		if (adm != null && adm.getPassword().equals(admin.getPassword()))
			return true;
		return false;
	}

	public List<Admin> getAll() {
		return adminDao.getAll();
	}
}
