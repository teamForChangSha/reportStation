package com.jxxp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.CaseCommentMapper;
import com.jxxp.dao.ReportCaseMapper;
import com.jxxp.pojo.CaseComment;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;
import com.jxxp.service.CaseService;

/**
 * 
 * @author cj
 *
 */
@Service("caseService")
public class CaseServiceImpl implements CaseService {
	@Resource
	private ReportCaseMapper reportCaseMapper;
	@Resource
	private CaseCommentMapper caseCommentMapper;
	
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

}
