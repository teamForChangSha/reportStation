package com.jxxp.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.DictionaryBeanMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.DictionaryBean;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.service.CompanyService;

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
	private CompanyService companyService;
	@Resource
	private DictionaryBeanMapper dictionaryMapper;

	@Before
	public void init() {

	}

	@Test
	public void testGetCompany() {
		String name = "ztesoft";
		assertNotNull(companyService.getCompany(name));
		assertNotNull(companyService.getCompanyById(new Long(1)));
	}

	@Test
	public void saveCompany() {
		boolean isSuccess = companyService.saveCompanyInfo(getCompany());
		assertTrue(isSuccess);
	}

	@Test
	public void getCompanyQuestions() {
		Company company = new Company();
		company.setCompanyId(1);
		Map<String, QuestionInfo> qMap = companyService.getCompanyQuestions(company);
		assertNotNull(qMap);
	}

	/**
	 * 查询某公司所选择的举报类型集合
	 */
	@Test
	public void TestGetReportType() {
		Company company = companyService.getCompanyById(new Long(1));
		if (company != null) {
			assertTrue(companyService.getCompanyReportType(company).size() >= 0);
		}
	}

	/**
	 * 获取公司在某行政区域的分支机构，行政区域可能为省、可能为市。
	 * 由于业务逻辑的先后（选择省>选择市>显示该市下的分支机构）顺序决定了，实际要传递的行政区域为市级的
	 */
	@Test
	public void TestGetCompanyBranch() {
		List<CompanyBranch> branches = companyService.getCompanyBranchByArea(new Long(10002),
				new Long(1));
		assertTrue(branches.size() >= 0);
	}

	private Company getCompany() {
		Company company = new Company();
		company.setCompanyCode("ZTX");
		company.setCompanyName("xingzhong");
		company.setDescription("规模大");
		company.setPhone("123456");
		DictionaryBean type = dictionaryMapper.getDictionary("company", "type", 3);
		DictionaryBean state = dictionaryMapper.getDictionary("company", "state", 1);
		company.setCompanyState(state);
		company.setCompanyType(type);
		return company;
	}
}
