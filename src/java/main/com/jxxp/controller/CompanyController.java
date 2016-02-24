package com.jxxp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.jxxp.dao.CompanyQuestionMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportType;
import com.jxxp.service.CompanyService;
import com.jxxp.service.QuestionService;

/**
 * @author gcx 公司信息管理，客户的增删该查
 * 
 */
@Controller("companyController")
@RequestMapping("/company")
public class CompanyController {
	private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

	@Resource
	private CompanyService companyService;
	@Resource
	private CompanyQuestionMapper cqMapper;
	@Resource
	private QuestionService questionService;

	/**
	 * 保存公司信息,其中包括公司的基本信息和公司所选的问题
	 * 
	 * @param company
	 * @param questIds
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/addCompanyQuestions.do")
	public String addCompanyQuestions(Company company, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		String questionsJson = request.getParameter("questions");
		log.debug("questionsJson==" + questionsJson);
		List<QuestionInfo> questionList = JSON.parseArray(questionsJson, QuestionInfo.class);
		companyService.saveCompanyQuestions(company, questionList);
		// TODO 返回界面待定
		return null;
	}

	/**
	 * @author gcx
	 * @param companyName
	 *            公司名字
	 * @return 公司
	 */

	@RequestMapping("/getAllByName.do")
	public String getAllByName(String companyName, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		if (companyName != null) {
			try {
				companyName = new String(companyName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		log.debug("get companyName====" + companyName);
		List<Company> companyList = new ArrayList<Company>();
		companyList = companyService.getCompanyByName(companyName);
		String companyJson = JSON.toJSONString(companyList);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(companyJson);
		} catch (IOException e) {
			log.error("add company failed", e);
		}
		log.debug("获取公司集合成功size=" + companyList.size());
		return null;

	}

	/**
	 * 添加公司所选择的问题类型
	 * 
	 * @param company
	 * @param reportType
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/addCompanyType.do")
	public String addCompanyType(Company company, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String typeJson = request.getParameter("reportType");
		log.debug("typeJson====" + typeJson);
		List<ReportType> rtList = JSON.parseArray(typeJson, ReportType.class);
		log.debug("rtList title====" + rtList.get(0).getRtTitle());
		companyService.saveCompanyReportType(company, rtList);
		return "jsp/test.jsp";

	}
}
