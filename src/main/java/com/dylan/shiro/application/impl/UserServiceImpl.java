package com.dylan.shiro.application.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dylan.shiro.application.UserService;
import com.dylan.shiro.domain.User;
import com.dylan.shiro.infrastructure.persist.user.MongoUserRepository;
import com.youboy.core.orm.Page;
/**
 * @author LHacker
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MongoUserRepository mongoUserRepository;
	@Override
	public User queryUniqueByUsername(String username) {
		return mongoUserRepository.queryUniqueByUsername(username);
	}

	@Override
	public User save(User user) {
		return mongoUserRepository.save(user);
	}

	@Override
	public boolean existsByUsername(String username) {
		return mongoUserRepository.existsByUsername(username);
	}

	@Override
	public void updatePassword(String username, String newPassword) {
		mongoUserRepository.updatePassword(username, newPassword);
	}

	@Override
	public Page<User> getPage(Page<User> page) {
		return mongoUserRepository.getPage(page);
	}

	@Override
	public User findOne(ObjectId id) {
		return mongoUserRepository.findOne(id);
	}

	@Override
	public void update(User user) {
		mongoUserRepository.update(user);
	}

	@Override
	public void delete(ObjectId id) {
		mongoUserRepository.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.youboy.vs.application.user.UserService#getByName(java.lang.String)
	 */
	@Override
	public User getByName(String username) {
		return mongoUserRepository.getByName(username);
	}

}
