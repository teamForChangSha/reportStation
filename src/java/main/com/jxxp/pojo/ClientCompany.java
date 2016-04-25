package com.jxxp.pojo;

import java.util.Date;

/**
 * 用户标记客户公司，如果是客户公司，则存入client_company表中,暂时只有一个companyId，后期可能扩展
 * 
 * @author gcx
 * 
 */
public class ClientCompany {
	/**
	 * 客户公司id
	 */
	private Long companyId;

	/**
	 * 有效期：年月日
	 */
	private Date expiryDate;

	/**
	 * 成为客户时间
	 */
	private Date createTime;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}