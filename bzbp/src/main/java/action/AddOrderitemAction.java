package action;
import service.AppService;
import model.Book;
import model.Orderitem;
//import model.Book;

public class AddOrderitemAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private int orderid;
	private int bookid;
	private int amount;
	private double price;

	private AppService appService;

	
	public double getPrice() {
		return price;
	}

	public void setPrice(int id) {

		Book the_book = appService.getBookById(id);
		
		this.price = the_book.getPrice();
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

		Orderitem orderitem = new Orderitem(orderid, bookid, amount,price);
		appService.addOrderitem(orderitem);

		return SUCCESS;
	}

}
