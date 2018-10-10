package com.feelingk.bot.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import com.feelingk.bot.manager.MessageManager;

@Component
public class TelegramContext {
	
	/**
	 * The constant logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TelegramContext.class);
	
	@Autowired
	private MessageManager manager;
	
	public void initTelegramBot() {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(manager);
			logger.info("Telegram Info :{}", telegramBotsApi);
		} catch (TelegramApiException e) {
			logger.debug("Telegram Error :{}", e.getMessage());
		}
	}
	
}
