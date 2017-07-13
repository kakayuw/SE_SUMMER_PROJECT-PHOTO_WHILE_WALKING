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
import model.ShareItem;
import model.User;
import service.ContacterService;
import service.RouteService;
import service.ShareItemService;
import service.UserpicService;
import util.SpringContextUtil;

@Path("/share")

public class ShareRestful {
	private ShareItemService shareItemService = (ShareItemService) SpringContextUtil.getBean("shareItemService");
	private RouteService routeService = (RouteService) SpringContextUtil.getBean("routeService");
	 
	@GET
    @Path("/getAll/")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<ShareItem> getAll(){
		 System.out.println("getall");
		 List<ShareItem> shareItems = shareItemService.getAll();
		 
		 return shareItems;
	 }
	 
	@GET
    @Path("/friendGetAll/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<ShareItem> friendGetAll(@PathParam("uid") int uid){
		 System.out.println("friendGetAll");
		 List<ShareItem> shareItems = shareItemService.getAllbyUid(uid);
		 
		 return shareItems;
	 }
	
	@GET
    @Path("/myGetAll/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<ShareItem> myGetAll(@PathParam("uid") int uid){
		 System.out.println("myGetAll");
		 List<ShareItem> shareItems = shareItemService.getMyAll(uid);
		 
		 return shareItems;
	 }
	
	 @POST
     @Path("/addShare")
	 @Produces("text/html")
     public String signup(ShareItem shareItem){
		 System.out.println("addShare");
		 System.out.println(shareItem.getUid());
		 System.out.println(shareItem.getTitle());;
		 shareItemService.addShareItem(shareItem);
		 return "success";
     }
	 
	 @GET
	 @Path("/addRoute/{sid}/{routedetail}")
	 @Produces("text/html")
	 public String addRoute(@PathParam("sid") String sid, @PathParam("routedetail") String routedetail){
		 System.out.println("addRoute");
		 routeService.addShareRoute(sid, routedetail);
		 return "success";
	 }
	 
}
