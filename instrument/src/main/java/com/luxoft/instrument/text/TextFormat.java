package com.luxoft.instrument.text;

public final class TextFormat {

	private static final TextFormat textFormat = new TextFormat();
	
	private Message message = null;
	private TextFormat(){
		message = new ParameterizedMessage();
	}
	
	public static TextFormat instance(){
		return textFormat;
	}
	
	public String format(final String msgPattern, Object... arguments){
		String textMessage = message.getFormattedMessage(msgPattern, arguments);
		return textMessage;
	}
	
	public static String formatMessage(final String msgPattern, Object... arguments){
		String textMessage = instance().format(msgPattern, arguments);
		return textMessage;
	}
}

