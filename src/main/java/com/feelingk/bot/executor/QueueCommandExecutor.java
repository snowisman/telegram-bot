package com.feelingk.bot.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feelingk.bot.context.TelegramContext;
import com.feelingk.bot.factory.CommandFactory;
import com.feelingk.bot.inf.BaseCommand;
import com.feelingk.bot.inf.BaseInterceptor;
import com.feelingk.bot.inf.BaseMessage;
import com.feelingk.bot.util.PropertiesUtil;


/**
 * The type Queue command executor.
 */
@Component
public class QueueCommandExecutor implements CommandExecutor, InitializingBean {
	
	/**
	 * The constant logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(QueueCommandExecutor.class);

	@Autowired
	private CommandFactory factory;
	
	@Autowired
	private TelegramContext telegram;
	
	/**
	 * The Queue.
	 */
	private BlockingQueue<BaseMessage> queue;
	/**
	 * The Customer task pool.
	 */
	private ExecutorService customerTaskPool;
	
	/**
	 * The Interceptor list.
	 */
	private List<BaseInterceptor> interceptorList = new ArrayList<BaseInterceptor>();

	/**
	 * Gets interceptor list.
	 *
	 * @return the interceptor list
	 */
	public List<BaseInterceptor> getInterceptorList() {
		return interceptorList;
	}

	/**
	 * Sets interceptor list.
	 *
	 * @param interceptorList the interceptor list
	 */
	public void setInterceptorList(List<BaseInterceptor> interceptorList) {
		this.interceptorList = interceptorList;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		telegram.initTelegramBot();
		
		String queueSize = PropertiesUtil.getProperty("queueSize");
		String poolSize = PropertiesUtil.getProperty("poolSize");
		queue = new ArrayBlockingQueue<BaseMessage>(Integer.parseInt(queueSize));
		customerTaskPool = Executors.newFixedThreadPool(Integer.parseInt(poolSize));
		for (int i = 0; i < Integer.parseInt(poolSize); i++) {
			customerTaskPool.submit(new MessageConsumer());
		}
	}
	
	public void execute(BaseMessage msg) throws Exception {
		queue.put(msg);
		logger.info("queue :{}", queue);
	}
	
	class MessageConsumer implements Runnable {

		/**
		 * The Msg.
		 */
		BaseMessage msg;
		/**
		 * The Cmd.
		 */
		BaseCommand cmd;

		/**
		 * Run.
		 */
		public void run() {
			while(true) {
				try {
					msg = queue.take();

					cmd = factory.getCommand(msg);
					boolean resultFlag = true;
					for (BaseInterceptor baseInterceptor : interceptorList) {
						resultFlag = baseInterceptor.preHandle(msg, cmd);
						if(!resultFlag) break; // false 리턴시 다음 interceptor 실행 안함
					}
					// false 리턴시 Command 실행 처리 안함
					if(!resultFlag) continue;
					
					cmd.excute(msg);
					
					for (BaseInterceptor baseInterceptor : interceptorList) {
						baseInterceptor.postHandle(msg, cmd);
					}
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
 
	}

}
