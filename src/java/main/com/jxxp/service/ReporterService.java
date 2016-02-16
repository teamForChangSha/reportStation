package com.jxxp.service;

import com.jxxp.pojo.Reporter;
/*
 * @author cj
 */
public interface ReporterService {
	/**
	 * 根据手机号获取实名用户信息
	 * 
	 * @param mobile
	 *            实名用户的手机
	 * @return 发送成功返回Reporter对象，否则返回null
	 */
	Reporter getByMobile(String mobile);
	
	/**
	 * 保存实名用户信息
	 * 
	 * @param reporter
	 *            实名用户对象
	 * @return 保存成功返回true，否则返回false
	 */
	boolean addReporter(Reporter reporter);
}
