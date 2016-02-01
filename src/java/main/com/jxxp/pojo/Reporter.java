package com.jxxp.pojo;

/**
 * 实名举报者信息<br>
 * 实名举报者在之后依靠手机号码登录，登录时使用临时下发的短信密码进行登录<br>
 * 因为预计实名举报者会非常少，因此暂不预留其他功能
 * 
 * @author pan
 * 
 */
public class Reporter {
	private long reporterId;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 身份证名称
	 */
	private String name;

	/**
	 * 身份证号
	 */
	private String idNo;

	/**
	 * 实名举报者邮箱
	 */
	private String email;

	public long getReporterId() {
		return reporterId;
	}

	public void setReporterId(long reporterId) {
		this.reporterId = reporterId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
