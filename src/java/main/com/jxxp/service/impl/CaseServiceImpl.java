package com.jxxp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.CaseCommentMapper;
import com.jxxp.dao.QuestionInfoMapper;
import com.jxxp.dao.ReportCaseMapper;
import com.jxxp.pojo.CaseComment;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;
import com.jxxp.service.CaseService;

/**
 * 
 * @author cuijian
 *
 */
@Service("caseService")
public class CaseServiceImpl implements CaseService {
	@Resource
	private ReportCaseMapper reportCaseMapper;
	@Resource
	private CaseCommentMapper caseCommentMapper;
	@Resource 
	private QuestionInfoMapper questionInfoMapper;
	
	@Override
	public boolean saveCaseInfo(ReportCase caseInfo) {
		return reportCaseMapper.insert(caseInfo) > 0;
	}

	@Override
	public boolean addCaseComment(CaseComment comment, Long caseId) {
		return caseCommentMapper.insert(comment, caseId) > 0;
	}

	@Override
	public List<ReportCase> getCaseList(Reporter reporter) {
		List<ReportCase> list = null;
		list = reportCaseMapper.getCaseByReport(reporter);
		return list;
	}

	@Override
	public ReportCase getReportCase(String trackingNo, String accessCode) {
		return reportCaseMapper.findByNo(trackingNo, accessCode);
	}

	@Override
	public String getNewTrackingNo(Company company) {
		//生成规则：公司代码+时间戳
		
		return null;
	}

	@Override
	public Map<String, QuestionInfo> getQuestByCompany(Company company) {
		Map<String, QuestionInfo> map = new HashMap<String, QuestionInfo>();
		List<QuestionInfo> list = questionInfoMapper.getAllByCompany(company.getCompanyId());
		for (int i = 0; i < list.size(); i++) {
			QuestionInfo questionInfo = list.get(i);
			map.put(questionInfo.getQuestKey(), questionInfo);
		}
		return map;
	}

}
