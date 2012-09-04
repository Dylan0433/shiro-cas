package com.dylan.shiro.infrastructure.shiro;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.shiro.application.RoleService;
import com.dylan.shiro.domain.Role;
import com.dylan.shiro.domain.User;
import com.dylan.shiro.domain.UserRepository;
import com.dylan.shiro.infrastructure.SpringBeanHolder;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
public class MongoShiroRealm extends AuthorizingRealm {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// null usernames are invalid
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}

		String username = (String) getAvailablePrincipal(principals);
		try {

			User user = getUserRepository().queryUniqueByUsername(username);
			checkUser(user, username);

			Set<String> roles = user.getRolesName();
			Set<String> permissions = user.getPermissions();
			//添加默认的角色
			if(roles.isEmpty()){
				addDefaultRole(roles);
			}
			
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
			info.setStringPermissions(permissions);
			return info;
		} catch (Exception e) {
			throw translateAuthorizationException(e);
		}
	}

	/**
	 * 
	 * @return
	 */
	protected UserRepository getUserRepository() {
		return SpringBeanHolder.getBean(UserRepository.class);
	}

	/**
	 * 如果登录用户没有设置角色跟权限，默认给webmaster的角色给该用户
	 * @param roles
	 * @param permissions
	 */
	private void addDefaultRole(Set<String> roles){
		Role role = getRoleRepository().getByName("webmaster");
		if(null == role)
			throw new RuntimeException("the db has not a role name 'webmaster',please add a role name 'webmaster' to the db,and add some permission to it");
		roles.add(role.getName());
	}
	protected RoleService getRoleRepository(){
		return SpringBeanHolder.getBean(RoleService.class);
	}
	private AuthorizationException translateAuthorizationException(Exception e) {
		if (AuthorizationException.class.isAssignableFrom(e.getClass())) {
			return (AuthorizationException) e;
		}

		return new AuthorizationException(e);
	}

	private void checkUser(User user, String username) {
		if (null == user) {
			throw new UnknownAccountException("No account found for user [" + username + "]");
		}
	}

	private AuthenticationException translateAuthenticationException(Exception e) {
		if (AuthenticationException.class.isAssignableFrom(e.getClass())) {
			return (AuthenticationException) e;
		}

		return new AuthenticationException(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		// Null username is invalid
		if (StringUtils.isBlank(username)) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
		try {

			User user = getUserRepository().queryUniqueByUsername(username);
			checkUser(user, username);

			return buildAuthenticationInfo(username, user.getPassword().toCharArray());
		} catch (Exception e) {
			throw translateAuthenticationException(e);
		}
	}

	protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
		return new SimpleAuthenticationInfo(username, password, getName());
	}
}
