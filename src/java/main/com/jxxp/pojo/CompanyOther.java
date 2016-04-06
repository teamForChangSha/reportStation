package com.jxxp.pojo;

/**
 * 用于保存公司信息中不常用的信息，如服务条款，logo等
 * 
 * @author pan
 * 
 */
public class CompanyOther {
	private long companyId;

	/**
	 * 服务协议的文本形式
	 */
	private String serviceProtocol;

	/**
	 * 服务协议的html形式，预留，用于在页面上展示html效果
	 */
	private String spHtml;

	/**
	 * logo的url
	 */
	private String logoUrl;

	/**
	 * logo的文件系统保存路径
	 */
	private String logoPath;

	/**
	 * logo的宽度，预留
	 */
	private int logoWidth;

	/**
	 * logo的高度，预留
	 */
	private int logoHeight;
	/**
	 * email地址
	 */
	private String email;
	/**
	 * 是否接收举报邮件
	 */
	private int isSend;
	
	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getServiceProtocol() {
		return serviceProtocol;
	}

	public void setServiceProtocol(String serviceProtocol) {
		this.serviceProtocol = serviceProtocol;
	}

	public String getSpHtml() {
		return spHtml;
	}

	public void setSpHtml(String spHtml) {
		this.spHtml = spHtml;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public int getLogoWidth() {
		return logoWidth;
	}

	public void setLogoWidth(int logoWidth) {
		this.logoWidth = logoWidth;
	}

	public int getLogoHeight() {
		return logoHeight;
	}

	public void setLogoHeight(int logoHeight) {
		this.logoHeight = logoHeight;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}
	
	
}
