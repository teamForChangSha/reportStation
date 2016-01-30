package com.jxxp.service;

public interface MobileService {

	/**
	 * 发送验证码短信，暂不使用，保留
	 * 
	 * @param mobile
	 *            要发送验证码的手机
	 * @return 发送成功返回true，否则返回false
	 */
	boolean sendVerifySMS(String mobile);

	/**
	 * 发送临时登录密码，实名举报人登录时使用
	 * 
	 * @param mobile
	 *            要发送临时密码的手机号码
	 * @return 发送成功返回true，否则返回false
	 */
	boolean sendTempPwd(String mobile);
}
