package com.feelingk.bot.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.feelingk.bot.annotation.MessageMapping;
import com.feelingk.bot.inf.BaseCommand;
import com.feelingk.bot.inf.BaseMessage;
import com.feelingk.bot.service.TelegramSendMessage;

@MessageMapping(messageId="0004")
public class TelegramWeatherCmd implements BaseCommand {

	@Autowired
	@Qualifier("sendWeatherInfo")
	private TelegramSendMessage telegramSendMessage;
	
	@Override
	public void excute(BaseMessage msg) {
		telegramSendMessage.makeMessage(msg);
	}

}