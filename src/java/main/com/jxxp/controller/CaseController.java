package com.jxxp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.jxxp.pojo.CaseAttach;
import com.jxxp.pojo.CaseChangeLog;
import com.jxxp.pojo.CaseComment;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;
import com.jxxp.pojo.User;
import com.jxxp.service.CaseAttachService;
import com.jxxp.service.CaseChangeLogService;
import com.jxxp.service.CaseCommentService;
import com.jxxp.service.CaseService;
import com.jxxp.service.CompanyService;
import com.jxxp.service.MobileService;
import com.jxxp.service.ReporterService;

/*
 * @author cj
 */
@Controller("caseController")
@RequestMapping("/case")
public class CaseController {
	private static final Logger log = LoggerFactory.getLogger(CaseController.class);
	
	@Resource
	private CaseService caseService;
	@Resource
	private ReporterService reporterService;
	@Resource
	private CaseAttachService caseAttachService;
	@Resource
	private MobileService mobileService;
	@Resource
	private CaseCommentService caseCommentService;
	@Resource
	private CaseChangeLogService caseChangeLogService;
	@Resource
	private CompanyService comapnyService;
	
	/*** 
     * 根据所输入的手机号，获取验证码 
     * @author cj
     * @param 
     * @return 
     */ 
	@RequestMapping("/getVerifyCode.do")
	public String getVerifyCode(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap ) {
		String mobile = request.getParameter("mobile");
		String verifyCode = mobileService.sendVerifySMS(mobile);
		request.getSession().setAttribute("verifyCode", verifyCode);	
		request.getSession().setAttribute("mobile", mobile);	
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(verifyCode != null) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}
	
	/*** 
     * 验证所输手机验证码，如果验证成功，则根据手机号码判断该实名用户是否存在，存在则输出该对象的JSON字符串 ,不存在则返回success,如若所填验证码不符则返回error
     * @author cj
     * @param 
     * @return 
     */  
	@RequestMapping("/checkVerifyCode.do")
	public String checkVerifyCode(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap ) {
		String mobile = request.getParameter("mobile");
		String verifyCode = request.getParameter("verifyCode");
		String trueMobile = (String) request.getSession().getAttribute("mobile"); 
		String trueCode = (String) request.getSession().getAttribute("verifyCode");
		if(verifyCode.trim() != null) {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				if(trueCode.equals(verifyCode) && trueMobile.equals(mobile)) {
					Reporter reporter = reporterService.getByMobile(mobile);
					if(reporter != null) {
						String reporterJson = JSON.toJSONString(reporter);
						log.debug("验证成功！并有该实名对象！");
						out.print(reporterJson);
					} else {
						log.debug("验证成功！");
						out.print("success");
					}
				} else {
					log.debug("验证失败！");
					out.print("error");
				}
			} catch (IOException e) {
				log.error("流获取失败！",e);
			}
		}
		return null;
	}
	
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
    	String answersJson = request.getParameter("answers");
    	String isAnonymous = request.getParameter("isAnonymous");
    	String verifyCode = request.getParameter("verifyCode");
    	String trueMobile = (String) request.getSession().getAttribute("mobile"); 
		String trueCode = (String) request.getSession().getAttribute("verifyCode");

    	log.debug("trackingNo:" + trackingNo + "\t" + "accessCode:" + accessCode + "\t" + "rtList:" + rtList);
    	log.debug("reporter:" + reporterJson);
    	log.debug("answers:" + answersJson);
    	log.debug("isAnonymous:" + isAnonymous);
    	log.debug("verifyCode:" + verifyCode);
    	
    	boolean flag = true;
    	
    	Reporter reporter = null;
    	if("false".equalsIgnoreCase(isAnonymous)) {
    		reporter = JSON.parseObject(reporterJson, Reporter.class);
    		flag = trueMobile.equals(reporter.getMobile()) && trueCode.equals(verifyCode);
    	}
    	
    	
		//获取session中保存的CompanyBranch对象
		CompanyBranch companyBranch = (CompanyBranch) request.getSession().getAttribute("companyBranch");
		
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
    	
    	//保存问题回答列表
    	List<ReportAnswer> answerList = JSON.parseArray(answersJson, ReportAnswer.class);
    	
		//获取附件列表
		List<CaseAttach> attachList = caseAttachService.getCaseAttachByTrackingNo(trackingNo);
		if(attachList.size() > 0) {
			String tempPath = request.getSession().getServletContext().getRealPath("/") + "fileupload/temp/" + trackingNo;
			String filePath = request.getSession().getServletContext().getRealPath("/") + "fileupload/file/" + trackingNo;
			
			tempToFile(tempPath, filePath);
		}
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(flag) {
				if(caseService.saveCase(reporter,reportCase,answerList)){
					log.debug(reportCase.getTrackingNo() + "案件提交成功！");
					out.print(reportCase.getTrackingNo());
				} else {
					out.print("saveError");
				}
			} else {
				out.print("checkError");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
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
    public String fileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {  
        response.setCharacterEncoding("UTF-8");
		if (!file.isEmpty()) {  
			String rootPath = request.getSession().getServletContext().getRealPath("/") + "fileupload/temp/";
			String webPath = request.getSession().getServletContext().getContextPath() + "/fileupload/temp/";
	        String trackingNo = request.getParameter("trackingNo");
	        String desc = request.getParameter("desc");
	        //保存文件到服务器的临时文件夹
	        try {
				saveTempFile(rootPath,file,trackingNo);
			} catch (Exception e) {
				log.error("文件保存失败！",e);
			} 
	        
	        //保存附件信息到数据库
	        String fileName = file.getOriginalFilename();
	        CaseAttach caseAttach = new CaseAttach();
	        caseAttach.setAttachFileName(fileName);
	        caseAttach.setAttachExt(fileName.substring(fileName.lastIndexOf('.') + 1));
	        caseAttach.setAttachName(fileName.substring(0,fileName.lastIndexOf('.')));
	        caseAttach.setAttachPath(webPath + trackingNo + "/");
	        caseAttach.setAttachUrl(webPath + trackingNo + "/" + fileName);
	        caseAttach.setState(0);
	        caseAttach.setTrackingNo(trackingNo);
	        caseAttach.setAttachSize(file.getSize());
	        caseAttach.setDescription(desc);
	        if(caseAttachService.addCaseAttach(caseAttach)) {
	        	log.debug("附件保存成功！");
	        }
	        
	        List<CaseAttach> fileList = caseAttachService.getCaseAttachByTrackingNo(trackingNo);
	        if(fileList.size() > 0) {
				modelMap.put("fileList", fileList);
			}
	    }
        return "/jsp/pages/upLoadFile";
    }  
    
	/*** 
     * 显示已上传文件列表 
     * @author cj
     * @param  
     * @return 
     */  
    @RequestMapping("/showFileList.do")  
    public String showFileList(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {  
		String trackingNo = request.getParameter("trackingNo");
        
		List<CaseAttach> fileList = caseAttachService.getCaseAttachByTrackingNo(trackingNo);
		if(fileList.size() > 0) {
			modelMap.put("fileList", fileList);
		}
    	return "/jsp/pages/upLoadFile";
    }

	/*** 
     * 根据所输入的手机号，获取临时密码 
     * @author cj
     * @param 
     * @return 
     */ 
	@RequestMapping("/getTempPwd.do")
	public String getTempPwd(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap ) {
		String mobile = request.getParameter("mobile");
		String tempPwd = mobileService.sendTempPwd(mobile);
		request.getSession().setAttribute("mobile", mobile);
		request.getSession().setAttribute("tempPwd", tempPwd);	
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(tempPwd != null) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}
    
	/*** 
     * 根据举报人显示以往举报列表 
     * @author cj
     * @param  
     * @return 
     */  
    @RequestMapping(value="/showCaseList.do",method=RequestMethod.POST)  
    public String showCaseList(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {  
		String reqMobile = request.getParameter("mobile");
		String reqPwd = request.getParameter("tempPwd");
		//还需要加手机验证码判断
		String mobile = (String) request.getSession().getAttribute("mobile");
		String tempPwd = (String) request.getSession().getAttribute("tempPwd");
		
		if(reqPwd.equals(tempPwd) && reqMobile.equals(mobile)) {
			Reporter reporter = reporterService.getByMobile(mobile);
			if(reporter != null) {
				List<ReportCase> caseList = caseService.getCaseList(reporter);
				modelMap.put("caseList", caseList);
		    	return "/jsp/pages/report_list";
			} else {
				modelMap.put("errorMsg", "未找到匹配用户数据！");
				return "/jsp/pages/error";
			}
		} 
		modelMap.put("errorMsg", "手机号或者临时密码有误，请重新输入！");
		return "/jsp/pages/error";
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
		
		modelMap.put("questionAnswerList", getQuestionAnswerList(reportCase));
		modelMap.put("reportCase", reportCase);
    	return "/jsp/pages/report_info";
    }
    
    
    /*** 
     * 根据跟踪号以及密码显示举报列表 
     * @author cj
     * @param  
     * @return 
     */  
    @RequestMapping("/showCaseByTrankingNo.do")  
    public String showCaseByTrackingNo(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {  
		String trackingNo = request.getParameter("trankingNo");
		String accessCode = request.getParameter("accecCode");
		log.debug("trackingNo:" + trackingNo);
		log.debug("accessCode:" + accessCode);
		ReportCase reportCase = caseService.getReportCase(trackingNo, accessCode);
		if(reportCase == null) {
			log.debug("reportCase获取失败！");
			modelMap.put("errorMsg", "未找到匹配数据！");
			return "/jsp/pages/error";
		}
		
		modelMap.put("reportCase", reportCase);
		modelMap.put("questionAnswerList", getQuestionAnswerList(reportCase));
    	return "/jsp/pages/report_info";
    }
    

    
    /*** 
     * 添加案件追加信息 
     * @author cj
     * @param  
     * @return 
     */  
    @RequestMapping("/addCaseComment.do")  
    public String addCaseComment(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {  
		String content = request.getParameter("content");
		String strId = request.getParameter("rcId");
		User user = (User) request.getSession().getAttribute("user");
		
		long rcId = Long.parseLong(strId);
		
		ReportCase reportCase = caseService.getReportCaseById(rcId);
		
		CaseComment caseComment = new CaseComment();
		caseComment.setContent(content);
		caseComment.setPostTime(new Date());
		
		if(user == null) {
			caseComment.setIsReporter(1);
		} else {
			caseComment.setIsReporter(0);
			caseComment.setOwner(user);
			caseComment.setOwnerCompany(user.getUserCompany());
		}
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(caseCommentService.addCaseComment(caseComment, rcId)){
				log.debug("案件追加信息添加成功！");
				//如果不是举报人追加信息，需要记录变更信息
				if(caseComment.getIsReporter() == 0) {
					CaseChangeLog caseChangeLog = new CaseChangeLog();
					caseChangeLog.setChangeTime(new Date());
					caseChangeLog.setOperator(user);
					caseChangeLog.setStateAfter(reportCase.getCaseState());
					caseChangeLog.setStateBefore(reportCase.getCaseState());
					caseChangeLog.setHandlerAfter(user.getUserCompany());
					caseChangeLog.setHandlerBefore(user.getUserCompany());
					
					if(caseChangeLogService.addCaseChangeLog(caseChangeLog, rcId)) {
						log.debug("日志信息添加成功！");
					} else {
						log.debug("日志信息添加失败！");
					}
				}
				out.print("success");
			} else {
				log.debug("案件追加信息添加失败！");
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		
    	return null;
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
    	Company currentHandler = comapnyService.getCompanyById(afterCompanyId);
    	
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
    
    
    //获取问题及答案的集合，方便前台展示
    public static List<Map<String,String>> getQuestionAnswerList(ReportCase reportCase) {
    	List<Map<String,String>> questAnswerList = new ArrayList<Map<String,String>>();
		List<ReportAnswer> answerList = reportCase.getAnswers();
		for (int i = 0; i < answerList.size(); i++) {
			ReportAnswer answer = answerList.get(i);
			List<QuestionInfo> questList = reportCase.getCompany().getQuestList();
			for (int j = 0; j < questList.size(); j++) {
				QuestionInfo question = questList.get(j);
				if(answer.getQuestKey().equals(question.getQuestKey())) {
					Map<String,String> map = new HashMap<String, String>();
					map.put("question", question.getQuest());
					map.put("questKey", answer.getQuestKey());
					map.put("questValue", answer.getQuestValue());
					questAnswerList.add(map);
				}
			}
		}
		log.debug("questAnswerList:" + questAnswerList);
		return questAnswerList;
    }
    
    //保存临时文件
    public void saveTempFile(String rootPath,MultipartFile file,String trackingNo) throws IllegalStateException, IOException {
    	String fileDir = rootPath +  trackingNo;
    	File dir = new File(fileDir);
    	log.debug("路径：" + System.getProperty("user.dir"));
    	if(!dir.exists()) {
    		if(dir.mkdirs()) {
    			log.debug("文件夹创建成功！");
    		} else {
    			log.debug("文件夹创建失败！");
    		}
    	}
    	String fileName = fileDir + "/" + file.getOriginalFilename();
    	log.debug("FileName:" + fileName);
        // 转存文件   
        file.transferTo(new File(fileName));
        log.debug("文件保存成功！");
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
    
    //将临时文件保存为永久文件
    public void tempToFile(String tempPath, String filePath) {
    	//复制文件
		File tempRoot = new File(tempPath);
		File newRoot = null;
		if(tempRoot.exists() && tempRoot.isDirectory()) {
			File[] tempFiles = tempRoot.listFiles();
			if(tempFiles.length == 0) {
				log.debug("文件丢失");
			}
			newRoot = new File(filePath);
			if(!newRoot.exists()) {
				newRoot.mkdirs();
			}
			for (int i = 0; i < tempFiles.length; i++) {
				try {
					FileInputStream fis = new FileInputStream(tempFiles[i]);
					FileOutputStream fos = new FileOutputStream(filePath + "/" + tempFiles[i].getName());
					byte[] b = new byte[1024];
					while(fis.read(b) != -1) {
						fos.write(b);
					}
					fos.flush();
					fos.close();
					fis.close();
				} catch (FileNotFoundException e) {
					log.error("文件未找到！",e);
				} catch (IOException e) {
					log.error("IO异常！",e);
				}
			}
		}
    }

    
    
    public static void main(String[] args) {
		
	}
}
