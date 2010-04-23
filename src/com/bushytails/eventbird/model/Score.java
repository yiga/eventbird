package com.bushytails.eventbird.model;

import java.io.Serializable;

public class Score implements Serializable{
	public String getMelody() {
		return melody;
	}

	public boolean isCancel() {
		return isCancel;
	}

	public boolean isEvent() {
		return false;
	}

	public boolean isMemberEvent() {
		return false;
	}

	public boolean isRegist() {
		return isRegist;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public void setMelody(String melody) {
		this.melody = melody;
	}

	public void setRegist(boolean isRegist) {
		this.isRegist = isRegist;
	}

	private boolean isCancel = false;
	private boolean isRegist = false;
	private String melody;

	private static final long serialVersionUID = 6673096686292498046L;
}
