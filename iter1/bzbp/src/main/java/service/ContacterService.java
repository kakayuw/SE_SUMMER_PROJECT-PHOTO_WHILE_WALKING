package service;

import java.util.List;

import model.Contacter;

public interface ContacterService {

	public void save(Contacter contacter);

	public void delete(Contacter contacter);

	public List<Contacter> getContacterById(int uid);

	public void update(Contacter contacter);
}
