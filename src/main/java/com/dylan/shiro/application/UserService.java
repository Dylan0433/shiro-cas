package com.dylan.shiro.application;

import org.bson.types.ObjectId;

import com.dylan.shiro.domain.User;
import com.youboy.core.orm.Page;
/**
 * @author LHacker
 *
 */
public interface UserService {


	/**
	 * 
	 * @param username
	 * @return
	 */
	User queryUniqueByUsername(String username);

	/**
	 * 
	 * @param user
	 * @return
	 */
	User save(User user);

	/**
	 * 
	 * @param username
	 * @param email
	 * @return
	 */
	boolean existsByUsername(String username);

	/**
	 * 
	 * @param username
	 * @param newPassword
	 */
	void updatePassword(String username, String newPassword);
	/**
	 * @param page
	 * @return
	 */
	Page<User> getPage(Page<User> page);
	/**
	 * @param id
	 * @return
	 */
	User findOne(ObjectId id);
	/**
	 * @param user
	 */
	void update(User user);
	/**
	 * @param id
	 */
	void delete(ObjectId id);

	/**
	 * @param username
	 * @return
	 */
	User getByName(String username);
	
	
}
