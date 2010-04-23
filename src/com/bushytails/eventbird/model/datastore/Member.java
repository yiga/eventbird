package com.bushytails.eventbird.model.datastore;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Member {
	public String getContent() {
		return content;
	}
	
	public Long getId() {
		return id;
	}
	
	public int getMemberId() {
		return memberId;
	}

	public String getName() {
		return name;
	}

	public long getEventId() {
		return eventId;
	}

	public boolean isDisbandment() {
		return isDisbandment;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDisbandment(boolean isDisbandment) {
		this.isDisbandment = isDisbandment;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	@Persistent
	private String content;
	
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private Long id;
	
	@Persistent
	private boolean isDisbandment;
	
	@Persistent
	private int memberId;

	@Persistent
	private String name;

	@Persistent
	private long eventId;
}
