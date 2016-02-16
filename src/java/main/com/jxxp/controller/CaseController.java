package com.jxxp.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxxp.pojo.CaseAttach;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;
import com.jxxp.service.CaseAttachService;
import com.jxxp.service.CaseService;
import com.jxxp.service.QuestionService;
import com.jxxp.service.ReporterService;

/*
 * @author cj
 */
@Controller("caseController")
@RequestMapping("/case")
public class CaseController {
	@Resource
	private CaseService caseService;
	@Resource
	private ReporterService reporterService;
	@Resource
	private CaseAttachService caseAttachService;
	@Resource
	private QuestionService questionService;
	
	/*** 
     * 添加案件 
     * @author cj
     * @param 
     * @return 
     */  
    @RequestMapping("/addCase.do")
	public String addCase(HttpServletRequest request, HttpServletResponse response) {
    	//获取参数，包括跟踪号，查询密码，问题列表
    	String trackingNo = request.getParameter("trackingNo");
    	String accessCode = request.getParameter("accessCode");
    	String rtList = request.getParameter("rtList");
    	String reporterJson = request.getParameter("reporter");
    	String questionsJson = request.getParameter("questions");
    	
    	Reporter reporter = JSON.parseObject(reporterJson, Reporter.class);
		//判断Reporter对象是否存在
		if(reporter.getReporterId() == 0) {
			reporterService.addReporter(reporter);
		}
		
		//获取session中保存的CompanyBranch对象
		CompanyBranch companyBranch = (CompanyBranch) request.getSession().getAttribute("companyBranch");
		
		//获取附件列表
		List<CaseAttach> attachList = caseAttachService.getCaseAttachByTrackingNo(trackingNo);
		if(attachList.size() > 0) {
			String filePath = "";
			
			caseAttachService.updateTempCaseAttach(trackingNo, filePath);
		}
		
		
		//保存ReportCase对象
		ReportCase reportCase = new ReportCase();
		reportCase.setRtList(rtList);
		reportCase.setAccessCode(accessCode);
		reportCase.setReporter(reporter);
		reportCase.setTrackingNo(trackingNo);
		reportCase.setCompany(companyBranch.getOwner());
		reportCase.setBranch(companyBranch);
		reportCase.setCaseState(0);
		reportCase.setCreateTime(new Date());
		
    	caseService.saveCaseInfo(reportCase);
    	
    	//保存问题回答列表
    	List<TempData> list = JSON.parseArray(questionsJson, TempData.class);
		for (TempData tempData : list) {
			String tempName = tempData.getName();
			String tempValue = tempData.getValue();
			long questionId = Long.parseLong(tempName.substring(tempName.indexOf('_') + 1));
			QuestionInfo questionInfo = questionService.getQuestionById(questionId);
			
			ReportAnswer reportAnswer = new ReportAnswer();
			reportAnswer.setQuestKey(questionInfo.getQuestKey());
			reportAnswer.setQuestValue(tempValue);
			reportAnswer.setRcId(reportCase.getRcId());
			questionService.addReportAnswer(reportAnswer);
		}
    	
    	
    	return null;
	}
	
	
	/*** 
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile 
     * @author cj
     * @param file 
     * @return 
     */  
    @RequestMapping("/fileUpload.do")  
    public String fileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response) {  
        response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (!file.isEmpty()) {  
				String rootPath = request.getSession().getServletContext().getRealPath("/") + "fileupload/temp/";
		        String trackingNo = request.getParameter("trackingNo");
				saveTempFile(rootPath,file,trackingNo );
		        out.print(getTempFileNames(rootPath, trackingNo));
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;  
    }  

    //保存临时文件
    public void saveTempFile(String rootPath,MultipartFile file,String trackingNo) throws IllegalStateException, IOException {
    	String fileDir = rootPath + File.separator + trackingNo;
    	File dir = new File(fileDir);
    	if(!dir.exists()) {
    		dir.mkdirs();
    	}
    	String fileName = fileDir + File.separator + file.getOriginalFilename();
        // 转存文件   
        file.transferTo(new File(fileName));
    }
    
    //获取临时文件列表
    public String getTempFileNames(String rootPath,String trackingNo) {
    	String fileNames = "";
    	String path = rootPath + trackingNo;
    	File root = new File(path);
    	if(root.exists()) {
    		File[] files = root.listFiles();
    		for (int i = 0; i < files.length; i++) {
				fileNames += files[i].getName() + ",";
			}
    	}
    	if(fileNames.length() > 0) {
    		fileNames = fileNames.substring(0, fileNames.length() - 1);
    	}
    	return fileNames;
    }
    
    
    public static void main(String[] args) {
		String test = "question_11";
		System.out.println(test.substring(test.indexOf('_') + 1));
	}
}

class TempData {
	private String name;
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}