package com.jxxp.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.CompanyMapper;
import com.jxxp.dao.CompanyQuestionMapper;
import com.jxxp.dao.ReportCaseMapper;
import com.jxxp.dao.ReporterMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;
import com.jxxp.service.CaseService;
import com.jxxp.test.mybatis.CaseTest;
import com.jxxp.test.mybatis.CompanyTest;
import com.jxxp.test.mybatis.QuestionInfoTest;
import com.jxxp.test.mybatis.ReporterTest;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class CaseServiceTest {
	@Resource
	private CaseService caseService;
	@Resource
	private ReportCaseMapper reportCaseMapper;
	@Resource
	private ReporterMapper reporterMapper;
	@Resource
	private CompanyMapper companyMapper;
	@Resource
	private CompanyQuestionMapper cqMapper;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * 实名举报
	 */
	@Test
	public void testAddCase() {
		ReportCase caseInfo = CaseTest.getReportCase();
		Company company = caseInfo.getCompany();
		companyMapper.insert(caseInfo.getCompany());
		List<QuestionInfo> qustionList = getQuestionList();
		for (int i = 0; i < qustionList.size(); i++) {
			cqMapper.insert(qustionList.get(i).getQuestId(), company.getCompanyId());
		}
		List<ReportAnswer> answerList = getAnswers(caseInfo, qustionList);
		Reporter reporter = ReporterTest.getReporter();
		reporterMapper.insert(reporter);
		assertTrue(caseService.saveCase(reporter, caseInfo, answerList));
		assertTrue(reportCaseMapper.getCaseByReporter(reporter).size() > 0);
		reporterMapper.deleteById(reporter.getReporterId());
		companyMapper.deleteById(company.getCompanyId());
		reportCaseMapper.deleteById(caseInfo.getRcId());
	}

	/**
	 * 匿名举报,无举报人
	 */
	@Test
	public void testAddAnoCase() {
		ReportCase caseInfo = CaseTest.getReportCase();
		Company company = caseInfo.getCompany();
		companyMapper.insert(caseInfo.getCompany());
		List<QuestionInfo> qustionList = getQuestionList();
		for (int i = 0; i < qustionList.size(); i++) {
			cqMapper.insert(qustionList.get(i).getQuestId(), company.getCompanyId());
		}
		List<ReportAnswer> answerList = getAnswers(caseInfo, qustionList);
		assertTrue(caseService.saveCase(null, caseInfo, answerList));

		assertNull(reportCaseMapper.getById(caseInfo.getRcId()).getReporter());
		companyMapper.deleteById(company.getCompanyId());
		reportCaseMapper.deleteById(caseInfo.getRcId());
	}

	/**
	 * 问题和答案不一致，异常
	 */
	@Test
	public void QuestUnSameWithAnswer() {
		ReportCase caseInfo = CaseTest.getReportCase();
		Company company = caseInfo.getCompany();
		companyMapper.insert(caseInfo.getCompany());
		List<QuestionInfo> qustionList = getQuestionList();
		for (int i = 0; i < qustionList.size(); i++) {
			cqMapper.insert(qustionList.get(i).getQuestId(), company.getCompanyId());
		}
		List<ReportAnswer> answerList = getNotSameAnswers(caseInfo, qustionList);
		try {
			caseService.saveCase(null, caseInfo, answerList);

		} catch (Exception ex) {
			ex.getMessage();
		}
		assertNull(reportCaseMapper.getById(caseInfo.getRcId()).getReporter());
		companyMapper.deleteById(company.getCompanyId());
		reportCaseMapper.deleteById(caseInfo.getRcId());
	}

	/**
	 * 存储后案件案件追踪号、密码是否生成
	 */
	@Test
	public void testHasTrackNo() {
		ReportCase caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = reportCaseMapper.getById(caseInfo.getRcId());
		String tracking = caseInfo2.getTrackingNo();
		String accessCode = caseInfo2.getAccessCode();
		boolean flag = (tracking == null || tracking.equals("") || accessCode == null
				|| accessCode.equals("") ? false : true);
		assertTrue(flag);
		assertTrue(caseInfo2.getAccessCode() != null);
		reportCaseMapper.deleteById(caseInfo.getRcId());

	}

	/**
	 * 案件号码的生成：生成并且每次生成的要不一致
	 */
	@Test
	public void testTrackingNo() {
		Company company = CompanyTest.getCompany();
		String trackingNo1 = caseService.getNewTrackingNo(company);
		String trackingNo2 = caseService.getNewTrackingNo(company);
		assertNotNull(trackingNo1);
		assertTrue(!trackingNo1.equals(trackingNo2));
	}

	/**
	 * 查询案件号，正确的案件追踪号和密码
	 */
	@Test
	public void getCaseByTrackNo() {
		ReportCase caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = caseService.getReportCase(caseInfo.getTrackingNo(),
				caseInfo.getAccessCode());
		assertNotNull(caseInfo2);
		reportCaseMapper.deleteById(caseInfo.getRcId());

	}

	/**
	 * 查询案件号,输入错误的密码
	 */
	@Test
	public void getCaseByErrorCode() {
		ReportCase caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = caseService.getReportCase(caseInfo.getTrackingNo(), "rr");
		assertNull(caseInfo2);
		reportCaseMapper.deleteById(caseInfo.getRcId());
	}

	/**
	 * 查询案件号,输入错误的追踪号
	 */
	@Test
	public void getCaseByErrorTrack() {
		ReportCase caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = caseService.getReportCase("rr", caseInfo.getAccessCode());
		assertNull(caseInfo2);
		reportCaseMapper.deleteById(caseInfo.getRcId());
	}

	/**
	 * 查询案件号,错误的案件追踪号和密码
	 */
	@Test
	public void getCaseByErrorTrackNo() {
		ReportCase caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = caseService.getReportCase("xr", "rr");
		assertNull(caseInfo2);
		reportCaseMapper.deleteById(caseInfo.getRcId());
	}

	/**
	 * 存储后案件初始状态是否正确,第一存储状态应该为正常，值是1。
	 */
	@Ignore
	public void testCaseState() {
		ReportCase caseInfo = CaseTest.getReportCase();
		caseInfo.setCaseState(0);
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = reportCaseMapper.getById(caseInfo.getRcId());
		reportCaseMapper.deleteById(caseInfo.getRcId());
		assertTrue(caseInfo2.getCaseState() == 0);

	}

	@Test
	public void getCaseByReporter() {
		ReportCase caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		Reporter reporter = caseInfo.getReporter();
		reporterMapper.insert(caseInfo.getReporter());
		List<ReportCase> list = caseService.getCaseList(caseInfo.getReporter());
		reportCaseMapper.deleteById(caseInfo.getRcId());
		reporterMapper.deleteById(reporter.getReporterId());
		assertTrue(list.size() > 0);
	}

	// TODO
	@Test
	public void getCaseNoReporter() {
		ReportCase caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		List<ReportCase> list = caseService.getCaseList(caseInfo.getReporter());
		reportCaseMapper.deleteById(caseInfo.getRcId());

	}

	/**
	 * 构造相应问题的答案列表
	 * 
	 * @param caseInfo
	 * 
	 * @param qustionList
	 * @return
	 */
	private List<ReportAnswer> getAnswers(ReportCase caseInfo, List<QuestionInfo> qustionList) {
		List<ReportAnswer> aList = new ArrayList<ReportAnswer>();
		ReportAnswer answer = null;
		for (int i = 0; i < qustionList.size(); i++) {
			answer = new ReportAnswer();
			answer.setQuestKey(qustionList.get(i).getQuestKey());
			answer.setQuestValue("answer" + i);
			answer.setRcId(caseInfo.getRcId());
		}
		aList.add(answer);
		return aList;
	}

	/**
	 * 构造问题列别表
	 * 
	 * @return
	 */
	private List<QuestionInfo> getQuestionList() {
		List<QuestionInfo> qList = new ArrayList<QuestionInfo>();
		QuestionInfo q = null;
		for (int i = 0; i < 3; i++) {
			q = new QuestionInfo();
			q = QuestionInfoTest.getQuestion();
			q.setQuestId(q.getQuestId() + i);
		}
		qList.add(q);
		return qList;
	}

	private List<ReportAnswer> getNotSameAnswers(ReportCase caseInfo, List<QuestionInfo> qustionList) {
		List<ReportAnswer> aList = new ArrayList<ReportAnswer>();
		ReportAnswer answer = null;
		for (int i = 0; i < qustionList.size(); i++) {
			answer = new ReportAnswer();
			answer.setQuestKey(qustionList.get(i).getQuestKey() + "no");
			answer.setQuestValue("answer" + i);
			answer.setRcId(caseInfo.getRcId());
		}
		aList.add(answer);
		return aList;
	}

}
