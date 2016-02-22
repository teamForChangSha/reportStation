package com.jxxp.test.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

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

	@Test
	public void saveCompany() {
		Company company = getCompany();
		int isSuccess = companyMapper.insert(company);
		assertTrue(isSuccess > 0);
		companyMapper.deleteById(company.getCompanyId());
	}

	@Test
	public void testGetCompany() {
		Company company1 = getCompany();
		companyMapper.insert(company1);
		Company company2 = companyMapper.getById(company1.getCompanyId());
		assertNotNull(company2);
		assertTrue(TestUtil.isEqual(company1, company2));
		companyMapper.deleteById(company1.getCompanyId());
	}

	@Test
	public void testGetCompanyByName() {
		Company company1 = getCompany();
		companyMapper.insert(company1);
		Company company2 = companyMapper.findByName(company1.getCompanyName());
		assertNotNull(company2);
		assertTrue(TestUtil.isEqual(company1, company2));
		companyMapper.deleteById(company1.getCompanyId());
	}

	public static Company getCompany() {
		Company company = new Company();
		// company.setCompanyId(100000);
		company.setCompanyCode("ZTX");
		company.setCompanyName("company name");
		company.setDescription("规模大");
		company.setPhone("1234567");
		company.setCompanyState(1);
		company.setCompanyType(1);
		return company;
	}
}
