package com.dylan.shiro.application.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dylan.shiro.application.RoleService;
import com.dylan.shiro.domain.Role;
import com.dylan.shiro.infrastructure.persist.user.MongoRoleRepository;
import com.youboy.core.orm.Page;
/**
 * @author LHacker
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private MongoRoleRepository mongoRoleRepository;
	
	@Override
	public Role get(ObjectId id) {
		return mongoRoleRepository.get(id);
	}

	@Override
	public Role getByName(String name) {
		return mongoRoleRepository.getByName(name);
	}

	@Override
	public List<Role> findAll() {
		return mongoRoleRepository.findAll();
	}

	@Override
	public void update(Role entity) {
		mongoRoleRepository.update(entity);
	}

	@Override
	public void delete(Role entity) {
		mongoRoleRepository.delete(entity);
	}

	@Override
	public Role save(Role entity) {
		return mongoRoleRepository.save(entity);
	}

	@Override
	public Page<Role> getPage(Page<Role> page) {
		return mongoRoleRepository.findPage(page);
	}

	@Override
	public List<Role> getRolesById(String[] id) {
		return mongoRoleRepository.getRolesById(id);
	}

	@Override
	public void delete(ObjectId id) {
		mongoRoleRepository.delete(id);
	}

	@Override
	public boolean existsByName(String name) {
		return mongoRoleRepository.existsByName(name);
	}

}
