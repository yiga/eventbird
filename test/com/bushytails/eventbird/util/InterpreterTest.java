package com.bushytails.eventbird.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

public class InterpreterTest extends TestCase {
	public void testInterpret() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("info", "テストイベント");
		Locale locale = Locale.JAPANESE;
		String key = "bird.message.event.regist.member";
		String result = Interpreter.interpret(key, locale, params);
		assertEquals(result, "参加登録を受け付けたよー, イベント: テストイベント");
	}
	
	public void testMakeParamString() throws Exception {
		assertEquals(Interpreter.makeParamString("test"), "${test}");
	}
	
	public void testDateToString() throws Exception {
		Calendar now = Calendar.getInstance();
		now.set(2010, 2, 30, 9, 30);
		Locale locale = Locale.JAPANESE;
		String interpreted = Interpreter.interpretDateToString(now.getTime(), locale);
		assertEquals(interpreted, "2010-03-30 09:30(JST)");
	}
}
