package com.jxxp.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.CaseCommentMapper;
import com.jxxp.dao.CompanyMapper;
import com.jxxp.dao.CompanyQuestionMapper;
import com.jxxp.dao.ReportAnswerMapper;
import com.jxxp.dao.ReportCaseMapper;
import com.jxxp.dao.ReporterMapper;
import com.jxxp.dao.UserMapper;
import com.jxxp.pojo.CaseComment;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;
import com.jxxp.pojo.User;
import com.jxxp.service.CaseCommentService;
import com.jxxp.service.CaseService;
import com.jxxp.test.mybatis.CaseCommentTest;
import com.jxxp.test.mybatis.CaseTest;
import com.jxxp.test.mybatis.CompanyTest;
import com.jxxp.test.mybatis.QuestionInfoTest;
import com.jxxp.test.mybatis.ReporterTest;
import com.jxxp.test.unit.TestUtil;

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
	private CaseCommentService caseCommentService;
	@Resource
	private ReportCaseMapper reportCaseMapper;
	@Resource
	private ReporterMapper reporterMapper;
	@Resource
	private ReportAnswerMapper reportAnswerMapper;
	@Resource
	private CompanyMapper companyMapper;

	@Resource
	private UserMapper userMapper;
	@Resource
	private CaseCommentMapper caseCommentMapper;
	@Resource
	private CompanyQuestionMapper cqMapper;
	private List<QuestionInfo> qustionList;
	private List<ReportAnswer> answerList;
	private ReportCase caseInfo;
	private ReportAnswer answer;
	private CaseComment caseComment;

	/**
	 * 实名举报,第一次举报，添加案件的同时添加举报人
	 */
	@Test
	public void FirstTimeAddCase() {
		caseInfo = CaseTest.getReportCase();
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		caseInfo.setCompany(company);
		qustionList = getQuestionList();
		for (int i = 0; i < qustionList.size(); i++) {
			cqMapper.insert(qustionList.get(i).getQuestId(), company.getCompanyId());
		}
		answerList = getAnswers(caseInfo, qustionList);
		Reporter reporter = ReporterTest.getReporter();
		// reporterMapper.insert(reporter);
		caseInfo.setReporter(reporter);
		assertTrue(caseService.saveCase(reporter, caseInfo, answerList));
		List<ReportCase> getList = reportCaseMapper.getCaseByReporter(reporter);
		// 案件本省
		assertTrue(getList.size() == 1);
		assertTrue(TestUtil.isEqual(getList.get(0), caseInfo));
		Reporter re = reporterMapper.getById(reporter.getReporterId());
		// 举报人
		assertTrue(TestUtil.isEqual(reporter, re));
		// 所举报的问题答案
		TestUtil.isEqual(getList.get(0).getAnswers(), answerList);
	}

	/**
	 * 实名举报,多次举报，添加案件的同时更新举报人
	 */
	@Test
	public void AddManyCase() {
		caseInfo = CaseTest.getReportCase();
		ReportCase caseInfo2 = CaseTest.getReportCase();
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		caseInfo.setCompany(company);
		caseInfo2.setCompany(company);
		qustionList = getQuestionList();
		for (int i = 0; i < qustionList.size(); i++) {
			cqMapper.insert(qustionList.get(i).getQuestId(), company.getCompanyId());
		}
		answerList = getAnswers(caseInfo, qustionList);
		Reporter reporter = ReporterTest.getReporter();
		caseInfo.setReporter(reporter);
		caseInfo2.setReporter(reporter);

		// 多次举报
		assertTrue(caseService.saveCase(reporter, caseInfo, answerList));

		assertTrue(caseService.saveCase(reporter, caseInfo2, answerList));

		List<ReportCase> getList = reportCaseMapper.getCaseByReporter(reporter);
		// 案件本省
		assertTrue(getList.size() == 2);
		List<Reporter> reList = reporterMapper.getAll();
		// 同一个举报人
		int count = 0;
		for (int i = 0; i < reList.size(); i++) {
			Reporter re = reList.get(i);
			if (TestUtil.isEqual(reporter, re) || re.getMobile().equals(reporter.getMobile())) {
				count++;
			}
		}
		assertTrue(count == 1);
		// 所举报的问题答案和添加的相同
		TestUtil.isEqual(getList.get(0).getAnswers(), answerList);
		reportCaseMapper.deleteById(caseInfo2.getRcId());
	}

	/**
	 * 匿名举报,无举报人
	 */
	@Test
	public void testAddCaseAno() {
		caseInfo = CaseTest.getReportCase();
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		caseInfo.setCompany(company);
		qustionList = getQuestionList();
		for (int i = 0; i < qustionList.size(); i++) {
			cqMapper.insert(qustionList.get(i).getQuestId(), company.getCompanyId());
		}
		answerList = getAnswers(caseInfo, qustionList);
		assertTrue(caseService.saveCase(null, caseInfo, answerList));
		ReportCase getCase = reportCaseMapper.getById(caseInfo.getRcId());
		assertTrue(TestUtil.isEqual(getCase, caseInfo));
		assertNull(getCase.getReporter());
		assertTrue(TestUtil.isEqual(answerList, getCase.getAnswers()));
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
		assertTrue(!trackingNo1.equals(""));
		assertTrue(!trackingNo1.equals(trackingNo2));
	}

	/**
	 * 根据案件追踪号和密码查件
	 * 
	 * 1、 正确的案件追踪号和密码
	 */
	@Test
	public void getCaseByTrackNo() {
		caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = caseService.getReportCase(caseInfo.getTrackingNo(),
				caseInfo.getAccessCode());
		assertNotNull(caseInfo2);
		assertTrue(TestUtil.isEqual(caseInfo, caseInfo2));

	}

	/**
	 * 2、查询案件号,输入错误的密码
	 */
	@Test
	public void getCaseByErrorCode() {
		caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = caseService.getReportCase(caseInfo.getTrackingNo(), "rr");
		assertNull(caseInfo2);
	}

	/**
	 * 查询案件号,输入错误的追踪号
	 */
	@Test
	public void getCaseByErrorTrack() {
		caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = caseService.getReportCase("rr", caseInfo.getAccessCode());
		assertNull(caseInfo2);
	}

	/**
	 * 查询案件号,错误的案件追踪号和密码
	 */
	@Test
	public void getCaseByErrorTrackNo() {
		caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = caseService.getReportCase("xr", "rr");
		assertNull(caseInfo2);
	}

	/**
	 * 存储后案件初始状态是否正确,第一存储状态应该为正常，值是1。
	 */
	@Test
	public void testCaseState() {
		caseInfo = CaseTest.getReportCase();
		caseService.saveCaseInfo(caseInfo);
		ReportCase caseInfo2 = reportCaseMapper.getById(caseInfo.getRcId());
		reportCaseMapper.deleteById(caseInfo.getRcId());
		assertTrue(caseInfo2.getCaseState() == 1);

	}

	/**
	 * 举报人存在
	 */
	@Test
	public void getCaseByReporter() {
		caseInfo = CaseTest.getReportCase();
		Reporter reporter = ReporterTest.getReporter();
		reporterMapper.insert(reporter);
		caseInfo.setReporter(reporter);
		caseService.saveCaseInfo(caseInfo);
		List<ReportCase> list = caseService.getCaseList(caseInfo.getReporter());
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (TestUtil.isEqual(caseInfo, list.get(i))) {
				count++;
			}
		}
		assertTrue(count == 1);
		assertTrue(list.size() > 0);
	}

	/**
	 * 举报人不存在
	 */
	@Test
	public void getCaseWithNotExitReporter() {
		caseInfo = CaseTest.getReportCase();
		Reporter reporter = ReporterTest.getReporter();
		caseService.saveCaseInfo(caseInfo);
		List<ReportCase> list = caseService.getCaseList(reporter);
		assertTrue(list.size() == 0);
		reporterMapper.deleteById(reporter.getReporterId());
	}

	/**
	 * 追加备注 取出来的添加的备注相同
	 * 
	 * 1、举报人追加的： 查找某个案件下的所有备注，含有该备注
	 */
	@Test
	public void addCaseCommentByReporter() {
		caseComment = CaseCommentTest.getComment();
		caseInfo = CaseTest.getReportCase();
		caseComment.setIsReporter(1);
		reportCaseMapper.insert(caseInfo);
		caseCommentService.addCaseComment(caseComment, caseInfo.getRcId());
		CaseComment CaseComment2 = caseCommentMapper.getById(caseComment.getCcId());
		assertTrue(TestUtil.isEqual(caseComment, CaseComment2));
		List<CaseComment> cComentList = caseCommentMapper.getAllByReportCaseId(caseInfo.getRcId());
		int count = 0;
		for (int i = 0; i < cComentList.size(); i++) {
			if (TestUtil.isEqual(caseComment, cComentList.get(i))) {
				count++;
			}
		}
		assertTrue(count == 1);
	}

	/**
	 * 追加备注 取出来的添加的备注相同
	 * 
	 * 2、用户追加的： 查找某个案件下的所有备注，含有该备注
	 */
	@Test
	public void addCaseCommentByUser() {
		caseComment = CaseCommentTest.getComment();
		caseInfo = CaseTest.getReportCase();
		User owner = userMapper.getAllUers().get(0);
		caseComment.setOwner(owner);
		caseComment.setOwnerCompany(owner.getUserCompany());
		reportCaseMapper.insert(caseInfo);
		caseCommentService.addCaseComment(caseComment, caseInfo.getRcId());
		CaseComment CaseComment2 = caseCommentMapper.getById(caseComment.getCcId());
		assertTrue(TestUtil.isEqual(caseComment, CaseComment2));
		List<CaseComment> cComentList = caseCommentMapper.getAllByReportCaseId(caseInfo.getRcId());
		int count = 0;
		for (int i = 0; i < cComentList.size(); i++) {
			if (TestUtil.isEqual(caseComment, cComentList.get(i))) {
				count++;
			}
		}
		assertTrue(caseComment.getIsReporter() == 0);
		assertTrue(count == 1);
	}

	/**
	 * 查件 除了公司id，没有其他关键字,同根据公司id查出来的结果一致
	 */
	@Test
	public void testSearchByCompnayId() {
		caseInfo = getFullReportCase(CaseTest.getReportCase());
		reportCaseMapper.insert(caseInfo);
		answer = CaseTest.getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);

		HashMap<String, String> params = new HashMap<String, String>();
		List<ReportCase> caseList1 = caseService.getCaseByCompany(caseInfo.getCompany(), params);
		List<ReportCase> caseList2 = reportCaseMapper.getAllByCompanyId(caseInfo.getCompany()
				.getCompanyId());

		assertTrue(caseList2.size() >= 1);
		assertTrue(caseList1.size() == caseList2.size());

	}

	/**
	 * 查件：通过公司的id、案件起始时间、关键字（答案中含有）、类型搜索案件其中 按照时间搜索：必须有起始时间，缺少任何一个则视作null，
	 * 
	 * 没有开始时间
	 */
	@Test
	public void testSearchByNoStartTime() {
		caseInfo = getFullReportCase(CaseTest.getReportCase());
		reportCaseMapper.insert(caseInfo);
		answer = CaseTest.getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("endTime", format.format(caseInfo.getCreateTime()));
		List<ReportCase> caseList1 = caseService.getCaseByCompany(caseInfo.getCompany(), params);
		List<ReportCase> caseList2 = reportCaseMapper.getAllByCompanyId(caseInfo.getCompany()
				.getCompanyId());
		assertTrue(caseList2.size() >= 1);
		assertTrue(caseList1.size() == caseList2.size());
		assertTrue(TestUtil.isEqual(caseList1, caseList2));

	}

	/**
	 * 查件：通过公司的id、案件起始时间、关键字（答案中含有）、类型搜索案件其中 按照时间搜索：必须有起始时间，缺少任何一个则视作null，
	 * 
	 * 没有结束时间
	 */
	@Test
	public void testSearchByNoEndTime() {
		caseInfo = getFullReportCase(CaseTest.getReportCase());
		reportCaseMapper.insert(caseInfo);
		answer = CaseTest.getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("startTime", format.format(caseInfo.getCreateTime()));
		List<ReportCase> caseList1 = caseService.getCaseByCompany(caseInfo.getCompany(), params);
		List<ReportCase> caseList2 = reportCaseMapper.getAllByCompanyId(caseInfo.getCompany()
				.getCompanyId());

		assertTrue(caseList2.size() >= 1);
		assertTrue(caseList1.size() == caseList2.size());
		assertTrue(TestUtil.isEqual(caseList1, caseList2));

	}

	/**
	 * 查件：通过公司的id、案件起始时间、关键字（答案中含有）、类型搜索案件其中 按照时间搜索：必须有起始时间，缺少任何一个则视作null，
	 * 
	 * 开始时间和结束时间相同,包括测试时间的临界值
	 */
	@Test
	public void testSearchBySameTimes() {
		caseInfo = getFullReportCase(CaseTest.getReportCase());
		reportCaseMapper.insert(caseInfo);
		answer = CaseTest.getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);
		List<ReportAnswer> answerList = new ArrayList<ReportAnswer>();
		answerList.add(answer);
		caseInfo.setAnswers(answerList);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("startTime", format.format(caseInfo.getCreateTime()));
		params.put("endTime", format.format(caseInfo.getCreateTime()));

		List<ReportCase> caseList = caseService.getCaseByCompany(caseInfo.getCompany(), params);
		assertTrue(caseList.size() == 1);
		assertTrue(TestUtil.isEqual(caseInfo, caseList.get(0)));
	}

	@Test
	public void SearchWithNotMatchType() {
		caseInfo = getFullReportCase(CaseTest.getReportCase());
		reportCaseMapper.insert(caseInfo);
		answer = CaseTest.getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);
		List<ReportAnswer> answerList = new ArrayList<ReportAnswer>();
		answerList.add(answer);
		caseInfo.setAnswers(answerList);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("startTime", format.format(caseInfo.getCreateTime()));
		params.put("endTime", format.format(caseInfo.getCreateTime()));
		params.put("rtList", "noThisType");

		List<ReportCase> caseList = caseService.getCaseByCompany(caseInfo.getCompany(), params);
		assertTrue(caseList.size() == 0);

	}

	@Test
	public void testSearchWithType() {
		caseInfo = getFullReportCase(CaseTest.getReportCase());
		reportCaseMapper.insert(caseInfo);
		answer = CaseTest.getAnswer();
		answer.setRcId(caseInfo.getRcId());
		reportAnswerMapper.insert(answer);
		List<ReportAnswer> answerList = new ArrayList<ReportAnswer>();
		answerList.add(answer);
		caseInfo.setAnswers(answerList);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("startTime", format.format(caseInfo.getCreateTime()));
		params.put("endTime", format.format(caseInfo.getCreateTime()));
		params.put("rtList", caseInfo.getRtList().substring(0, caseInfo.getRtList().length() / 2));

		List<ReportCase> caseList = reportCaseMapper.getAllByCompanyId(caseInfo.getCompany()
				.getCompanyId());
		assertTrue(caseList.size() == 1);
		assertTrue(TestUtil.isEqual(caseInfo, caseList.get(0)));

	}

	private Date getNextDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 1);
		Date afterDate = cal.getTime();
		return afterDate;
	}

	/**
	 * 问题和答案不一致，异常
	 */
	@Ignore
	public void QuestNotSameWithAnswer() {
		caseInfo = CaseTest.getReportCase();
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		caseInfo.setCompany(company);
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
	}

	// TODO
	@Ignore
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

	@After
	public void clear() {
		// 删除案件
		if (caseInfo != null) {
			reportCaseMapper.deleteById(caseInfo.getRcId());

			// 删除案件举报人
			if (caseInfo.getReporter() != null) {
				reporterMapper.deleteById(caseInfo.getReporter().getReporterId());
			}
			// 删除所属的公司、删除公司和问题的关系、删除答案
			if (caseInfo.getCompany() != null) {
				companyMapper.deleteById(caseInfo.getCompany().getCompanyId());
				cqMapper.deleteByCompanyId(caseInfo.getCompany().getCompanyId());
				reportAnswerMapper.delByCaseId(caseInfo.getRcId());
			}
		}

		if (caseComment != null) {
			caseCommentMapper.deleteById(caseComment.getCcId());
		}
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

}
