package com.jxxp.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.CompanyMapper;
import com.jxxp.dao.ReportTypeMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.ReportType;
import com.jxxp.service.ReportTypeService;
import com.jxxp.test.mybatis.CompanyTest;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class reportTypeServiceTest {
	@Resource
	private ReportTypeService reportTypeService;
	@Resource
	private ReportTypeMapper reportTypeMapper;
	@Resource
	private CompanyMapper companyMapper;
	private Company company;

	private List<ReportType> getRtList() {
		ReportType type1 = new ReportType();
		type1.setRtDesc("test desc1");
		type1.setRtTitle("test title1");
		company = CompanyTest.getCompany();
		companyMapper.insert(company);
		type1.setOwner(company);
		List<ReportType> rtList = new ArrayList<ReportType>();
		rtList.add(type1);
		ReportType type2 = new ReportType();
		type2.setRtDesc("test desc2");
		type2.setRtTitle("test title2");
		type2.setOwner(company);
		rtList.add(type2);
		return rtList;
	}

	@Test
	public void getDefTypes() {
		List<ReportType> types = getRtList();
		reportTypeMapper.insert(types.get(0));
		reportTypeMapper.insert(types.get(1));
		reportTypeMapper.deleteByCompanyId(company.getCompanyId());

	}

}
