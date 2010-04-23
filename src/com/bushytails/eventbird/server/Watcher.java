package com.bushytails.eventbird.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bushytails.eventbird.exception.EventbirdException;
import com.bushytails.eventbird.model.Feed;
import com.bushytails.eventbird.model.Score;
import com.bushytails.eventbird.model.ScoreImage;
import com.bushytails.eventbird.util.Keeper;
import com.bushytails.eventbird.util.Writer;


@SuppressWarnings("serial")
public class Watcher extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException
	{
		try {
			Feed feed = makeFeed(req);
			Eventbird bird = new Eventbird(feed);
			
			new Keeper().sweepEvent();
			
			System.out.println("wake up");
			bird.wakeupOnRoost();
			
			bird.checkEventCountDown();
			
			System.out.println("over look");
			bird.checkScore();
			
			System.out.println("listen");
			List<ScoreImage> imageList = bird.listenTweet();
			System.out.println(imageList.size());
			System.out.println("write score");
			List<Score> scoreList = Writer.writeEventScore(
				imageList, feed.getEventCode(), feed.getEntryCode(), bird.getLocale());
			
			System.out.println("chorus");
			List<Score> leftoverScoreList = bird.chorusTweet(scoreList);
			
			System.out.println("sleep");
			bird.sleepOnRoost(leftoverScoreList);
		} catch(EventbirdException e) {
			log.log(Level.SEVERE, "missing eventbird", e);
		}
	}
	
	private Feed makeFeed(HttpServletRequest request){
		Feed feed = new Feed();
		feed.setBirdName(getInitParameter("id"));
		feed.setSecretHumming(getInitParameter("pass"));
		feed.setEventCode(getInitParameter("request.code.event"));	
		feed.setEntryCode(getInitParameter("request.code.entry"));	
		feed.setEventCount(Integer.valueOf(getInitParameter("event.parallel.count")));	
		feed.setPagingCount(Integer.valueOf(getInitParameter("paging.count")));	
		feed.setEventRhythm(getInitParameter("countdown.event"));	
		feed.setMemberRhythm(getInitParameter("countdown.member"));
		feed.setLocale(request.getLocale());
		return new Keeper().mix(feed);
	}
	
	private Logger log = Logger.getLogger(Watcher.class.getName());
}
