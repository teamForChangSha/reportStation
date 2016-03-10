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

import com.jxxp.pojo.AreaInfo;
import com.jxxp.service.AreaService;

@Controller("areaController")
@RequestMapping("/area")
public class AreaController {
	private static final Logger log = LoggerFactory.getLogger(AreaController.class);
	
	@Resource
	private AreaService areaService;

	/**
	 * 获取所有
	 * 
	 * @author gcx
	 * @return
	 */
	@RequestMapping("/getAll.do")
	public String getAllProvice(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception  {
		List<AreaInfo> areaList = areaService.getAllProvince();
		modelMap.put("areaList", areaList);
		return "/jsp/index";

	}

	/**
	 * 获取某个行政区域的下一级行政区域
	 * 
	 * @author gcx
	 * @param parent
	 *            父级行政区域
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/getChildArea.do")
	public String getChildArea(AreaInfo parent, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception  {
		List<AreaInfo> areaList = areaService.getByParent(parent);
		modelMap.put("areaList", areaList);
		return "/jsp/areaAll";

	}
}
