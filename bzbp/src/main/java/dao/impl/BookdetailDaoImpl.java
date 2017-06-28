package dao.impl;

import java.util.List;
import dao.BookdetailDao;
import model.Bookdetail;
import service.AppService;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import dao.BookDao;


public class BookdetailDaoImpl implements BookdetailDao {

	private MongoTemplate mongoTemplate;
	
	public MongoTemplate getMongoTemplate(){
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public Bookdetail findById(int id) {
		return this.mongoTemplate.findById(id, Bookdetail.class);
	}

	public List<Bookdetail> findAll(Class<Bookdetail> entityClass) {
		return this.mongoTemplate.findAll(entityClass);
	}

	public void remove(Bookdetail bookdetail) {
		this.mongoTemplate.remove(bookdetail);
	}

	public void add(Bookdetail bookdetail) {
		this.mongoTemplate.insert(bookdetail);

	}

	public void saveOrUpdate(Bookdetail bookdetail) {
		this.mongoTemplate.save(bookdetail);
	}

	public void savePicture(int id, String source){
		String newFilename = Integer.toString(id);
		File imageFile = new File(source);
		DB db = mongoTemplate.getDb();  
		GridFS gfsPhoto = new GridFS(db,"photo");
		try {
			GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
			gfsFile.setFilename(newFilename);
			gfsFile.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
