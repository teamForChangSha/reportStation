package com.jxxp.controller;

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
import com.jxxp.pojo.DictionaryBean;
import com.jxxp.service.DictionaryService;

@Controller("dictController")
@RequestMapping("/dict")
public class DictionaryController {
	private static final Logger log = LoggerFactory.getLogger(CaseController.class);

	@Resource
	private DictionaryService dictionaryService;
	
	/*** 
     * 根据字典类型和值取对应的中文描述
     * @author cj
     * @param 
     * @return 
     */  
	@RequestMapping("/getDictName.do")
	public String getDictName(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap ) {
		String dictType = request.getParameter("dictType");
		String dictValue = request.getParameter("dictValue");
		DictionaryBean dict = dictionaryService.getDictName(dictType, Integer.parseInt(dictValue));
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(dict != null) {
				out.print(dict.getDictName());
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}
	
	/*** 
     * 根据类型获取该类型的字典类型集合
     * @author cj
     * @param 
     * @return 
     */  
	@RequestMapping("/getDictList.do")
	public String getDictList(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap ) {
		String dictType = request.getParameter("dictType");
		List<DictionaryBean> dictList = dictionaryService.getDictTypeList(dictType);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			String jsonStr = JSON.toJSONString(dictList);
			out.print(jsonStr);
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}
}
