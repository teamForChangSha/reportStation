package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.UserMapper;
import com.jxxp.pojo.User;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
// 添加注释，回滚对数据库的操作
@Transactional
public class UserTest {
	@Resource
	private UserMapper userMapper;

	private User user1;

	@Before
	public void init() {
		user1 = getUser();
	}

	@Test
	public void testAddUser() {
		userMapper.insert(user1);
		User user2 = userMapper.getById(user1.getUserId());
		assertTrue(TestUtil.isEqual(user1, user2));
	}

	@Test
	public void testDelUser() {
		userMapper.insert(user1);
		userMapper.deleteById(user1.getUserId());
		User user2 = userMapper.getById(user1.getUserId());
		assertTrue(user2 == null);
	}

	@Test
	public void testUpdateUser() {
		userMapper.insert(user1);
		User user2 = userMapper.getById(user1.getUserId());
		user2.setLoginName("update name");
		assertTrue(userMapper.update(user2) > 0);
		assertTrue(!TestUtil.isEqual(user1, user2));
		assertTrue(!(user1.getLoginName().equals(user2.getLoginName())));

	}

	@After
	public void clear() {
		userMapper.deleteById(user1.getUserId());
	}

	public static User getUser() {
		User user = new User();
		user.setWorkNo("0564");
		user.setMobile("13142056776");
		user.setUserName("春");
		user.setLoginName("c");
		user.setUserPwd("123");
		user.setUserType(1);
		user.setUserState(1);
		return user;
	}

}
