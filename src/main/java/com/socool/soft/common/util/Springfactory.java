package com.socool.soft.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Springfactory implements ApplicationContextAware {

	// Spring应用上下文环境
	private static ApplicationContext applicationContext;
	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 *
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		Springfactory.applicationContext = applicationContext;
	}
	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static <T> T getBeanForClass(Class<T> clazz) {
		return (T) applicationContext.getBean(clazz);
	}

}