package dao;

import model.Userpic;

public interface UserpicDao {

	public void save(Userpic userpic);

	public void delete(Userpic userpic);

	public void update(Userpic userpic);

	public Userpic getUserpicById(int uid);

}
