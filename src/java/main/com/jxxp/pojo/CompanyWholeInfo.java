package com.jxxp.pojo;

public class CompanyWholeInfo {
	private Company company;
	private CompanyOther companyOther;
	
	public CompanyWholeInfo() {
		super();
	}
	public CompanyWholeInfo(Company company, CompanyOther companyOther) {
		super();
		this.company = company;
		this.companyOther = companyOther;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public CompanyOther getCompanyOther() {
		return companyOther;
	}
	public void setCompanyOther(CompanyOther companyOther) {
		this.companyOther = companyOther;
	}
}
