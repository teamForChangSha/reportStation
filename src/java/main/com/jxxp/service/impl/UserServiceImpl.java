package com.jxxp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.comms.web.Page;
import com.jxxp.dao.UserMapper;
import com.jxxp.pojo.User;
import com.jxxp.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public User longin(User user) {
		return userMapper.login(user.getLoginName(), user.getUserPwd());
	}

	@Override
	public boolean update(User user) {
		return userMapper.update(user) > 0;
	}

	@Override
	public List<User> getUsersByParams(Page page, Map<String, Object> params) {
		String keyWord = (String) params.get("keyWord");
		Long companyId = (Long) params.get("companyId");
		Integer userType = (Integer) params.get("userType");
		Integer userState = (Integer) params.get("userState");
		User user = (User) params.get("user");
		return userMapper.getUsersByParams(page, user, keyWord, companyId, userType, userState);
	}

	@Override
	public User getUserById(long userId) {
		return userMapper.getById(userId);
	}

	@Override
	public boolean addUser(User user) {
		// 判断登入名是否存在
		List<User> isExist = userMapper.getUserByLoginName(user);
		if (isExist.size() > 0) {
			return false;
		}
		// 用户初始状态是新增
		user.setUserState(1);
		return userMapper.insert(user) > 0;
	}

	@Override
	public boolean stopAllUsersByCompanyId(Long companyId) {
		int flag = userMapper.stopAllUsersByCompanyId(companyId);
		if (flag > 0) {
			return true;
		}
		return false;
	}
}
