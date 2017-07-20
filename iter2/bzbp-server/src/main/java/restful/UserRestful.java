package restful;

import java.io.File;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.print.attribute.standard.Media;
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

import org.apache.commons.fileupload.FileItemHeaders;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.util.Base64Codec;
import com.sun.jersey.core.util.Base64;

import dao.UserDao;
import model.Contacter;
import model.Friend;
import model.User;
import model.Userpic;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.ContacterService;
import service.UserService;
import service.UserpicService;
import util.ByteUtil;
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
		 System.out.println(userService.login(user));
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
	//	 System.out.println("getpicture");
	//	 System.out.println(uid);

		 
		 response.setContentType("application/octet-stream");
		 response.addHeader("Content-Disposition", "attachment; filename=\"acc.jpg\"");
	//	 byte[] bb = userpicService.getPicById(uid);
	//	 return bb;
		 return userpicService.getPicById(uid);
		// String str = "锟斤拷锟絓u0000\u0010JFIF\u0000\u0001\u0001\u0000\u0000\u0001\u0000\u0001\u0000\u0000锟斤拷\u0000C\u0000\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0015\u0015\u0015\u0015锟斤拷\u0000\u0011\b\u0000锟絓u0000锟絓u0003\u0000\"\u0000\u0001\u0011\u0000\u0002\u0011\u0000锟斤拷\u0000W\u0000\u0001\u0000\u0003\u0001\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0003\u0004\u0006\u0005\u0010\u0001\u0000\u0002\u0001\u0002\u0003\u0005\u0003\u0006\u000B\t\u0001\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0011\u0003\u0012\u0004!1\u0013\"2AQaq锟絓u0005BRb浠璡u0014#3Cr鍋勫悧鑰盶u00064鍎濊緢瑁傝锟斤拷\u0000\f\u0003\u0000\u0000\u0001\u0000\u0002\u0000\u0000?\u0000榄�*锟絓u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001[锟�)|u鍩欓儘鎶北鍔伴礈濠囧垗鐑嗘&璩筭=|閸絓u0016濠℡瑕�/\u0013>\u001D锟絓n娉瀦O缇嬮锟�3\u0013\u0019锟絓u0001 \u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002b绋欙拷?\u0004顥檜@绺�C鎺ｅ诞鐘嶉锟�=闅垱閳竗Eq>-婵冭禋N&y鑿欙拷9锟絓u0000\u0003锟�8锟�:璋睼婧╁洔锟絓u0000濠氼墛锟�\u0015绺氾拷7瑭峰矔鐝檈锟�33\u001D锟�6闉嬭潧w\u001D&鎹塿鎹活儫妗讹拷\u0005闅达拷\u001AR鎱旓拷\u0000鏉竆u0000\u0000\u0000\u0000\u0000\u0000\u0013\u0013\u001Dc閳筡u0000! \u0000\u0002kY绮筡u0012宥瞈u0019鏀楋拷\u0000鈺?銉板箒kH顎╡7娆廫n鑸冿拷#闉絓u00059铻綷u0000\u00111\u001Ep锟絓u0019[K鐠ㄦ娣�:锟�1\u0013\u0018鎲嶈W(浠�5锟�(鍟�1 \bH\u0000\u0000\u0000\u0000&'\u0013鐑�)Z闃�6鐑庨楱愮ˉ澧�)ih顩欏揩J鎾宎鎯わ拷\u0016閭5\u0005顥\u0000BS\u00116娣曟矗绁抽簱顔牆楂歌瑓i棰�:\u0000\n锟絓u0000\u0000\u0000\u0000\u0000\u000E{瑙�8锟�?s鐗佽樈鎯熷钘顏硄鐬匡拷\r顚昤\u0001\t\u0000\u0000\u0000\u0002#3寤㈤灝g\u001D+\u001C閹╃疾鏀哱"q*\u0000鍓玆7Z=濮疯鐭攎锟�&[\u0000锟絓u0003閹┾棊鏋盶u0001\u0014宕滎仺顭讹拷\"v鑵ｏ拷>绡宬鍒�/}婵�=铓痋u0003顕归€囬陡杓烻姝�3澶樻櫚鐕ч贰\u0015m3宥縒顧�1鐎伙拷\u0003閸�>3S鏈剮鐜盶u0007{}/锟�5}h棰勬尮qn锟�+[璇矲绐�绫у畠妤嶹\u0013N&姹戠懂i顨爉铦焮c浼氳懞}铓瀄u001E&姹嶆弽m锝橈拷:SR姹掗粚_婊嶾u0015娌�2娴"璨晆_[OON璋у簱锟絓u001D婊曪拷+顕ば愵攧\"绛岋拷7锟絓u0000鐞拌牋N\u0003瀣曡叞\u001FO瀚鈹楅敊47jS杩棗顗濇崏鍝旇皾閽擽\u0012姣u001D锟�<铇忎集鏄洪瓙x\u0013铦愨挃璨ョ惍\u000F?_锟絓u000E绌掓埠榉犳澅5铦禠~鐒奸摳G邪鍜嬬5瀵镐緵H锟�[槌�:锟絓/"q锟絓u00005锟�?鏇旈憨鍛硷拷='鍠插弸顑★拷*R榄丷鐮叉崅鏌噁#c绐玤tG锟�-\u0018锟絓u0002娈ㄨ殯缃�$锟�&c瑗〕I閸奬u00044\u0000\u0000\u0000?灏禐鏄革拷\u001DX锟�/h瀹�*铇堣槇/zDG/楫u0002c鏀曟嚔妤涚櫩锟�<屑y锟�\u000BP\u0012鑷熻Х8姘у韫籠u0016濠癋2\u0000鏃瞈u0001\u0013\u0011h娑呰笇鈺夐眻锟絓u000ES锟�9妯篭u0012t寮氾拷\u0002\u0000\u0000s_鑸榹銉媬v鑺砠婕瀄u0000*锟絓u0000\u0000\u0000\u0004閳楋拷)娓�(@c妞忣槶\u0005缈炊鎶�<婕涘垁鏌橈拷\u0016K鐓犻嚘\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00113鍨袱顒�-楂滆硱鍠嗗瓗@\u0010锟絓u0000\u0000\u0001鐡俁&鐫撴瑦娅杅c璧橽f鈷俓u001D/'顎崇硠鐑玣g璐�,锟�&&f\u001Ab&鐪昏潏\u0013,:}锟�7S锟�+N=鐚宐g鏀冿拷&绠烜\b]5绮梋\u0015纰�3\u001F\u00071\u001319蹇旓拷-\\楂鳖唸锟�/6铚穃u001C楣乧願栧粩鑶淺u0000\u0000\u0000\u0000\u0000妾�9\u0001K閬g锟�+mO锝戯拷=~锟�1z瑗ｆ篂g锟絓u0002鐡圽u0000\u0000\u0000\u0000g}M=8澹寸禒\u00138宓佃皹楱窽鍛€妞愩€╂-鍨�>宓嶉ぎi锟�/顧犻€烘暥纰ョ剺8锟�.\u001A妗ｉ珕锟絓n锟絓u001F鎷ョ暜锟�5缍︺帯\r锟ワ拷&&妗擠榻℃實M鈷撻帲棰涚攧绮拷=O\t锟�:<>鏋卞惢鑸拷/6娆瀘鑶掔矠缂�=妞庯拷\u0000c顗拷\u0001顥橽u0000\u001Ai钘捐樆楝鹃媰/h绗玒璺嶈湬锟絓tg\u0013ku寤筋悎濉キ锟絓u0019鐑典拷\u0001鏀桯uE纭ヨ嵑r姒瑒绺额厔h鑴擄拷/N绐n锟絓u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0007,楗�?k]n锟絓/"榀樿憴鑸婅暪O\u000B锟絓u000EQ锟絓u00136褰撶皡m锟絓u0002\u0000\u0000_N绡價+I鍧婅涓€c\u001E鑼疜;Z\"1\u001DV\u0001,淇橽u0001\u0019韬�3闇‐u001C锟�>顫庡鍏艰彆鎷〣锟�*锟絓u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001瑕甿瀵塀铦€杈濷绲ョ柅娌摐灞箃\f+鈺嗭拷-鐎瞊濞锟�,\"3锟�?d$\u0000V璎矘GP锟�=\u000BN#.i锟�9楫栬瓈O鐩�6鐠硅澂锟�!p\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001Ai锟�#妞€妲ュǔ7F\u001D[锛礬u0013z顛媆u001C鑱勵湱锟�+鎽卬婀ュ懚T\u00044鍨愬瓐\u0002\u0012\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000锟斤拷";
		// return str.getBytes();
	 }
	 
	 @GET
     @Path("/getPictureById/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public JSONArray getPictureById(@PathParam("uid") int uid){
		 System.out.println("getPictureById");
		 System.out.println(uid);
		 JSONObject jsonObject = new JSONObject();
		 JSONArray jsonArray = new JSONArray();
		 //String string = new String(userpicService.getUserPicById(uid).getPic());
		byte[] bs = userpicService.getUserPicById(uid).getPic();
		 jsonObject.put("pic", bs);
		 jsonObject.put("uid", uid);
		 jsonObject.put("picname", "23333");
		 jsonArray.add(jsonObject);
		 
		 //System.out.println(string);
		 
		 return jsonArray;
		 //String string = new String();
		 //return userpicService.getUserPicById(uid);
		 
		
		 
	 }
	 
	 @GET
     @Path("/getPic/{uid}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Userpic getPic(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("uid") int uid){
		 System.out.println("getPictureById");
		 System.out.println(uid);
		 JSONObject jsonObject = new JSONObject();
		 //String string = new String(userpicService.getUserPicById(uid).getPic());
		// jsonObject.put("pic", string);
		// jsonObject.put("uid", uid);
		// jsonObject.put("picname", null);
		 return userpicService.getUserPicById(uid);
		 //String string = new String();
		 //return userpicService.getUserPicById(uid);
		 
	 }
	 
	 @GET
     @Path("/testPicture/{uid}")
	 @Produces(MediaType.APPLICATION_OCTET_STREAM)
	 public byte[] testPicture(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("uid") int uid){
		 //int uid = 1;
		 System.out.println("getpicture");
		 System.out.println(uid);

		 
		 response.setContentType("application/octet-stream");
		 response.addHeader("Content-Disposition", "attachment; filename=\"acc.jpg\"");
		// return userpicService.getPicById(uid);
		 String str = "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";
		 return ByteUtil.StringToByte(str) ;
	 }
	 
	 
	 @POST
	 @Path("/saveUserPicture/{uid}")
	 @Consumes(MediaType.APPLICATION_JSON)
	 public void saveUserPicture(JSONObject jsonpic, @PathParam("uid") int uid){
		 //int uid = 1;
		 System.out.println("saveUserPicture");
		 System.out.println(uid);

		 byte[] userpic = (byte[])jsonpic.get("pic");
		 System.out.println(userpic);

		 userpicService.usersave(userpic, uid);
	 }
	 

}