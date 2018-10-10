package com.feelingk.bot.inf;

import org.telegram.telegrambots.api.objects.Message;

/**
 * The interface Base message.
 */
public interface BaseMessage {
	/**
	 * Gets message id.
	 *
	 * @return the message id
	 */
	public String getMessageId();

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public String getType();
	
	/**
	 * Gets telegram Message.
	 *
	 * @return the type
	 */
	public Message getMessage();
}
