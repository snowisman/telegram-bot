package com.feelingk.bot.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;

import com.feelingk.bot.constants.Constants;
import com.feelingk.bot.inf.BaseMessage;
import com.feelingk.bot.service.TelegramSendMessage;

@Component
public class SendMessageReplyInfo implements TelegramSendMessage {

	@Autowired
	private AbsSender absSender;

	public void makeMessage(BaseMessage message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.getMessage().getChatId().toString());
		sendMessage.enableMarkdown(true);
		String sendText ="";
		try {
			String output="";
			String text = message.getMessage().getText().replaceAll("/", "");
			HttpClient httpClient = new DefaultHttpClient();
			
			HttpGet getRequest = new HttpGet(Constants.SIMSIMI_API_URL+"&key="+Constants.SIMSIMI_API_KEY+"&text="+URLEncoder.encode(text));
			HttpResponse response = httpClient.execute(getRequest);
			
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatusLine().getStatusCode());
			}
		
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			StringBuilder builder = new StringBuilder();
			
			while ((output = br.readLine()) != null) {
				builder.append(output);		
			}
			
			JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) jsonParser.parse(builder.toString());
	        sendText = message.getMessage().getFrom().getFirstName()+"님\n : "+ (String) jsonObject.get("response");
			sendMessage.setText(sendText);	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		try {
			absSender.sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
	}
}
