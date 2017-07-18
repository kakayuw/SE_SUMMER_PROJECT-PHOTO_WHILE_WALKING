package dao;

import java.util.List;

import model.Routepic;

public interface RoutepicDao {

	public void save(Routepic routepic);

	public void delete(Routepic routepic);

	public void update(Routepic routepic);

	public Routepic getRoutepicById(String pid);
	
	public List<Routepic> getRoutepicsById(String pid);

}
