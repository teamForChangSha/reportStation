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
	 * 是否接受举报邮件（0：不接收；1：接收）
	 */
	private int isSend;

	/**
	 * logo的高度，预留
	 */
	private int logoHeight;

	/**
	 * 第一个联系人
	 */
	private String contacts1;
	/**
	 * 第一联系人的邮件
	 */
	private String email1;
	/**
	 * 第二个联系人
	 */
	private String contacts2;
	/**
	 * 第二联系人的邮件
	 */
	private String email2;

	/**
	 * 第三个联系人
	 */
	private String contacts3;
	/**
	 * 第三个联系人的邮件
	 */
	private String email3;

	/**
	 * 发邮件的形式：收到发送/每周发送
	 */
	private int sendType;


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

	public String getContacts1() {
		return contacts1;
	}

	public void setContacts1(String contacts1) {
		this.contacts1 = contacts1;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getContacts2() {
		return contacts2;
	}

	public void setContacts2(String contacts2) {
		this.contacts2 = contacts2;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getContacts3() {
		return contacts3;
	}

	public void setContacts3(String contacts3) {
		this.contacts3 = contacts3;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

	public int getSendType() {
		return sendType;
	}

	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
}
