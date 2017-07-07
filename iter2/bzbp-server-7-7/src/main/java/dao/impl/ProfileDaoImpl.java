package dao.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.sun.swing.internal.plaf.metal.resources.metal;

import java.util.ArrayList;
import java.util.List;
import dao.ProfileDao;
import model.Profile;

public class ProfileDaoImpl implements ProfileDao {
	private MongoTemplate mongoTemplate;
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public void save(Profile profile) {
		mongoTemplate.save(profile);
	}

	@Override
	public void delete(Profile profile) {
		mongoTemplate.remove(profile);
	}

	@Override
	public Profile getProfileByUid(int uid) {
		return mongoTemplate.findOne(Query.query(Criteria.where("uid").is(uid)), Profile.class);
	}

	@Override
	public List<Profile> getAll(){
		List<Profile> profiles = new ArrayList<Profile>();
		profiles = mongoTemplate.findAll(Profile.class);
		return profiles;
	}
	
	@Override
	public void update(Profile profile) {
		mongoTemplate.updateFirst(Query.query(Criteria.where("uid").is(profile.getUid())),
				 Update.update("intro",profile.getIntro()).update("autograph", profile.getAutograph()),
				 Profile.class);
	}

}
