package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Date;

import model.Bookdetail;
import service.AppService;

public class AddBookdetailAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	

	private AppService appService;
	private InputStream inputStream;

    public InputStream getInputStream() {  
        return inputStream;  
    }  
  
    public void setInputStream(InputStream inputStream) {  
        this.inputStream = inputStream;  
    }  
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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


	@Override
	public String execute() throws Exception {

		Bookdetail bookdetail = new Bookdetail(id,title);
		inputStream = new ByteArrayInputStream(Integer.toString(id) 
                .getBytes("UTF-8")); 
		appService.addBookdetail(bookdetail);
		Bookdetail bookdet= appService.getBookdetailById(id);
		inputStream = new ByteArrayInputStream(bookdet.getDetail()
                .getBytes("UTF-8")); 
		return "success";
	}

}