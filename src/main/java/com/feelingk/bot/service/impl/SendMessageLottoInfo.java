package com.feelingk.bot.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
public class SendMessageLottoInfo implements TelegramSendMessage {

	@Autowired
	private AbsSender absSender;

	public void makeMessage(BaseMessage message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.getMessage().getChatId().toString());
		sendMessage.enableMarkdown(true);
		
		String lottoStr="로또번호 : ";
		
		List<Integer> lottoNum = new ArrayList<Integer>();

        for (int i = 1; i <= 45; i++) {
            lottoNum.add(i);
        }
 
        // set안의 수를 무작위로 섞는다
        Collections.shuffle(lottoNum);
 
        int[] lottoNums = new int[6];
        for (int i = 0; i < 6; i++) {
            lottoNums[i] = lottoNum.get(i);
        }
         
        // 정렬
        Arrays.sort(lottoNums);
        
        for(int i=0; i<lottoNums.length; i++){
        	lottoStr += lottoNums[i] + "  ";
        }
		sendMessage.setText(lottoStr);
		
		try {
			absSender.sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
