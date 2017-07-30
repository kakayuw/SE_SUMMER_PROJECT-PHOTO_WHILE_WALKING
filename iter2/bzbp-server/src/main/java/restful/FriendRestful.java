package restful;

import java.util.ArrayList;
import java.util.List;

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

import model.Contacter;
import model.Friend;
import model.User;
import service.ContacterService;
import service.UserService;
import util.SpringContextUtil;

@Path("/friend")
public class FriendRestful 
{

	private ContacterService contacterService=(ContacterService) SpringContextUtil.getBean("contacterService");
	private UserService userService=(UserService) SpringContextUtil.getBean("userService");

	 
	 @POST
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
	 
	 @GET
     @Path("/getAll/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Friend> getAll(@PathParam("uid") int uid){
		 //int uid = 1;
		 System.out.println("getall");
		 System.out.println(uid);
		 List<Contacter> contacters = contacterService.getContacterById(uid);
		 List<Friend> result_friends = new ArrayList<Friend>();
		 int size = contacters.size();
		 for (int i = 0; i < size; i++){
			 result_friends.add(new Friend(contacters.get(i)));
			 System.out.println(contacters.get(i).getRemark());
		 }
		 
		 return result_friends;
	 }
	 

	 
	 
	 @GET
     @Path("/addFriend/{uid1}/{uid2}")
	 @Produces("text/html")
     public String addFriend(@PathParam("uid1") int uid1, @PathParam("uid2") int uid2){
		 //int uid2 = 2;
		 System.out.println("addfriend");
		 System.out.println(uid1);
		 System.out.println(uid2);
		 return contacterService.addfriend(uid1, uid2);
     }
	 
	 @GET
     @Path("/deleteFriend/{uid1}/{uid2}")
	 @Produces("text/html")
     public String deleteFriend(@PathParam("uid1") int uid1, @PathParam("uid2") int uid2){
		 System.out.println("deleteFriend");
		 System.out.println(uid1);
		 System.out.println(uid2);
		 contacterService.delete(uid1, uid2);
		 return "success";
	 }
	 
	 @GET
     @Path("/changeRemark/{uid1}/{uid2}")
	 @Produces("text/html")
     public String changeRemark(@PathParam("uid1") int uid1, @PathParam("uid2") int uid2){
		 System.out.println("changeRemark");
		 Contacter contacter = contacterService.getContacterByIds(uid1, uid2);
		 contacter.setRemark("2333");
		 contacterService.update(contacter);
		 return "success";
	 }
	 
	 @GET
	 @Path("/search/{idname}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<User> search(@PathParam("idname") String idname){
		 //int uid = 1;
		 System.out.println("search");
		 System.out.println(idname);

		 List<User> users = userService.searchUser(idname);
		 return users;
	 }

}