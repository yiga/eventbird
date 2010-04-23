package com.bushytails.eventbird.model;

public class EventImage extends ScoreImage {
	public int getOwnerId() {
		return ownerId;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	@Override
	public boolean isEvent() {
		return true;
	}
	
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	private int ownerId;
	private String ownerName;
}
