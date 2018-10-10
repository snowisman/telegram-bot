package com.feelingk.bot.service.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;

import com.feelingk.bot.inf.BaseMessage;
import com.feelingk.bot.service.TelegramSendMessage;

@Component
public class SendMessageMovieInfo implements TelegramSendMessage {

	@Autowired
	private AbsSender absSender;
	
	public void makeMessage(BaseMessage message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.getMessage().getChatId().toString());
		sendMessage.enableMarkdown(true);
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://ticket2.movie.daum.net/Movie/MovieRankList.aspx");
		String movies ="";
		
		try {
			movies=httpClient.execute(httpget, new BasicResponseHandler() {
				
				@Override
				public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
					StringBuilder builder = new StringBuilder();
					String res = new String(super.handleResponse(response).getBytes("utf-8"), "utf-8");
					Document document = Jsoup.parse(res);
					Elements rows = document.select("ul.list_boxthumb li div.desc_boxthumb");
					
					for(Element row : rows){
						builder.append(row.getElementsByTag("strong").iterator().next().text()+"\n");	
					}
					return builder.toString();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sendMessage.setText(movies);
		try {
			absSender.sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
