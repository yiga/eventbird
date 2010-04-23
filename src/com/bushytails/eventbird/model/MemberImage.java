package com.bushytails.eventbird.model;

public class MemberImage extends ScoreImage {
	public int getMemberId() {
		return memberId;
	}
	
	public String getMemberName() {
		return memberName;
	}

	@Override
	public boolean isMember() {
		return true;
	}
	
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	private int memberId;
	private String memberName;
}
