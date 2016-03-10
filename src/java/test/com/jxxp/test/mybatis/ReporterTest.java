package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.ReporterMapper;
import com.jxxp.pojo.Reporter;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class ReporterTest {

	@Resource
	private ReporterMapper reporterMapper;
	private Reporter addReporter;

	/**
	 * 增加一个举报者
	 */
	@Test
	public void addReporter() {
		addReporter = getReporter();
		int count = reporterMapper.insert(addReporter);
		assertTrue(count == 1);
		Reporter getReporter = reporterMapper.getById(addReporter.getReporterId());
		assertTrue(TestUtil.isEqual(addReporter, getReporter));

	}

	@Test
	public void getReportByMobile() {
		addReporter = getReporter();
		int count = reporterMapper.insert(addReporter);
		assertTrue(count == 1);
		Reporter getReporter = reporterMapper.getByMobile(addReporter.getMobile());
		reporterMapper.deleteById(addReporter.getReporterId());
		assertTrue(TestUtil.isEqual(addReporter, getReporter));

	}

	@Test
	public void updateReporter() {
		addReporter = getReporter();
		reporterMapper.insert(addReporter);
		Reporter getReporter = reporterMapper.getById(addReporter.getReporterId());
		getReporter.setMobile("12345678908");
		reporterMapper.update(getReporter);
		Reporter getReporter2 = reporterMapper.getById(addReporter.getReporterId());

		assertTrue(!TestUtil.isEqual(addReporter, getReporter2));

	}

	@After
	public void clear() {
		if (addReporter != null) {
			reporterMapper.deleteById(addReporter.getReporterId());
		}
	}

	public static Reporter getReporter() {
		Reporter reporter = new Reporter();
		reporter.setEmail("8207865622@qq.com");
		reporter.setMobile("13142056499");
		reporter.setName("lisi");
		reporter.setIdNo("432024199405046467X");
		return reporter;
	}
}
