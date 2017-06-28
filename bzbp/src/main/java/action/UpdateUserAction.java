package action;

import model.User;
import service.AppService;

public class UpdateUserAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private int id;
	private String username;
	private String password;
	private String role;
	private String name;
	private String gender;
	private String phonenumber;
	private String address;
	
	private AppService appService;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	@Override
	public String execute() throws Exception {

		User user = appService.getUserById(id);
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);
		user.setName(name);
		user.setGender(gender);
		user.setPhonenumber(phonenumber);
		user.setAddress(address);
		appService.updateUser(user);

		return SUCCESS;
	}

}
