package com.bushytails.eventbird.util;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.BasicAuthorization;

public class SingField {
	public static Twitter getChorus(String name, String humming) {
		if(field == null) {
			return field = new TwitterFactory().getInstance(
				new BasicAuthorization(name, humming));
		}
		return field;
	}
	
	private static Twitter field;
}
