package com.jxxp.pojo;

import java.util.Date;

/**
 * 用于表达省市地区，有可能扩充至国家
 * 
 * @author cj
 * 
 */
public class OprationLog {
	private long logId;
	private Date logDate;
	private String opration;
	private User oprator;

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getOpration() {
		return opration;
	}

	public void setOpration(String opration) {
		this.opration = opration;
	}

	public User getOprator() {
		return oprator;
	}

	public void setOprator(User oprator) {
		this.oprator = oprator;
	}

}
