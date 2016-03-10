package com.jxxp.test.service;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.ReporterMapper;
import com.jxxp.pojo.Reporter;
import com.jxxp.service.ReporterService;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class ReporterServiceTest {
	@Resource
	private ReporterService reporterService;
	@Resource
	private ReporterMapper reporterMapper;

	private Reporter addReporter;

	/**
	 * 增加一个举报者
	 */
	@Test
	public void addReporter() {
		addReporter = getReporter();
		boolean flag = reporterService.addReporter(addReporter);
		assertTrue(flag);
		Reporter getReporter = reporterMapper.getById(addReporter.getReporterId());
		assertTrue(TestUtil.isEqual(addReporter, getReporter));

	}

	/**
	 * 增加一个举报者 电话号码重复,应该抛出异常
	 */
	@Ignore
	public void addReporterWithSameMoblie() {
		for (int i = 0; i < 2; i++) {
			Reporter addReporter = getReporter();
			reporterService.addReporter(addReporter);
		}

	}

	/**
	 * 增加一个举报者 电话号码为空
	 */
	@Ignore
	public void addReporterWithNoMoblie() {
		for (int i = 0; i < 2; i++) {
			Reporter addReporter = getReporter();
			addReporter.setMobile(null);
			reporterService.addReporter(addReporter);
		}

	}

	/**
	 * 号码存在
	 */
	@Test
	public void getReportByMobile() {
		addReporter = getReporter();
		boolean flag = reporterService.addReporter(addReporter);
		assertTrue(flag);
		Reporter getReporter = reporterService.getByMobile(addReporter.getMobile());
		TestUtil.isEqual(addReporter, getReporter);

	}

	/**
	 * 号码不存在
	 */
	@Test
	public void getReportWithNotExistMobile() {
		addReporter = getReporter();
		boolean flag = reporterService.addReporter(addReporter);
		assertTrue(flag);
		Reporter getReporter = reporterService.getByMobile(addReporter.getMobile() + "55");
		assertTrue(getReporter == null);
	}

	/**
	 * 号码为空串或者为null，实际上不会允许这种情况出现，一旦存储就应该有号码
	 */
	@Ignore
	public void getReportWithNullMobile() {
		Reporter getReporter = reporterService.getByMobile(null);
		assertTrue(getReporter == null);
		Reporter getReporter2 = reporterService.getByMobile("");
		assertTrue(getReporter2 == null);
	}

	@After
	public void clear() {
		if (addReporter != null) {
			reporterMapper.deleteById(addReporter.getReporterId());
		}
	}

	private Reporter getReporter() {
		Reporter reporter = new Reporter();
		reporter.setEmail("8207865622@qq.com");
		reporter.setMobile("13142056499");
		reporter.setName("lisi");
		reporter.setIdNo("432024199405046467X");
		return reporter;
	}
}
