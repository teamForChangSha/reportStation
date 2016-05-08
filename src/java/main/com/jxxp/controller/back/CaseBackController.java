package com.jxxp.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jxxp.comms.web.Page;
import com.jxxp.controller.CaseController;
import com.jxxp.pojo.CaseChangeLog;
import com.jxxp.pojo.CaseComment;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.User;
import com.jxxp.service.CaseChangeLogService;
import com.jxxp.service.CaseCommentService;
import com.jxxp.service.CaseService;
import com.jxxp.service.CompanyService;
import com.jxxp.service.OprationLogService;
import com.jxxp.service.QuestionService;

@Controller("caseBackController")
@RequestMapping("/admin/caseBack")
public class CaseBackController {
	private static final Logger log = LoggerFactory.getLogger(CaseBackController.class);

	@Resource
	private CaseService caseService;
	@Resource
	private CaseChangeLogService caseChangeLogService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CaseCommentService caseCommentService;
	@Resource
	private QuestionService questionService;
	@Resource
	private OprationLogService oprationLogService;

	/***
	 * 根据公司以及其他条件获取案件信息列表
	 * 
	 * @author cj
	 * @param
	 * @return
	 */
	@RequestMapping("/showCaseByCompany.do")
	public String showCaseByCompany(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String rtList = request.getParameter("rtList");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String keyWord = request.getParameter("keyWord");
		String caseState = request.getParameter("caseState");
		String trackingNo = request.getParameter("trackingNo");

		Map<String, String> map = new HashMap<String, String>();
		map.put("rtList", rtList);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("keyWord", keyWord);
		map.put("caseState", caseState);
		map.put("trackingNo", trackingNo);
		User user = (User) request.getSession().getAttribute("user");
		Page page = null;
		// 当前页
		String pageNow = request.getParameter("pageNow");
		int totalCount = caseService.getCaseByCompany(null, user.getUserCompany(), map).size();
		if (pageNow != null && !pageNow.equals("")) {
			page = new Page(totalCount, Integer.valueOf(pageNow));
		} else {
			page = new Page(totalCount, 1);
		}
		List<ReportCase> caseList = caseService.getCaseByCompany(page, user.getUserCompany(), map);
		Map<String, Object> companyResultMap = new HashMap<String, Object>();
		companyResultMap.put("page", page);
		companyResultMap.put("caseList", caseList);
		// 将公司信息集合和page组装成map，返回给前台
		String caseMapJson = JSON.toJSONString(companyResultMap,
				SerializerFeature.WriteMapNullValue);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.print(caseMapJson);
		return null;
		// return "jsp/admin/pages/reportAdmin";
	}

	/***
	 * 根据举报人显示以往举报列表
	 * 
	 * @author cj
	 * @param
	 * @return
	 */
	@RequestMapping("/showCaseById.do")
	public String showCaseById(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String strId = request.getParameter("rcId");
		long rcId = 0;
		if (strId != null) {
			rcId = Long.parseLong(strId);
		}
		ReportCase reportCase = caseService.getReportCaseById(rcId);
		if (reportCase == null) {
			log.debug("案例获取失败！");
			modelMap.put("errorMsg", "未找到匹配数据！");
			return "/jsp/pages/error";
		}

		modelMap.put("questionAnswerList",
				CaseController.getQuestionAnswerList(reportCase, questionService.getAllQuestions()));
		modelMap.put("reportCase", reportCase);
		return "/jsp/admin/pages/report_info";
	}

	/***
	 * 修改案件状态
	 * 
	 * @author cj
	 * @param
	 * @return
	 */
	@RequestMapping("/updateCaseState.do")
	public String updateCaseState(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String strId = request.getParameter("rcId");
		String strState = request.getParameter("state");
		String strCompanyId = request.getParameter("companyId");
		String strSendToPlatform = request.getParameter("sendToPlatform");

		long rcId = Long.parseLong(strId);
		int afterState = Integer.parseInt(strState);
		Integer sendToPlatform = (strSendToPlatform != null && !strSendToPlatform.equals("")) ? Integer
				.parseInt(strSendToPlatform) : null;
		long afterCompanyId = Long.parseLong(strCompanyId);
		Company afterHandler = null;

		ReportCase reportCase = caseService.getReportCaseById(rcId);
		User user = (User) request.getSession().getAttribute("user");
		// 判断是否要更改处理的公司
		if (sendToPlatform != null) {
			if (sendToPlatform == 1) {
				afterHandler = companyService.getPlatformCompany();
			} else {
				// 不是交给平台方处理，则是案件所属公司处理
				afterHandler = reportCase.getCompany();
			}
		} else {
			afterHandler = companyService.getCompanyById(afterCompanyId);
		}
		int beforeState = reportCase.getCaseState();
		Company beforeCompany = reportCase.getCurrentHandler();
		reportCase.setCaseState(afterState);
		reportCase.setCurrentHandler(afterHandler);

		CaseChangeLog caseChangeLog = new CaseChangeLog();
		caseChangeLog.setChangeTime(new Date());
		caseChangeLog.setOperator(user);
		caseChangeLog.setStateBefore(beforeState);
		caseChangeLog.setStateAfter(reportCase.getCaseState());
		caseChangeLog.setHandlerBefore(beforeCompany);
		caseChangeLog.setHandlerAfter(reportCase.getCurrentHandler());

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (caseService.updateCaseInfo(reportCase)
					&& caseChangeLogService.addCaseChangeLog(caseChangeLog, rcId)) {
				log.debug("修改案例并且添加日志记录成功！");
				out.print("success");
			} else {
				log.debug("修改案例并且添加日志记录失败！");
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}

		return null;
	}

	/***
	 * 添加案件追加信息
	 * 
	 * @author cj
	 * @param
	 * @return
	 */
	@RequestMapping("/addCaseComment.do")
	public String addCaseComment(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String content = request.getParameter("content");
		String strId = request.getParameter("rcId");
		User user = (User) request.getSession().getAttribute("user");

		long rcId = Long.parseLong(strId);

		ReportCase reportCase = caseService.getReportCaseById(rcId);

		CaseComment caseComment = new CaseComment();
		caseComment.setContent(content);
		caseComment.setPostTime(new Date());

		if (user == null) {
			caseComment.setIsReporter(1);
		} else {
			caseComment.setIsReporter(0);
			caseComment.setOwner(user);
			caseComment.setOwnerCompany(user.getUserCompany());
		}

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (caseCommentService.addCaseComment(caseComment, rcId)) {
				log.debug("案件追加信息添加成功！");
				// 如果不是举报人追加信息，需要记录变更信息
				if (caseComment.getIsReporter() == 0) {
					CaseChangeLog caseChangeLog = new CaseChangeLog();
					caseChangeLog.setChangeTime(new Date());
					caseChangeLog.setOperator(user);
					caseChangeLog.setStateAfter(reportCase.getCaseState());
					caseChangeLog.setStateBefore(reportCase.getCaseState());
					caseChangeLog.setHandlerAfter(user.getUserCompany());
					caseChangeLog.setHandlerBefore(user.getUserCompany());

					if (caseChangeLogService.addCaseChangeLog(caseChangeLog, rcId)) {
						log.debug("日志信息添加成功！");
					} else {
						log.debug("日志信息添加失败！");
					}
				}
				out.print("success");
			} else {
				log.debug("案件追加信息添加失败！");
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}

		return null;
	}

	/**
	 * 最近的案件简报
	 * 
	 * @author gcx
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showLastCase.do")
	public String showLastCase(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		// map中的存放的是搜索关键词语，没有key则查询所有，故map不为空，key可以为空。
		Map<String, String> map = new HashMap<String, String>();
		User user = (User) request.getSession().getAttribute("user");
		List<ReportCase> caseList = new ArrayList<ReportCase>();
		if (user.getUserType() <= 2) {// 客户公司用户和试用
			caseList = caseService.getCaseByCompany(null, user.getUserCompany(), map);
			modelMap.put("caseList", caseList);
		} else {// 管理员和超级管理员
			List<Map<String, Object>> userLogList = oprationLogService.getLastOpration();
			modelMap.put("userLogList", userLogList);
			// 最近十个用户登录进行的操作日志
			List<ReportCase> clientCaseList = caseService.getClientCase();
			List<ReportCase> notClientCaseList = caseService.getNotClientCase();
			modelMap.put("clientCaseList", clientCaseList);
			modelMap.put("notClientCaseList", notClientCaseList);
		}
		return "jsp/admin/default";
	}

	/**
	 * 下载案件
	 * 
	 * @author cj
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadCases.do")
	public String downloadCases(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String paramsId = request.getParameter("case");
		String[] cases = paramsId.split(",");

		String rootPath = request.getSession().getServletContext().getRealPath("/") + "download/"
				+ new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + "/";
		String webPath = request.getSession().getServletContext().getContextPath() + "/download/"
				+ new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + "/";

		log.debug("webPath:" + webPath);
		log.debug("rootPath:" + rootPath);
		String resPath = caseService.downloadCases(cases, rootPath);
		resPath = resPath.substring(resPath.indexOf("reportStation") - 1);
		log.debug("resPath:" + resPath);

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		out = response.getWriter();
		out.print(resPath);

		return null;
	}

	public static void main(String[] args) {
		String str = "C:\\software\\mydata\\catt\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\reportStation\\download/20160417031602.zip";

		System.out.println(str.substring(str.indexOf("reportStation")));
	}
}