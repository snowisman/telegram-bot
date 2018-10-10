package com.feelingk.bot.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feelingk.bot.executor.CommandExecutor;
import com.feelingk.bot.inf.Acceptor;
import com.feelingk.bot.inf.BaseMessage;

@Component
public class MessageBroker implements Acceptor{
	
	@Autowired
	private CommandExecutor commandExecutor;
	
	public void sendMessage(BaseMessage msg) {
		System.out.println(msg);
	}
	
	public void receiveMessage(BaseMessage msg) {
		try {
			commandExecutor.execute(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
