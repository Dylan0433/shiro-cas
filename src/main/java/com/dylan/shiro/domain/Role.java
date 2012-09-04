package com.dylan.shiro.domain;

import java.io.Serializable;
import java.util.List;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;

/**
 * @author LHacker
 *
 */
@Document
public class Role implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@JsonIgnore
	private ObjectId id;
	@Indexed(unique=true)
	private String name;
	private String showName;

	@DBRef
	private List<Authority> authorities = Lists.newArrayList();

	public String getName() {
		return name;
	}


	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public List<Authority> getAuthorities() {
		return authorities;
	}


	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}


	public Role setName(String name) {
		this.name = name;
		return this;
	}

	public String getShowName() {
		return showName;
	}

	public Role setShowName(String showName) {
		this.showName = showName;
		return this;
	}

}
