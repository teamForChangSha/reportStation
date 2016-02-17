package com.jxxp.service.impl;

import javax.annotation.Resource;

import com.jxxp.dao.QuestionInfoMapper;
import com.jxxp.dao.ReportAnswerMapper;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;
import com.jxxp.service.QuestionService;

public class QuestionServiceImpl implements QuestionService {
	@Resource
	private QuestionInfoMapper questionInfoMapper;
	@Resource
	private ReportAnswerMapper reportAnswerMapper;
	
	@Override
	public QuestionInfo getQuestionById(long questionId) {
		// TODO Auto-generated method stub
		return questionInfoMapper.getById(questionId);
	}

	@Override
	public boolean addReportAnswer(ReportAnswer reportAnswer) {
		// TODO Auto-generated method stub
		return reportAnswerMapper.insert(reportAnswer) > 0;
	}

}
