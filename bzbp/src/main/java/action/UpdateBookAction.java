package action;

import java.io.File;
import java.sql.Date;

import model.Book;
import service.AppService;

public class UpdateBookAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String author;
	private double price;
	private String publisher;
	private Date date;
	private String picture;

	private AppService appService;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	@Override
	public String execute() throws Exception {

		Book book = appService.getBookById(id);
		book.setTitle(title);
		book.setAuthor(author);
		book.setPrice(price);
		book.setPublisher(publisher);
		book.setDate(date);
		appService.updateBook(book);
		
		String src = picture;
		appService.addBookpicture(id, src);
	//	attachment.createNewFile();
	//	int w=1;
		return SUCCESS;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}


}
