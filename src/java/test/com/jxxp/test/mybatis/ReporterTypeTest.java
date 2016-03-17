package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
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

	private ReportType type;
	private List<ReportType> addList;
	private Company owner;

	@Before
	public void init() {
		type = getReportType();
		owner = CompanyTest.getCompany();
	}

	@Test
	public void insert() {
		companyMapper.insert(owner);
		reportTypeMapper.insert(type);
		ReportType type2 = reportTypeMapper.getById(type.getRtId());
		assertTrue(TestUtil.isEqual(type, type2));
	}

	@Test
	public void getAllTypeByCompany() {
		owner = CompanyTest.getCompany();
		companyMapper.insert(owner);
		addList = new ArrayList<ReportType>();
		for (int i = 0; i < 2; i++) {
			ReportType addType = getReportType();
			addType.setOwner(owner);
			addType.setIsStandard(1);
			addList.add(addType);
			reportTypeMapper.insert(addType);
		}
		List<ReportType> getList = reportTypeMapper.getAllByCompanyId(owner.getCompanyId());
		assertTrue(getList.size() == 2);
		assertTrue(TestUtil.isEqual(addList, getList));

	}

	@Test
	public void deleteByCompanyId() {
		owner = CompanyTest.getCompany();
		companyMapper.insert(owner);
		addList = new ArrayList<ReportType>();
		for (int i = 0; i < 2; i++) {
			ReportType addType = getReportType();
			addType.setOwner(owner);
			addType.setIsStandard(1);
			reportTypeMapper.insert(addType);
			addList.add(addType);
		}
		List<ReportType> getList = reportTypeMapper.getAllByCompanyId(owner.getCompanyId());
		assertTrue(getList.size() == 2);
		reportTypeMapper.deleteByCompanyId(owner.getCompanyId());
		List<ReportType> delList = reportTypeMapper.getAllByCompanyId(owner.getCompanyId());
		assertTrue(delList.size() == 0);

	}

	@After
	public void clear() {
		if (type != null) {
			reportTypeMapper.deleteById(type.getRtId());
		}
		if (owner != null) {
			companyMapper.deleteById(owner.getCompanyId());
		}

		if (addList != null) {
			for (int i = 0; i < addList.size(); i++) {
				reportTypeMapper.deleteById(addList.get(i).getRtId());
				companyMapper.deleteById(addList.get(i).getOwner().getCompanyId());
			}
		}

	}

	public ReportType getReportType() {
		ReportType type = new ReportType();
		type.setRtDesc("举报工作态度");
		type.setRtTitle("title");
		// 是否为标准类型：0标准，1非标准,默认是标准
		type.setIsStandard(0);
		return type;
	}

}
