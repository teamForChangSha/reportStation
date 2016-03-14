package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
import com.jxxp.pojo.CompanyQuestion;
import com.jxxp.pojo.QuestionInfo;
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
public class CompanyQuestionTest {

	@Resource
	private CompanyQuestionMapper companyQuestionMapper;
	@Resource
	private CompanyMapper companyMapper;
	@Resource
	private QuestionInfoMapper questionInfoMapper;
	private Company company;
	private List<CompanyQuestion> comQuestList;

	/**
	 * 测试批量插入公司所选择的问题列表
	 */
	@Test
	public void saveCompanyQuestions() {
		company = CompanyTest.getCompany();
		comQuestList = getComQuestList();
		companyMapper.insert(company);
		for (int i = 0; i < comQuestList.size(); i++) {
			comQuestList.get(i).setCompanyId(company.getCompanyId());
		}
		companyQuestionMapper.insertQuestionList(comQuestList, company.getCompanyId());

	}

	@Test
	public void getAllByCompany() {
		company = CompanyTest.getCompany();
		comQuestList = getComQuestList();
		companyMapper.insert(company);
		for (int i = 0; i < comQuestList.size(); i++) {
			comQuestList.get(i).setCompanyId(company.getCompanyId());
		}
		companyQuestionMapper.insertQuestionList(comQuestList, company.getCompanyId());
		List<CompanyQuestion> getComQuestList = companyQuestionMapper.getAllByCompany(company
				.getCompanyId());
		assertTrue(getComQuestList.size() == comQuestList.size());
		assertTrue(TestUtil.isEqual(comQuestList, getComQuestList));
		System.out.println("questId" + getComQuestList.get(0).getQuestId());

	}

	@After
	public void clear() {
		if (company != null) {
			companyMapper.deleteById(company.getCompanyId());
			companyQuestionMapper.deleteByCompanyId(company.getCompanyId());
		}
		if (comQuestList != null) {
			for (int i = 0; i < comQuestList.size(); i++) {
				CompanyQuestion cquest = comQuestList.get(i);
				questionInfoMapper.deleteById(cquest.getQuestId());
			}
		}
	}

	private List<CompanyQuestion> getComQuestList() {
		List<CompanyQuestion> comQuestList = new ArrayList<CompanyQuestion>();
		for (int i = 0; i < 2; i++) {
			CompanyQuestion comQuest = getComQuest();
			comQuestList.add(comQuest);
		}
		return comQuestList;
	}

	private CompanyQuestion getComQuest() {
		QuestionInfo questInfo = QuestionInfoTest.getQuestion();
		CompanyQuestion comQuest = new CompanyQuestion();
		questionInfoMapper.insert(questInfo);
		comQuest.setIsNeeded(1);
		comQuest.setQuestId(questInfo.getQuestId());
		// comQuest.setQuestInfo(questInfo);
		return comQuest;
	}
}
