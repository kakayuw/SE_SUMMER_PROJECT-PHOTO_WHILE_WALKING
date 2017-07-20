package restful;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.ShareItem;
import model.Saitem;
import net.sf.json.JSONObject;
import service.RouteService;
import service.RoutepicService;
import service.ShareItemService;
import util.SpringContextUtil;

@Path("/share")

public class ShareRestful {
	private ShareItemService shareItemService = (ShareItemService) SpringContextUtil.getBean("shareItemService");
	private RouteService routeService = (RouteService) SpringContextUtil.getBean("routeService");
	private RoutepicService routepicService = (RoutepicService) SpringContextUtil.getBean("routepicService");
	

	@GET
    @Path("/getAll/")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Saitem> getAll(){
		 System.out.println("getall");
		 List<ShareItem> shareItems = shareItemService.getAll();
		 List<Saitem> sList = new ArrayList<>();
		 for(ShareItem si: shareItems){
			 sList.add(new Saitem(si));
		 }
		 return sList;
	}
	 
	@GET
    @Path("/friendGetAll/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Saitem> friendGetAll(@PathParam("uid") int uid){
		 System.out.println("friendGetAll");
		 List<ShareItem> shareItems = shareItemService.getAllbyUid(uid);
		 List<Saitem> sList = new ArrayList<>();
		 for(ShareItem si: shareItems){
			 sList.add(new Saitem(si));
		 }
		 return sList;
		 
	 }
	
	@GET
    @Path("/myGetAll/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Saitem> myGetAll(@PathParam("uid") int uid){
		 System.out.println("myGetAll");
		 List<ShareItem> shareItems = shareItemService.getMyAll(uid);
		 List<Saitem> sList = new ArrayList<>();
		 for(ShareItem si: shareItems){
			 sList.add(new Saitem(si));
		 }
		 return sList;
	 }
	
	 @POST
     @Path("/addShare")
	 @Produces("text/html")
     public String addShare(Saitem item){
		 System.out.println("addShare");
		 System.out.println(item.getUid());
		 System.out.println(item.getStarttime());
		 System.out.println(item.getEndtime());
		 ShareItem shareItem = new ShareItem(item);
		 
		 shareItemService.addShareItem(shareItem);
		 return "success";
     }
	 
	 @POST
     @Path("/testAddShare")
	 @Produces("text/html")
     public String testAddShare(JSONObject json){
		 System.out.println("addShare");
		 System.out.println(json.get("uid"));
		 System.out.println(json.get("sid"));
		 System.out.println(json.get("starttime"));
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
	 
	 @GET
	 @Path("/upvote/{sid}")
	 @Produces("text/html")
	 public String upvote(@PathParam("sid") String sid){
		 System.out.println("upvote");
		 shareItemService.upvote(sid);
		 return "success";
	 }
	 
	 @GET
	 @Path("/cancelUpvote/{sid}")
	 @Produces("text/html")
	 public String cancelUpvote(@PathParam("sid") String sid){
		 System.out.println("cancelUpvote");
		 shareItemService.cancelUpvote(sid);
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
