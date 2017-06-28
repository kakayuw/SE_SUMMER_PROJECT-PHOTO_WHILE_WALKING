package action;

import model.Orderitem;
import service.AppService;
import model.Book;

public class UpdateOrderitemAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private int id;
	private int orderid;
	private int bookid;
	private int amount;
	private double price;

	private AppService appService;

	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {

		Book the_book = appService.getBookById(price);
		
		this.price = the_book.getPrice();
		
	}	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	@Override
	public String execute() throws Exception {

		Orderitem orderitem = appService.getOrderitemById(id);
		orderitem.setOrderid(orderid);
		orderitem.setBookid(bookid);
		orderitem.setAmount(amount);
		orderitem.setPrice(price);
		appService.updateOrderitem(orderitem);

		return SUCCESS;
	}

}
