package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import model.Bookdetail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.AppService;


public class GetBookdetailAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private int id;
	private String result;
	
	private AppService appService;
	private InputStream inputStream;

    public InputStream getInputStream() {  
        return inputStream;  
    }  
  
    public void setInputStream(InputStream inputStream) {  
        this.inputStream = inputStream;  
    }  
	
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@Override
	public String execute() throws Exception {

		Bookdetail bookdet= appService.getBookdetailById(id);
		String book_detail = bookdet.getDetail();
	//	request().setAttribute("bookdetail", book_detail);
	   	inputStream = new ByteArrayInputStream(book_detail
                .getBytes("UTF-8")); 	
	//   	ActionContext actionContext = ActionContext.getContext();  
	//	Map<String, Object> session = actionContext.getSession();  
	//	session.put("bookdetail", book_detail);
	   	
	 //  	JSONObject jsonObject = new JSONObject();   
	 //  	jsonObject.put("bookdetail", book_detail);
	  // 	JSONArray jsonArray = new JSONArray(); 
	 //  	jsonArray.add(0,book_detail);
	 //  	jarray = jsonArray;
	 //  	JSONArray.fromObject(String)
	   	Map<String,String> map = new HashMap<String,String>();
	   	map.put("bookdetail", book_detail);
	   	JSONObject json = JSONObject.fromObject(map);
	   	result = json.toString();
	   	return SUCCESS;
	}






}