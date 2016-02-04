package com.jxxp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jxxp.service.CompanyService;

@Controller("companyController")
@RequestMapping("/")
public class ReportStationController {
	@Resource
	private CompanyService companyService;
	
	@RequestMapping("/index.do")
	public String getCompany(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		modelMap.put("companyList", companyService.getAllCompanyList());
		return "jsp/index";
	}
}
