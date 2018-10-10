package com.feelingk.bot.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.feelingk.bot.annotation.MessageMapping;
import com.feelingk.bot.context.RootContext;
import com.feelingk.bot.inf.BaseCommand;
import com.feelingk.bot.inf.BaseMessage;
import com.feelingk.bot.util.ProxyConverter;

@Component("factory")
public class BotCommandFactory implements CommandFactory {

	/**
	 * The constant logger.
	 */
	public static final Logger logger = LoggerFactory.getLogger(BotCommandFactory.class);

	/**
	 * The constant mappingCmdMap.
	 */
	public static Map<String, BaseCommand> mappingCmdMap = new ConcurrentHashMap<String, BaseCommand>();

	/**
	 * Init.
	 */
	private synchronized void init(){
		
		if(mappingCmdMap.isEmpty()){
			
			String messageId = null;
			Map<String, Object> cmdMap = RootContext.applicationContext.getBeansWithAnnotation(MessageMapping.class);
			
			for (Object cmd : cmdMap.values()) {
				
				Object obj = null;
				
				try {
					
					obj = ProxyConverter.getTargetObject(cmd);
					
					MessageMapping messageMapping = obj.getClass().getAnnotation(MessageMapping.class);
					messageId =  messageMapping.messageId();
					mappingCmdMap.put(messageId, (BaseCommand) cmd);
				
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
			
		}
		
	}

	/**
	 * Gets mapping cmd map.
	 *
	 * @return the mapping cmd map
	 */
	private Map<String, BaseCommand> getMappingCmdMap() {
		
		if(mappingCmdMap.isEmpty()){
			init();
		}
	
		return mappingCmdMap;
	}

	/**
	 * Create command base command.
	 *
	 * @param msg the msg
	 *
	 * @return the base command
	 */
	public BaseCommand createCommand(BaseMessage msg) {
		
		BaseCommand cmd = null;
		cmd = getMappingCmdMap().get(msg.getMessageId());

		// 커맨드 객체 생성
		cmd = (BaseCommand)RootContext.applicationContext.getAutowireCapableBeanFactory().createBean(cmd.getClass(), AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
		return cmd;
	
	}

	/**
	 * Get command base command.
	 *
	 * @param msg the msg
	 *
	 * @return the base command
	 */
	public BaseCommand getCommand(BaseMessage msg){
		return getMappingCmdMap().get(msg.getMessageId());
	}

	/**
	 * Gets command by message id.
	 *
	 * @param messageId the message id
	 *
	 * @return the command by message id
	 */
	public BaseCommand getCommandByMessageId(String messageId) {
		return getMappingCmdMap().get(messageId);
	}
	
}
