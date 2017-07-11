package com.luxoft.instrument.text;

public interface Message {
	String getFormattedMessage();
	String getFormat();
	Object[] getParameters();
	String getFormattedMessage(final String msgPattern, Object... arguments);
}