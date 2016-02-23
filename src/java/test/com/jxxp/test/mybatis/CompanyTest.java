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
import com.jxxp.pojo.Company;
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
public class CompanyTest {

	@Resource
	private CompanyMapper companyMapper;
	private Company company1;

	@Before
	public void init() {
		company1 = getCompany();
	}

	@Test
	public void saveCompany() {
		int isSuccess = companyMapper.insert(company1);
		assertTrue(isSuccess > 0);
		companyMapper.deleteById(company1.getCompanyId());
	}

	@Test
	public void testGetCompany() {
		companyMapper.insert(company1);
		Company company2 = companyMapper.getById(company1.getCompanyId());
		assertNotNull(company2);
		assertTrue(TestUtil.isEqual(company1, company2));
		companyMapper.deleteById(company1.getCompanyId());
	}

	@Test
	public void testGetCompanyByName() {
		companyMapper.insert(company1);
		Company company2 = companyMapper.findByName(company1.getCompanyName());
		assertNotNull(company2);
		assertTrue(TestUtil.isEqual(company1, company2));
		companyMapper.deleteById(company1.getCompanyId());
	}

	@After
	public void clear() {
		companyMapper.deleteById(company1.getCompanyId());
	}

	public static Company getCompany() {
		Company company = new Company();
		company.setCompanyCode("ZTX");
		company.setCompanyName("company name");
		company.setDescription("规模大");
		company.setPhone("1234567");
		company.setCompanyState(1);
		company.setCompanyType(1);
		return company;
	}
}
