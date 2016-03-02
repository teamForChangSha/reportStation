package com.jxxp.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jxxp.pojo.Company;
import com.jxxp.pojo.User;
import com.jxxp.service.UserService;
import com.jxxp.test.mybatis.CompanyTest;
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
public class UserServiceTest {
	@Resource
	private UserService userService;

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
	public void addUser() {
		boolean flag = userService.addUser(user1);
		assertTrue(flag);
		User user = userService.getUserById(user1.getUserId());
		assertTrue(TestUtil.isEqual(user1, user));
	}

	@Test
	public void delUser() {
		boolean flag = userService.addUser(user1);
		assertTrue(flag);
		User saveUser = userService.getUserById(user1.getUserId());
		userMapper.deleteById(user1.getUserId());
		User delUser = userService.getUserById(user1.getUserId());
		assertNotNull(saveUser);
		assertNull(delUser);
	}

	/**
	 * 测试用户登入
	 * 
	 * 1.正确的登入名和密码
	 */
	@Test
	public void loginWithRight() {
		userMapper.insert(user1);
		User user2 = userService.longin(user1);
		assertTrue(TestUtil.isEqual(user1, user2));
	}

	/**
	 * 测试用户登入
	 * 
	 * 2.用户名为null，密码正确
	 */
	@Test
	public void loginWithNameNull() {
		userMapper.insert(user1);
		user1.setLoginName(null);
		User user2 = userService.longin(user1);
		assertNull(user2);
		assertTrue(!TestUtil.isEqual(user1, user2));
	}

	/**
	 * 测试用户登入
	 * 
	 * 3.用户名为空串，密码正确
	 */
	@Test
	public void loginWithNameEmpty() {
		userMapper.insert(user1);
		user1.setLoginName("");
		User user2 = userService.longin(user1);
		assertNull(user2);
		assertTrue(!TestUtil.isEqual(user1, user2));
	}

	/**
	 * 测试用户登入
	 * 
	 * 4.密码为null，用户名正确
	 */
	@Test
	public void loginWithPswNull() {
		userMapper.insert(user1);
		user1.setUserPwd(null);
		User user2 = userService.longin(user1);
		assertNull(user2);
	}

	/**
	 * 测试用户登入
	 * 
	 * 5.密码为空串，用户名正确
	 */
	@Test
	public void loginWithPswEmpty() {
		userMapper.insert(user1);
		user1.setUserPwd("");
		User user2 = userService.longin(user1);
		assertNull(user2);
	}

	/**
	 * 测试用户登入
	 * 
	 * 6、错误的登入名和正确的密码
	 */
	@Test
	public void loginWithErroeName() {
		userMapper.insert(user1);
		user1.setLoginName("错误名");
		User user2 = userService.longin(user1);
		assertNull(user2);
	}

	/**
	 * 测试用户登入
	 * 
	 * 7、错误的登入名和错误的密码
	 */
	@Test
	public void loginWithErrorNameAndPws() {
		userMapper.insert(user1);
		user1.setLoginName("错误名");
		user1.setUserPwd("错误密码");
		User user2 = userService.longin(user1);
		assertNull(user2);
	}

	/**
	 * 更新User
	 * 
	 * 1、更新某个字段，这里更改用户登入名字,登入名不为空
	 */
	@Test
	public void updateUserName() {
		assertTrue(userMapper.insert(user1) > 0);
		User user2 = userMapper.getById(user1.getUserId());
		user2.setLoginName("update name");
		assertTrue(userService.update(user2));
		User user3 = userMapper.getById(user1.getUserId());
		assertTrue(user3.getLoginName().equals("update name"));
	}

	/**
	 * 更新User ???此处有问题 如果不调用update,断言错误？？？
	 * 
	 * 2、更改某个属性，属性为null则不更改
	 */
	@Test
	public void updateUserWithNullAttr() {
		assertTrue(userMapper.insert(user1) > 0);
		User user2 = userMapper.getById(user1.getUserId());
		user2.setLoginName(null);
		userService.update(user2);
		User user3 = userMapper.getById(user1.getUserId());
		System.out.println("loginName=====" + user3.getLoginName() + "===id=" + user1.getUserId());
		assertTrue(TestUtil.isEqual(user1, user3));
	}

	/**
	 * 更新User
	 * 
	 * 3、更改某个属性，属性为空串则不更改
	 */
	@Test
	public void updateUserWithEmptyAttr() {
		assertTrue(userMapper.insert(user1) > 0);
		User user2 = userMapper.getById(user1.getUserId());
		user2.setLoginName("");
		userService.update(user2);
		User user3 = userMapper.getById(user1.getUserId());
		assertTrue(TestUtil.isEqual(user1, user3));
	}

	/**
	 * 测试条件查询用户，根据关键字(用户名or手机号),公司id,用户状态和用户类型
	 * 
	 * 1、所有字段都为空则相当于查询所有,与获取所有的用户数目相同且对象的值相同
	 */
	@Test
	public void getUsersWithNoParam() {
		userMapper.insert(user1);
		List<User> usersList1 = userService.getUsersByParams(new HashMap<String, Object>());
		assertTrue(usersList1.size() > 0);
		List<User> usersList2 = userMapper.getAllUers();
		assertTrue(TestUtil.isEqual(usersList1, usersList2));
		assertTrue(usersList1.size() == usersList2.size());
	}

	/**
	 * 测试条件查询用户，根据关键字(用户名or手机号),公司id,用户状态和用户类型
	 * 
	 * 2、含有所有条件,用户名完整（用户名唯一）
	 */
	@Test
	public void getUsersWithAllParams() {
		userMapper.insert(user1);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWord", user1.getUserName());
		params.put("companyId", user1.getUserCompany().getCompanyId());
		params.put("userType", user1.getUserType());
		params.put("userState", user1.getUserState());
		List<User> usersList1 = userService.getUsersByParams(params);
		assertTrue(usersList1.size() == 1);
	}

	/**
	 * 测试条件查询用户，根据关键字(用户名or手机号),公司id,用户状态和用户类型
	 * 
	 * 2、含有所有条件,手机完整（手机号唯一）
	 */
	@Test
	public void getUsersWithAllParams2() {
		assertTrue(userService.addUser(user1));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWord", user1.getMobile());
		params.put("companyId", user1.getUserCompany().getCompanyId());
		params.put("userType", user1.getUserType());
		params.put("userState", user1.getUserState());
		List<User> usersList1 = userService.getUsersByParams(params);
		assertTrue(usersList1.size() == 1);
	}

	/**
	 * 测试条件查询用户，根据关键字（用户名or手机号含有该关键字的）,公司id,用户状态和用户类型
	 * 
	 * 3、用户名含有关键字
	 */
	@Test
	public void getUsersWithKeyName() {
		List<User> users = getUsers();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWord", "Name");
		List<User> users1 = userService.getUsersByParams(params);
		assertTrue(users1.size() >= 2);
		assertTrue(users1.size() >= users.size());
		for (int i = 0; i < users.size(); i++) {
			userMapper.deleteById(users.get(i).getUserId());
		}
	}

	/**
	 * 测试条件查询用户，根据关键字（用户名or手机号含有该关键字的）,公司id,用户状态和用户类型
	 * 
	 * 4、手机中含有关键字
	 */
	@Test
	public void getUsersWithKeyMobile() {
		List<User> users = getUsers();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWord", "134");
		List<User> users1 = userService.getUsersByParams(params);
		assertTrue(users1.size() >= 2);
		assertTrue(users1.size() >= users.size());
		for (int i = 0; i < users.size(); i++) {
			userMapper.deleteById(users.get(i).getUserId());
		}
	}

	@After
	public void clear() {
		userMapper.deleteById(user1.getUserId());
	}

	private List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		User userOne = getUser();
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		userOne.setUserName("oneName");
		userOne.setUserCompany(company);
		userOne.setMobile("13467896540");
		User userTwo = getUser();
		userTwo.setUserName("secondName");
		userTwo.setMobile("13455556666");
		userTwo.setUserCompany(company);
		userMapper.insert(userOne);
		userMapper.insert(userTwo);

		users.add(userOne);
		users.add(userTwo);
		return users;
	}

	public User getUser() {
		User user = new User();
		user.setWorkNo("0564");
		user.setMobile("13142056776");
		user.setUserName("春");
		user.setLoginName("c");
		user.setUserPwd("123");
		user.setUserType(1);
		user.setUserState(1);
		Company userCompany = CompanyTest.getCompany();
		companyMapper.insert(userCompany);
		user.setUserCompany(userCompany);
		return user;
	}

}
