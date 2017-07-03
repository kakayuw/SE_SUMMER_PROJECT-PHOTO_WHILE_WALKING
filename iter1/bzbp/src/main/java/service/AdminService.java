package service;

import java.util.List;

import model.Admin;

public interface AdminService {

	public Boolean check(Admin admin);

	public List<Admin> getAll();

}
