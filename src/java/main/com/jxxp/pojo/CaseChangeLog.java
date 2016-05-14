package com.jxxp.pojo;

import java.util.Date;

/**
 * 用于表示案件的一条改变记录
 * 
 * @author pan
 * 
 */
public class CaseChangeLog {
	private long cclId;

	/**
	 * 记录改变发生时间
	 */
	private Date changeTime;

	/**
	 * 改变前的状态，同caseState
	 */
	private int stateBefore;

	/**
	 * 改变后的状态，同caseState
	 */
	private int stateAfter;

	/**
	 * 状态改变操作人
	 */
	private User operator;

	/**
	 * 状态改变前的处理公司
	 */
	private Company handlerBefore;

	/**
	 * 状态改变后的处理公司
	 */
	private Company handlerAfter;

	/**
	 * 具体操作
	 */
	private String actionName;
	

	public long getCclId() {
		return cclId;
	}

	public void setCclId(long cclId) {
		this.cclId = cclId;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public int getStateBefore() {
		return stateBefore;
	}

	public void setStateBefore(int stateBefore) {
		this.stateBefore = stateBefore;
	}

	public int getStateAfter() {
		return stateAfter;
	}

	public void setStateAfter(int stateAfter) {
		this.stateAfter = stateAfter;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public Company getHandlerBefore() {
		return handlerBefore;
	}

	public void setHandlerBefore(Company handlerBefore) {
		this.handlerBefore = handlerBefore;
	}

	public Company getHandlerAfter() {
		return handlerAfter;
	}

	public void setHandlerAfter(Company handlerAfter) {
		this.handlerAfter = handlerAfter;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	
	

}
