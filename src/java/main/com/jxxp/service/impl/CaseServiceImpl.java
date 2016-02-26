package com.jxxp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jxxp.controller.CaseController;
import com.jxxp.dao.CaseAttachMapper;
import com.jxxp.dao.CaseCommentMapper;
import com.jxxp.dao.GenerateKeyMapper;
import com.jxxp.dao.QuestionInfoMapper;
import com.jxxp.dao.ReportAnswerMapper;
import com.jxxp.dao.ReportCaseMapper;
import com.jxxp.dao.ReporterMapper;
import com.jxxp.pojo.CaseAttach;
import com.jxxp.pojo.CaseComment;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;
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
	private static final Logger log = LoggerFactory.getLogger(CaseServiceImpl.class);
	
	@Resource
	private ReportCaseMapper reportCaseMapper;
	@Resource
	private CaseCommentMapper caseCommentMapper;
	@Resource
	private GenerateKeyMapper generateKeyMapper;
	@Resource
	private ReporterMapper reporterMapper;
	@Resource
	private ReportAnswerMapper reportAnswerMapper;
	@Resource
	private CaseAttachMapper caseAttachMapper;
	
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
		list = reportCaseMapper.getCaseByReporter(reporter);
		return list;
	}

	@Override
	public ReportCase getReportCase(String trackingNo, String accessCode) {
		return reportCaseMapper.findByNo(trackingNo, accessCode);
	}

	@Override
	public String getNewTrackingNo(Company company) {
		//生成规则：公司代码+时间戳
		String trackingNo = "";
		trackingNo += company.getCompanyCode();
		trackingNo += new SimpleDateFormat("yyyyMM").format(new Date());
		trackingNo += generateKeyMapper.getKey();
		if(generateKeyMapper.updateKey() > 0) {
			
		}
		return trackingNo;
	}

	@Override
	public ReportCase getReportCaseById(long rcId) {
		return reportCaseMapper.getById(rcId);
	}

	@Override
	public boolean saveCase(Reporter reporter, ReportCase reportCase, List<ReportAnswer> answerList) {
		boolean flag = false;
		//判断Reporter对象是否存在
		if(reporter != null) {
			if(reporter.getReporterId() == 0) {
				flag = reporterMapper.insert(reporter) > 0;
				if(flag) {
					log.debug(reporter + "添加成功！");
				} else {
					log.debug(reporter + "添加成功！");
					return flag;
				}
			} else {
				if(reporterMapper.update(reporter) > 0) {
					log.debug(reporter + "更新成功！");
				} else {
					log.debug(reporter + "更新失败！");
					return flag;
				}
			}
		}
		
		//保存案例对象
		if(reportCaseMapper.insert(reportCase) > 0) {
    		log.debug(reportCase + "添加成功！");
    	} else {
    		log.debug(reportCase + "添加失败！");
    		return flag;
    	}
		
		//保存案件集合
		for (ReportAnswer reportAnswer : answerList) {
			reportAnswer.setRcId(reportCase.getRcId());
			flag = reportAnswerMapper.insert(reportAnswer) > 0;
			if(flag) {
				log.debug(reportAnswer + "添加成功！");
			} else {
				log.debug(reportAnswer + "添加失败！");
				return flag;
			}
		}
		
		//更新临时文件信息
		List<CaseAttach> caseAttachList = caseAttachMapper.getAllByTrackingNo(reportCase.getTrackingNo());
		for (CaseAttach caseAttach : caseAttachList) {
			caseAttach.setAttachPath(caseAttach.getAttachPath().replaceFirst("/temp/", "/file/"));
			caseAttach.setAttachUrl(caseAttach.getAttachUrl().replaceFirst("/temp/", "/file/"));
			caseAttach.setState(1);
			flag = caseAttachMapper.update(caseAttach) > 0;
			if(flag) {
				log.debug(caseAttach + "修改成功！");
			} else {
				log.debug(caseAttach + "修改失败！");
				return flag;
			}
		}
		
		return flag;
	}

	@Override
	public List<ReportCase> getCaseByCompany(Company company, Map<String,String> map) {
		List<ReportCase> caseList = new ArrayList<ReportCase>();
		if(company == null) {
			return caseList;
		}
		
		String rtList = map.get("rtList");
		String createTime = map.get("createTime");
		String keyWord = map.get("keyWord");
		
		caseList = reportCaseMapper.searchByKeys(company.getCompanyId(), createTime, keyWord, rtList);
		return caseList;
	}

	@Override
	public boolean updateCaseInfo(ReportCase caseInfo) {
		return reportCaseMapper.update(caseInfo) > 0;
	}
	
}
