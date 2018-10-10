package com.feelingk.bot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
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
public class SendWeatherInfo implements TelegramSendMessage {
	
	@Autowired
	private AbsSender absSender;
	
	public void makeMessage(BaseMessage message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.getMessage().getChatId().toString());
		sendMessage.enableMarkdown(true);
		
		String text = message.getMessage().getText().replaceAll("/", "");
		HashMap<String, String> cityMap = new HashMap<String, String>();
		cityMap.put("서울", "Seoul");
		cityMap.put("부산", "Busan");
		cityMap.put("대구", "Daegu");
		cityMap.put("광주", "Gwangju");
		cityMap.put("인천", "Incheon");
		cityMap.put("대전", "Daejeon");
		cityMap.put("울산", "Ulsan");
		

		String[] strMessage =text.split(" ");
		System.out.println(strMessage);
		List<String> wCity = new ArrayList<String>();
		
		for (String str : strMessage) {
			if(cityMap.containsKey(str)){
				wCity.add(cityMap.get(str));
			}
		}
		
		if(wCity.size() > 0){
			sendMessage.setText(getWeatherInfo(wCity.get(0)));
		}else{
			sendMessage.setText("해당 지역에 대한 날씨를 가져올 수 없습니다.");
		}
		
		try {
			absSender.sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	public String getWeatherInfo(String cityName){
		String apiUrl = Constants.WEATHER_API_URL+"appid="+Constants.WEATHER_API_KEY+"&q="+cityName+"&units=metric";
		String weatherStr = "해당 지역 날씨 \n";
		try {
			CloseableHttpClient client = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
			HttpGet request = new HttpGet(apiUrl);
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
            String responseString = EntityUtils.toString(buf, "UTF-8");
               
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseString);
            
            System.out.println(jsonObject);
            // 지역 출력
            weatherStr+="지역 : " + jsonObject.get("name")+"\n";
            
            // 날씨 출력
            JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
            System.out.println(weatherArray);
            JSONObject obj = (JSONObject) weatherArray.get(0);
            weatherStr+="날씨 : " +obj.get("main")+"\n";

            // 온도 출력
            JSONObject mainArray = (JSONObject) jsonObject.get("main");
            String temp =mainArray.get("temp").toString();
            weatherStr+="온도 : "+temp;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return weatherStr;
	}
}
