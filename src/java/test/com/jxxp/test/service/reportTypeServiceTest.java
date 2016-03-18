package com.jxxp.test.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.CompanyMapper;
import com.jxxp.dao.ReportTypeMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.ReportType;
import com.jxxp.service.ReportTypeService;
import com.jxxp.test.unit.TestUtil;

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

	private ReportType type;
	private Company company;
	private List<ReportType> types;

	/**
	 * 关于公司自定义问题类型的测试在companyServiceTest中
	 * 获取默认标准问题类型列表,包括主要（主要类型isStandard=0）的和次要的类型(次要类型isStandard=1)
	 * 
	 * 1、不添加，取出的数据与直接从数据库取出的一致
	 */
	@Test
	public void getDefTypes() {
		List<ReportType> getTypes = reportTypeService.getDefaultList();
		List<ReportType> defTypes = reportTypeMapper.getAllDefualt();
		for (int i = 0; i < getTypes.size(); i++) {
			assertTrue(getTypes.get(i).getOwner() == null);
		}
		assertTrue(getTypes.size() == defTypes.size());

	}

	/**
	 * 获取默认标准问题类型列表,包括主要（主要类型isStandard=0）的和次要的类型(次要类型isStandard=1)
	 * 
	 * 2、添加次要的
	 */
	@Test
	public void getDefTypesAddDefualt() {
		List<ReportType> getTypesBeforAdd = reportTypeService.getDefaultList();
		types = getRtList(1);
		for (int i = 0; i < types.size(); i++) {
			reportTypeMapper.insert(types.get(i));
		}
		List<ReportType> getTypesAfterAdd = reportTypeService.getDefaultList();
		List<ReportType> defTypes = reportTypeMapper.getAllDefualt();
		assertTrue(getTypesBeforAdd.size() + 2 == getTypesAfterAdd.size());
		assertTrue(getTypesAfterAdd.size() == defTypes.size());

	}

	/**
	 * 获取默认标准问题类型列表,包括主要（主要类型isStandard=0）的和次要的类型(次要类型isStandard=1)
	 * 
	 * 3、添加主要的
	 */
	@Test
	public void getDefTypesAddStandand() {
		List<ReportType> getTypesBeforAdd = reportTypeService.getDefaultList();
		types = getRtList(0);
		for (int i = 0; i < types.size(); i++) {
			reportTypeMapper.insert(types.get(i));
		}
		List<ReportType> getTypesAfterAdd = reportTypeService.getDefaultList();
		List<ReportType> defTypes = reportTypeMapper.getAllDefualt();
		assertTrue(getTypesBeforAdd.size() + 2 == getTypesAfterAdd.size());
		assertTrue(getTypesAfterAdd.size() == defTypes.size());
	}

	/**
	 * 更新主要的
	 * 
	 * 更新前取出来的和添加的相同，更新后取出和添加的不同
	 */
	@Test
	public void updateReportType() {
		type = getReportType(0);
		reportTypeMapper.insert(type);
		ReportType getType = reportTypeMapper.getById(type.getRtId());
		assertTrue(TestUtil.isEqual(getType, type));
		getType.setRtTitle("change title");
		reportTypeService.updateReportType(getType);
		ReportType getType2 = reportTypeMapper.getById(type.getRtId());
		assertTrue(!TestUtil.isEqual(getType2, type));

	}

	/**
	 * 更新次要的
	 * 
	 * 更新前取出来的和添加的相同，更新后取出和添加的不同
	 */
	@Test
	public void updateNotStandardReportType() {
		type = getReportType(1);
		reportTypeMapper.insert(type);
		ReportType getType = reportTypeMapper.getById(type.getRtId());
		assertTrue(TestUtil.isEqual(getType, type));
		getType.setIsStandard(0);
		getType.setOwner(null);
		reportTypeService.updateReportType(getType);
		ReportType getType2 = reportTypeMapper.getById(type.getRtId());
		assertTrue(!TestUtil.isEqual(getType2, type));
	}

	@After
	public void clear() {
		if (types != null && types.size() > 0) {
			for (int i = 0; i < types.size(); i++) {
				reportTypeMapper.deleteById(types.get(i).getRtId());
				if (types.get(i).getOwner() != null) {
					companyMapper.deleteById(types.get(i).getOwner().getCompanyId());
				}
			}
		}
		if (type != null) {
			reportTypeMapper.deleteById(type.getRtId());
			if (type.getOwner() != null) {
				companyMapper.deleteById(type.getOwner().getCompanyId());
			}
		}
		if (company != null) {
			companyMapper.deleteById(company.getCompanyId());
		}
	}

	private List<ReportType> getRtList(int isStandard) {
		List<ReportType> rtList = new ArrayList<ReportType>();
		for (int i = 0; i < 2; i++) {
			ReportType typeObj = getReportType(isStandard);
			rtList.add(typeObj);
		}
		return rtList;
	}

	public ReportType getReportType(int isStandard) {
		ReportType type = new ReportType();
		type.setRtDesc("举报工作态度");
		type.setRtTitle("title");
		type.setIsStandard(isStandard);
		return type;
	}

}
