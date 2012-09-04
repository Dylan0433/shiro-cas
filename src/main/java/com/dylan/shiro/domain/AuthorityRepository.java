package com.dylan.shiro.domain;

import java.util.List;

import org.bson.types.ObjectId;

import com.youboy.core.orm.Page;

/**
 * @author LHacker
 *
 */
public interface AuthorityRepository {
	/**
	 * @param id
	 * @return
	 */
	Authority findOne(ObjectId id);
	/**
	 * @return
	 */
	List<Authority> findAll();
	/**
	 * @param entity
	 */
	void update(Authority entity);
	/**
	 * @param entity
	 */
	void delete(Authority entity);
	/**
	 * @param id
	 */
	void delete(ObjectId id);
	/**
	 * @param entity
	 * @return
	 */
	Authority save(Authority entity);
	/**
	 * @param page
	 * @return
	 */
	Page<Authority> getPage(Page<Authority> page);
	/**
	 * @param ids
	 * @return
	 */
	List<Authority> getAuthoritiesById(String [] ids);
	/**
	 * @param name
	 * @return
	 */
	boolean existsByName(String name);
}
