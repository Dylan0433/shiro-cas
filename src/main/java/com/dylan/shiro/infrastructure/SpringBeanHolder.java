package com.dylan.shiro.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.youboy.util.AssertUtils;

/**
 * 
 * @author loudyn
 * 
 */
public class SpringBeanHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
	 * ApplicationContext)
	 */
	@Override
	public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringBeanHolder.awareApplicationContext(applicationContext);
	}

	private static void awareApplicationContext(ApplicationContext applicationContext) {
		AssertUtils.notNull(applicationContext);
		SpringBeanHolder.applicationContext = applicationContext;
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

}
