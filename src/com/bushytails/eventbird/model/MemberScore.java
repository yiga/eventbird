package com.bushytails.eventbird.model;

public class MemberScore extends Score {
	public String getUserName() {
		return userName;
	}

	@Override
	public boolean isMemberEvent() {
		return true;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String userName;
}
