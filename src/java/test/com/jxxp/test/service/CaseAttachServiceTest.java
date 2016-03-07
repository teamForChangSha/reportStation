package com.jxxp.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.CaseAttachMapper;
import com.jxxp.pojo.CaseAttach;
import com.jxxp.service.CaseAttachService;
import com.jxxp.test.mybatis.CaseAttachTest;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class CaseAttachServiceTest {

	@Resource
	private CaseAttachService caseAttachService;
	@Resource
	private CaseAttachMapper caseAttachMapper;
	private CaseAttach caseAttach1;

	public static String trackingNo = "GCX20160502";

	@Before
	public void init() {
		caseAttach1 = CaseAttachTest.getAttach();
	}

	/**
	 * 通过案件跟踪号查询案件 正常情况：有案件跟踪号
	 */
	@Test
	public void getCaseAttachByTrackingNo() {
		caseAttachService.addCaseAttach(caseAttach1);

		List<CaseAttach> caseAttaches = caseAttachService.getCaseAttachByTrackingNo(trackingNo);
		assertTrue(caseAttaches.size() > 0);
		int count = 0;
		for (int i = 0; i < caseAttaches.size(); i++) {
			if (TestUtil.isEqual(caseAttaches.get(i), caseAttach1)) {
				count++;
			}
		}
		assertTrue(count == 1);

	}

	/**
	 * 通过案件跟踪号查询案件 正常情况:案件号为空null
	 */
	@Test
	public void getCaseAttachByNoTracking() {
		caseAttachService.addCaseAttach(caseAttach1);
		List<CaseAttach> caseAttaches = caseAttachService.getCaseAttachByTrackingNo(null);
		assertTrue(caseAttaches.size() == 0);
		assertNotNull(caseAttaches);

	}

	/**
	 * 通过案件跟踪号查询案件 正常情况:错误的案件跟踪号
	 */
	@Test
	public void getCaseAttachByErrorTracking() {
		caseAttachService.addCaseAttach(caseAttach1);
		List<CaseAttach> caseAttaches = caseAttachService.getCaseAttachByTrackingNo("ahhedeoe");
		assertTrue(caseAttaches.size() == 0);
		assertNotNull(caseAttaches);

	}

	/**
	 * 保存
	 */
	@Test
	public void addCaseAttach() {
		caseAttachService.addCaseAttach(caseAttach1);
		CaseAttach caseAttach2 = caseAttachMapper.getById(caseAttach1.getCaId());
		assertTrue(caseAttach2 != null);
		assertTrue(TestUtil.isEqual(caseAttach1, caseAttach2));

	}

	@After
	public void clear() {
		caseAttachMapper.deleteById(caseAttach1.getCaId());
	}
}
