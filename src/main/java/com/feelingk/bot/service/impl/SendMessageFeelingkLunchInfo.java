package com.feelingk.bot.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;

import com.feelingk.bot.inf.BaseMessage;
import com.feelingk.bot.service.TelegramSendMessage;

@Component
public class SendMessageFeelingkLunchInfo implements TelegramSendMessage {

	@Autowired
	private AbsSender absSender;
	
	public void makeMessage(BaseMessage message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.getMessage().getChatId().toString());
		sendMessage.enableMarkdown(true);
		
		String lottoStr=message.getMessage().getFrom().getFirstName()+"님에게 점심 추천 메뉴 : ";
		
		List<String> shop = new ArrayList<String>();
		shop.add("유가네");
		shop.add("에베레스트");
		shop.add("부대찌개");
		shop.add("야마돈까스");
		shop.add("새마을식당");

		Collections.shuffle(shop);
		
		lottoStr += shop.get(0) + "  ";
		sendMessage.setText(lottoStr);
		
		try {
			absSender.sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}


}
