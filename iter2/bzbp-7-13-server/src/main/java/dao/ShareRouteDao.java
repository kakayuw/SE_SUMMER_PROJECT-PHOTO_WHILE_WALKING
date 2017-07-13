package dao;

import java.util.List;

import com.mongodb.gridfs.GridFSDBFile;

import model.Admin;
import net.sf.json.JSONObject;

public interface ShareRouteDao {

	public void saveRoute(String id, String routedetail);
	
	public void updateRoute(String id, String routedetail);
	
	public GridFSDBFile getRoute(String id);
	
	public void deleteRoute(String id);

}
