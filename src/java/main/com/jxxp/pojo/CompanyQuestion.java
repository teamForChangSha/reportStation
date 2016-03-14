package com.jxxp.pojo;

public class CompanyQuestion {
	/**
	 * 公司id
	 */
	private Long companyId;

	/**
	 * 该问题是否必填
	 */
	private int isNeeded;
	/**
	 * 问题id
	 */
	private Long questId;

	// private QuestionInfo questInfo;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getIsNeeded() {
		return isNeeded;
	}

	public void setIsNeeded(Integer isNeeded) {
		this.isNeeded = isNeeded;
	}

	public Long getQuestId() {
		return questId;
	}

	public void setQuestId(Long questId) {
		this.questId = questId;
	}
}