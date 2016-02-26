package com.jxxp.service;

import java.util.List;

import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;
import com.jxxp.pojo.ReportType;

/**
 * 
 * @author cj
 * 
 */
public interface QuestionService {
	/**
	 * 根据ID获取问题
	 * 
	 * @param questionID
	 *            问题编号
	 *            
	 * @return 返回问题对象，若未能匹配，则返回null
	 */
	QuestionInfo getQuestionById(long questionId);
	
	/**
	 * 保存问题回复
	 * 
	 * @param reportAnswer
	 *            问题回复对象
	 *            
	 * @return 返回保存结果，成功为true，否则返回false
	 */
	boolean addReportAnswer(ReportAnswer reportAnswer);
	
	/**
	 * 返回所有问题列表
	 * 
	 * @return 所有问题集合，没有则返回空列表
	 */
	public List<QuestionInfo> getAllQuestions();
}
