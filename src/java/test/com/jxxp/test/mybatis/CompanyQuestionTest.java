package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.CompanyQuestionMapper;
import com.jxxp.pojo.QuestionInfo;

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
	private List<QuestionInfo> list;

	@Before
	public void init() {
		list = new ArrayList<QuestionInfo>();
		QuestionInfo q1 = new QuestionInfo();
		q1.setQuestId(new Long(8));
		list.add(q1);
		QuestionInfo q2 = new QuestionInfo();
		q2.setQuestId(new Long(9));
		list.add(q2);
	}

	/**
	 * 测试批量插入公司所选择的问题列表
	 */
	@Test
	public void saveCompanyQuestions() {
		assertTrue(companyQuestionMapper.insertQuestionList(list, new Long(1)) > 0);
		assertTrue(companyQuestionMapper.deleteQuestionList(list, new Long(1)) > 0);

	}

}
