package com.jxxp.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;
import com.jxxp.service.CaseService;
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
public class ReportCaseTest {
	@Resource
	private CaseService caseService;
	@Resource
	private CompanyService companyService;

	@Before
	public void init() {

	}

	/**
	 * 生成案件追踪号，案件追踪号要求不为空，并且唯一（前一次和下一次不相等）
	 */
	@Test
	public void testTrackingNo() {
		Company company = null;
		company = companyService.getCompanyById(new Long(1));
		String trackingNo1 = caseService.getNewTrackingNo(company);
		String trackingNo2 = caseService.getNewTrackingNo(company);
		assertNotNull(trackingNo1);
		assertTrue(trackingNo1 != trackingNo2);
	}

	@Ignore
	public void testGetCase() {
		String trackingNo = "";
		String accessCode = "";
		ReportCase reportCase = caseService.getReportCase(trackingNo, accessCode);
		assertNotNull(reportCase);
	}

	/**
	 * 案件的存储是否成功、
	 */
	@Test
	public void saveCaseInfo() {
		ReportCase caseInfo = new ReportCase();
		caseInfo.setAccessCode("ZT");
		caseInfo.setTrackingNo("ZT2016021001");
		caseInfo.setDetail(null);
		caseInfo.setCreateTime(new Date());
		caseInfo.setCompany(getCompany());
		caseInfo.setBranch(getBranch());
		caseInfo.setReporter(getReporter());
		assertTrue(caseService.saveCaseInfo(caseInfo));
	}

	private Reporter getReporter() {
		Reporter reporter = new Reporter();
		reporter.setEmail("8207865622@qq.com");
		reporter.setMobile("13142056467");
		reporter.setName("李四");
		reporter.setIdNo("432024199405046467X");
		return reporter;
	}

	private CompanyBranch getBranch() {
		CompanyBranch branch = new CompanyBranch();
		branch.setAddress("中意一路160号");
		branch.setBranchName("分支机构1");
		branch.setPostCode("424207");
		branch.setContactor("张三");
		branch.setOwner(getCompany());
		branch.setCity(null);
		branch.setProvince(null);
		return branch;
	}

	private Company getCompany() {
		List<Company> list = companyService.getAllCompanyList();
		Company company = list.size() > 0 ? list.get(0) : null;
		return company;
	}

}
