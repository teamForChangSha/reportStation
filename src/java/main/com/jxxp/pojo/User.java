package com.jxxp.pojo;

import java.util.Date;

/**
 * 此为后台用户的业务对象，包括客户方的用户和管理方的用户
 * 
 * @author pan
 * 
 */
public class User {
	private long userId;

	/**
	 * 用户类型，字典类型user.type
	 */
	private DictionaryBean userType;

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
	private DictionaryBean userState;

	/**
	 * 用户状态最后一次改变时间
	 */
	private Date stateChanged;
}
