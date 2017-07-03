package dao;


import java.util.List;

import model.Profile;

public interface ProfileDao {
		
	public void save(Profile profile);

	public void delete(Profile profile);

	public Profile getProfileByUid(int uid);
		
	public List<Profile> getAll();
	
	public void update (Profile profile);

}
