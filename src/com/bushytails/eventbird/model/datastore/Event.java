package com.bushytails.eventbird.model.datastore;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Event {
	public String getAuthor() {
		return author;
	}

	public int getCountdowned() {
		return countdowned;
	}

	public Long getId() {
		return id;
	}

	public String getContents() {
		return contents;
	}

	public String getName() {
		return name;
	}

	public int getNowMember() {
		return nowMember;
	}

	public int getNumberOfMembers() {
		return numberOfMembers;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public Date getPlayAt() {
		return new Date(playAt);
	}

	public Date getWriteAt() {
		return new Date(writeAt);
	}

	public boolean isEnd() {
		return isEnd;
	}

	public boolean isPlayed() {
		return isPlayed;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setCountdowned(int countdowned) {
		this.countdowned = countdowned;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNowMember(int nowMember) {
		this.nowMember = nowMember;
	}
	
	public void setNumberOfMembers(int numberOfMembers) {
		this.numberOfMembers = numberOfMembers;
	}
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public void setPlayAt(Date playAt) {
		this.playAt = playAt.getTime();
	}

	public void setPlayed(boolean isPlayed) {
		this.isPlayed = isPlayed;
	}

	public void setWriteAt(Date writeAt) {
		this.writeAt = writeAt.getTime();
	}
	
	/**
	 * イベント作成者のScreenName
	 */
	@Persistent
	private String author;
	
	@Persistent
	private int countdowned;
	
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private Long id;
	
	/**
	 * イベントが終了したかどうか
	 */
	@Persistent
	private boolean isEnd;

	/**
	 * 受付が終了したかどうか
	 */
	@Persistent
	private boolean isPlayed;

	/**
	 * イベント内容
	 */
	@Persistent
	private String contents;
	
	/**
	 * イベントのシステム上の名前
	 */
	@Persistent
	private String name;

	/**
	 * 現在の人数
	 */
	@Persistent
	private int nowMember;

	/**
	 * イベントの許容参加人数
	 */
	@Persistent
	private int numberOfMembers;
	
	/**
	 * ？
	 */
	@Persistent
	private int pageNumber;

	/**
	 * イベント受付終了日
	 */
	@Persistent
	private Long playAt;

	/**
	 * イベント告知作成日
	 */
	@Persistent
	private Long writeAt;
}
