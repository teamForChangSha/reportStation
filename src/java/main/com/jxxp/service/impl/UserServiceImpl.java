package com.jxxp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
	public List<User> getUsersByParams(Map<String, String> params) {
		List<User> userList = new ArrayList<User>();
		return userList;
	}

}
