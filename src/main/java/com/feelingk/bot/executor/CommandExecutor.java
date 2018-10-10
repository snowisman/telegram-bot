package com.feelingk.bot.executor;

import com.feelingk.bot.inf.BaseMessage;

/**
 * The interface Command executor.
 */
public interface CommandExecutor {
	/**
	 * Execute.
	 *
	 * @param msg the msg
	 *
	 * @throws Exception the exception
	 */
	public void execute(BaseMessage msg) throws Exception;
}
