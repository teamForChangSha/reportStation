package com.jxxp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.jxxp.pojo.AreaInfo;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportType;
import com.jxxp.service.AreaService;
import com.jxxp.service.CaseService;
import com.jxxp.service.CompanyService;

/*
 * auther cj
 */
@Controller("reportStationController")
@RequestMapping("/")
public class ReportStationController {
	private static final Logger log = LoggerFactory.getLogger(ReportStationController.class);

	@Resource
	private CompanyService companyService;
	@Resource
	private AreaService areaService;
	@Resource
	private CaseService caseService;

	@RequestMapping("/getAllCompany.do")
	public String getCompany(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		log.debug("into getAllCompany.do...");
		List<Company> dataList = companyService.getAllCompanyList();

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			String json = JSONArray.toJSONString(dataList);
			out.print(json);
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	@RequestMapping("/getProvinceByCompany.do")
	public String getProvinceByCompanyId(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		long companyId = 0;

		String cId = request.getParameter("companyId");
		if (cId != null) {
			companyId = Long.parseLong(cId);
		}

		List<AreaInfo> dataList = areaService.getProvinceByCompanyId(companyId);

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			String json = JSONArray.toJSONString(dataList);
			out.print(json);
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	@RequestMapping("/getCityByCompany.do")
	public String getCityByCompanyId(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		long companyId = 0;
		long parentId = 0;

		String cId = request.getParameter("companyId");
		String aId = request.getParameter("parentId");
		if (cId != null) {
			companyId = Long.parseLong(cId);
		}
		if (aId != null) {
			parentId = Long.parseLong(aId);
		}

		List<AreaInfo> dataList = areaService.getCityByCompanyId(companyId, parentId);

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			String json = JSONArray.toJSONString(dataList);
			out.print(json);
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	@RequestMapping("/getCompanyBranch.do")
	public String getCompanyBranch(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		long companyId = 0;
		long areaId = 0;

		String cId = request.getParameter("companyId");
		String aId = request.getParameter("areaId");
		if (cId != null) {
			companyId = Long.parseLong(cId);
		}
		if (aId != null) {
			areaId = Long.parseLong(aId);
		}

		List<CompanyBranch> dataList = companyService.getCompanyBranchByArea(areaId, companyId);

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			String json = JSONArray.toJSONString(dataList);
			out.print(json);
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	@RequestMapping("/showReportType.do")
	public String showReportType(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		long branchId = 0;

		String bId = request.getParameter("branchId");

		if (bId != null) {
			branchId = Long.parseLong(bId);
		}

		CompanyBranch companyBranch = companyService.getCompanyBranchById(branchId);

		HttpSession session = request.getSession();
		session.setAttribute("companyBranch", companyBranch);

		List<ReportType> dataList = companyService.getCompanyReportType(companyBranch.getOwner());
		// 如果该公司未配置举报类型，则使用默认类型
		if (dataList.size() == 0) {
			dataList = companyService.getCompanyReportType(new Company());
		}
		modelMap.put("reportTypeList", dataList);

		return "/jsp/pages/reportType";
	}

	@RequestMapping("/showQuestionPage.do")
	public String showQuestionPage(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String[] strs = request.getParameterValues("reportType");
		String rtList = "";
		for (int i = 0; i < strs.length; i++) {
			rtList += strs[i] + ",";
		}
		if (rtList.length() > 0) {
			rtList = rtList.substring(0, rtList.length() - 1);
		}

		CompanyBranch companyBranch = (CompanyBranch) request.getSession().getAttribute(
				"companyBranch");
		Map<String, QuestionInfo> dataMap = companyService.getCompanyQuestions(companyBranch
				.getOwner());
		// System.out.println("size======" +
		// dataMap.get("quest").getQuestDesc());
		String trackingNo = caseService.getNewTrackingNo(companyBranch.getOwner());
		HttpSession session = request.getSession();
		session.setAttribute("trackingNo", trackingNo);

		modelMap.put("questionMap", dataMap);
		modelMap.put("rtList", rtList);

		return "/jsp/pages/reportCase";
	}

	@RequestMapping("/admin")
	public String admin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return "/jsp/admin/index";
	}
}
