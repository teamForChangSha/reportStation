package com.jxxp.service;

import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;

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
}
