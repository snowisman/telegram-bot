package com.feelingk.bot.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * The interface Message mapping.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MessageMapping {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 *
	 * @return the suggested component name, if any
	 */
	String value() default "";

	/**
	 * The value may indicate a suggestion for a mapping message ID of BaseMessage
	 *
	 * @return the message ID
	 */
	String messageId() default "";

}