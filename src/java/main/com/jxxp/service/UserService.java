package com.jxxp.service;

import java.util.List;
import java.util.Map;

import com.jxxp.comms.web.Page;
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

	/**
	 * 根据参数集合获取符合条件的用户信息
	 * 
	 * @param page
	 *            分页对象
	 * 
	 * @param params
	 *            条件集合对象
	 * @return 更新成功返回为用户集合,否则返回空列表
	 */
	List<User> getUsersByParams(Page page, Map<String, Object> params);

	/**
	 * 根据用户ID获取该用户信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 获取成功返回为user对象,否则返回null
	 */
	User getUserById(long userId);

	/**
	 * 增加用户信息
	 * 
	 * @param User
	 *            需要增加的USER对象
	 * @return 添加成功返回为true,否则返回false
	 */
	boolean addUser(User user);

	/**
	 * 改变公司所有用户的状态
	 * 
	 * @param companyId
	 * @param userState
	 * @return
	 */
	boolean changeUserStateByCompany(Long companyId, Integer userState);
}
