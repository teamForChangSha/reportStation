package com.jxxp.test.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.CompanyMapper;
import com.jxxp.dao.ReportAnswerMapper;
import com.jxxp.dao.ReportCaseMapper;
import com.jxxp.dao.ReporterMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.ReportAnswer;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class CaseTest {

	@Resource
	private ReportCaseMapper reportCaseMapper;
	@Resource
	private ReportAnswerMapper reportAnswerMapper;
	@Resource
	private ReporterMapper reporterMapper;
	@Resource
	private CompanyMapper companyMapper;

	private ReportCase caseInfo;
	private ReportAnswer answer;
	/**
	 * 案件追踪号
	 */
	private static String trackingNo = "YT2016021040";
	private static String accessCode = "123456";

	@Before
	public void init() {
		caseInfo = getReportCase();
	}

	/**
	 * 案件的存储是否成功
	 */
	@Test
	public void saveCaseInfo() {
		caseInfo = getReportCase();
		assertTrue(reportCaseMapper.insert(caseInfo) > 0);
		ReportCase caseInfo2 = reportCaseMapper.getById(caseInfo.getRcId());
		assertTrue(TestUtil.isEqual(caseInfo, caseInfo2));
	}

	/**
	 * 根据案件号追踪号和密码查询案件
	 */
	@Test
	public void testFindByNo() {
		caseInfo = getReportCase();
		reportCaseMapper.insert(caseInfo);
		ReportCase caseInfo2 = reportCaseMapper.findByNo(caseInfo.getTrackingNo(),
				caseInfo.getAccessCode());
		assertNotNull(caseInfo2);
		assertTrue(TestUtil.isEqual(caseInfo, caseInfo2));

	}

	/**
	 * 查询某个公司的所有案件
	 * 
	 * 给某个公司添加一个案件，要求size>0,且查出来的集合所有案件属于这个公司的
	 */
	@Test
	public void getAllByCompanyId() {
		caseInfo = getReportCase();
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		caseInfo.setCompany(company);
		reportCaseMapper.insert(caseInfo);
		List<ReportCase> list = reportCaseMapper.getAllByCompanyId(company.getCompanyId());
		assertTrue(list.size() > 0);
		for (int i = 0; i < list.size(); i++) {
			assertTrue(TestUtil.isEqual(list.get(i).getCompany(), caseInfo.getCompany()));
		}
	}

	/**
	 * 查件：通过公司的id、案件起始时间、关键字（答案中含有）、类型搜索案件
	 * 
	 * 所有关键字都有
	 */
	@Test
	public void searchByAllParams() {
		caseInfo = getFullReportCase(getReportCase());
		reportCaseMapper.insert(caseInfo);
		answer = getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date createTime = caseInfo.getCreateTime();
		List<ReportCase> caseList = reportCaseMapper.searchByKeys(caseInfo.getCompany()
				.getCompanyId(), format.format(createTime), fullFormat.format(createTime), answer
				.getQuestValue(), caseInfo.getRtList());
		System.out.println(caseList.size());
		assertTrue(caseList.size() == 1);
	}

	/**
	 * 查件，没有开始时间（要求开始时间和结束时间同时存在，不同时存在则相当于时间设置无效）
	 * 
	 * 1、size>0 2、与不设置时间的集合结果相同
	 */
	@Test
	public void testSearchByNoStartTime() {
		caseInfo = getFullReportCase(getReportCase());
		reportCaseMapper.insert(caseInfo);
		answer = getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);

		List<ReportCase> caseList1 = reportCaseMapper.getAllByCompanyId(caseInfo.getCompany()
				.getCompanyId());
		List<ReportCase> caseList2 = reportCaseMapper.searchByKeys(caseInfo.getCompany()
				.getCompanyId(), "", "2016-02-28", null, null);
		assertTrue(caseList2.size() >= 1);
		assertTrue(caseList1.size() == caseList2.size());

	}

	/**
	 * 查件 除了公司id，没有其他关键字
	 */
	@Test
	public void testSearchByCompnayId() {
		caseInfo = getFullReportCase(getReportCase());
		reportCaseMapper.insert(caseInfo);
		answer = getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);

		List<ReportCase> caseList1 = reportCaseMapper.getAllByCompanyId(caseInfo.getCompany()
				.getCompanyId());
		List<ReportCase> caseList2 = reportCaseMapper.searchByKeys(caseInfo.getCompany()
				.getCompanyId(), null, null, null, null);
		assertTrue(caseList2.size() >= 1);
		assertTrue(caseList1.size() == caseList2.size());

	}

	/**
	 * 查件：通过公司的id、案件起始时间、关键字（答案中含有）、类型搜索案件
	 * 
	 * 按照时间搜索，时间存在
	 * 
	 * size>0 且集合中含有自己加进去的
	 */
	@Ignore
	public void testSearchByKeys() {
		ReportCase caseInfo = getReportCase();
		Company company = caseInfo.getCompany();
		companyMapper.insert(company);
		reportCaseMapper.insert(caseInfo);
		isEqual(reportCaseMapper.getById(caseInfo.getRcId()).getCompany(), caseInfo.getCompany());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date createTime = caseInfo.getCreateTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 1);
		Date afterDate = cal.getTime();
		ReportAnswer answer = getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);
		List<ReportCase> caseList = reportCaseMapper.searchByKeys(company.getCompanyId(),
				format.format(createTime), format.format(afterDate), null, null);
		assertTrue(caseList.size() > 0);
		int count = 0;
		for (int i = 0; i < caseList.size(); i++) {
			ReportCase getCase = caseList.get(i);
			// if (isEqual(getCase, caseInfo)) {
			// count++;
			// }

		}
		System.out.println("----" + count);
		// companyMapper.deleteById(company.getCompanyId());
		reportAnswerMapper.deleteById(answer.getRdId());
		// reportCaseMapper.deleteById(caseInfo.getRcId());

	}

	/**
	 * 获取案件，包括该案件包含的附件list、追加信息list、日志list、答案list
	 */
	@Ignore
	public void testGetCaseById() {
		ReportCase case1 = getReportCase();
		reportCaseMapper.insert(case1);
		ReportCase caseInfo = reportCaseMapper.getById(case1.getRcId());
		reportCaseMapper.deleteById(case1.getRcId());
		assertNotNull(caseInfo.getAnswers());
		assertNotNull(caseInfo.getAttachList());
		assertNotNull(caseInfo.getChangeList());
		assertNotNull(caseInfo.getCommentList());

	}

	/**
	 * 获取实名举报这举报过案件
	 * 
	 * 取出来的集合中包含该案件，且数量为1
	 */
	@Test
	public void getCaseByReport() {
		caseInfo = getFullReportCase(getReportCase());
		reportCaseMapper.insert(caseInfo);
		List<ReportCase> caseList = reportCaseMapper.getCaseByReporter(caseInfo.getReporter());
		int count = 0;

		for (int i = 0; i < caseList.size(); i++) {
			ReportCase getCase = caseList.get(i);
			if (TestUtil.isEqual(getCase, caseInfo)) {
				count++;
			}
		}
		assertTrue(caseList.size() > 0);
		assertTrue(count == 1);
	}

	/**
	 * 保存answer
	 */
	@Test
	public void testInsertAnswer() {
		caseInfo = getReportCase();
		reportCaseMapper.insert(caseInfo);
		answer = getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);
		ReportAnswer answer2 = reportAnswerMapper.getById(answer.getRdId());
		assertTrue(TestUtil.isEqual(answer, answer2));
	}

	/**
	 * 删除answer
	 */
	@Test
	public void delAnswer() {
		ReportAnswer answer1 = getAnswer();
		reportAnswerMapper.insert(answer1);
		ReportAnswer answer2 = reportAnswerMapper.getById(answer1.getRdId());
		assertTrue(TestUtil.isEqual(answer1, answer2));
		reportAnswerMapper.deleteById(answer1.getRdId());
		ReportAnswer answer3 = reportAnswerMapper.getById(answer1.getRdId());
		assertTrue(answer3 == null);

	}

	public ReportCase getFullReportCase(ReportCase casePoj) {
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		casePoj.setCompany(company);

		// CompanyBranch branch = getBranch();
		// casePoj.setBranch(branch);
		Reporter reporter = ReporterTest.getReporter();
		reporterMapper.insert(reporter);
		casePoj.setReporter(reporter);
		return casePoj;
	}

	public static ReportCase getReportCase() {
		ReportCase caseInfo = new ReportCase();
		caseInfo.setAccessCode(accessCode);
		caseInfo.setTrackingNo(trackingNo);
		caseInfo.setCreateTime(new Date(new Timestamp(new Date().getTime() / 1000 * 1000).getTime()));
		// caseInfo.setCompany(CompanyTest.getCompany());
		// caseInfo.setBranch(getBranch());
		// caseInfo.setReporter(ReporterTest.getReporter());
		caseInfo.setRtList("举报类型1，举报类型2");
		caseInfo.setCaseState(1);
		return caseInfo;
	}

	public static ReportAnswer getAnswer() {
		ReportAnswer anwser = new ReportAnswer();
		anwser.setQuestKey("NOXX");
		anwser.setQuestValue("answer value");
		return anwser;
	}

	public static CompanyBranch getBranch() {
		CompanyBranch branch = new CompanyBranch();
		branch.setAddress("中意一路160号");
		branch.setBranchName("武汉办事处");
		branch.setPostCode("424207");
		branch.setContactor("zhang san");
		branch.setOwner(CompanyTest.getCompany());
		branch.setCity(null);
		branch.setProvince(null);
		return branch;
	}

	/**
	 * java反射机制验证两个对象的属性值是否相等
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public boolean isEqual(Object src, Object dst) {
		Field[] fields = src.getClass().getDeclaredFields();
		boolean flag = true;
		for (Field srcField : fields) {
			srcField.setAccessible(true);
			try {
				// 获取原对象字段值
				Object srcFieldData = srcField.get(src);
				Field dstField = dst.getClass().getDeclaredField(srcField.getName());
				dstField.setAccessible(true);
				Object dstFieldData = dstField.get(dst);
				/*
				 * System.out.println("fieldName==" + srcField.getName() +
				 * "----value=" + dstFieldData + "--" + srcFieldData +
				 * "---isEquals=" + dstFieldData.equals(srcFieldData));
				 */if (!srcFieldData.equals(dstFieldData)) {
					flag = false;
					break;
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	@After
	public void clear() {
		reportCaseMapper.deleteById(caseInfo.getRcId());
		if (caseInfo.getCompany() != null) {
			companyMapper.deleteById(caseInfo.getCompany().getCompanyId());
		}
		if (answer != null) {
			reportAnswerMapper.deleteById(answer.getRdId());
		}
		if (caseInfo.getReporter() != null) {
			reporterMapper.deleteById(caseInfo.getReporter().getReporterId());

		}
	}
}
