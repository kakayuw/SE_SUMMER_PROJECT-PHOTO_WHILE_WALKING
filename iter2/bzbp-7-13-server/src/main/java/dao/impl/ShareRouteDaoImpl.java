package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import dao.ShareRouteDao;


public class ShareRouteDaoImpl  implements ShareRouteDao {
	
	private MongoTemplate mongoTemplate;
	final static String TAG = "Fileutil:";
	
	public MongoTemplate getMongoTemplate(){
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void saveRoute(String id, String routedetail) {
		String newFilename = id;
		//File imageFile = new File("D:/background.jpg");
		jsonToFile("C:/Users/dell/git/BookStoreDemoWebService/src/main/webapp/bzbp/file/json.txt", routedetail); 
		File routeFile = new File("C:/Users/dell/git/BookStoreDemoWebService/src/main/webapp/bzbp/file/json.txt");
		DB db = mongoTemplate.getDb();  
		GridFS gfsRoute = new GridFS(db,"route");
		try {
			GridFSInputFile gfsFile = gfsRoute.createFile(routeFile);
			gfsFile.setFilename(newFilename);
			gfsFile.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateRoute(String id, String routedetail) {
		String newFilename = id;
		//File imageFile = new File("D:/background.jpg");
		jsonToFile("C:/Users/dell/git/BookStoreDemoWebService/src/main/webapp/bzbp/file/json.txt", routedetail); 
		File routeFile = new File("C:/Users/dell/git/BookStoreDemoWebService/src/main/webapp/bzbp/file/json.txt");
		DB db = mongoTemplate.getDb();  
		GridFS gfsRoute = new GridFS(db,"route");
		try {
			try{
			gfsRoute.remove(gfsRoute.findOne(newFilename));}catch (Exception e) {
				// TODO: handle exception
			}
			GridFSInputFile gfsFile = gfsRoute.createFile(routeFile);	
			gfsFile.setFilename(newFilename);
			gfsFile.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public GridFSDBFile getRoute(String id) {
		// TODO Auto-generated method stub
        try {  
            DB db = mongoTemplate.getDb();  
            String the_Filename = id;
            // 获取fs的根节点  
            GridFS gridFS = new GridFS(db, "route");  
            GridFSDBFile dbfile = gridFS.findOne(the_Filename);  
            if (dbfile != null) {  
                return dbfile;  
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
        }  
        return null;  	}

	@Override
	public void deleteRoute(String id) {
		String Filename = id;

		DB db = mongoTemplate.getDb();  
		GridFS gfsPhoto = new GridFS(db,"route");
		try {
			
			gfsPhoto.remove(gfsPhoto.findOne(Filename));}catch (Exception e) {
			

		}		
	}

	
    private void jsonToFile(String pathname, String jsonString) {
        //判断文件是否存在
    	
        File file = new File(pathname);
        if (file.exists()) {
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();
//            Log.d(TAG, "JSON STORED LOCAL SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
