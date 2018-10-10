package com.feelingk.bot.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ApplicationContext 생성
 *
 * @author bumsoo
 */
public class RootContext {

	/**
	 * The constant applicationContext.
	 */
	public final static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/config/application-context.xml");
	
	
	
}
