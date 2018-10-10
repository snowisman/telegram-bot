package com.feelingk.bot.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import com.feelingk.bot.Message.TelegramMessage;
import com.feelingk.bot.constants.Constants;
import com.feelingk.bot.constants.TelegramTypeEnum;
import com.feelingk.bot.inf.Acceptor;
import com.feelingk.bot.scheduler.SendLunchInfoByTelegram;

@Component
public class MessageManager extends TelegramLongPollingBot {
	
	/**
	 * The constant logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MessageManager.class);
	
	@Autowired
	private SendLunchInfoByTelegram sendLunchInfoByTelegram;
	
	@Autowired
	private Acceptor messageBroker;
	
	@Override
	public String getBotToken() {
		return Constants.BOT_TOKEN;
	}

	public String getBotUsername() { 
		return Constants.BOT_NAME;
	}
	
	public void onUpdateReceived(Update update) {
		TelegramMessage telegramMessage = new TelegramMessage();
		Message message = update.getMessage();
		sendLunchInfoByTelegram.telegramChatMessage = message;
		Boolean replyType = false;
		for (TelegramTypeEnum enumInfo : TelegramTypeEnum.values()) {
			
			if (message.getText().equals(enumInfo.getType())) {
				telegramMessage.setMessageId(enumInfo.getCode());
				replyType = true;
			}
			if ( ! replyType) {
				telegramMessage.setMessageId(enumInfo.REPLY.getCode());
			}
		}
		
		telegramMessage.setGetMessage(message);
		logger.info("TelegramMessage VO : {}", telegramMessage.toString());
		
		messageBroker.receiveMessage(telegramMessage); 
	}		
	
}
