package com.jxxp.dao;

import com.jxxp.pojo.User;

/**
 * 客户方的用户和管理方的用户数据层接口
 * 
 * @author gcx
 * 
 */
public interface UserMapper {

	/**
	 * 添加一个用户（客户方的用户/管理方的用户）
	 * 
	 * @param user
	 * @return
	 */
	int insert(User user);

	/**
	 * 更改用户的信息，比如更改用户的状态、更改密码等操作
	 * 
	 * @param user
	 * @return
	 */
	int update(User user);

	User getById(long userId);

	int deleteById(long userId);

}
