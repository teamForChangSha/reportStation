package com.jxxp.test.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

	@Test
	public void testUpdateCompany() {
		companyMapper.insert(company1);
		Company company2 = companyMapper.getById(company1.getCompanyId());
		assertTrue(TestUtil.isEqual(company1, company2));
		company2.setStockCode("update stockCode");
		companyMapper.update(company2);
		Company company3 = companyMapper.getById(company1.getCompanyId());
		assertTrue(!TestUtil.isEqual(company1, company3));

	}

	/**
	 * 获取平台公司，内定平台公司的companyId=0;
	 */
	@Ignore
	public void getTemplateCompany() {
		Company company = companyMapper.getPlatformCompany();
		long id = 0;
		// assertTrue(company.getCompanyId() == id);
		assertNotNull(company);
	}

	/**
	 * 批量删除公司
	 */
	@Test
	public void delCompanyByIds() {
		Company company1 = getCompany();
		Company company2 = getCompany();
		companyMapper.insert(company1);
		companyMapper.insert(company2);
		Long[] ids = new Long[2];
		ids[0] = company1.getCompanyId();
		ids[1] = company2.getCompanyId();
		companyMapper.deleteByIds(ids);
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
		company.setIndustries("某某行业");
		company.setStockCode("股票代码");
		company.setCreateTime(new Date(new Timestamp(new Date().getTime() / 1000 * 1000).getTime()));
		return company;
	}
}
