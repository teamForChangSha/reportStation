package com.jxxp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jxxp.comms.util.PDFUtil;
import com.jxxp.comms.util.ZipUtil;
import com.jxxp.comms.web.Page;
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
		list = reportCaseMapper.getCaseByReporter(reporter);
		return list;
	}

	@Override
	public ReportCase getReportCase(String trackingNo, String accessCode) {
		return reportCaseMapper.findByNo(trackingNo, accessCode);
	}

	@Override
	public String getNewTrackingNo(Company company) {
		// 生成规则：公司代码+时间戳
		String trackingNo = "";
		// trackingNo += company.getCompanyCode();
		trackingNo += new SimpleDateFormat("yyyyMM").format(new Date());
		trackingNo += generateKeyMapper.getKey();
		if (generateKeyMapper.updateKey() > 0) {

		}
		// 客户要求案件编号为6或者5位数
		int noLength = 6;
		trackingNo = trackingNo.substring(trackingNo.length() - noLength, trackingNo.length());
		return trackingNo;
	}

	@Override
	public ReportCase getReportCaseById(long rcId) {
		return reportCaseMapper.getById(rcId);
	}

	@Override
	public boolean saveCase(Reporter reporter, ReportCase reportCase, List<ReportAnswer> answerList) {
		boolean flag = false;
		// 判断Reporter对象是否存在
		if (reporter != null) {
			if (reporter.getReporterId() == 0) {
				flag = reporterMapper.insert(reporter) > 0;
				if (flag) {
					log.debug(reporter + "添加成功！");
				} else {
					log.debug(reporter + "添加成功！");
					return flag;
				}
			} else {
				if (reporterMapper.update(reporter) > 0) {
					log.debug(reporter + "更新成功！");
				} else {
					log.debug(reporter + "更新失败！");
					return flag;
				}
			}
		}

		// 保存案例对象
		flag = reportCaseMapper.insert(reportCase) > 0;
		if (flag) {
			log.debug(reportCase + "添加成功！");
		} else {
			log.debug(reportCase + "添加失败！");
			return flag;
		}

		// 保存案件集合
		for (ReportAnswer reportAnswer : answerList) {
			reportAnswer.setRcId(reportCase.getRcId());
			flag = reportAnswerMapper.insert(reportAnswer) > 0;
			if (flag) {
				log.debug(reportAnswer + "添加成功！");
			} else {
				log.debug(reportAnswer + "添加失败！");
				return flag;
			}
		}

		// 更新临时文件信息
		List<CaseAttach> caseAttachList = caseAttachMapper.getAllByTrackingNo(reportCase
				.getTrackingNo());
		for (CaseAttach caseAttach : caseAttachList) {
			caseAttach.setAttachPath(caseAttach.getAttachPath().replaceFirst("/temp/", "/file/"));
			caseAttach.setAttachUrl(caseAttach.getAttachUrl().replaceFirst("/temp/", "/file/"));
			caseAttach.setState(1);
			flag = caseAttachMapper.update(caseAttach) > 0;
			if (flag) {
				log.debug(caseAttach + "修改成功！");
			} else {
				log.debug(caseAttach + "修改失败！");
				return flag;
			}
		}

		return flag;
	}

	@Override
	public List<ReportCase> getCaseByCompany(Page page, Company company, Map<String, String> map) {
		List<ReportCase> caseList = new ArrayList<ReportCase>();
		if (company == null) {
			return caseList;
		}

		String rtList = map.get("rtList");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		String keyWord = map.get("keyWord");
		String trackingNo = map.get("trackingNo");
		Integer caseState = null;

		if (map.get("caseState") != null && map.get("caseState").length() > 0
				&& map.get("caseState").matches("^[0-9]*$")) {
			caseState = Integer.valueOf(map.get("caseState"));
		}
		caseList = reportCaseMapper.searchByKeysWithPage(page, company.getCompanyId(), startTime,
				endTime, keyWord, rtList, caseState, trackingNo);
		return caseList;
	}

	@Override
	public boolean updateCaseInfo(ReportCase caseInfo) {
		return reportCaseMapper.update(caseInfo) > 0;
	}

	@Override
	public List<ReportCase> getClientCase() {
		List<ReportCase> list = reportCaseMapper.getCaseByIsClient(1);
		return list;
	}

	@Override
	public List<ReportCase> getNotClientCase() {
		List<ReportCase> list = reportCaseMapper.getCaseByIsClient(0);
		return list;
	}

	@Override
	public String downloadCases(String[] cases, String webPath) throws Exception {
		for (int i = 0; i < cases.length; i++) {
			long rcId = Long.parseLong(cases[i]);
			ReportCase reportCase = reportCaseMapper.getById(rcId);
			List<Map<String, String>> questAnswerList = CaseController.getQuestionAnswerList(
					reportCase, questionInfoMapper.getQuestionTemlate());
			PDFUtil.createReportPDF(reportCase, questAnswerList, webPath);
		}
		String resPath = webPath.substring(0, webPath.lastIndexOf("/")) + ".zip";
		ZipUtil zipUril = new ZipUtil(resPath);
		zipUril.compress(webPath);
		return resPath;
	}

	public static void main(String[] args) {
		String path = "/54389403/";
		System.out.println(path.substring(0, path.lastIndexOf("/")) + ".zip");
	}

}
