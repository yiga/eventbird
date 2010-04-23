package com.bushytails.eventbird.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.bushytails.eventbird.exception.EventbirdException;
import com.bushytails.eventbird.model.EventImage;
import com.bushytails.eventbird.model.Feed;
import com.bushytails.eventbird.model.MemberImage;
import com.bushytails.eventbird.model.Score;
import com.bushytails.eventbird.model.ScoreImage;
import com.bushytails.eventbird.model.datastore.Event;
import com.bushytails.eventbird.util.Keeper;
import com.bushytails.eventbird.util.SingField;
import com.bushytails.eventbird.util.StoreManager;
import com.bushytails.eventbird.util.Writer;

public class Eventbird {
	public Eventbird(Feed feed) {
		this.feed = feed;
	}
	
	public List<ScoreImage> listenTweet() throws EventbirdException {
		try {
//			ResponseList<Status> list = chorus.getMentions(new Paging(1, 100, 10423143190L));
			ResponseList<Status> list = chorus.getMentions(new Paging(1, 100, feed.getListeningPoint()));
			List<ScoreImage> imageList = new ArrayList<ScoreImage>();
			int memberCount = 0;
			Map<String, Event> map = new Keeper().getEventMap();
			for(int i = 0; i < list.size(); i++) {
				Status st = list.get(i);
				if(st.getText().contains(feed.getEventCode())) {
					if(feed.getEventCount() == feed.getNowEventCount()) {
						continue;
					}
					EventImage image = new EventImage();
					image.setBirdName(feed.getBirdName());
					image.setContents(st.getText());
					image.setOwnerId(st.getUser().getId());
					image.setOwnerName(st.getUser().getScreenName());
					image.setPageCount(feed.getPagingCount());
					imageList.add(image);
					feed.setEventCount(feed.getEventCount() + 1);
				} else if(st.getText().contains(feed.getEntryCode())) {
					try{
						String code = st.getText().split(feed.getEntryCode())[1].trim();
						if(map.containsKey(code)) {
							Event score = map.get(code);
							if(score.isPlayed() || score.getNumberOfMembers() < score.getNowMember() + memberCount) {
								continue;
							}
							MemberImage image = new MemberImage();
							image.setContents(st.getText());
							image.setMemberName(st.getUser().getScreenName());
							image.setMemberId(st.getUser().getId());
							image.setScoreId(score.getId());
							image.setBirdName(feed.getBirdName());
							imageList.add(image);
							memberCount++;
						}
					}catch(Exception e){
						log.log(Level.WARNING, "invalid entry code");
						continue;
					}
				}
			}
			if(0 < list.size()) {
				feed.setListeningPoint(list.get(list.size() - 1).getId());
			}
			return imageList;
		} catch(TwitterException e) {
			throw new EventbirdException("can't churus", e);
		}
	}
	
	public List<Score> chorusTweet(List<Score> list) throws EventbirdException {
		for(Score score : list) {
			try {
				warble(score);
			} catch(TwitterException e) {
				throw new EventbirdException("can not chorus", e);
			}
		}
		return list;
	}

	public void checkEventCountDown() throws EventbirdException{
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		end.setTime(begin.getTime());
		begin.set(Calendar.HOUR_OF_DAY, 0);
		begin.set(Calendar.MINUTE, 0);
		begin.set(Calendar.SECOND, 0);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);

		int nowDay = 0;
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try{
			for(int day : feed.getEventRhythm()){
				nowDay += day;
				begin.add(Calendar.DAY_OF_MONTH, day);
				end.add(Calendar.DAY_OF_MONTH, day);
				for(Event event : new Keeper().getEventMap(pm).values()) {
					if(day == event.getCountdowned()){
						continue;
					}
					long playAt = event.getPlayAt().getTime();
					if(begin.getTimeInMillis() < playAt && playAt < end.getTimeInMillis()) {
						try {
							warbleEventCountDown(event, day);
							event.setCountdowned(day);
							pm.makePersistent(event);
						} catch(TwitterException e) {
							throw new EventbirdException("can not countdown", e);
						}
					}
				}
			}
		}finally{
			pm.close();
		}
	}
	
	public void checkScore() throws EventbirdException {
		Calendar now = Calendar.getInstance();
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try{
			List<Event> list = new Keeper().getOutDatedEventList(pm, now.getTimeInMillis());
			for(Event event : list) {
				warbleSatisfaction(event);
				event.setPlayed(true);
			}
		} catch(TwitterException e) {
			throw new EventbirdException("can't look up", e);
		}finally{
			pm.close();
		}
	}
	
	public void sleepOnRoost(List<Score> list) {
		new Keeper().takeNotes(feed, list);
	}
	
	public void wakeupOnRoost() throws EventbirdException {
		chorus = SingField.getChorus(feed.getBirdName(), feed.getSecretHumming());
	}
	
	/**
	 * イベントの何日前をtweetする
	 * @param score イベント内容
	 * @throws TwitterException 
	 */	
	private void warbleEventCountDown(Event event, int count) throws TwitterException {
		chorus.updateStatus(Writer.createEventCountDownMessage(event, count, feed.getLocale()));
	}

	/**
	 * 受付時間の終了をtweetする
	 * @param event
	 * @throws TwitterException
	 */
	private void warbleSatisfaction(Event event) throws TwitterException {
		chorus.updateStatus(Writer.createMemberTimeOverMessage(event, feed.getLocale()));
	}

	/**
	 * 受け付けた内容をtweetする
	 * @param score 内容
	 * @throws TwitterException
	 */
	private void warble(Score score) throws TwitterException {
		chorus.updateStatus(score.getMelody());
	}
	
	public Locale getLocale() {
		return feed.getLocale();
	}
	
	private Twitter chorus;
	private Feed feed;
	
	private static Logger log = Logger.getLogger(Eventbird.class.getName());
}
