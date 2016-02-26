package com.jxxp.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jxxp.controller.CaseController;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.User;
import com.jxxp.service.CaseService;

@Controller("caseBackController")
@RequestMapping("/admin/caseBack")
public class CompanyBackController {
	private static final Logger log = LoggerFactory.getLogger(CaseController.class);
	
	@Resource
	private CaseService caseService;

	/*** 
     * 根据公司
     * @author cj
     * @param  
     * @return 
     */
    @RequestMapping("/showCaseByCompany.do")  
    public String showCaseByCompany(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		String rtList = request.getParameter("rtList");
		String createTime = request.getParameter("createTime");
		String keyWord = request.getParameter("keyWord");
    	Map<String,String> map = new HashMap<String, String>();
		map.put("rtList", rtList);
		map.put("createTime", createTime);
		map.put("keyWord", keyWord);

    	User user = (User) request.getSession().getAttribute("user");
    	List<ReportCase> caseList = caseService.getCaseByCompany(user.getUserCompany(), map);
    	log.debug("caseList:" + caseList);
    	modelMap.put("caseList", caseList);
    	return "jsp/admin/pages/reportAdmin";
    }
    
    /*** 
     * 根据举报人显示以往举报列表 
     * @author cj
     * @param  
     * @return 
     */
    @RequestMapping("/showCaseById.do")  
    public String showCaseById(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {  
		String strId = request.getParameter("rcId");
		long rcId = 0;
		if(strId != null) {
			rcId = Long.parseLong(strId);
		}
		ReportCase reportCase = caseService.getReportCaseById(rcId);
		if(reportCase == null) {
			log.debug("案例获取失败！");
			modelMap.put("errorMsg", "未找到匹配数据！");
			return "/jsp/pages/error";
		}
		
		modelMap.put("questionAnswerList", CaseController.getQuestionAnswerList(reportCase));
		modelMap.put("reportCase", reportCase);
    	return "/jsp/admin/pages/report_info";
    }
    
    
    
}
