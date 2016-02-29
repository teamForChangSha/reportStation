package com.jxxp.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
import com.jxxp.pojo.CaseChangeLog;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.User;
import com.jxxp.service.CaseChangeLogService;
import com.jxxp.service.CaseService;
import com.jxxp.service.CompanyService;

@Controller("caseBackController")
@RequestMapping("/admin/caseBack")
public class CompanyBackController {
	private static final Logger log = LoggerFactory.getLogger(CaseController.class);
	
	@Resource
	private CaseService caseService;
	@Resource
	private CaseChangeLogService caseChangeLogService;
	@Resource
	private CompanyService companyService;

	/*** 
     * 根据公司以及其他条件获取案件信息列表
     * @author cj
     * @param  
     * @return 
     */
    @RequestMapping("/showCaseByCompany.do")  
    public String showCaseByCompany(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		String rtList = request.getParameter("rtList");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String keyWord = request.getParameter("keyWord");
    	Map<String,String> map = new HashMap<String, String>();
		map.put("rtList", rtList);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("keyWord", keyWord);
		log.debug("Map:" + map);
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
    
    /*** 
     * 修改案件状态 
     * @author cj
     * @param  
     * @return 
     */  
    @RequestMapping("/updateCaseState.do")  
    public String updateCaseState(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
    	String strId = request.getParameter("rcId");
    	String strState = request.getParameter("state");
    	String strCompanyId = request.getParameter("companyId");
    	
    	long rcId = Long.parseLong(strId);
    	int afterState = Integer.parseInt(strState);
    	long afterCompanyId = Long.parseLong(strCompanyId);
    	Company currentHandler = companyService.getCompanyById(afterCompanyId);
    	
    	ReportCase reportCase = caseService.getReportCaseById(rcId);
    	User user = (User) request.getSession().getAttribute("user");
    	
    	int beforeState = reportCase.getCaseState();
    	Company beforeCompany = reportCase.getCurrentHandler();
    	
    	reportCase.setCaseState(afterState);
    	reportCase.setCurrentHandler(currentHandler);
    	
    	CaseChangeLog caseChangeLog = new CaseChangeLog();
		caseChangeLog.setChangeTime(new Date());
		caseChangeLog.setOperator(user);
		caseChangeLog.setStateBefore(beforeState);
		caseChangeLog.setStateAfter(reportCase.getCaseState());
		caseChangeLog.setHandlerBefore(beforeCompany);
		caseChangeLog.setHandlerAfter(reportCase.getCurrentHandler());
    	
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(caseService.updateCaseInfo(reportCase) && caseChangeLogService.addCaseChangeLog(caseChangeLog, rcId)) {
				log.debug("修改案例并且添加日志记录成功！");
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
    	return null;
    }
    
}
