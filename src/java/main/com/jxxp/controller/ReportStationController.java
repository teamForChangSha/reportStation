package com.jxxp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.jxxp.pojo.AreaInfo;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.service.AreaService;
import com.jxxp.service.CompanyService;

@Controller("reportStationController")
@RequestMapping("/")
public class ReportStationController {
	@Resource
	private CompanyService companyService;
	@Resource
	private AreaService areaService;
	
	@RequestMapping("/index.do")
	public String getCompany(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		modelMap.put("companyList", companyService.getAllCompanyList());
		return "jsp/index";
	}
	

	@RequestMapping("/getProvinceByCompany.do")
	public String getProvinceByCompanyId(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		long companyId = 0;
		
		String cId = request.getParameter("companyId");
		if(cId != null) {
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
	public String getCityByCompanyId(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		long companyId = 0;
		long areaId = 0;
		
		String cId = request.getParameter("companyId");
		String aId = request.getParameter("areaId");
		if(cId != null) {
			companyId = Long.parseLong(cId);
		}
		if(aId != null) {
			areaId = Long.parseLong(aId);
		}
		
		List<AreaInfo> dataList = areaService.getCityByCompanyId(companyId, areaId);
		
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
	
//	@RequestMapping("/getCompanyBranch.do")
//	public String getCompanyBranch(HttpServletRequest request,
//			HttpServletResponse response, ModelMap modelMap) {
//		long companyId = 0;
//		long areaId = 0;
//		
//		String cId = request.getParameter("companyId");
//		String aId = request.getParameter("areaId");
//		if(cId != null) {
//			companyId = Long.parseLong(cId);
//		}
//		if(aId != null) {
//			areaId = Long.parseLong(aId);
//		}
//		
//		List<CompanyBranch> dataList = companyService.getCompanyBranchByArea(area, company);
//		
//		response.setCharacterEncoding("UTF-8");
//		PrintWriter out;
//		try {
//			out = response.getWriter();
//			String json = JSONArray.toJSONString(dataList);
//			out.print(json);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}
