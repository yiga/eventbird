package com.bushytails.eventbird.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jdo.PersistenceManager;

import org.apache.commons.lang.RandomStringUtils;

import com.bushytails.eventbird.model.EventImage;
import com.bushytails.eventbird.model.EventScore;
import com.bushytails.eventbird.model.MemberImage;
import com.bushytails.eventbird.model.MemberScore;
import com.bushytails.eventbird.model.Score;
import com.bushytails.eventbird.model.ScoreImage;
import com.bushytails.eventbird.model.datastore.Event;
import com.bushytails.eventbird.model.datastore.Member;

public class Writer {
	public static String createEventCountDownMessage(Event event, int count, Locale locale) {
		StringBuilder sb = new StringBuilder();
		sb.append("@");
		sb.append(event.getAuthor());
		sb.append(" ");
		Map<String, String> params = new HashMap<String, String>();
		params.put("beforeCount", String.valueOf(count));
		String preMessage = Interpreter.interpret("bird.message.event.countdown", locale, params);
		String key = Interpreter.makeParamString("info");
		String info = makeLimitedInfoMessage(key, event.getContents(), sb.toString() + preMessage);
		sb.append(preMessage.replace(key, info));
		return sb.toString();
	}
	
	public static String createMemberTimeOverMessage(Event event, Locale locale) {
		StringBuilder sb = new StringBuilder();
		sb.append("@");
		sb.append(event.getAuthor());
		sb.append(" ");
		
		String preMessage = Interpreter.interpret("bird.message.event.regist.timeup", locale);
		String key = Interpreter.makeParamString("info");
		String info = makeLimitedInfoMessage(key, event.getContents(), sb.toString() + preMessage);
		sb.append(preMessage.replace(key, info));
		return sb.toString();
	}

	public static String makeImprovisationCode() {
		return RandomStringUtils.randomAlphanumeric(12);
	}
	
	public static List<Score> writeEventScore(
		List<ScoreImage> imageList, String eventCode, String entryCode, Locale locale) 
	{
		List<Score> list = new ArrayList<Score>();
		List<Event> eventList = new ArrayList<Event>();
		List<Member> memberList = new ArrayList<Member>();
		Map<String, Event> map = new Keeper().getEventMap();
		for(ScoreImage image : imageList){
			if(image.isEvent()) {
				EventScore score = writeEventScore((EventImage)image, eventCode, entryCode, locale);
				list.add(score);
				if(score.isRegist()) {
					Event event = createEvent((EventImage)image, score, eventCode);
					eventList.add(event);
				}
			}else if(image.isMember()) {
				String eventKey = ((MemberImage)image).getContents().split(entryCode)[1];
				MemberScore score = writeMemberScore((MemberImage)image, entryCode, map.get(eventKey), locale);
				list.add(score);
				if(score.isRegist()) {
					Member member = createMember((MemberImage)image, entryCode);
					memberList.add(member);
				}
			}
		}
		
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try {
			pm.makePersistentAll(memberList);
			pm.makePersistentAll(eventList);
		} finally {
			pm.close();
		}
		return list;
	}
	
	private static Event createEvent(EventImage image, EventScore score, String eventCode) {
		String content = image.getContents().replace("@" + image.getBirdName(), "");
		String[] contents = content.split(eventCode);
		String melody = contents[0].trim();
		String[] attr = contents[1].trim().split("/");
		int numberOfMembers = Integer.valueOf(attr[0]);		
		String[] date = attr[1].split("-");
		String[] time = new String[]{"10", "00"};
		if(2 < attr.length){
			time = attr[2].split(":");
		}
		Calendar playAt = Calendar.getInstance();
		playAt.set(Integer.valueOf(date[0]), Integer.valueOf(date[1])
			, Integer.valueOf(date[2]), Integer.valueOf(time[0])
			, Integer.valueOf(time[1]));
		
		Event event = new Event();
		event.setAuthor(image.getOwnerName());
		event.setContents(melody);
		event.setNowMember(0);
		event.setNumberOfMembers(numberOfMembers);
		event.setPlayAt(playAt.getTime());
		event.setPlayed(false);
		event.setEnd(false);
		event.setWriteAt(new Date());
		event.setPageNumber(image.getPageCount());
		event.setName(score.getScoreName());
		event.setCountdowned(0);
		return event;
	}
	
	private static Member createMember(MemberImage image, String entryCode){
		String[] contents = image.getContents().split(entryCode);
		Member member = new Member();
		member.setMemberId(image.getMemberId());
		member.setName(image.getMemberName());
		member.setContent(contents[0]);
		member.setEventId(image.getScoreId());
		member.setDisbandment(false);
		return member;
	}

	private static String makeLimitedInfoMessage(String key, String infoMessage, String preMessage) {
		int count = 140 - (preMessage.length() - key.length());
		return infoMessage.length() < count ? infoMessage : infoMessage.substring(0, count - 3) + "...";
	}

	private static EventScore writeEventScore(EventImage image, String eventCode, String entryCode, Locale locale){
		String content = image.getContents().replace("@" + image.getBirdName(), "");
		String[] contents = content.split(eventCode);
		String melody = contents[0].trim();
		
		String[] attr = contents[1].trim().split("/");
		int numberOfMembers = Integer.valueOf(attr[0]);		
		String[] date = attr[1].split("-");
		String[] time = new String[]{"10", "00"};
		if(2 < attr.length){
			time = attr[2].split(":");
		}
		Calendar playAt = Calendar.getInstance();
		playAt.set(Integer.valueOf(date[0]), Integer.valueOf(date[1])
			, Integer.valueOf(date[2]), Integer.valueOf(time[0])
			, Integer.valueOf(time[1]));
		
		String publicEventCode = "m8x5hNWW31Fs";		
//		String publicEventCode = makeImprovisationCode();
		
		EventScore score = new EventScore();
		score.setUserName(image.getOwnerName());
		score.setRegist(true);
		score.setScoreName(publicEventCode);
		score.setMelody(createRegistEventMessage(
				image.getOwnerName(), numberOfMembers, melody, entryCode + publicEventCode, playAt, locale));
		return score;
	}
	
	private static String createRegistEventMessage(
		String ownerName, int memberCount, String contents, String entryCode, Calendar date, Locale locale) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("@");
		sb.append(ownerName);
		sb.append(" ");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("members", String.valueOf(memberCount));
		params.put("limittime", Interpreter.interpretDateToString(date.getTime(), locale));
		params.put("entryCode", entryCode);
		
		String preMessage = Interpreter.interpret("bird.message.event.regist", locale, params);
		String key = Interpreter.makeParamString("info");
		String info = makeLimitedInfoMessage(key, contents, sb.toString() + preMessage);
		sb.append(preMessage.replace(key, info));
		return sb.toString();
	}
	
	private static MemberScore writeMemberScore(MemberImage image, String entryCode, Event event, Locale locale){
		MemberScore score = new MemberScore();
		score.setRegist(true);
		score.setUserName(image.getMemberName());
		score.setMelody(createRegistMemberMessage(image.getMemberName(), event.getContents(), locale));
		return score;
	}
	
	private static String createRegistMemberMessage(
		String memberName, String eventContents, Locale locale) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("@");
		sb.append(memberName);
		sb.append(" ");
		
		String preMessage = Interpreter.interpret("bird.message.event.regist.member", locale);
		String key = Interpreter.makeParamString("info");
		String info = makeLimitedInfoMessage(key, eventContents, sb.toString() + preMessage);
		sb.append(preMessage.replace(key, info));
		return sb.toString();
	}
}
