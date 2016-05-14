package com.jxxp.pojo;

import java.util.Date;

/**
 * 此为后台用户的业务对象，包括客户方的用户和管理方的用户
 * 
 * @author pan
 * 
 */
public class User {
	//试用
	public static final int USER_TYPE_TEST = 1;
	//公司
	public static final int USER_TYPE_COMPANY = 2;
	//系统操作员
	public static final int USER_TYPE_OPRATOR = 3;
	//超级管理员
	public static final int USER_TYPE_SUPER = 4;

	//新建用户
	public static final int USER_STATE_NEW = 1;
	//有效用户
	public static final int USER_STATE_ACTIVE = 2;
	//停用用户
	public static final int USER_STATE_STOP = 3;
	//注销用户
	public static final int USER_STATE_OFF = 4;
	
	
	private long userId;

	/**
	 * 用户类型，字典类型user.type
	 */
	private int userType;

	/**
	 * 登录名称，仅限英文和数字
	 */
	private String loginName;

	/**
	 * 登录密码，有密码合法规则
	 */
	private String userPwd;

	/**
	 * 用户显示名称
	 */
	private String userName;

	/**
	 * 用户所属公司，用户只需要所属公司，无需所属分支机构<br>
	 * 管理方也作为一个公司存在，若用户属于管理方员工，只需所属公司为管理方即可
	 */
	private Company userCompany;

	/**
	 * 联系手机，需要符合手机号码构成规则，目前不支持国外手机
	 */
	private String mobile;

	/**
	 * 所属公司工号
	 */
	private String workNo;

	/**
	 * 用户备注信息
	 */
	private String remark;

	/**
	 * 用户当前状态，字典类型user.state
	 */
	private int userState;

	/**
	 * 用户状态最后一次改变时间
	 */
	private Date stateChanged;
	/**
	 * 邮件地址
	 */
	private String email;
	/**
	 * 办公室电话
	 */
	private String phone;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 微信号
	 */
	private String weixin;
	/**
	 * 部门
	 */
	private String department;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 用户有效期
	 */
	private Date expiryDate;
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Company getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(Company userCompany) {
		this.userCompany = userCompany;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStateChanged() {
		return stateChanged;
	}

	public void setStateChanged(Date stateChanged) {
		this.stateChanged = stateChanged;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getUserState() {
		return userState;
	}

	public void setUserState(int userState) {
		this.userState = userState;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", loginName=" + loginName
				+ ", userCompany=" + userCompany + "]";
	}
	
	
}
