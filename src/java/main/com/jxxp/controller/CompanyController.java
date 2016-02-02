package com.jxxp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jxxp.pojo.Company;
import com.jxxp.service.CompanyService;

@Controller("companyController")
@RequestMapping("/company")
public class CompanyController {
	@Resource
	private CompanyService CompanyService;

	@RequestMapping("/save.do")
	public String getAllProvice(Company company, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		CompanyService.saveCompanyInfo(company);
		return "/jsp/areaAll";
	}

	@RequestMapping("/getByName.do")
	public String getByName(String name, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		Company company = CompanyService.getCompany(name);
		model.put("company", company);
		return "jsp/success";

	}
}
