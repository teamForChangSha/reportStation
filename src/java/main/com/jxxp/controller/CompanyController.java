package com.jxxp.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyOther;
import com.jxxp.pojo.CompanyWholeInfo;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportType;
import com.jxxp.pojo.User;
import com.jxxp.service.CompanyService;
import com.jxxp.service.QuestionService;
import com.jxxp.service.ReportTypeService;

/**
 * @author gcx 公司信息管理，客户的增删该查
 * 
 */
@Controller("companyController")
@RequestMapping("/company")
public class CompanyController {
	private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

	@Resource
	private CompanyService companyService;
	@Resource
	private ReportTypeService reportTypeService;
	@Resource
	private QuestionService questionService;

	/**
	 * @author gcx
	 * @param companyName
	 *            公司名字
	 * @return 公司
	 */

	@RequestMapping("/getAllByName.do")
	public String getAllByName(String companyName, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		if (companyName != null) {
			try {
				companyName = new String(companyName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		log.debug("get companyName====" + companyName);
		List<Company> companyList = new ArrayList<Company>();
		companyList = companyService.getCompanyByName(companyName);
		String companyJson = JSON.toJSONString(companyList);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(companyJson);
		} catch (IOException e) {
			log.error("add company failed", e);
		}
		log.debug("获取公司集合成功size=" + companyList.size());
		return null;

	}

	/**
	 * 获取问题模版
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getQuestTemlate.do")
	public String getDefQuestTemlate(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		List<Map<String, String>> questList = questionService.getMarkQuestions(company);
		model.put("questList", questList);
		return "/jsp/admin/pages/settingQuest";
	}

	/**
	 * @author gcx 保存公司信息,其中包括公司的基本信息和公司所选的问题
	 * @param company
	 * @param questIds
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/addCompanyQuestions.do")
	public String addCompanyQuestions(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {

		List<QuestionInfo> questionList = new ArrayList<QuestionInfo>();
		// 获取前台复选框question的ids
		String[] questIdStr = request.getParameterValues("questId");
		for (int i = 0; i < questIdStr.length; i++) {
			QuestionInfo question = new QuestionInfo();
			question.setQuestId(Long.parseLong(questIdStr[i]));
		}
		// 获取该客户所属的公司
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		boolean flag = companyService.saveCompanyQuestions(company, questionList);
		PrintWriter out;
		try {
			out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}

		} catch (IOException e) {
			log.error("添加问题列表失败！", e);
		}

		return null;
	}

	@RequestMapping("/getAllReportTypes.do")
	public String getAllReportTypes(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		List<ReportType> delfRtList = reportTypeService.getDefaultList();
		List<ReportType> rtList = new ArrayList<ReportType>();
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			Company company = user.getUserCompany();
			rtList = companyService.getCompanyReportType(company);
			log.debug("user id=" + user.getUserCompany().getCompanyId());
		}
		model.put("rtList", rtList);
		model.put("delfRtList", delfRtList);
		return "/jsp/admin/pages/settingType";
	}

	/**
	 * @author gcx 添加公司所选择的问题类型
	 * 
	 * @param company
	 * @param reportType
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/addQuestionTypes.do")
	public String addQuestionTypes(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		String typeJson = request.getParameter("reportType");
		log.debug("typeJson====" + typeJson);
		List<ReportType> rtList = JSON.parseArray(typeJson, ReportType.class);
		log.debug("rtList title====" + rtList.get(0).getRtTitle());
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		boolean flag = companyService.saveCompanyReportType(company, rtList);
		try {
			PrintWriter out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}

		} catch (IOException e) {
			log.error("添加问题类型失败！", e);
		}
		return null;

	}

	@RequestMapping("/updateCompanyWholeInfo.do")
	public String uploadLogo(CompanyWholeInfo wholeCompany, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IllegalStateException, IOException {

		// 获取文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("testFile");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String createPath = "/fileupload/companylogo";
		String dirPath = rootPath + createPath;
		if (file != null) {
			// 保存文件
			saveLogo(file, dirPath);
			// 完善公司其他信息 CompanyOther
			String webPath = request.getSession().getServletContext().getContextPath();
			String accessPath = webPath + createPath;
			CompanyOther other = wholeCompany.getCompanyOther();
			other.setLogoUrl(accessPath);
			other.setLogoPath(accessPath);
			other.setServiceProtocol("8080");
		}
		// 调用service,存储公司所有信息
		boolean flag = companyService.updateCompanyWholeInfo(wholeCompany);
		PrintWriter out = response.getWriter();
		if (flag) {
			out.print("success");
		} else {
			out.print("fail");
		}
		return null;
	}

	private void saveLogo(MultipartFile file, String dirPath) throws IllegalStateException,
			IOException {
		// 建立存放文件的文件夹
		File fileDir = new File(dirPath);
		if (!fileDir.exists()) {
			if (fileDir.mkdirs()) {
				log.debug("文件夹创建成功！");
			} else {
				log.debug("文件夹创建失败！");
			}
		}
		String filePath = dirPath + "/" + file.getOriginalFilename();
		// 存储文件
		file.transferTo(new File(filePath));

	}
}
