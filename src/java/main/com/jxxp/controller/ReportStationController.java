package com.jxxp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.jxxp.pojo.AreaInfo;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.ReportType;
import com.jxxp.service.AreaService;
import com.jxxp.service.CompanyService;

@Controller("reportStationController")
@RequestMapping("/")
public class ReportStationController {
	@Resource
	private CompanyService companyService;
	@Resource
	private AreaService areaService;

	@RequestMapping("/getAllCompany.do")
	public String getCompany(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		List<Company> dataList = companyService.getAllCompanyList();
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			String json = JSONArray.toJSONString(dataList);
			out.print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/getProvinceByCompany.do")
	public String getProvinceByCompanyId(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
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
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/getCityByCompany.do")
	public String getCityByCompanyId(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
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
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/getCompanyBranch.do")
	public String getCompanyBranch(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
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
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/showReportType.do")
	public String showReportType(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		long companyId = 0;
		long branchId = 0;

		String cId = request.getParameter("companyId");
		String bId = request.getParameter("branchId");
		if (cId != null) {
			companyId = Long.parseLong(cId);
		}
		if (bId != null) {
			branchId = Long.parseLong(bId);
		}

		Company company = companyService.getCompanyById(companyId);
		CompanyBranch companyBranch = companyService.getCompanyBranchById(branchId);
		
		HttpSession session = request.getSession();
		session.setAttribute("company", company);
		session.setAttribute("companyBranch", companyBranch);
		
		List<ReportType> dataList = companyService.getCompanyReportType(company);
		modelMap.put("rtList", dataList);
		
		return "/jsp/pages/reportType";
	}
}
