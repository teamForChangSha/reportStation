package com.jxxp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.CompanyQuestionMapper;
import com.jxxp.dao.QuestionInfoMapper;
import com.jxxp.dao.ReportAnswerMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;
import com.jxxp.service.QuestionService;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
	@Resource
	private QuestionInfoMapper questionInfoMapper;
	@Resource
	private ReportAnswerMapper reportAnswerMapper;
	@Resource
	private CompanyQuestionMapper companyQuestionMapper;;

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

	@Override
	public List<QuestionInfo> getAllQuestions() {
		// TODO Auto-generated method stub
		return questionInfoMapper.getQuestionTemlate();
	}

	@Override
	public List<Map<String, String>> getMarkQuestions(Company company) {
		List<Map<String, String>> questList = new ArrayList<Map<String, String>>();
		// 问题模版
		List<QuestionInfo> defQuestList = questionInfoMapper.getQuestionTemlate();
		// 该公司下的问题
		List<QuestionInfo> comQuestList = questionInfoMapper
				.getAllByCompany(company.getCompanyId());

		for (int i = 0; i < defQuestList.size(); i++) {
			QuestionInfo defQuest = defQuestList.get(i);
			Map<String, String> questMap = new HashMap<String, String>();
			for (int j = 0; j < comQuestList.size(); j++) {
				QuestionInfo comQuest = comQuestList.get(j);
				questMap.put("questId", String.valueOf(defQuest.getQuestId()));
				questMap.put("quest", defQuest.getQuest());
				if (defQuest.getQuestId() == comQuest.getQuestId()) {
					questMap.put("mark", "1");
				} else {
					questMap.put("mark", "0");
				}
			}
			questList.add(questMap);
		}
		return questList;
	}
}
