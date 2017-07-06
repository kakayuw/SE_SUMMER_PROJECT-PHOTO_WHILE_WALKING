package dao;

import java.util.List;

import model.Admin;

public interface AdminDao {

	public Admin getAdminByName(String aname);

	public List<Admin> getAll();

}
