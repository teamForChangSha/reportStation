package com.jxxp.test.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.CompanyMapper;
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
	@Resource
	private CompanyMapper companyMapper;
	private User user1;

	@Before
	public void init() {
		user1 = getUser();
	}

	@Test
	public void testSaveUser() {
		companyMapper.insert(user1.getUserCompany());
		assertTrue(userMapper.insert(user1) > 0);
		User user2 = userMapper.getById(user1.getUserId());
		assertTrue(TestUtil.isEqual(user1, user2));
	}

	@Test
	public void testUpdateUser() {
		assertTrue(userMapper.insert(user1) > 0);
		User user2 = userMapper.getById(user1.getUserId());
		user2.setLoginName("update name");
		assertTrue(userMapper.update(user2) > 0);
		assertTrue(!TestUtil.isEqual(user1, user2));
	}

	@Test
	public void testLogin() {
		userMapper.insert(user1);
		User user2 = userMapper.login(user1.getLoginName(), user1.getUserPwd());
		assertNotNull(user2);
		assertTrue(!TestUtil.isEqual(user1, user2));
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
		user.setUserCompany(CompanyTest.getCompany());
		return user;
	}

}
