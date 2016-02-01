package com.jxxp.pojo;

/**
 * 用于表示用户的举报中填写的一项具体信息/问题<br>
 * 
 * @author pan
 * 
 */
public class ReportAnswer {
	private long rdId;

	/**
	 * 所属举报案件的id值，此处使用id值不使用对象，为防止ReportCase循环
	 */
	private long rcId;

	/**
	 * 用于指代具体问题的键值，键值的取值在QuestionInfo
	 */
	private String questKey;

	/**
	 * 用户所填的信息
	 */
	private String questValue;

	public long getRdId() {
		return rdId;
	}

	public void setRdId(long rdId) {
		this.rdId = rdId;
	}

	public long getRcId() {
		return rcId;
	}

	public void setRcId(long rcId) {
		this.rcId = rcId;
	}

	public String getQuestKey() {
		return questKey;
	}

	public void setQuestKey(String questKey) {
		this.questKey = questKey;
	}

	public String getQuestValue() {
		return questValue;
	}

	public void setQuestValue(String questValue) {
		this.questValue = questValue;
	}

}
