package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ContacterDao;
import model.Contacter;

public class ContacterDaoImpl extends HibernateDaoSupport implements ContacterDao {

	public void save(Contacter contacter) {
		getHibernateTemplate().save(contacter);
	}

	public void delete(Contacter contacter) {
		getHibernateTemplate().delete(contacter);
	}

	@SuppressWarnings("unchecked")
	public List<Contacter> getContacterById(int uid) {
		return (List<Contacter>) getHibernateTemplate().find("from Contacter");
	}

	public void update(Contacter contacter) {
		getHibernateTemplate().update(contacter);
	}
}
