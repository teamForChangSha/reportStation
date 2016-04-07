package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.CompanyMapper;
import com.jxxp.dao.CompanyOtherMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyOther;
import com.jxxp.test.service.CompanyServiceTest;
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
public class CompanyOtherTest {

	@Resource
	private CompanyMapper companyMapper;
	@Resource
	private CompanyOtherMapper companyOtherMapper;
	private CompanyOther other1;

	@Test
	public void saveCompanyOther() {
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		CompanyOther other = CompanyServiceTest.getCompanyOther(company);
		companyOtherMapper.insert(other);
		CompanyOther other2 = companyOtherMapper.getByCompanyId(company.getCompanyId());
		assertTrue(TestUtil.isEqual(other, other2));

	}

	@Test
	public void updateCompanyOther() {
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		CompanyOther other = CompanyServiceTest.getCompanyOther(company);
		companyOtherMapper.insert(other);
		CompanyOther other2 = companyOtherMapper.getByCompanyId(company.getCompanyId());
		other2.setContacts1("xiaogu");
		CompanyOther other3 = companyOtherMapper.getByCompanyId(company.getCompanyId());
		assertTrue(!TestUtil.isEqual(other, other3));
	}
}
