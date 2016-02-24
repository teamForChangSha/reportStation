package com.jxxp.pojo;

import java.util.Date;

/**
 * 用来表示某个案子的追加信息<br>
 * 举报者本人、举报单位用户和平台管理员可以为某个案子追加信息
 * 
 * @author pan
 * 
 */
public class CaseComment {
	private long ccId;

	/**
	 * 追加信息的内容
	 */
	private String content;

	/**
	 * 是否是举报人自己的追加，若为1，则owner和ownerCompany字段忽略
	 */
	private int isReporter;

	/**
	 * 发表追加的用户个人信息
	 */
	private User owner;

	/**
	 * 发表追加的用户的所属公司
	 */
	private Company ownerCompany;

	/**
	 * 追加信息发表的时间
	 */
	private Date postTime;

	public long getCcId() {
		return ccId;
	}

	public void setCcId(long ccId) {
		this.ccId = ccId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIsReporter() {
		return isReporter;
	}

	public void setReporter(int isReporter) {
		this.isReporter = isReporter;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Company getOwnerCompany() {
		return ownerCompany;
	}

	public void setOwnerCompany(Company ownerCompany) {
		this.ownerCompany = ownerCompany;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
