package com.dylan.shiro.domain;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author LHacker
 *
 */
@Document
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private ObjectId id;
	@Indexed(unique=true)
	private String username;
	private String password;
	private boolean status = true;
	/*private String email;
	private int status;
	private int vipStatus;*/
	@DBRef
	private List<Role> roles = Lists.newArrayList();
	

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * 
	 * @param password
	 * @return
	 */
	public boolean isCorrectPassword(String password) {
		return StringUtils.equals(getPassword(), password);
	}

	/*public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVipStatus() {
		return vipStatus;
	}

	public void setVipStatus(int vipStatus) {
		this.vipStatus = vipStatus;
	}
	*/
	public Set<String> getRolesName(){
		List<Role> roles = getRoles();
		if(roles.isEmpty()){
			return Sets.newHashSet();
		}
		Set<String> roleName = Sets.newHashSet();
		for(Role role : roles){
			roleName.add(role.getName());
		}
		return roleName;
	}
	public String getRolesNameAsString(){
		StringBuilder builder = new StringBuilder();
		List<Role> roles = getRoles();
		if(roles.isEmpty()){
			return "";
		}
		for(Role role : roles){
			builder.append(role.getShowName()).append(" ");
		}
		return builder.toString();
	}
	public Set<String> getPermissions(){
		List<Role> roles = getRoles();
		if(roles.isEmpty()){
			return Sets.newHashSet();
		}
		Set<String> permissions = Sets.newHashSet();
		for(Role role : roles){
			List<Authority> authorities = role.getAuthorities();
			if(authorities.isEmpty()){
				return Sets.newHashSet();
			}
			for(Authority authority : authorities){
				permissions.add(authority.getPermission());
			}
		}
		return permissions;
	}
	
	public String getPermmissionsAsString(){
		StringBuilder builder = new StringBuilder();
		List<Role> roles = getRoles();
		if(roles.isEmpty()){
			return "";
		}
		for(Role role : roles){
			List<Authority> authorities = role.getAuthorities();
			if(authorities.isEmpty()){
				return "";
			}
			for(Authority authority : authorities){
				builder.append(authority.getName()).append(" ");
			}
		}
		return builder.toString();
	}

}
