package com.jxxp.pojo;

/**
 * 代表企业的一个分支机构，若企业只有一个总部，则总部的相关信息也作为分支机构来保存
 * 
 * @author pan
 * 
 */
public class CompanyBranch {
	private long branchId;

	/**
	 * 所在省份
	 */
	private AreaInfo province;

	/**
	 * 所在市
	 */
	private AreaInfo city;

	/**
	 * 分支机构名称
	 */
	private String branchName;

	/**
	 * 分支机构地址
	 */
	private String address;

	/**
	 * 分支机构联系电话
	 */
	private String phone;

	/**
	 * 分支机构联系人名称
	 */
	private String contactor;

	/**
	 * 分支机构所属公司
	 */
	private Company owner;

	public long getBranchId() {
		return branchId;
	}

	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}

	public AreaInfo getProvince() {
		return province;
	}

	public void setProvince(AreaInfo province) {
		this.province = province;
	}

	public AreaInfo getCity() {
		return city;
	}

	public void setCity(AreaInfo city) {
		this.city = city;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public Company getOwner() {
		return owner;
	}

	public void setOwner(Company owner) {
		this.owner = owner;
	}

}
