package com.dylan.shiro.application;

import java.util.List;

import org.bson.types.ObjectId;

import com.dylan.shiro.domain.Role;
import com.youboy.core.orm.Page;
/**
 * @author LHacker
 *
 */
public interface RoleService {
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
	/**
	 * @param name
	 * @return
	 */
	boolean existsByName(String name);
}
