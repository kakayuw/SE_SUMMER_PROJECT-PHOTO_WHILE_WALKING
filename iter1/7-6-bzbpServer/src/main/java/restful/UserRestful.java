package restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Contacter;
import model.Friend;
import model.User;
import service.UserService;
import util.SpringContextUtil;

 
@Path("/user/")
public class UserRestful 
{

	private UserService userService=(UserService) SpringContextUtil.getBean("userService");

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
     @Path("/getUserByUsername/{username}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public User getUserByUsername(@PathParam("username") String username){
		 System.out.println("getUserByUsername");
		 System.out.println(username);
		 User theUser = userService.getUserByUsername(username);
		 theUser.setPassword("");
		 return theUser;
	 }
}