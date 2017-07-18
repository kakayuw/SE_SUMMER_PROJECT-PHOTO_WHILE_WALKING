package dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import dao.RoutepicDao;
import model.Routepic;

public class RoutepicDaoImpl implements RoutepicDao {

	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void save(Routepic routepic) {
		mongoTemplate.save(routepic);
	}

	@Override
	public void delete(Routepic routepic) {
		mongoTemplate.remove(routepic);
	}

	@Override
	public void update(Routepic routepic) {
		Query query = Query.query(Criteria.where("pid").is(routepic.getPid()));
		Update update = Update.update("pic", routepic.getPic());
		mongoTemplate.updateFirst(query, update, Routepic.class);
	}

	@Override
	public Routepic getRoutepicById(String pid) {
		return mongoTemplate.findOne(Query.query(Criteria.where("pid").is(pid)), Routepic.class);
	}

	public List<Routepic> getRoutepicsById(String pid){
		return mongoTemplate.find(Query.query(Criteria.where("pid").is(pid)), Routepic.class);
	}
}
