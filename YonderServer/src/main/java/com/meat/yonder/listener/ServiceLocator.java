package com.meat.yonder.listener;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceLocator {

	private static ApplicationContext CONTEXT;

	public static void setApplicationContext(ApplicationContext context) {
		CONTEXT = context;
	}
	
	public static ApplicationContext getApplicationContext() {
		if (CONTEXT == null) {
			CONTEXT = new ClassPathXmlApplicationContext(new String[]{"classpath:/config/spring/spring.xml"});
		}
		return CONTEXT;
	}

	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	public static <T> T getBean(Class<T> T) {
		return getApplicationContext().getBean(T);
	}

	public static String getMessage(String key, String defaultMessage) {
		return getApplicationContext().getMessage(key, null, defaultMessage, null);
	}

	public static String getMessage(String key) {
		return getApplicationContext().getMessage(key, null, "", Locale.CHINA);
	}
}
