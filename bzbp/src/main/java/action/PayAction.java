package action;

import service.AppService;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Map;  
import com.opensymphony.xwork2.ActionContext;  
import com.opensymphony.xwork2.ActionSupport;

import model.Book;
import model.Order;
import model.Orderitem;
import model.User;  

public class PayAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private InputStream inputStream;
	private Date date;
	private AppService appService;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public String execute() throws Exception {
		ActionContext actionContext = ActionContext.getContext();  
		Map<String, Object> session = actionContext.getSession();  
		String username = (String)session.get("username");
		User the_user = appService.getUserByUsername(username);
		int the_userid = the_user.getId();		
		
		Order order = new Order(the_userid, date);
		int the_orderid =appService.addOrder(order);
	 	List<Integer> session_bookid=(List<Integer>)session.get("bookid"); 
	 	List<Double> session_price=(List<Double>)session.get("price"); 
	 	List<Double> session_amount=(List<Double>)session.get("amount"); 
		
	 	int the_bookid = 0;
	 	double the_price = 0;
	 	double the_amount = 0;
	 	Orderitem orderitem;
	 	if (session_bookid != null){
		 	for (int i = 0; i < session_bookid.size(); i++){
		 		the_bookid = session_bookid.get(i);
		 		the_price = session_price.get(i);
		 		the_amount = session_amount.get(i);
				orderitem = new Orderitem(the_orderid, the_bookid, (int)the_amount, the_price);
				appService.addOrderitem(orderitem);
		 	}
	 	}
	 	session.clear();
		actionContext = ActionContext.getContext();  
		Map<String, Object> session2 = actionContext.getSession();  
	 	session2.put("username", username);
	 	session2.put("role", "Customer");
		
		return SUCCESS;
	}


}
