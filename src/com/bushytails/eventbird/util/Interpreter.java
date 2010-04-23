package com.bushytails.eventbird.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Interpreter {
	public static String interpretDateToString(Date date, Locale locale){
		SimpleDateFormat sdf = new SimpleDateFormat(interpret("format.datetime", locale));
		return sdf.format(date);
	}
	public static String interpret(String key, Locale locale){
		return interpret(key, locale, new HashMap<String, String>());
	}

	public static String makeParamString(String value){
		return "${" + value + "}";
	}
	
	public static String interpret(String key, Locale locale, Map<String, String> params){
		try {
			ResourceBundle rb = ResourceBundle.getBundle("eventbird", locale);
			String message = rb.getString(key);
			StringBuilder sb = new StringBuilder();
			for(String paramKey : params.keySet()){
				sb.append(makeParamString(paramKey));
				message = message.replace(sb.toString(), params.get(paramKey));
				sb.delete(0, sb.length());
			}
			return message;
		} catch(Exception e) {
			log.log(Level.WARNING, "dictionary is not found");
			return "";
		}
	}
	
	private static Logger log = Logger.getLogger(Interpreter.class.getName());
}
