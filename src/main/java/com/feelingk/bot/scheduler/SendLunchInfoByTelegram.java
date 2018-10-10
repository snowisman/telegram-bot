package com.feelingk.bot.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;

import com.feelingk.bot.Message.TelegramMessage;
import com.feelingk.bot.constants.TelegramTypeEnum;
import com.feelingk.bot.inf.Acceptor;

@Component
public class SendLunchInfoByTelegram {

	/**
	 * The Logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(SendLunchInfoByTelegram.class);
	
	public Message telegramChatMessage = null;
	
	@Autowired
	private Acceptor messageBroker;
	
	@Scheduled(cron="0 40 11 * * *")
	public void exceute() {
		
		logger.info("SendLunchInfoByTelegram Start");
		TelegramMessage telegramMessage = new TelegramMessage();
		telegramMessage.setMessageId(TelegramTypeEnum.LUNCH.getCode());
		telegramMessage.setGetMessage(telegramChatMessage);
		logger.info("TelegramMessage VO : {}", telegramMessage.toString());
		
		if (telegramMessage.getGetMessage() != null) {
			messageBroker.receiveMessage(telegramMessage); 
		}
		
	}
	
}
