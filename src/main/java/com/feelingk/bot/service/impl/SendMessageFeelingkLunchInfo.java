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
		shop.add("신선설농탕");
		shop.add("세븐스프링스카페");
		shop.add("명동칼국수");
		shop.add("낙지칼국수");
		shop.add("장강");
		shop.add("금궁");
		shop.add("순대국밥");
		shop.add("칠형제 해장국");
		shop.add("포몬스 쌀국수");
		shop.add("미스터피자");
		shop.add("아비코커리");
		shop.add("함흥냉면");
		shop.add("맥도날드");
		shop.add("한우국밥");
		shop.add("나주곰탕");
		shop.add("사보텐 돈까스");
		shop.add("불고기 백반");
		shop.add("달인찜닭");

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
