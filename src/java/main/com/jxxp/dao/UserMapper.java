package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.User;

/**
 * 客户方的用户和管理方的用户数据层接口
 * 
 * @author gcx
 * 
 */
public interface UserMapper {

	/**
	 * 登入
	 * 
	 * @param userName
	 * @param userPwd
	 * @return
	 */
	User login(@Param("loginName") String loginName, @Param("userPwd") String userPwd);

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

	/**
	 * 用户查询
	 * 
	 * @param user
	 *            在搜索中暂时没有用该关键字
	 * 
	 * @param keyWord
	 *            关键字匹配用户名和手机号
	 * @param cmopanyId
	 *            公司id
	 * @param userType
	 *            用户类型
	 * @param userState
	 *            用户状态
	 * @return
	 */
	List<User> getUsersByParams(@Param("user") User user, @Param("keyWord") String keyWord,
			@Param("companyId") Long companyId, @Param("userType") Integer userType,
			@Param("userState") Integer userState);

	User getById(long userId);

	int deleteById(long userId);

	List<User> getAllUers();

	int stopAllUsersByCompanyId(Long companyId);

	List<User> getUserByLoginName(User user);

}
