package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.AdminDao;
import model.Admin;

public class AdminDaoImpl extends HibernateDaoSupport implements AdminDao {

	@Override
	public Admin getAdminByName(String aname) {
		@SuppressWarnings("unchecked")
		List<Admin> admins = (List<Admin>) getHibernateTemplate().find("from Admin as a where a.username=?", aname);
		Admin admin = admins.size() > 0 ? admins.get(0) : null;
		return admin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Admin> getAll() {
		return (List<Admin>) getHibernateTemplate().find("from Admin");
	}

}
