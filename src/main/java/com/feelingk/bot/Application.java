package com.feelingk.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feelingk.bot.context.RootContext;
import com.feelingk.bot.context.SystemProperty;

/**
 * Hello world!
 *
 */
public class Application {
    
	/**
	 * The constant logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	/** 
	 * Main
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		init();
	}

	/**
	 * Init.
	 */
	private static void init() {
	 
		try {
			
			SystemProperty.init();
			if(RootContext.applicationContext == null){
				logger.error("Spring applicationContext 설정 오류");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		
	}

}
