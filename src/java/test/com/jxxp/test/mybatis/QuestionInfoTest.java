package com.jxxp.test.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.CompanyMapper;
import com.jxxp.dao.CompanyQuestionMapper;
import com.jxxp.dao.QuestionInfoMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.QuestionInfo;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
@Transactional
public class QuestionInfoTest {

	@Resource
	private QuestionInfoMapper questionInfoMapper;
	@Resource
	private CompanyQuestionMapper cqMapper;

	@Resource
	private CompanyMapper companyMapper;

	@Test
	public void getByCompanyId() {
		QuestionInfo question = getQuestion();
		Company company = CompanyTest.getCompany();
		companyMapper.insert(company);
		questionInfoMapper.insert(question);
		cqMapper.insert(question.getQuestId(), company.getCompanyId());
		List<QuestionInfo> qList = questionInfoMapper.getAllByCompany(company.getCompanyId());
		assertNotNull(qList);
		assertTrue(qList.size() > 0);
		companyMapper.deleteById(company.getCompanyId());
		questionInfoMapper.deleteById(question.getQuestId());
		cqMapper.deleteByDoubleId(question.getQuestId(), company.getCompanyId());

	}

	public static QuestionInfo getQuestion() {
		QuestionInfo question = new QuestionInfo();
		question.setQuest("quest");
		question.setQuestDesc("this is desc xx");
		question.setQuestKey("NOXY");
		return question;
	}

	/**
	 * 构造问题集合
	 * 
	 * @return
	 */
	public static List<QuestionInfo> getQuestList() {
		List<QuestionInfo> qList = new ArrayList<QuestionInfo>();
		for (int i = 0; i < 2; i++) {
			QuestionInfo question = getQuestion();
			qList.add(question);
		}
		return qList;
	}
}
