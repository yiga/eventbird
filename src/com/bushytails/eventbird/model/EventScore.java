package com.bushytails.eventbird.model;

public class EventScore extends Score {	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public boolean isEvent() {
		return true;
	}
	
	private String userName;
	private Long scoreId;
	private String scoreName;
	
	public Long getScoreId() {
		return scoreId;
	}

	public void setScoreId(Long scoreId) {
		this.scoreId = scoreId;
	}

	public void setScoreName(String scoreName) {
		this.scoreName = scoreName;
	}

	public String getScoreName() {
		return scoreName;
	}
}
