package action;

import java.io.IOException;
import java.util.List;

import model.ShareItem;
import net.sf.json.JSONArray;
import service.ShareItemService;

public class BestshareAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private ShareItemService shareItemService;
	

	public ShareItemService getShareItemService() {
		return shareItemService;
	}

	public void setShareItemService(ShareItemService shareItemService) {
		this.shareItemService = shareItemService;
	}

	
	public void getTopTen(){    
		List<ShareItem> shareItems = shareItemService.getTopNumber(3);
		try {
			JSONArray jsonArray = JSONArray.fromObject(shareItems);
			response().setCharacterEncoding("utf-8");
			response().getWriter().println(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getBest(){    

		return "success";
	}

}
