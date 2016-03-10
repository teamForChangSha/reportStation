package com.jxxp.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.jxxp.pojo.AreaInfo;
import com.jxxp.service.AreaService;

@Controller("areaBackController")
@RequestMapping("/admin/areaBack")
public class AreaBackController {
	private static final Logger log = LoggerFactory.getLogger(AreaBackController.class);

	@Resource
	private AreaService areaService;

	/**
	 * 获取所有省
	 * 
	 * @author gcx
	 * @return
	 */
	@RequestMapping("/getAllProvice.do")
	public void getAllProvice(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception  {
		List<AreaInfo> provinceList = areaService.getAllProvince();
		modelMap.put("provinceList", provinceList);
		String jsonList = JSON.toJSONString(provinceList);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonList);

		} catch (IOException e) {
			log.debug("查询所有省异常");
		}
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
	@RequestMapping("/getChildrenArea.do")
	public void getChildArea(AreaInfo parent, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception  {
		List<AreaInfo> childrenList = areaService.getByParent(parent);
		log.debug("have parentId??==" + parent.getAreaId() + "size====" + childrenList.size());
		String jsonList = JSON.toJSONString(childrenList);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonList);
		} catch (IOException e) {
			log.debug("查询下一级异常");
		}

	}
}
