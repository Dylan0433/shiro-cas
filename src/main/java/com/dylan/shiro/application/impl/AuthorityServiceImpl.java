package com.dylan.shiro.application.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dylan.shiro.application.AuthorityService;
import com.dylan.shiro.domain.Authority;
import com.dylan.shiro.infrastructure.persist.user.MongoAuthorityRepository;
import com.youboy.core.orm.Page;
/**
 * @author LHacker
 *
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private MongoAuthorityRepository mongoAuthorityRepository;
	
	@Override
	public void update(Authority entity) {
		mongoAuthorityRepository.update(entity);
	}

	@Override
	public void delete(Authority entity) {
		mongoAuthorityRepository.delete(entity);
	}

	@Override
	public Authority save(Authority entity) {
		return mongoAuthorityRepository.save(entity);
	}

	@Override
	public Authority findOne(ObjectId id) {
		return mongoAuthorityRepository.findOne(id);
	}

	@Override
	public List<Authority> findAll() {
		return mongoAuthorityRepository.findAll();
	}

	@Override
	public Page<Authority> getPage(Page<Authority> page) {
		return mongoAuthorityRepository.findPage(page);
	}

	@Override
	public List<Authority> getAuthoritiesById(String[] ids) {
		return mongoAuthorityRepository.getAuthoritiesById(ids);
	}

	@Override
	public void delete(ObjectId id) {
		mongoAuthorityRepository.delete(id);
	}

	@Override
	public boolean existsByName(String name) {
		return mongoAuthorityRepository.existsByName(name);
	}

}
