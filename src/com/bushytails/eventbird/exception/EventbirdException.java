package com.bushytails.eventbird.exception;

public class EventbirdException extends Exception {
	public EventbirdException(String message) {
		super(message);
	}
	
	public EventbirdException(String message, Exception e) {
		super(message, e);
	}
}
