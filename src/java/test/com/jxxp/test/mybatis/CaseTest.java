package com.jxxp.test.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.CaseAttachMapper;
import com.jxxp.dao.ReportAnswerMapper;
import com.jxxp.dao.ReportCaseMapper;
import com.jxxp.dao.ReporterMapper;
import com.jxxp.pojo.CaseAttach;
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
	private CaseAttachMapper caseAttachMapper;
	@Resource
	private ReportAnswerMapper reportAnswerMapper;
	@Resource
	private ReporterMapper reporterMapper;

	/**
	 * 案件追踪号
	 */
	private static String trackingNo = "YT2016021040";
	private static String accessCode = "123456";

	/**
	 * 案件的存储是否成功
	 */
	@Test
	public void saveCaseInfo() {
		ReportCase case1 = getReportCase();
		assertTrue(reportCaseMapper.insert(case1) > 0);
		ReportCase case2 = reportCaseMapper.getById(case1.getRcId());
		reportCaseMapper.deleteById(case1.getRcId());
		assertNotNull(case2);
		// assertTrue(TestUtil.isEqual(case1, case2));
	}

	/**
	 * 根据案件号追踪号和密码查询案件
	 */
	@Test
	public void testFindByNo() {
		ReportCase case1 = getReportCase();
		reportCaseMapper.insert(case1);
		ReportCase case2 = reportCaseMapper.findByNo(case1.getTrackingNo(), case1.getAccessCode());
		reportCaseMapper.deleteById(case1.getRcId());
		assertNotNull(case2);
		// assertTrue(TestUtil.isEqual(case1, case2));

	}

	/**
	 * 获取案件，包括该案件包含的附件list、追加信息list、日志list、答案list
	 */
	@Test
	public void testGetCase() {
		ReportCase case1 = getReportCase();
		reportCaseMapper.insert(case1);
		ReportCase caseInfo = reportCaseMapper.getById(new Long(1));
		reportCaseMapper.deleteById(case1.getRcId());
		assertNotNull(caseInfo.getAnswers());
		assertNotNull(caseInfo.getAttachList());
		assertNotNull(caseInfo.getChangeList());
		assertNotNull(caseInfo.getCommentList());
		assertNotNull(caseInfo.getCompany());
	}

	/**
	 * 获取实名举报这举报过案件
	 */
	@Test
	public void getCaseByReport() {
		ReportCase caseInfo = getReportCase();
		Reporter reporter = ReporterTest.getReporter();
		caseInfo.setReporter(reporter);
		reporterMapper.insert(reporter);
		reportCaseMapper.insert(caseInfo);
		assertTrue(reportCaseMapper.getCaseByReporter(reporter).size() > 0);
		reporterMapper.deleteById(reporter.getReporterId());
		reportCaseMapper.deleteById(caseInfo.getRcId());

	}

	/**
	 * 更新附件
	 */
	@Test
	public void testUpdateAttach() {
		CaseAttach caseAttach1 = getAttach();
		caseAttachMapper.insert(caseAttach1);
		caseAttach1.setAttachName("change fileName");
		assertTrue(caseAttachMapper.update(caseAttach1) > 0);
		CaseAttach caseAttach2 = caseAttachMapper.getById(caseAttach1.getCaId());
		assertTrue(!TestUtil.isEqual(caseAttach1, caseAttach2));
		caseAttachMapper.deleteById(caseAttach2.getCaId());

	}

	/**
	 * 保存answer
	 */
	@Test
	public void testInsertAnswer() {
		reportCaseMapper.getAllCase().get(0).getRcId();
		ReportAnswer answer1 = getAnswer();
		reportAnswerMapper.insert(answer1);
		ReportAnswer answer2 = reportAnswerMapper.getById(answer1.getRdId());
		assertTrue(TestUtil.isEqual(answer1, answer2));
		reportAnswerMapper.deleteById(answer1.getRdId());
	}

	/**
	 * 保存answer
	 */
	@Test
	public void insertAnswerByQuestKey() {
		reportCaseMapper.getAllCase().get(0).getRcId();
		ReportAnswer answer1 = getAnswer();
		reportAnswerMapper.insertByQuestionId(answer1, new Long(2));
		ReportAnswer answer2 = reportAnswerMapper.getById(answer1.getRdId());
		reportAnswerMapper.deleteById(answer1.getRdId());
		assertNotNull(answer2);
	}

	public static CaseAttach getAttach() {
		CaseAttach caseAttach = new CaseAttach();
		caseAttach.setAttachExt("扩展文本");
		caseAttach.setAttachName("附件名");
		caseAttach.setTrackingNo(trackingNo);
		caseAttach.setAttachSize(200);
		caseAttach.setAttachFileName("fileName");
		caseAttach.setAttachPath("upload");
		caseAttach.setAttachUrl("http://");
		caseAttach.setState(1);
		caseAttach.setDescription("附件描述");
		return caseAttach;
	}

	public static ReportCase getReportCase() {
		ReportCase caseInfo = new ReportCase();
		caseInfo.setAccessCode(accessCode);
		caseInfo.setTrackingNo(trackingNo);
		caseInfo.setCreateTime(new Timestamp(new Date().getTime() / 1000 * 1000));
		caseInfo.setCompany(CompanyTest.getCompany());
		caseInfo.setBranch(getBranch());
		caseInfo.setReporter(ReporterTest.getReporter());
		caseInfo.setRtList("举报类型1，举报类型2");
		caseInfo.setCaseState(1);
		return caseInfo;
	}

	public static ReportAnswer getAnswer() {
		ReportAnswer anwser = new ReportAnswer();
		anwser.setQuestKey("NOXX");
		anwser.setQuestValue("answer value");
		anwser.setRcId(getReportCase().getRcId());
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

}
