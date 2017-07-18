package service;

import java.io.File;

/**
 * 
 * 
 */
public interface RouteService {

	public String getShareRoute(String id);
	
	public void addShareRoute(String id, String routedetail);
	
	public File getShareRoutePic(String id);
	
	public void addShareRoutePic(String id, File pic);

}
