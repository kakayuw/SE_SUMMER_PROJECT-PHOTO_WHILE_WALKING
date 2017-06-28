package dao;

import java.util.List;
import model.Bookdetail;

public interface BookdetailDao {

	public Bookdetail findById(int id);

	public List<Bookdetail> findAll(Class<Bookdetail> entityClass);

	public void remove(Bookdetail bookdetail);

	public void add(Bookdetail bookdetail);

	public void saveOrUpdate(Bookdetail bookdetail);
	
	public void savePicture(int id, String source);
	
}
