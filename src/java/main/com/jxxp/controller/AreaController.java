package com.jxxp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jxxp.pojo.AreaInfo;
import com.jxxp.service.AreaService;

@Controller("areaController")
@RequestMapping("/area")
public class AreaController {
	@Resource
	private AreaService areaService;

	@RequestMapping("/getAll.do")
	public String getAllProvice(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		List<AreaInfo> areaList = areaService.getAllProvince();
		modelMap.put("areaList", areaList);
		return "/jsp/areaAll";

	}

	@RequestMapping("/getChildArea.do")
	public String getChildArea(AreaInfo parent, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		List<AreaInfo> areaList = areaService.getByParent(parent);
		modelMap.put("areaList", areaList);
		return "/jsp/areaAll";

	}
}
