package com.feelingk.bot.factory;

import com.feelingk.bot.inf.BaseCommand;
import com.feelingk.bot.inf.BaseMessage;

/**
 * The interface Command factory.
 */
public interface CommandFactory {
	/**
	 * Create command base command.
	 *
	 * @param msg the msg
	 *
	 * @return the base command
	 */
	BaseCommand createCommand(BaseMessage msg);

	/**
	 * Gets command.
	 *
	 * @param msg the msg
	 *
	 * @return the command
	 */
	BaseCommand getCommand(BaseMessage msg);

	/**
	 * Gets command by message id.
	 *
	 * @param messageId the message id
	 *
	 * @return the command by message id
	 */
	BaseCommand getCommandByMessageId(String messageId);
}
