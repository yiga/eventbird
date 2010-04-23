package com.bushytails.eventbird.model;

public class ScoreImage {
	public String getBirdName() {
		return birdName;
	}
	
	public String getContents() {
		return contents;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public Long getScoreId() {
		return scoreId;
	}
	
	public boolean isEvent() {
		return false;
	}
	
	public boolean isMember() {
		return false;
	}
	
	public void setBirdName(String birdName) {
		this.birdName = birdName;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public void setScoreId(Long scoreId) {
		this.scoreId = scoreId;
	}

	private String birdName = "";
	private String contents = "";
	private int pageCount = 0;
	private Long scoreId = new Long(0);
}
