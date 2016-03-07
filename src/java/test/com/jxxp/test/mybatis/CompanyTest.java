package com.jxxp.test.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
		Company company2 = companyMapper.getById(company1.getCompanyId());
		assertNotNull(company2);
		assertTrue(TestUtil.isEqual(company1, company2));
	}

	@Test
	public void testDelCompany() {
		companyMapper.insert(company1);
		Company company2 = companyMapper.getById(company1.getCompanyId());
		companyMapper.deleteById(company2.getCompanyId());
		Company company3 = companyMapper.getById(company1.getCompanyId());
		assertTrue(company3 == null);

	}

	/**
	 * 根据公司名字模糊查询，名字为空串则查询所有
	 */
	@Test
	public void getAllByName() {
		Company addCompany = null;
		List<Company> addList = new ArrayList<Company>();
		for (int i = 0; i < 2; i++) {
			addCompany = getCompany();
			companyMapper.insert(addCompany);
			addList.add(addCompany);
		}
		List<Company> getList = companyMapper.getAllByName(addCompany.getCompanyName());
		assertTrue(getList.size() > 0);
		assertTrue(getList.size() >= addList.size());
		// assertTrue(getList.containsAll(addList));
		for (int i = 0; i < 2; i++) {
			companyMapper.deleteById(addList.get(i).getCompanyId());
		}
	}

	@Ignore
	// TODO
	public void getTemplateCompany() {
		Company company = companyMapper.getPlatformCompany();
		System.out.println("---" + company.getCompanyCode());
	}

	@After
	public void clear() {
		companyMapper.deleteById(company1.getCompanyId());
	}

	public static Company getCompany() {
		Company company = new Company();
		company.setCompanyCode("ZTX");
		company.setCompanyName("xx公司");
		company.setDescription("高大上");
		company.setPhone("073136786688");
		company.setCompanyState(1);
		company.setCompanyType(1);
		return company;
	}
}
