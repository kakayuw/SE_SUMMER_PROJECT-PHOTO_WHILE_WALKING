package restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.UserDao;
import model.Contacter;
import model.Friend;
import model.User;
import service.ContacterService;
import service.UserService;
import service.UserpicService;
import util.SpringContextUtil;

 
@Path("/user/")
public class UserRestful 
{

	private UserService userService=(UserService) SpringContextUtil.getBean("userService");
	private UserpicService userpicService = (UserpicService) SpringContextUtil.getBean("userpicService");

	
	@GET
	@Produces("text/html")
	public Response getStartingPage()
	{
		String output = "<h1>No Zuo No Die!<h1>";
		return Response.status(200).entity(output).build();
	}
/*	
	@GET
	@Path("/getBooks")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book>  AllBooks()
	{
		System.out.println("getBooks");
		List<Book> books = appService.getAllBooks();
		return books;
	}
	
	 @POST
     @Path("/addBook")
	 //@Produces(MediaType.APPLICATION_JSON)
	 //@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
     public String addBook(Book book){
		 System.out.println("addBook");
		 appService.addBook(book);
		 return "success";
     }

	 
	 @POST
     @Path("/updateBook")
     public String updateBook(Book book){
		 System.out.println("updateBook");
		 appService.updateBook(book);
		 return "success";
     }
	 
	 @GET
     @Path("/deleteBook/{id}")
     public String deleteById(@PathParam("id") int id){
		 System.out.println("deleteBook");
		 appService.deleteBookById(id);
        return "success";
     }
	*/
	
	 @GET
     @Path("/test")
	 @Produces("text/html")
     public String test(){
		 System.out.println("test");
		 return "success";
     }	
	 
	 @POST
     @Path("/login")
	 @Produces("text/html")
     public String login(User user){
		 System.out.println("login");
		 System.out.println(user.getUsername());
		 System.out.println(user.getPassword());
		 return userService.login(user);
     }
	 
	 @POST
     @Path("/signup")
	 @Produces("text/html")
     public String signup(User user){
		 System.out.println("signup");
		 System.out.println(user.getUsername());
		 System.out.println(user.getPassword());
		 System.out.println(user.getPhone());
		 System.out.println(user.getEmail());
		 return userService.addUser(user);
     }
	 
	 @GET
     @Path("/json")
	 @Produces(MediaType.APPLICATION_JSON)
     public List<Friend> json(){
		 System.out.println("testjson");
		 Friend friend1 = new Friend(3,"qwer");
		 Friend friend2 = new Friend(4,"asdf");
		 List<Friend> result_friends = new ArrayList<Friend>();
		 result_friends.add(friend1);
		 result_friends.add(friend2);
		 return result_friends;  
     }	
	 
	 @POST
     @Path("/getUserByUid/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public User getUserByUid(@PathParam("uid") int uid){
		 System.out.println("getUserByUid");
		 System.out.println(uid);
		 User theUser = userService.getUserByUid(uid);
		 theUser.setPassword("");
		 return theUser;
	 }
	 
	 @POST
     @Path("/updateUser")
	 @Produces("text/html")
     public String updateUser(User user){
		 System.out.println("updateUser");
		 System.out.println(user.getUsername());
		 System.out.println(user.getEmail());
		 user.setUsername(user.getUsername());
		 user.setPhone(user.getPhone());
		 return userService.updateUser(user);
	 }
	 
	 @POST
     @Path("/changePassword")
	 @Produces("text/html")
     public String changePassword(User user, String newPassword){
		 System.out.println("changePassword");
		 System.out.println(user.getPassword());
		 System.out.println(newPassword);
		 User oldUser = userService.getUserByUid(user.getUid());
		 if (oldUser.getPassword().equals(user.getPassword())){
			 oldUser.setPassword(newPassword);
			 userService.updateUser(oldUser);
			 return "success";
		 }
		 return "failed";
	 }
	 
	 @POST
     @Path("/changePhone")
	 @Produces("text/html")
     public String changePhone(int uid, String phone){
		 System.out.println("changePhone");
		 System.out.println(uid);
		 System.out.println(phone);
		 User user = userService.getUserByUid(uid);
		 user.setPhone(phone);
		 userService.updateUser(user);
		 return "success";
	 }
	 
	 
	 @POST
     @Path("/getUserByUsername/{username}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public User getUserByUsername(@PathParam("username") String username){
		 System.out.println("getUserByUsername");
		 System.out.println(username);
		 User theUser = userService.getUserByUsername(username);
		 theUser.setPassword("");
		 System.out.println(theUser.getEmail());
		 return theUser;
	 }

	 @GET
     @Path("/getPicture/{uid}")
	 @Produces(MediaType.APPLICATION_OCTET_STREAM)
	 public byte[] getPicture(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("uid") int uid){
		 //int uid = 1;
		 System.out.println("getpicture");
		 System.out.println(uid);

		 
		 response.setContentType("application/octet-stream");
		 response.addHeader("Content-Disposition", "attachment; filename=\"acc.jpg\"");
		 return userpicService.getPicById(uid);
	 }
	 
	 @POST
	 @Path("/saveUserPicture/{uid}")
	 @Consumes(MediaType.APPLICATION_OCTET_STREAM)
	 public void saveUserPicture(byte[] userpic, @PathParam("uid") int uid){
		 //int uid = 1;
		 System.out.println("saveUserPicture");
		 System.out.println(userpic);
		 userpicService.usersave(userpic, uid);
	 }
	 
}