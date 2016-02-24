package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

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
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class ReporterTypeTest {

	@Resource
	private ReportTypeMapper reportTypeMapper;
	@Resource
	private CompanyMapper companyMapper;

	@Test
	public void insert() {
		ReportType type1 = getReportType();
		reportTypeMapper.insert(type1);
		ReportType type2 = reportTypeMapper.getById(type1.getRtId());
		reportTypeMapper.deleteById(type2.getRtId());
		System.out.println("===" + type2.getRtDesc());
		assertTrue(TestUtil.isEqual(type1, type2));
		companyMapper.deleteById(type1.getOwner().getCompanyId());

	}

	@Test
	public void getAllTypeByCompany() {
		ReportType type = getReportType();
		Company company = type.getOwner();
		reportTypeMapper.insert(type);
		List<ReportType> list = reportTypeMapper.getAllByCompanyId(company.getCompanyId());
		assertTrue(list.size() > 0);
		companyMapper.deleteById(company.getCompanyId());
		reportTypeMapper.deleteById(type.getRtId());
	}

	public ReportType getReportType() {
		ReportType type = new ReportType();
		Company owner = CompanyTest.getCompany();
		companyMapper.insert(owner);
		type.setOwner(owner);
		type.setRtDesc("举报工作态度");
		type.setRtTitle("title");
		type.setIsStandard(1);
		return type;
	}

}