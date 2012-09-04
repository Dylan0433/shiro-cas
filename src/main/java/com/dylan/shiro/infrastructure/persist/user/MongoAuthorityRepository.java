package com.dylan.shiro.infrastructure.persist.user;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.dylan.shiro.domain.Authority;
import com.dylan.shiro.domain.AuthorityRepository;
import com.dylan.shiro.infrastructure.persist.CrudMongoRepositorySupport;
import com.google.common.collect.Lists;
import com.youboy.core.orm.Page;

/**
 * @author LHacker
 *
 */
@Repository
public class MongoAuthorityRepository extends CrudMongoRepositorySupport<Authority,ObjectId> implements AuthorityRepository {

	@Autowired
	protected MongoAuthorityRepository(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	@Override
	public List<Authority> findAll() {
		return getMongoOperations().findAll(Authority.class);
	}

	@Override
	public void update(Authority entity) {
		delete(entity);
		save(entity);
	}

	@Override
	public void delete(Authority entity) {
		getMongoOperations().remove(entity);
	}

	@Override
	public Authority save(Authority entity) {
		getMongoOperations().save(entity);
		return entity;
		
	}

	@Override
	public Page<Authority> getPage(Page<Authority> page) {
		return findPage(page);
	}

	@Override
	public List<Authority> getAuthoritiesById(String[] ids) {
		List<Authority> Authorities = Lists.newArrayList();
		for(String id : ids){
			Authorities.add(findOne(new ObjectId(id)));
			
		}
		return Authorities;
	}

	@Override
	public boolean existsByName(String name) {
		Authority authority = getMongoOperations().findOne(new Query(Criteria.where("name").is(name)), Authority.class);
		return null != authority ? true : false;
	}

	
}
