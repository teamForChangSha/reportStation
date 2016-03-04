package com.jxxp.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.CompanyBranchMapper;
import com.jxxp.dao.CompanyMapper;
import com.jxxp.dao.ReportTypeMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportType;
import com.jxxp.service.CompanyService;
import com.jxxp.service.ReportTypeService;
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
public class CompanyServiceTest {

	@Resource
	private CompanyService companyService;
	@Resource
	private CompanyMapper companyMapper;
	@Resource
	private CompanyBranchMapper companyBranchMapper;
	@Resource
	private ReportTypeMapper reportTypeMapper;
	@Resource
	private ReportTypeService reportTypeService;

	@Ignore
	public void saveCompany() {
		Company company = getCompany();
		boolean isSuccess = companyService.saveCompanyInfo(company);
		assertTrue(isSuccess);
		companyMapper.deleteById(company.getCompanyId());
	}

	/**
	 * 存储公司的同时必须存储分支机构
	 */
	@Ignore
	public void saveCompanyBranch() {
		Company company = getCompany();
		boolean isSuccess = companyService.saveCompanyInfo(company);
		List<CompanyBranch> branchList = companyBranchMapper
				.getAllByCompany(company.getCompanyId());
		assertNotNull(branchList);
		assertTrue(isSuccess);
		companyMapper.deleteById(company.getCompanyId());
	}

	/**
	 * 公司选择了问题
	 */
	@Ignore
	public void getCompanyQuestions() {
		Company company = companyService.getAllCompanyList().get(0);
		Map<String, QuestionInfo> qMap = companyService.getCompanyQuestions(company);
		assertNotNull(qMap);
	}

	/**
	 * 公司添加问题类型，一次性提交多个问题类型List
	 */
	@Ignore
	public void testAddCompanyTypes() {
		List<ReportType> rtList = getRtList();
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		assertTrue(companyService.saveCompanyReportType(company, rtList));
		companyMapper.deleteById(company.getCompanyId());
		reportTypeMapper.deleteByCompanyId(company.getCompanyId());
	}

	/**
	 * 获取公司的问题类型列表
	 */
	@Test
	public void testGetCompanyTypes() {
		List<ReportType> rtList = getRtList();
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		assertTrue(companyService.saveCompanyReportType(company, rtList));
		assertTrue(companyService.getCompanyReportType(company).size() > 0);
		companyMapper.deleteById(company.getCompanyId());
		reportTypeMapper.deleteByCompanyId(company.getCompanyId());
	}

	/**
	 * 获取公司的问题类型列表,company=null,获取默认的列表
	 */
	@Test
	public void testGetTypesWithNo() {
		Company company = new Company();
		List<ReportType> types = companyService.getCompanyReportType(company);
		List<ReportType> types2 = reportTypeService.getDefaultList();
		assertTrue(TestUtil.isEqual(types, types2));
		assertTrue(companyService.getCompanyReportType(company).size() > 0);
	}

	/**
	 * 获取公司的问题类型列表,没有属于该公司的自定义类型
	 */
	@Test
	public void testTypesWithNoId() {
		Company company = new Company();
		company.setCompanyId(new Long(88));
		List<ReportType> types = companyService.getCompanyReportType(company);
		assertNotNull(types);
		assertTrue(types.size() == 0);
	}

	/**
	 * TODO 通过公司对象中含公司其他信息，看是否取的到公司的其他信息
	 */
	@Test
	public void getCompanyById() {
		System.out.println("----"
				+ companyMapper.getById(new Long(1)).getOtherInfo().getServiceProtocol());
	}

	private List<ReportType> getRtList() {
		ReportType type1 = new ReportType();
		type1.setRtDesc("test desc1");
		type1.setRtTitle("test title1");
		List<ReportType> rtList = new ArrayList<ReportType>();
		rtList.add(type1);
		ReportType type2 = new ReportType();
		type2.setRtDesc("test desc2");
		type2.setRtTitle("test title2");
		rtList.add(type2);
		return rtList;
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
