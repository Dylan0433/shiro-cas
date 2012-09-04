package com.dylan.shiro.infrastructure.persist.user;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.dylan.shiro.domain.Role;
import com.dylan.shiro.domain.RoleRepository;
import com.dylan.shiro.infrastructure.persist.CrudMongoRepositorySupport;
import com.google.common.collect.Lists;
import com.youboy.core.orm.Page;

/**
 * @author LHacker
 *
 */
@Repository
public class MongoRoleRepository extends CrudMongoRepositorySupport<Role, ObjectId> implements RoleRepository {

	@Autowired
	protected MongoRoleRepository(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	@Override
	public List<Role> findAll() {
		return getMongoOperations().findAll(Role.class);
	}


	@Override
	public void update(Role entity) {
		delete(entity);
		save(entity);
	}

	@Override
	public void delete(Role entity) {
		getMongoOperations().remove(entity);
	}

	@Override
	public Role save(Role entity) {
		getMongoOperations().save(entity);
		return entity;
		
	}

	@Override
	public Role get(ObjectId id) {
		
		return getMongoOperations().findById(id, Role.class);
	}

	@Override
	public Role getByName(String name) {
		return getMongoOperations().findOne(new Query(Criteria.where("name").is(name)), Role.class);
	}

	@Override
	public Page<Role> getPage(Page<Role> page) {
		return findPage(page);
	}

	@Override
	public List<Role> getRolesById(String [] id) {
		List <Role> roles = Lists.newArrayList();
		if(null != id){
			for(int i = 0;i < id.length;i++){
				roles.add(get(new ObjectId(id[i])));
			}
		}
		return roles;
	}

	@Override
	public boolean existsByName(String name) {
		Role role = getMongoOperations().findOne(new Query(Criteria.where("name").is(name)), Role.class);
		return null != role ? true : false;
	}

}
