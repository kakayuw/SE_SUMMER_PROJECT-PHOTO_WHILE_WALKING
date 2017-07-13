package dao;

import java.util.List;

import model.Contacter;

public interface ContacterDao {
	public void save(Contacter contacter);

	public void delete(Contacter contacter);

	public List<Contacter> getContacterById(int uid);

	public Contacter getContacterByIds(int uid1, int uid2);
	
	public void update(Contacter contacter);

	public int duplicate(Contacter contacter);
}
