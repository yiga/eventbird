package com.bushytails.eventbird.util;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.bushytails.eventbird.model.Feed;
import com.bushytails.eventbird.model.Score;
import com.bushytails.eventbird.model.datastore.Event;
import com.bushytails.eventbird.model.datastore.Filer;
import com.bushytails.eventbird.model.datastore.Member;


public class Keeper {	
	public void takeNotes(Feed feed, List<Score> list) {
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		Query rq = pm.newQuery(Filer.class);
		try{
			List<Filer> rList = (List<Filer>)rq.execute();
			Filer filer = rList.get(0);
			filer.setListeningPoint(feed.getListeningPoint());
			filer.setScoreCount(feed.getEventCount());
		}finally{
			rq.closeAll();
			pm.close();
		}
	}
	
	public Event getScore(PersistenceManager pm, String name){
		Query q = pm.newQuery(Event.class);
		q.setFilter("name == '" + name + "'");
		try {
			List<Event> list = (List<Event>)q.execute();
			if(list.size() == 0){
				return null;
			}
			return list.get(0);
		}finally{
			q.closeAll();
		}
	}
	
	public List<Member> getMemberList(PersistenceManager pm, Event score) {
		Query q = pm.newQuery(Member.class);
		q.setFilter("eventId == " + score.getId());
		try{
			return (List<Member>)q.execute();
		}finally{
			q.closeAll();
		}
	}
	
	public void buildRoost() {
		Filer filer = new Filer();
		filer.setDeployedAt(new Date());
		filer.setScoreCount(0);
		filer.setListeningPoint(1);
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try {
			pm.makePersistent(filer);
		}finally{
			pm.close();
		}
	}
	
	public void fileScore(Event score, Filer filer) {
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try {
			filer.setScoreCount(filer.getScoreCount() + 1);
			pm.makePersistent(filer);
			pm.makePersistent(score);
		}finally{
			pm.close();
		}
	}
	
	public Map<String, Event> getScoreMap(PersistenceManager pm, String ownerId){
	   Query q = pm.newQuery(Event.class);
	   q.setFilter("isPlayed == false");
	   q.setFilter("author == '" + ownerId + "'");
	   Map<String, Event> map = new LinkedHashMap<String, Event>();
	   try {
	      for(Event es : (List<Event>)q.execute()) {
	         map.put(es.getName(), es);
	      }
	      return map;
	   }finally{
	      q.closeAll();
	   }
	}
	public Map<String, Event> getScoreMap(String ownerId){
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try {
			return getScoreMap(pm, ownerId);
		}finally{
			pm.close();
		}
	}

	public Map<String, Event> getEventMap(){
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try {
			return getEventMap(pm);
		}finally {
			pm.close();
		}
	}
	
	public Map<String, Event> getEventMap(PersistenceManager pm){
		Query q = pm.newQuery(Event.class);
		q.setFilter("isPlayed == false");
		Map<String, Event> map = new LinkedHashMap<String, Event>();
		try{
			for(Event es : (List<Event>)q.execute()) {
				map.put(es.getName(), es);
			}
			return map;
		}finally{
			q.closeAll();
		}
	}
	
	public Filer openRoost() {
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		Query q = pm.newQuery(Filer.class);
		try{
			List<Filer> list = (List<Filer>)q.execute();
			return list.get(0);
		}finally{
			q.closeAll();
			pm.close();
		}
	}
	
	public void registMember(Member resume, Event score){
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try{
			score.setNowMember(score.getNowMember() + 1);
			pm.makePersistent(resume);
			pm.makePersistent(score);
		}finally{
			pm.close();
		}
	}
	
	public void pingListenPoint(Filer filer, long point) {
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try{
			filer.setListeningPoint(point);
			pm.makePersistent(filer);
		}finally{
			pm.close();
		}
	}
	
	public void sweepEvent() {
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		Query eq = pm.newQuery(Event.class);
		try{
			Calendar atSevenDay = Calendar.getInstance();
			atSevenDay.add(Calendar.DAY_OF_MONTH, -7);
			eq.setFilter("isPlayed == true");
			eq.setFilter("playAt < " + atSevenDay.getTimeInMillis());
			List<Event> list = (List<Event>)eq.execute();
			List<Member> memberList;
			for(Event event : list){
				Query mq = pm.newQuery(Member.class);
				mq.setFilter("eventId == " + event.getId());
				try{
					memberList = (List<Member>) mq.execute();
					for(Member member : memberList){
						member.setDisbandment(true);
					}
					if(0 < memberList.size()){
						pm.makePersistentAll(memberList);
					}
				}finally{
					mq.closeAll();
				}
				event.setEnd(true);
			}
			pm.makePersistentAll(list);
		}finally{
			eq.closeAll();
			pm.close();
		}
	}
	
	public List<Event> getOutDatedEventList(PersistenceManager pm, Long datetime) {
		Query q = pm.newQuery(Event.class);
		q.setFilter("isPlayed == " + false);
		q.setFilter("playAt < " + datetime);
		try{
			return (List<Event>)q.execute();
		}finally{
			q.closeAll();
		}
	}
	
	public void sweepEvent(Event score, Filer filer) {
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		Query q = pm.newQuery(Member.class);
		q.setFilter("scoreId == " + score.getId());
		try{
			List<Member> list = (List<Member>) q.execute();
			score.setEnd(true);
			for(Member member : list){
				member.setDisbandment(true);
			}
			filer.setScoreCount(filer.getScoreCount() - 1);
			pm.makePersistent(filer);
			pm.makePersistentAll(list);
			pm.makePersistent(score);
		}finally{
			q.closeAll();
			pm.close();
		}
	}
	
	public void countdownEvent(Event score, int count){
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try{
			score.setCountdowned(count);
			pm.makePersistent(score);
		}finally{
			pm.close();
		}
	}
	
	public Feed mix(Feed feed){
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		Query q = pm.newQuery(Filer.class);
		try {
			Filer filer = ((List<Filer>)q.execute()).get(0);
			feed.setDeployedAt(filer.getDeployedAt());
			feed.setListeningPoint(filer.getListeningPoint());
			return feed;
		}finally {
			pm.close();
		}
	}
}
