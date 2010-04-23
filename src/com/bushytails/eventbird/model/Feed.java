package com.bushytails.eventbird.model;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Feed {
	public String getBirdName() {
		return birdName;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public String getEventCode() {
		return eventCode;
	}

	public int getEventCount() {
		return eventCount;
	}

	public int[] getEventRhythm() {
		return eventRhythm;
	}

	public Locale getLocale() {
		return locale;
	}

	public int[] getMemberRhythm() {
		return memberRhythm;
	}

	public int getPagingCount() {
		return pagingCount;
	}

	public String getSecretHumming() {
		return secretHumming;
	}

	public void setBirdName(String birdName) {
		this.birdName = birdName;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}

	public void setEventRhythm(String eventRhythm) {
		this.eventRhythm = readEventRhythm(eventRhythm);
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setMemberRhythm(String memberRhythm) {
		this.memberRhythm = readMemberRhythm(memberRhythm);
	}

	public void setPagingCount(int pagingCount) {
		this.pagingCount = pagingCount;
	}

	public void setSecretHumming(String secretHumming) {
		this.secretHumming = secretHumming;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	private int[] readEventRhythm(String note) {
		String[] notes = note.split(",");
		int[] rhythm = new int[notes.length];
		
		for(int i = 0; i < notes.length; i++){
			rhythm[i] = Integer.valueOf(notes[i]);
		}
		return rhythm;
	}
	private int[] readMemberRhythm(String note) {
		String[] notes = note.split(",");
		int[] rhythm = new int[notes.length];
		int adding = 0;
		for(int i = 0; i < notes.length; i++){
			rhythm[i] = Integer.valueOf(notes[i]) - adding;
			adding += rhythm[i];
		}
		return rhythm;
	}
	private String birdName;
	private String entryCode;
	private String eventCode;
	private int eventCount;
	private int[] eventRhythm;
	private Locale locale;
	private int[] memberRhythm;
	private int pagingCount;
	private Long listeningPoint;
	private int nowEventCount;
	private Date deployedAt;
	
	public Date getDeployedAt() {
		return deployedAt;
	}

	public void setDeployedAt(Date deployedAt) {
		this.deployedAt = deployedAt;
	}

	public int getNowEventCount() {
		return nowEventCount;
	}

	public void setNowEventCount(int nowEventCount) {
		this.nowEventCount = nowEventCount;
	}

	public Long getListeningPoint() {
		return listeningPoint;
	}

	public void setListeningPoint(Long listeningPoint) {
		this.listeningPoint = listeningPoint;
	}
	private String secretHumming;
}
