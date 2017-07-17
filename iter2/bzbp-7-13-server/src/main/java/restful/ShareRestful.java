package restful;

import java.io.File;
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
import model.Routepic;		
import service.ContacterService;
import service.RouteService;
import service.RoutepicService;
import service.ShareItemService;
import service.UserpicService;
import util.SpringContextUtil;

@Path("/share")

public class ShareRestful {
	private ShareItemService shareItemService = (ShareItemService) SpringContextUtil.getBean("shareItemService");
	private RouteService routeService = (RouteService) SpringContextUtil.getBean("routeService");
	private RoutepicService routepicService = (RoutepicService) SpringContextUtil.getBean("routepicService");
	 
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
	 
	 @POST
	 @Path("/addRoute/{sid}")
	 @Produces("text/html")
	 public String addRoute(@PathParam("sid") String sid, String routedetail){
		 System.out.println("addRoute");
		 routeService.addShareRoute(sid, routedetail);
		 return "success";
	 }
	 
	 @GET
	 @Path("/getRoute/{sid}")
	 @Produces("text/html")
	 public String getRoute(@PathParam("sid") String sid){
		 System.out.println("getRoute");
		 return routeService.getShareRoute(sid);
	 }
	 
	 @GET
	 @Path("/getPics/{sid}")
	 @Consumes(MediaType.APPLICATION_OCTET_STREAM)
	 @Produces(MediaType.APPLICATION_OCTET_STREAM)
	 public List<byte[]> getPics(@PathParam("sid") String sid, byte[] pics){
		 System.out.println("getPics");
		 return routepicService.getPicsById(sid);
	 }
	 
	 @GET
	 @Path("/addPics/{sid}")
	 @Consumes(MediaType.APPLICATION_OCTET_STREAM)
	 @Produces("text/html")
	 public String addPics(@PathParam("sid") String sid, byte[] pics){
		 System.out.println("addPics");
		 routepicService.save(sid, pics);
		 return "success";
	 }
	 
	 @POST
	 @Path("/addPicFile/{sid}")
	 @Consumes(MediaType.APPLICATION_OCTET_STREAM)
	 @Produces("text/html")
	 public String addPicFile(@PathParam("sid") String sid, File pics){
		 System.out.println("addPicFile");
		 System.out.println(pics);
		 routeService.addShareRoutePic(sid, pics);
		// routepicService.save(sid, pics);
		 return "success";
	 }
	 
	 @GET
	 @Path("/getPicFile/{sid}")
	 @Produces(MediaType.APPLICATION_OCTET_STREAM)
	 public File getPicFile(@PathParam("sid") String sid){
		 System.out.println("getPicFile");
		 System.out.println(sid);
		 return routeService.getShareRoutePic(sid);	 
	}
	 
}
