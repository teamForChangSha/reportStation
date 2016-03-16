package com.jxxp.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.jxxp.dao.CompanyOtherMapper;
import com.jxxp.dao.ReportTypeMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.CompanyOther;
import com.jxxp.pojo.CompanyWholeInfo;
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
	@Resource
	private CompanyOtherMapper companyOtherMapper;

	@Ignore
	public void saveCompany() {
		Company company = getCompany();
		boolean isSuccess = companyService.saveCompanyInfo(company);
		assertTrue(isSuccess);
		companyMapper.deleteById(company.getCompanyId());
	}

	/**
	 * 保存Company(公司基本信息)和CompanyOther(其他信息)
	 */
	@Ignore
	public void saveWholeCompany() {
		Company company = CompanyTest.getCompany();
		// companyMapper.insert(company);
		CompanyOther other = getCompanyOther(company);
		CompanyWholeInfo whole = new CompanyWholeInfo();
		whole.setCompany(company);
		whole.setCompanyOther(other);

	}

	/**
	 * 通过公司名字模糊查询,当名字为空则查询所有,由于公司数量庞大，为了快速搜索，至查询出来对象的 company_id,
	 * company_name两个属性 因此断言不比较存取对象是否相等
	 * 
	 * 1、公司名字中含有该关键字
	 */
	@Ignore
	public void getCompanyByName() {
		String name = "好办公";
		Company company = getCompany();
		company.setCompanyName(name);
		companyService.saveCompanyInfo(company);
		List<Company> comList = companyService.getCompanyByName("好");
		int count = 0;
		for (int i = 0; i < comList.size(); i++) {
			Company com = comList.get(i);
			if (com.getCompanyName().equals(company.getCompanyName())) {
				count++;
			}
		}
		assertTrue(comList.size() > 0);
		assertTrue(count == 1);
		companyMapper.deleteById(company.getCompanyId());

	}

	/**
	 * 通过公司名字模糊查询
	 * 
	 * 2、无关键字，关键字=""，则应该查出所有
	 */
	@Ignore
	public void getCompanyNoName() {
		String name = "好办公";
		Company company = getCompany();
		company.setCompanyName(name);
		companyService.saveCompanyInfo(company);
		List<Company> comList = companyService.getCompanyByName("");
		List<Company> allList = companyMapper.getAllCompany();
		assertTrue(comList.size() == allList.size());
		assertTrue(comList.size() > 0);
		companyMapper.deleteById(company.getCompanyId());
	}

	/**
	 * 通过公司名字模糊查询
	 * 
	 * 2、无关键字，关键字=null
	 */
	@Ignore
	public void getCompanyNullName() {
		String name = "好办公";
		Company company = getCompany();
		company.setCompanyName(name);
		companyService.saveCompanyInfo(company);
		List<Company> comList = companyService.getCompanyByName(null);
		List<Company> allList = companyMapper.getAllCompany();
		assertTrue(comList.size() == allList.size());
		assertTrue(comList.size() > 0);
		companyMapper.deleteById(company.getCompanyId());

	}

	/**
	 * 存储公司的同时必须存储分支机构
	 */
	@Ignore
	public void saveCompanyBranch() {
		Company company = CompanyTest.getCompany();
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
	@Ignore
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
	@Ignore
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
	@Ignore
	public void testTypesWithNoId() {
		Company company = new Company();
		company.setCompanyId(new Long(88));
		List<ReportType> types = companyService.getCompanyReportType(company);
		assertNotNull(types);
		assertTrue(types.size() == 0);
	}

	/**
	 * 通过公司对象中含公司其他信息，看是否取的到公司的其他信息
	 * 
	 * 1、有公司其他信息（有CompanyOther）
	 */
	@Test
	public void getCompanyById() {
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		CompanyOther other = getCompanyOther(company);
		companyOtherMapper.insert(other);
		company.setOtherInfo(other);
		Company getCompany = companyMapper.getById(company.getCompanyId());
		assertTrue(TestUtil.isEqual(company, getCompany));
		assertTrue(TestUtil.isEqual(other, getCompany.getOtherInfo()));
	}

	/**
	 * 通过公司对象中含公司其他信息，看是否取的到公司的其他信息
	 * 
	 * 2、无 公司其他信息（无CompanyOther）
	 */
	@Test
	public void getCompanyByIdWithNoOther() {
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		Company getCompany = companyMapper.getById(company.getCompanyId());
		assertTrue(TestUtil.isEqual(company, getCompany));
		assertNull(getCompany.getOtherInfo());
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
		company.setCompanyName("company name");
		return company;
	}

	public static CompanyOther getCompanyOther(Company company) {
		CompanyOther other = new CompanyOther();
		other.setCompanyId(company.getCompanyId());
		other.setLogoHeight(400);
		other.setLogoPath("upload/testImg");
		other.setLogoWidth(300);
		other.setServiceProtocol("http");
		return other;
	}
}
