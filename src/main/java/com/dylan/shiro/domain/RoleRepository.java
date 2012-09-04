package com.dylan.shiro.domain;

import java.util.List;

import org.bson.types.ObjectId;

import com.youboy.core.orm.Page;

/**
 * 
 * @author LHacker
 *
 */
public interface RoleRepository {
	/**
	 * @param id
	 * @return
	 */
	Role get(ObjectId id);
	/**
	 * @param name
	 * @return
	 */
	Role getByName(String name);
	/**
	 * @return
	 */
	List<Role> findAll();
	/**
	 * @param entity
	 */
	void update(Role entity);
	/**
	 * @param entity
	 */
	void delete(Role entity);
	/**
	 * @param id
	 */
	void delete(ObjectId id);
	/**
	 * @param entity
	 * @return
	 */
	Role save(Role entity);
	/**
	 * @param page
	 * @return
	 */
	Page<Role> getPage(Page<Role> page);
	/**
	 * @param id
	 * @return
	 */
	List<Role> getRolesById(String [] id);
	
	boolean existsByName(String name);

}
