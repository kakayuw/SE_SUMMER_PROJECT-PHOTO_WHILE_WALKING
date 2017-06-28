package action;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import service.AppService;


public class AddtoShoppingcartAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private AppService appService;
	private int bookid;
	private double price;
	private double amount;
	private String title;
	
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	
	
	@Override
	public String execute() throws Exception {

		ActionContext actionContext = ActionContext.getContext();  
		Map<String, Object> session = actionContext.getSession();  
		List<Integer> temp_bookid;
		List<Double> temp_price;
		List<Double> temp_amount;
		List<String> temp_title;
		if (session.get("bookid") != null){
			temp_bookid = (List<Integer>)session.get("bookid");
			temp_bookid.add(bookid);
		}
		else{
			temp_bookid = new LinkedList<Integer>();
			temp_bookid.add(bookid);
		}
		
		if (session.get("price") != null){
			temp_price = (List<Double>)session.get("price");
			temp_price.add(price);
		}
		else{
			temp_price = new ArrayList<Double>();
			temp_price.add(price);
		}
		
		if (session.get("amount") != null){
			temp_amount = (List<Double>)session.get("amount");
			temp_amount.add(amount);
		}
		else{
			temp_amount = new ArrayList<Double>();
			temp_amount.add(amount);
		}		
		
		if (session.get("title") != null){
			temp_title = (List<String>)session.get("title");
			temp_title.add(title);
		}
		else{
			temp_title = new ArrayList<String>();
			temp_title.add(title);
		}		
		
		session.put("bookid", temp_bookid);
		session.put("price", temp_price);
		session.put("amount", temp_amount);
		session.put("title", temp_title);

		return SUCCESS;
	}


}
