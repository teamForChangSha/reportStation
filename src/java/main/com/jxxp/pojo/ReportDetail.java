package com.jxxp.pojo;

/**
 * 用于表示用户的举报中填写的一项具体信息/问题<br>
 * 
 * @author pan
 * 
 */
public class ReportDetail {
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

}
