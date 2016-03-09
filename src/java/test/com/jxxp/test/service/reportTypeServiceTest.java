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
import com.jxxp.test.mybatis.CompanyTest;
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
	private Company company;
	private List<ReportType> types;

	/**
	 * 获取默认问题类型列表,isStandard=0标准，isStandard=1非标准
	 * 
	 * 1、不添加，获取的默认列表所有类型的值应该为isStandard=0
	 */
	@Test
	public void getDefTypes() {
		List<ReportType> getTypes = reportTypeService.getDefaultList();
		List<ReportType> defTypes = reportTypeMapper.getAllStandard();
		for (int i = 0; i < getTypes.size(); i++) {
			assertTrue(getTypes.get(i).getIsStandard() == 0);
		}
		assertTrue(getTypes.size() == defTypes.size());

	}

	/**
	 * 获取默认问题类型列表,isStandard=0标准，isStandard=1非标准
	 * 
	 * 2、添加非标准的，获取的默认列表与原来的默认列表相同
	 */
	@Test
	public void getDefTypesAddNoStandand() {
		List<ReportType> getTypesBeforAdd = reportTypeService.getDefaultList();
		types = getRtList(1);
		for (int i = 0; i < types.size(); i++) {
			reportTypeMapper.insert(types.get(i));
		}
		List<ReportType> getTypesAfterAdd = reportTypeService.getDefaultList();
		assertTrue(TestUtil.isEqual(getTypesBeforAdd, getTypesAfterAdd));
	}

	/**
	 * 获取默认问题类型列表,isStandard=0标准，isStandard=1非标准
	 * 
	 * 3、添加标准的，获取的默认列表的size=原来的默认列表的size+添加的列表size
	 */
	@Test
	public void getDefTypesAddStandand() {
		List<ReportType> getTypesBeforAdd = reportTypeService.getDefaultList();
		types = getRtList(0);
		for (int i = 0; i < types.size(); i++) {
			reportTypeMapper.insert(types.get(i));
		}
		List<ReportType> getTypesAfterAdd = reportTypeService.getDefaultList();
		assertTrue(getTypesAfterAdd.size() == (getTypesBeforAdd.size() + types.size()));
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
	}

	private List<ReportType> getRtList(int isStandard) {
		List<ReportType> rtList = new ArrayList<ReportType>();
		company = CompanyTest.getCompany();
		companyMapper.insert(company);
		for (int i = 0; i < 2; i++) {
			ReportType type1 = getReportType();
			type1.setIsStandard(isStandard);
			// 非标准的则会添加公司类型
			if (isStandard == 1) {
				type1.setOwner(company);
			}
		}
		return rtList;
	}

	public ReportType getReportType() {
		ReportType type = new ReportType();
		type.setRtDesc("举报工作态度");
		type.setRtTitle("title");
		// 是否为标准类型：0标准，1非标准
		type.setIsStandard(0);
		return type;
	}

}
