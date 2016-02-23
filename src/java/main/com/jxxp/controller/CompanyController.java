package com.jxxp.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.jxxp.dao.CompanyQuestionMapper;
import com.jxxp.dao.QuestionInfoMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
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
	private QuestionInfoMapper questionInfoMapper;
	@Resource
	private QuestionService questionService;

	/**
	 * 保存公司信息,其中包括公司的基本信息和公司所选的问题
	 * 
	 * @author gcx
	 * @param company
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/addCompany.do")
	public String saveCompany(Company company, long[] questIds, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		log.debug("get questions ids" + questIds);
		// questIds为空则该公司使用默认的问题列表
		if (questIds != null) {
			cqMapper.insertAll(questIds, company.getCompanyId());
		} else {
			// 获取默认的问题列别表questionInfoMapper.getAllByCompany(companyId)
			cqMapper.insertAll(questIds, company.getCompanyId());
		}
		companyService.saveCompanyInfo(company);
		return "/jsp/areaAll";
	}

	/**
	 * @author gcx
	 * @param companyName
	 *            公司名字
	 * @return 公司
	 */
	@RequestMapping("/getAllByName.do")
	public String getByName(String companyName, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		log.debug("get companyName==" + companyName);
		List<Company> companyList = new ArrayList<Company>();
		companyList = companyService.getCompanyByName(companyName);
		model.put("companyList", companyList);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(companyList);
		} catch (IOException e) {
			log.error("add company failed", e);
		}
		log.debug("获取公司集合成功size=" + companyList.size());
		return null;

	}

	@RequestMapping("/getBranches.do")
	public String getBranches(Company company, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<CompanyBranch> branchList = companyService.getCompanyBranchByArea(0,
				company.getCompanyId());
		model.put("branchList", branchList);
		return "jsp/success";

	}

}
