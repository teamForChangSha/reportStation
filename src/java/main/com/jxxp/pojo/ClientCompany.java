package com.jxxp.pojo;

/**
 * 用户标记客户公司，如果是客户公司，则存入client_company表中,暂时只有一个companyId，后期可能扩展
 * 
 * @author gcx
 * 
 */
public class ClientCompany {
	/**
	 *
	 */
	private Long companyId;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}