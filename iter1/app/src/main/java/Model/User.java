package Model;

/**
 * Created by liujinxu on 17/6/28.
 */

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;

    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}
    public String getUsername() {return this.username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return this.password;}
    public void setPassword(String password) {this.password = password;}
    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhone() {return this.phone;}
    public void setPhone(String phonenumber) {this.phone = phonenumber;}
}
