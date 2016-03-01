package com.jxxp.controller.back;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.jxxp.controller.CaseController;
import com.jxxp.pojo.CaseChangeLog;
import com.jxxp.pojo.CaseComment;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.CompanyOther;
import com.jxxp.pojo.CompanyWholeInfo;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.ReportType;
import com.jxxp.pojo.User;
import com.jxxp.service.CaseChangeLogService;
import com.jxxp.service.CaseCommentService;
import com.jxxp.service.CaseService;
import com.jxxp.service.CompanyBranchService;
import com.jxxp.service.CompanyService;
import com.jxxp.service.QuestionService;
import com.jxxp.service.ReportTypeService;

@Controller("caseBackController")
@RequestMapping("/admin/caseBack")
public class CompanyBackController {
	private static final Logger log = LoggerFactory.getLogger(CaseController.class);

	@Resource
	private CaseService caseService;
	@Resource
	private CaseChangeLogService caseChangeLogService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CaseCommentService caseCommentService;
	@Resource
	private ReportTypeService reportTypeService;
	@Resource
	private QuestionService questionService;
	@Resource
	private CompanyBranchService companyBranchService;

	/***
	 * 根据公司以及其他条件获取案件信息列表
	 * 
	 * @author cj
	 * @param
	 * @return
	 */
	@RequestMapping("/showCaseByCompany.do")
	public String showCaseByCompany(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		String rtList = request.getParameter("rtList");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String keyWord = request.getParameter("keyWord");
		Map<String, String> map = new HashMap<String, String>();
		map.put("rtList", rtList);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("keyWord", keyWord);
		log.debug("Map:" + map);
		User user = (User) request.getSession().getAttribute("user");
		List<ReportCase> caseList = caseService.getCaseByCompany(user.getUserCompany(), map);
		log.debug("caseList:" + caseList);
		modelMap.put("caseList", caseList);
		return "jsp/admin/pages/reportAdmin";
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
			ModelMap modelMap) {
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

		modelMap.put("questionAnswerList", CaseController.getQuestionAnswerList(reportCase));
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
			ModelMap modelMap) {
		String strId = request.getParameter("rcId");
		String strState = request.getParameter("state");
		String strCompanyId = request.getParameter("companyId");
		String strSendToPlatform = request.getParameter("sendToPlatform");

		long rcId = Long.parseLong(strId);
		int afterState = Integer.parseInt(strState);
		int sendToPlatform = Integer.parseInt(strSendToPlatform);
		long afterCompanyId = Long.parseLong(strCompanyId);
		Company afterHandler = null;

		// 判断是否交由平台方处理
		if (sendToPlatform == 1) {
			afterHandler = companyService.getPlatformCompany();
			afterCompanyId = afterHandler.getCompanyId();
		} else {
			afterHandler = companyService.getCompanyById(afterCompanyId);
		}
		ReportCase reportCase = caseService.getReportCaseById(rcId);
		User user = (User) request.getSession().getAttribute("user");

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
			ModelMap modelMap) {
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
	 * @author gcx 获取问题模版
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getQuestTemlate.do")
	public String getQuestTemlate(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		List<Map<String, String>> questList = questionService.getMarkQuestions(company);
		log.debug("questList:" + questList);
		model.put("questList", questList);
		return "/jsp/admin/pages/settingQuest";
	}

	/**
	 * @author gcx 保存公司信息,其中包括公司的基本信息和公司所选的问题
	 * @param company
	 * @param questIds
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/addCompanyQuestions.do")
	public String addCompanyQuestions(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {

		List<QuestionInfo> questionList = new ArrayList<QuestionInfo>();
		// 获取前台复选框question的ids
		String[] questIdStr = request.getParameterValues("questId");
		log.debug("-----------" + request.getParameterValues("questId"));

		for (int i = 0; i < questIdStr.length; i++) {
			QuestionInfo question = new QuestionInfo();
			log.debug("--------" + questIdStr[i]);
			question.setQuestId(Long.parseLong(questIdStr[i]));
			questionList.add(question);
		}
		// 获取该客户所属的公司
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		boolean flag = companyService.saveCompanyQuestions(company, questionList);
		if (flag) {
			return "redirect:getQuestTemlate.do";
		} else {
			return "redirect:getQuestTemlate.do";
		}

	}

	/**
	 * @author gcx
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAllReportTypes.do")
	public String getAllReportTypes(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		List<ReportType> delfRtList = reportTypeService.getDefaultList();
		List<ReportType> rtList = new ArrayList<ReportType>();
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			Company company = user.getUserCompany();
			rtList = companyService.getCompanyReportType(company);
			log.debug("user id=" + user.getUserCompany().getCompanyId());
		}
		model.put("rtList", rtList);
		model.put("delfRtList", delfRtList);
		return "/jsp/admin/pages/settingType";
	}

	/**
	 * 添加公司所选择的问题类型
	 * 
	 * @author gcx
	 * @param company
	 * @param reportType
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/addQuestionTypes.do")
	public String addQuestionTypes(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		String typeJson = request.getParameter("reportType");
		log.debug("typeJson====" + typeJson);
		List<ReportType> rtList = JSON.parseArray(typeJson, ReportType.class);
		log.debug("rtList title====" + rtList.get(0).getRtTitle());
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		boolean flag = companyService.saveCompanyReportType(company, rtList);
		try {
			PrintWriter out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}

		} catch (IOException e) {
			log.error("添加问题类型失败！", e);
		}
		return null;

	}

	/**
	 * @author gcx
	 * @param wholeCompany
	 *            包含Company和CompanyOther
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/updateCompanyWholeInfo.do")
	public String updateCompanyWholeInfo(CompanyWholeInfo wholeCompany, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IllegalStateException, IOException {
		// 测试工公司基本信息是否有
		log.debug("baseInfo companyName=" + wholeCompany.getCompany().getCompanyName());
		// 其他信息
		log.debug("otherInfo serviceProtocol ="
				+ wholeCompany.getCompanyOther().getServiceProtocol());
		User user = (User) request.getSession().getAttribute("user");
		Company company = wholeCompany.getCompany();

		// 获取文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("logo");
		String dirPath = request.getSession().getServletContext().getRealPath("/")
				+ "fileupload/logo/" + company.getCompanyId();
		log.debug("is image=");
		if (file != null && !file.isEmpty()) {
			// 保存文件
			saveLogo(file, dirPath);

			// 完善公司其他信息 CompanyOther
			CompanyOther other = wholeCompany.getCompanyOther();
			if (other != null) {
				// 获取文件的高度和宽度
				String webPath = request.getSession().getServletContext().getContextPath();
				String accessPath = webPath + "/fileupload/logo/" + company.getCompanyId();
				FileInputStream fis = new FileInputStream(new File(dirPath + "/"
						+ file.getOriginalFilename()));
				BufferedImage image = ImageIO.read(fis);
				int width = image.getWidth();
				int height = image.getHeight();
				log.debug("width=======" + width + "path==" + UUID.randomUUID().toString());
				other.setLogoUrl(accessPath + "/" + file.getOriginalFilename());
				other.setLogoPath(accessPath);
				other.setLogoWidth(width);
				other.setLogoHeight(height);
			}
		}
		// 调用service,存储公司所有信息
		boolean flag = companyService.updateCompanyWholeInfo(wholeCompany);
		PrintWriter out = response.getWriter();
		if (flag) {
			out.print("success");
		} else {
			out.print("fail");
		}
		return null;
	}

	/**
	 * 添加分支机构
	 * 
	 * @author gcx
	 * @param branch
	 *            分支机构
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addCompanyBranches.do", method = RequestMethod.POST)
	public String addCompanyBranches(CompanyBranch branch, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		User user = (User) request.getSession().getAttribute("user");
		Company owner = user.getUserCompany();
		branch.setOwner(owner);
		boolean flag = companyBranchService.addCompanyBranch(branch);
		log.debug("provinceId=" + branch.getProvince().getAreaId());
		log.debug("branch name=" + branch.getBranchName());
		if (flag) {
			return "/jsp/pages/error";
		}
		return "/jsp/pages/error";
	}

	private void saveLogo(MultipartFile file, String dirPath) throws IllegalStateException,
			IOException {
		// 建立存放文件的文件夹
		File fileDir = new File(dirPath);
		if (!fileDir.exists()) {
			if (fileDir.mkdirs()) {
				log.debug("文件夹创建成功！");
			} else {
				log.debug("文件夹创建失败！");
			}
		}

		String filePath = dirPath + "/" + file.getOriginalFilename();
		// 存储文件
		File saveFile = new File(filePath);
		file.transferTo(saveFile);

	}

	/**
	 * @author gcx
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getCompanyBranches.do")
	public String getCompanyBranches(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		List<CompanyBranch> branchList = companyBranchService.getCompanyBranches(company
				.getCompanyId());
		model.put("branchList", branchList);
		return "/jsp/admin/pages/branchAdmin";
	}

	/**
	 * @author gcx
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getOwnerCompanyInfo.do")
	public String getOwnerCompanyInfo(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		company = companyService.getCompanyById(company.getCompanyId());
		model.put("company", company);
		return "/jsp/admin/pages/enterEditor";
	}
}
