package com.jxxp.test;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.pojo.Company;
import com.jxxp.service.CaseService;
import com.jxxp.service.CompanyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class ReportCaseTest {
	@Resource
	private CaseService caseService;
	@Resource
	private CompanyService companyService;

	@Before
	public void init() {

	}

	@Test
	public void testTrackingNo() {
		Company company = null;
		company = companyService.getCompanyById(new Long(1));
		String trackingNo = caseService.getNewTrackingNo(company);
		System.out.println(trackingNo);
		assertNotNull(trackingNo);
	}
}
