package com.dylan.shiro.interfaces.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.dylan.shiro.domain.User;
import com.dylan.shiro.infrastructure.shiro.SubjectAwareConstants;

/**
 * we can get all kind of message from CurrentUser by this class;
 * @author Dylan
 * @time 2012-8-17
 */
public class CurrentUserUtil {

	/**
	 * get current user
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getSession().getAttribute(
				SubjectAwareConstants.USER_AWARE_CONSTANT);
		return user;
	}
	
	
	public static void setCurrentUser(User user) {
		Subject subject = SecurityUtils.getSubject();
		 subject.getSession().setAttribute(
				SubjectAwareConstants.USER_AWARE_CONSTANT,user);
	}
	
}
