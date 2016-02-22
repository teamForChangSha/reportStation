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

import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.service.CompanyService;

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
	public String saveCompany(Company company, List<QuestionInfo> questions,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		log.debug("get company questions 1" + questions.get(0).getQuest());
		log.debug("get company questions 2" + questions.get(1).getQuest());
		companyService.saveCompanyInfo(company);
		return "/jsp/areaAll";
	}

	/**
	 * @author gcx
	 * @param name
	 *            公司名字
	 * @return 公司
	 */
	@RequestMapping("/getByName.do")
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
