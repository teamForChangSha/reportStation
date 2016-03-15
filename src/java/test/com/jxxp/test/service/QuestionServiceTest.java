package com.jxxp.test.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
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
import com.jxxp.service.QuestionService;
import com.jxxp.test.mybatis.CompanyTest;
import com.jxxp.test.mybatis.QuestionInfoTest;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
@Transactional
public class QuestionServiceTest {
	@Resource
	private QuestionService questionService;
	@Resource
	private QuestionInfoMapper questionInfoMapper;
	@Resource
	private CompanyQuestionMapper cqMapper;
	@Resource
	private CompanyMapper companyMapper;
	private QuestionInfo addQuest;
	private Company company;

	@Test
	public void getQuestionById() {
		addQuest = QuestionInfoTest.getQuestion();
		questionInfoMapper.insert(addQuest);
		QuestionInfo getQuest = questionService.getQuestionById(addQuest.getQuestId());
		assertTrue(TestUtil.isEqual(addQuest, getQuest));
		questionInfoMapper.deleteById(addQuest.getQuestId());
	}

	@Test
	public void getQuestionNoId() {
		addQuest = QuestionInfoTest.getQuestion();
		questionInfoMapper.insert(addQuest);
		QuestionInfo getQuest = questionService.getQuestionById(addQuest.getQuestId() + 1);
		assertNull(getQuest);
	}

	/**
	 * 获取模版问题列表
	 */
	@Test
	public void getAllQuestions() {
		List<QuestionInfo> tmpQuests = questionService.getAllQuestions();
		addQuest = QuestionInfoTest.getQuestion();
		questionInfoMapper.insert(addQuest);
		List<QuestionInfo> tmpQuests2 = questionService.getAllQuestions();
		int count = 0;
		for (int i = 0; i < tmpQuests2.size(); i++) {
			if (TestUtil.isEqual(addQuest, tmpQuests2.get(i))) {
				count++;
			}
		}
		assertTrue(count == 1);
		assertTrue((tmpQuests.size() + 1) == tmpQuests2.size());

	}

	/**
	 * 默认的问题列表中标记客户公司选择了哪些问题，mark=1表示客户公司选择了该问题，mark=0表示客户没有选择
	 * 
	 * 1、没有选择则使用默认问题模板列表,map的size=问题模板列表的size，所有的mark=0;
	 */
	@Test
	public void getMarkQuestions() {
		addQuest = QuestionInfoTest.getQuestion();
		questionInfoMapper.insert(addQuest);
		company = CompanyTest.getCompany();
		companyMapper.insert(company);
		List<Map<String, String>> assembleQuestList = questionService.getMarkQuestions(company);
		List<QuestionInfo> TemplateQuests = questionService.getAllQuestions();
		boolean flag = true;
		for (int i = 0; i < assembleQuestList.size(); i++) {
			if (assembleQuestList.get(i).get("mark").equals("1")) {
				flag = false;
				break;
			}
		}
		assertTrue(flag);
		assertTrue(assembleQuestList.size() > 0);
		assertTrue(assembleQuestList.size() == TemplateQuests.size());
	}

	/**
	 * 默认的问题列表中标记客户公司选择了哪些问题，mark=1表示客户公司选择了该问题，mark=0表示客户没有选择
	 * 
	 * 2、有选择则不使用用默认问题模板列表,map的size=问题模板列表的size，客户公司所选择的问题map的mark=1，
	 * 取出来的客户所选择问题等于添加的;
	 */
	@Test
	public void getMarkQuestions2() {
		addQuest = QuestionInfoTest.getQuestion();
		company = CompanyTest.getCompany();
		companyMapper.insert(company);
		questionInfoMapper.insert(addQuest);
		cqMapper.insert(addQuest.getQuestId(), company.getCompanyId());
		List<Map<String, String>> assembleQuestList = questionService.getMarkQuestions(company);
		// 记录客户公司所选择的问题个数
		int count = 0;
		// 个数等于所添加的，且map中的每个key所对应的value值正确
		for (int i = 0; i < assembleQuestList.size(); i++) {
			if (assembleQuestList.get(i).get("mark").equals("1")) {
				assertTrue(assembleQuestList.get(i).get("quest").equals(addQuest.getQuest()));
				assertTrue(assembleQuestList.get(i).get("questId")
						.equals(addQuest.getQuestId() + ""));
				count++;
			}
		}
		assertTrue(count == 1);
		assertTrue(assembleQuestList.size() > 0);

	}

	@After
	public void clear() {
		if (addQuest != null) {
			questionInfoMapper.deleteById(addQuest.getQuestId());
		}
		if (company != null) {
			companyMapper.deleteById(company.getCompanyId());
		}
		if (company != null && addQuest != null) {
			cqMapper.deleteByDoubleId(addQuest.getQuestId(), company.getCompanyId());
		}

	}
}
