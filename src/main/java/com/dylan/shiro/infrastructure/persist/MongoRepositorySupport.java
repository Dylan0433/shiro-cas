package com.dylan.shiro.infrastructure.persist;

import org.springframework.data.mongodb.core.MongoOperations;

import com.youboy.util.AssertUtils;

/**
 * 
 * @author loudyn
 * 
 */
public abstract class MongoRepositorySupport {

	private final MongoOperations mongoOperations;

	/**
	 * 
	 * @param mongoOperations
	 */
	
	
	protected MongoRepositorySupport(MongoOperations mongoOperations) {
		AssertUtils.notNull(mongoOperations);
		this.mongoOperations = mongoOperations;
	}

	/**
	 * 
	 * @return
	 */
	protected final MongoOperations getMongoOperations() {
		return mongoOperations;
	}
	
}
