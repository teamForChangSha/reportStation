package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.CaseCommentMapper;
import com.jxxp.dao.ReportCaseMapper;
import com.jxxp.pojo.CaseComment;
import com.jxxp.pojo.ReportCase;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
// 添加注释，回滚对数据库的操作
@Transactional
public class CaseCommentTest {
	@Resource
	private CaseCommentMapper caseCommentMapper;
	@Resource
	private ReportCaseMapper reportCaseMapper;
	private CaseComment caseComment;
	private ReportCase caseInfo;

	@Before
	public void init() {
		caseComment = getComment();
	}

	@Test
	public void AddCaseComment() {
		caseInfo = CaseTest.getReportCase();
		reportCaseMapper.insert(caseInfo);
		caseCommentMapper.insert(caseComment, caseInfo.getRcId());
		CaseComment CaseComment2 = caseCommentMapper.getById(caseComment.getCcId());
		assertTrue(TestUtil.isEqual(caseComment, CaseComment2));
	}

	@After
	public void clear() {
		caseCommentMapper.deleteById(caseComment.getCcId());
		reportCaseMapper.deleteById(caseInfo.getRcId());
	}

	public static CaseComment getComment() {
		CaseComment caseComment = new CaseComment();
		caseComment.setContent("评论内容");
		caseComment.setPostTime(new Date(new Timestamp(new Date().getTime() / 1000 * 1000)
				.getTime()));

		return caseComment;
	}

}
