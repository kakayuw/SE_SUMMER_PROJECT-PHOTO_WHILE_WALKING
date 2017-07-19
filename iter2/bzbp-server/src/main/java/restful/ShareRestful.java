package restful;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
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

import com.sun.jersey.core.impl.provider.xml.ThreadLocalSingletonContextProvider;

import model.Contacter;
import model.Friend;
import model.ShareItem;
import model.User;
import net.sf.json.JSONObject;
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
	 public List<saitem> getAll(){
		 System.out.println("getall");
		 List<ShareItem> shareItems = shareItemService.getAll();
		 List<saitem> sList = new ArrayList<>();
		 for(ShareItem si: shareItems){
			 sList.add(new saitem(si));
		 }
		 return sList;
	}
	 
	@GET
    @Path("/friendGetAll/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<saitem> friendGetAll(@PathParam("uid") int uid){
		 System.out.println("friendGetAll");
		 List<ShareItem> shareItems = shareItemService.getAllbyUid(uid);
		 List<saitem> sList = new ArrayList<>();
		 for(ShareItem si: shareItems){
			 sList.add(new saitem(si));
		 }
		 return sList;
		 
	 }
	
	@GET
    @Path("/myGetAll/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<saitem> myGetAll(@PathParam("uid") int uid){
		 System.out.println("myGetAll");
		 List<ShareItem> shareItems = shareItemService.getMyAll(uid);
		 List<saitem> sList = new ArrayList<>();
		 for(ShareItem si: shareItems){
			 sList.add(new saitem(si));
		 }
		 return sList;
	 }
	
	 @POST
     @Path("/addShare")
	 @Produces("text/html")
     public String addShare(ShareItem shareItem){
		 System.out.println("addShare");
		 System.out.println(shareItem.getUid());
		 System.out.println(shareItem.getTitle());
		 System.out.println(shareItem.getUpvote());
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
	 
		private class saitem implements Serializable{
		    private String sid;
			private int uid;
			private String username;
		    private String title;
		    private int picnum;
		    private String starttime;
		    private String endtime;
		    private int upvote;
		    private int comment;
		    private String poem;
		    public saitem(ShareItem satm) {
		    	this.sid = satm.getSid();
		    	this.uid = satm.getUid();
		    	this.username = satm.getUsername();
		    	this.title = satm.getTitle();
		    	this.picnum = satm.getPicnum();
		    	this.starttime = satm.getStarttime().toString();
		    	this.endtime = satm.getEndtime().toString();
		    	this.upvote = satm.getUpvote();
		    	this.comment = satm.getComment();
		    	this.poem = satm.getPoem();
			}
		    
		    public String getSid() {
				return sid;
			}
			public void setSid(String sid) {
				this.sid = sid;
			}
			public int getUid() {
				return uid;
			}
			public void setUid(int uid) {
				this.uid = uid;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}
			public int getPicnum() {
				return picnum;
			}
			public void setPicnum(int picnum) {
				this.picnum = picnum;
			}
			public String getStarttime() {
				return starttime;
			}
			public void setStarttime(String starttime) {
				this.starttime = starttime;
			}
			public String getEndtime() {
				return endtime;
			}
			public void setEndtime(String endtime) {
				this.endtime = endtime;
			}
			public int getUpvote() {
				return upvote;
			}
			public void setUpvote(int upvote) {
				this.upvote = upvote;
			}
			public int getComment() {
				return comment;
			}
			public void setComment(int comment) {
				this.comment = comment;
			}
			public String getPoem() {
				return poem;
			}
			public void setPoem(String poem) {
				this.poem = poem;
			}
		} 
	 
	 
}
