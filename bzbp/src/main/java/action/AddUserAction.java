package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


import model.User;
import service.AppService;

public class AddUserAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String role;
	private String name;
	private String gender;
	private String phonenumber;
	private String address;

	private AppService appService;
	private InputStream inputStream;

    public InputStream getInputStream() {  
        return inputStream;  
    }  
  
    public void setInputStream(InputStream inputStream) {  
        this.inputStream = inputStream;  
    }  	

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
		Boolean flag = appService.duplicate_username(username);
		if (flag){
			inputStream = new ByteArrayInputStream("duplicate username"  
	                .getBytes("UTF-8")); 
			return "success";
		}
		
		User user = new User(username, password, role ,name ,gender ,phonenumber ,address);
		appService.addUser(user);
		
		inputStream = new ByteArrayInputStream("success"  
                .getBytes("UTF-8")); 
		return "success";
	}

}
