package action;

import java.io.IOException;
import java.util.List;

import javax.swing.plaf.synth.SynthTextAreaUI;

import model.ShareItem;
import net.sf.json.JSONArray;
import service.ShareItemService;

public class BestshareAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private ShareItemService shareItemService;
	private String sid;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

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
		ShareItem shareItem = shareItemService.getBest();
		request().setAttribute("bestShareItem", shareItem);
		return "success";
	}
	
	public String changeBest(){  
		shareItemService.changeBest(sid);
		return "success";
	}

}
