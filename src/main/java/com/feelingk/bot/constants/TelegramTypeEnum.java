package com.feelingk.bot.constants;

public enum TelegramTypeEnum {
	
	MOVIE(Constants.TELEGRAM_TYPE_MOVIE, Constants.TELEGRAM_TYPE_MOVIE_CODE), 
	LOTTO(Constants.TELEGRAM_TYPE_LOTTO, Constants.TELEGRAM_TYPE_LOTTO_CODE), 
	LUNCH(Constants.TELEGRAM_TYPE_LUNCH, Constants.TELEGRAM_TYPE_LUNCH_CODE), 
	WEATHER(Constants.TELEGRAM_TYPE_WEATHER, Constants.TELEGRAM_TYPE_WEATHER_CODE),
	REPLY(Constants.TELEGRAM_TYPE_REPLY, Constants.TELEGRAM_TYPE_REPLY_CODE);
	
	private final String type;
	private final String code;
	
	private TelegramTypeEnum(String type, String code) {
		this.type = type;
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public String getType(){
		return this.type;
	}
	
}
