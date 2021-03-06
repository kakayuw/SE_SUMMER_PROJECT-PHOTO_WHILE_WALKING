package restful;

import java.io.OutputStream;
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

import model.Contacter;
import model.Friend;
import model.User;
import service.ContacterService;
import service.UserpicService;
import util.SpringContextUtil;

@Path("/friend")
public class FriendRestful 
{

	private ContacterService contacterService=(ContacterService) SpringContextUtil.getBean("contacterService");
	private UserpicService userpicService = (UserpicService) SpringContextUtil.getBean("userpicService");

	 
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
	 
}