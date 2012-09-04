package com.dylan.shiro.infrastructure.persist;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Sort;
import org.springframework.data.mongodb.repository.query.EntityInformationCreator;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.DefaultEntityInformationCreator;
import org.springframework.data.repository.CrudRepository;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.youboy.core.orm.Page;
import com.youboy.util.AssertUtils;
import com.youboy.util.ReflectionUtils;

/**
 * 
 * @author loudyn
 * 
 */
public abstract class CrudMongoRepositorySupport<T, ID extends Serializable> extends MongoRepositorySupport implements CrudRepository<T, ID> {

	private static final Query EMPTY_QUERY = new Query();
	private final MongoEntityInformation<T, ID> entityInformation;

	/**
	 * 
	 * @param mongoOperations
	 */
	@SuppressWarnings("unchecked")
	protected CrudMongoRepositorySupport(MongoOperations mongoOperations) {
		super(mongoOperations);

		EntityInformationCreator creator = new DefaultEntityInformationCreator(mongoOperations.getConverter().getMappingContext());
		this.entityInformation = (MongoEntityInformation<T, ID>) creator.getEntityInformation(ReflectionUtils.getSuperClassGenricType(getClass()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
	 */
	@Override
	public T save(T entity) {
		AssertUtils.notNull(entity, "Entity must not be null!");
		getMongoOperations().save(entity, entityInformation.getCollectionName());
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
	 */
	@Override
	public Iterable<T> save(Iterable<? extends T> entities) {

		AssertUtils.notNull(entities);

		List<T> result = Lists.newArrayList();
		for (T entity : entities) {
			getMongoOperations().save(entity, entityInformation.getCollectionName());
			result.add(entity);
		}

		return result;
	}

	/**
	 * 
	 * @param entities
	 * @return
	 */
	public Iterable<T> lazySave(Iterable<? extends T> entities) {

		AssertUtils.notNull(entities);

		return Iterables.transform(entities, new Function<T, T>() {

			@Override
			public T apply(T input) {
				getMongoOperations().save(input, entityInformation.getCollectionName());
				return input;
			}

		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
	 */
	@Override
	public T findOne(ID id) {
		AssertUtils.notNull(id, "Entity ID must not be null!");
		return getMongoOperations().findById(id, entityInformation.getJavaType(), entityInformation.getCollectionName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)
	 */
	@Override
	public boolean exists(ID id) {
		AssertUtils.notNull(id, "Entity ID must not be null!");
		return null != getMongoOperations().findOne(new Query(Criteria.where("_id").is(id)), Object.class, entityInformation.getCollectionName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#findAll()
	 */
	@Override
	public Iterable<T> findAll() {
		throw new UnsupportedOperationException("Never use this query method!");
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	public Page<T> findPage(Page<T> page) {

		AssertUtils.notNull(page, "Page must not be null");

		List<T> result = getMongoOperations().find(
													applyPagination(applyTotalCount(page)),
													entityInformation.getJavaType(),
													entityInformation.getCollectionName()
												);

		if (isEmptyResult(result)) {
			return page.setResult(Collections.<T> emptyList());
		}

		return page.setResult(result);
	}

	private Page<T> applyTotalCount(Page<T> page) {
		long totalCount = count(transformQueryObject(page));
		return page.setTotalCount(totalCount);
	}

	protected long count(DBObject query) {
		return getMongoOperations().getCollection(entityInformation.getCollectionName()).count(query);
	}

	private Query applyPagination(final Page<T> page) {

		Query query = new Query() {

			@Override
			public DBObject getQueryObject() {
				return transformQueryObject(page);
			}
		};

		return applySorting(query.skip(page.getFirst() - 1).limit(page.getPageSize()), page);
	}

	private DBObject transformQueryObject(Page<T> page) {

		Map<String, Object> params = page.getParams();

		DBObject query = new BasicDBObject();
		for (Entry<String, Object> entry : params.entrySet()) {

			if (isNullEntryValue(entry)) {
				continue;
			}

			if (isPrimitiveOrNotEmptyStringEntryValue(entry) || isObjectIdEntryValue(entry)) {
				query.put(entry.getKey(), entry.getValue());
				continue;
			}

			if (isNotEmptyMapEntryValue(entry)) {
				filterAndPopulate(query, entry);
				continue;
			}

		}

		return query;
	}

	private boolean isNullEntryValue(Entry<String, Object> entry) {
		return null == entry.getValue();
	}

	private boolean isPrimitiveOrNotEmptyStringEntryValue(Entry<String, Object> entry) {
		return isPrimitiveOrString(entry.getValue()) && StringUtils.isNotBlank(entry.getValue().toString());
	}

	private boolean isObjectIdEntryValue(Entry<String, Object> entry) {
		return entry.getValue() instanceof ObjectId;
	}

	@SuppressWarnings("unchecked")
	private boolean isNotEmptyMapEntryValue(Entry<String, Object> entry) {
		return Map.class.isAssignableFrom(entry.getValue().getClass()) && ((Map<String, Object>) entry.getValue()).isEmpty();
	}

	@SuppressWarnings("unchecked")
	private void filterAndPopulate(DBObject query, Entry<String, Object> entry) {
		Map<?, Object> value = (Map<?, Object>) entry.getValue();
		query.put(entry.getKey(), Maps.filterValues(value, new Predicate<Object>() {

			@Override
			public boolean apply(Object input) {

				if (isPrimitiveOrString(input)) {
					return StringUtils.isNotBlank(input.toString());
				}

				return false;
			}
		}));
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	protected boolean isPrimitiveOrString(Object input) {
		if (null == input) {
			return false;
		}
		if(input.getClass().isPrimitive()){
			return true;
		}
		
		if(input instanceof String){
			return true;
		}

		Class<?> klass = input.getClass();
		return klass == Boolean.class || klass == Integer.class || klass == Long.class 
							|| klass == Float.class || klass == Byte.class || klass == Double.class;
	}

	private Query applySorting(Query query, Page<T> page) {

		// if not orderBy set,return as soon as possiable
		if (!page.isOrderBySetted()) {
			return query;
		}

		String[] orderBys = StringUtils.split(page.getOrderBy(), ",");
		String[] orders = StringUtils.split(page.getOrder(), ",");
		AssertUtils.isTrue(orderBys.length == orders.length, "OrderBys's length must eq Orders's length!");

		Sort sort = query.sort();
		for (int i = 0; i < orderBys.length; i++) {
			sort.on(orderBys[i], asOrder(orders[i]));
		}

		return query;
	}

	private Order asOrder(String orderString) {
		return StringUtils.equalsIgnoreCase(orderString, "ASC") ? Order.ASCENDING : Order.DESCENDING;
	}

	private boolean isEmptyResult(List<T> result) {
		return null == result || result.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#count()
	 */
	@Override
	public long count() {
		return getMongoOperations().getCollection(entityInformation.getCollectionName()).count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)
	 */
	@Override
	public void delete(ID id) {
		AssertUtils.notNull(id, "Entity ID must not be null!");
		getMongoOperations().remove(getIdQuery(id), entityInformation.getJavaType());
	}

	protected Query getIdQuery(ID id) {
		return new Query(getIdCriteria(id));
	}

	protected Criteria getIdCriteria(ID id) {
		return Criteria.where(entityInformation.getIdAttribute()).is(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {
		AssertUtils.notNull(entity, "Entity must not be null!");
		delete(entityInformation.getId(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)
	 */
	@Override
	public void delete(Iterable<? extends T> entities) {
		AssertUtils.notNull(entities, "Entities must not be null!");
		for (T entity : entities) {
			delete(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#deleteAll()
	 */
	@Override
	public void deleteAll() {
		getMongoOperations().remove(EMPTY_QUERY, entityInformation.getCollectionName());
	}

	/**
	 * 
	 * @return
	 */
	protected final MongoEntityInformation<T, ID> getEntityInformation() {
		return entityInformation;
	}
}
