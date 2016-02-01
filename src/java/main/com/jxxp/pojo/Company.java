package com.jxxp.pojo;

import java.util.Date;
import java.util.List;

/**
 * 代表公司的业务对象，管理方作为系统添加的公司，默认便存在
 * 
 * @author pan
 * 
 */
public class Company {
	private long companyId;

	/**
	 * 公司中文名称
	 */
	private String companyName;

	/**
	 * 公司名称代码，程序内部使用此名称作为公司的关键索引（在不使用id的情况下），以兼顾可读性和效率
	 */
	private String companyCode;

	/**
	 * 公司描述信息
	 */
	private String description;

	/**
	 * 公司联系电话，是公司的对外联系电话
	 */
	private String phone;

	/**
	 * 公司类型，字典类型company.type
	 */
	private DictionaryBean companyType;

	/**
	 * 公司当前状态，字典类型company.state
	 */
	private DictionaryBean companyState;

	/**
	 * 公司状态最后一次更改时间
	 */
	private Date stateChanged;

	/**
	 * 公司其他信息，通常情况下无需加载此信息，只在必须时才加载
	 */
	private CompanyOther otherInfo;

	/**
	 * 该公司所选取的问题列表
	 */
	private List<QuestionInfo> questList;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public DictionaryBean getCompanyType() {
		return companyType;
	}

	public void setCompanyType(DictionaryBean companyType) {
		this.companyType = companyType;
	}

	public DictionaryBean getCompanyState() {
		return companyState;
	}

	public void setCompanyState(DictionaryBean companyState) {
		this.companyState = companyState;
	}

	public Date getStateChanged() {
		return stateChanged;
	}

	public void setStateChanged(Date stateChanged) {
		this.stateChanged = stateChanged;
	}

	public CompanyOther getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(CompanyOther otherInfo) {
		this.otherInfo = otherInfo;
	}

	public List<QuestionInfo> getQuestList() {
		return questList;
	}

	public void setQuestList(List<QuestionInfo> questList) {
		this.questList = questList;
	}
}
