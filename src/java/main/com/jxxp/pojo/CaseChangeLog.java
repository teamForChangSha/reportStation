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

}
