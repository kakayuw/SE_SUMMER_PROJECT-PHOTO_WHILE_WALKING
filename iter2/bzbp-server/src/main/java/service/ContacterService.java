package service;

import java.util.List;

import model.Contacter;

public interface ContacterService {

	public void save(Contacter contacter);
	
	public String addfriend(int uid1, int uid2);

	public void delete(int uid1, int uid2);

	public List<Contacter> getContacterById(int uid);
	
	public Contacter getContacterByIds(int uid1, int uid2);

	public void update(Contacter contacter);
}
