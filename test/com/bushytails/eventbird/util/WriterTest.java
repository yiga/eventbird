package com.bushytails.eventbird.util;

import java.util.Locale;

import com.bushytails.eventbird.model.datastore.Event;

import junit.framework.TestCase;

public class WriterTest extends TestCase {
	public void testCreateMemberTimeOverMessage() throws Exception{
		Event event = new Event();
		event.setAuthor("test author");
		String message = Writer.createEventCountDownMessage(event, 20, Locale.JAPANESE);
		assertEquals(message, "");
	}
	
	public void testCreateEventCountDownMessage() throws Exception {
		
	}
	
	public void testWriteEventScore() throws Exception {
		
	}
}
