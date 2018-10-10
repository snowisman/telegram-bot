package com.feelingk.bot.Message;

import org.telegram.telegrambots.api.objects.Message;

import com.feelingk.bot.inf.BaseMessage;

public class TelegramMessage implements BaseMessage{

	private String messageId;
	
	private String type="TELEGRAM";
	
	private Message getMessage;
	
	public void setGetMessage(Message getMessage) {
		this.getMessage = getMessage;
	}

	public Message getGetMessage() {
		return getMessage;
	}

	public String getMessageId() {
		return messageId;
	}
	
	public String getType() {
		return type;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TelegramMessage [messageId=" + messageId + ", type=" + type + ", getMessage=" + getMessage + "]";
	}

	@Override
	public Message getMessage() {
		return getMessage;
	}

}
