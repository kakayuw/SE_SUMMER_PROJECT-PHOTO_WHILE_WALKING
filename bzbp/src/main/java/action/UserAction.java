package action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import model.User;
import service.AppService;

public class UserAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;

	private AppService appService;
	private User usr;
	private Map<String, Object> userSession;

	/*
	 * service setter
	 */

	public void setUserService(AppService appService) {
		this.appService = appService;
	}

	/*
	 * session setter
	 */

	public void setSession(Map<String, Object> session) {
		userSession = session;
	}

	/*
	 * usr setter & getter
	 */
	public void setUsr(User user) {
		this.usr = user;
	}

	public User getUsr() {
		return usr;
	}

	/*
	 * methods
	 */
}
