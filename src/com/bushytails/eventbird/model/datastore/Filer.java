package com.bushytails.eventbird.model.datastore;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Filer {
	public Date getDeployedAt() {
		return deployedAt;
	}
	
	public Long getId() {
		return id;
	}
	
	public long getListeningPoint() {
		return listeningPoint;
	}
	
	public int getScoreCount() {
		return scoreCount;
	}

	public void setDeployedAt(Date deployedAt) {
		this.deployedAt = deployedAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setListeningPoint(long listeningPoint) {
		this.listeningPoint = listeningPoint;
	}

	public void setScoreCount(int scoreCount) {
		this.scoreCount = scoreCount;
	}

	@Persistent
	private Date deployedAt;

	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private Long id;
	
	@Persistent
	private long listeningPoint;

	@Persistent
	private int scoreCount;
}
