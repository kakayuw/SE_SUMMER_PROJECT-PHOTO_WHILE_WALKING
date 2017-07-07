package dao.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import dao.UserpicDao;
import model.Userpic;

public class UserpicDaoImpl implements UserpicDao {

	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void save(Userpic userpic) {
		mongoTemplate.save(userpic);
	}

	@Override
	public void delete(Userpic userpic) {
		mongoTemplate.remove(userpic);
	}

	@Override
	public void update(Userpic userpic) {
		Query query = Query.query(Criteria.where("uid").is(userpic.getUid()));
		Update update = Update.update("pic", userpic.getPic());
		mongoTemplate.updateFirst(query, update, Userpic.class);
	}

	@Override
	public Userpic getUserpicById(int uid) {
		return mongoTemplate.findOne(Query.query(Criteria.where("uid").is(uid)), Userpic.class);
	}

}
