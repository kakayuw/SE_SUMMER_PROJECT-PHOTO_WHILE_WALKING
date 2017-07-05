package action;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import java.util.List;
import dao.ProfileDao;
import model.Profile;

public class ProfileAction extends BaseAction{

	private Profile profile;
	private ProfileDao profileDao;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public ProfileDao getProfileDao() {
		return profileDao;
	}

	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}
	
	//method
	public String get(){
		return "success";
	}
	
	public String getAll(){
		List<Profile> profiles = profileDao.getAll();
		request().setAttribute("profiles", profiles);
		session().setAttribute("profiles", profiles);
		return "success";
	}
}
