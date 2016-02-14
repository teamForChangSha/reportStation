package com.jxxp.pojo;

public class QuestionInfo {
	private long questId;
	/**
	 * 举报所填问题的关键索引，为维护问题的可读性，程序中应使用此索引值来表示问题
	 */
	private String questKey;

	/**
	 * 问题本身
	 */
	private String quest;

	/**
	 * 该问题的进一步描述
	 */
	private String questDesc;

	/**
	 * 该问题是否必填
	 */
	private int isNeeded;

	public String getQuestKey() {
		return questKey;
	}

	public void setQuestKey(String questKey) {
		this.questKey = questKey;
	}

	public String getQuest() {
		return quest;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}

	public String getQuestDesc() {
		return questDesc;
	}

	public void setQuestDesc(String questDesc) {
		this.questDesc = questDesc;
	}

	public long getQuestId() {
		return questId;
	}

	public void setQuestId(long questId) {
		this.questId = questId;
	}

	public int getIsNeeded() {
		return isNeeded;
	}

	public void setIsNeeded(int isNeeded) {
		this.isNeeded = isNeeded;
	}
}
