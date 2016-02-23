package com.jxxp.controller;


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
import com.jxxp.pojo.CompanyOther;
import com.jxxp.pojo.CompanyWholeInfo;
import com.jxxp.service.CompanyService;

@Controller("companyController")
@RequestMapping("/company")
public class CompanyController {
	private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

	@Resource
	private CompanyService companyService;

	/**
	 * 保存公司信息
	 * 
	 * @author gcx
	 * @param company
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveCompany.do")
	public String saveCompany(Company company, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
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
	public String getByName(String name, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		Company company = companyService.getCompany(name);
		model.put("company", company);
		return "jsp/success";

	}

	@RequestMapping("/getBranches.do")
	public String getBranches(Company company, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<CompanyBranch> branchList = companyService.getCompanyBranchByArea(0,
				company.getCompanyId());
		model.put("branchList", branchList);
		return "jsp/success";

	}

	/**
	 * 保存公司信息
	 * 
	 * @author cj
	 * @param companyWhole
	 * @return
	 */
	@RequestMapping("/saveCompanyWhole.do")
	public String saveCompanyWhole(Company company, CompanyOther companyOther, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		companyService.saveWholeCompany(new CompanyWholeInfo(company, companyOther));
		
		return "/jsp/areaAll";
	}
}
