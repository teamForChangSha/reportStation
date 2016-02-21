package com.jxxp.service;

import com.jxxp.pojo.User;

public interface UserService {
	/**
	 * 用户登录
	 * 
	 * @param User
	 *            登录的USER对象，只有用户名和密码两个属性有值
	 * @return 用户名以及密码匹配，则返回该用户对象，否则返回NULL
	 */
	User longin(User user);
	
	/**
	 * 更新用户信息
	 * 
	 * @param User
	 *            需要更新信息的USER对象
	 * @return 更新成功返回为true,否则返回false
	 */
	boolean update(User user);
}
