package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ContacterDao;
import model.Contacter;
import model.ShareItem;

public class ContacterDaoImpl extends HibernateDaoSupport implements ContacterDao {

	public void save(Contacter contacter) {
		getHibernateTemplate().save(contacter);
	}

	public void delete(Contacter contacter) {
		getHibernateTemplate().delete(contacter);
	}

	@SuppressWarnings("unchecked")
	public List<Contacter> getContacterById(int uid) {
		return (List<Contacter>) getHibernateTemplate().find("from Contacter as c where c.uid1=?", uid);
	}

	public void update(Contacter contacter) {
		getHibernateTemplate().update(contacter);
	}

	
	@SuppressWarnings("unchecked")
	public int duplicate(Contacter contacter) {
		List<Contacter> contacters = (List<Contacter>) getHibernateTemplate().find("from Contacter as c "
		 		+ "where c.uid1=? and c.uid2 = ?", contacter.getUid1(),contacter.getUid2());
		 if (contacters.size()!=0){
			 return 1;
		 }
		 else return 0;
	}
	
	public Contacter getContacterByIds(int uid1, int uid2){  
		@SuppressWarnings("unchecked")
		List<Contacter> contacters = (List<Contacter>) getHibernateTemplate().find(
				"from Contacter as c where c.uid1=? and c.uid2=?", uid1, uid2);
		Contacter contacter = contacters.size() > 0 ? contacters.get(0) : null;
		return contacter;
	}

}
