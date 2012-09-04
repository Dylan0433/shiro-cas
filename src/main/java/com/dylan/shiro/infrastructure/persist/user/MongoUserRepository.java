package com.dylan.shiro.infrastructure.persist.user;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.dylan.shiro.domain.User;
import com.dylan.shiro.domain.UserRepository;
import com.dylan.shiro.infrastructure.persist.CrudMongoRepositorySupport;
import com.youboy.core.orm.Page;

/**
 * @author LHacker
 *
 */
@Repository
public class MongoUserRepository extends CrudMongoRepositorySupport<User,ObjectId> implements UserRepository {

	@Autowired
	protected MongoUserRepository(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	@Override
	public User queryUniqueByUsername(String username) {
		return getMongoOperations().findOne(new Query(Criteria.where("username").is(username)), User.class);
	}

	@Override
	public boolean existsByUsername(String username) {
		User user = getMongoOperations().findOne(new Query(Criteria.where("username").is(username)), User.class);
		return null != user ? true : false;
	}

	@Override
	public void updatePassword(String username, String newPassword) {
		getMongoOperations().updateFirst(new Query(Criteria.where("username").is(username)), Update.update("password", newPassword), User.class);
	}

	@Override
	public Page<User> getPage(Page<User> page) {
		return findPage(page);
	}

	@Override
	public void update(User user) {
		getMongoOperations().remove(new Query(Criteria.where("id").is(user.getId())), User.class);
		save(user);
	}

	/**
	 * @param username
	 * @return
	 */
	public User getByName(String username) {
		return getMongoOperations().findOne(new Query(Criteria.where("username").is(username)), User.class);
	}
	
}
