package service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import dao.ShareRouteDao;
import service.RouteService;


public class RouteServiceImpl implements RouteService {
	
	private ShareRouteDao shareRouteDao;

	public ShareRouteDao getShareRouteDao() {
		return shareRouteDao;
	}

	public void setShareRouteDao(ShareRouteDao shareRouteDao) {
		this.shareRouteDao = shareRouteDao;
	}
	
	@Override
	public String getShareRoute(String id) {
		File file = new File("C:/Users/dell/git/BookStoreDemoWebService/src/main/webapp/bzbp/file/getjson.txt");
		try {
			shareRouteDao.getRoute(id).writeTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileToJson("C:/Users/dell/git/BookStoreDemoWebService/src/main/webapp/bzbp/file/getjson.txt");
	}

	@Override
	public void addShareRoute(String id, String routedetail) {
		shareRouteDao.saveRoute(id, routedetail);
	}


	   private String fileToJson(String path) {
	        BufferedReader reader = null;
	        String laststr = "";
	        try {
	            FileInputStream fileInputStream = new FileInputStream(path);
	            InputStreamReader inputStreamReader = new InputStreamReader(
	                    fileInputStream, "utf-8");
	            reader = new BufferedReader(inputStreamReader);
	            String tempString = null;
	            while ((tempString = reader.readLine()) != null) {
	                laststr += tempString;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return laststr;
	    }



}
