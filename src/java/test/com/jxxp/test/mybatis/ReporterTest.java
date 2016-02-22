package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

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

	@Test
	public void getReportByMobile() {
		Reporter reporter1 = getReporter();
		assertTrue(reporterMapper.insert(reporter1) > 0);
		Reporter reporter2 = reporterMapper.getByMobile(reporter1.getMobile());
		reporterMapper.deleteById(reporter1.getReporterId());
		TestUtil.isEqual(reporter1, reporter2);

	}

	public static Reporter getReporter() {
		Reporter reporter = new Reporter();
		reporter.setEmail("8207865622@qq.com");
		reporter.setMobile("13142056499");
		reporter.setName("lisi");
		reporter.setIdNo("432024199405046467X");
		reporter.setReporterId(555);
		return reporter;
	}
}
