package com.jxxp.controller.back;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jxxp.comms.web.Page;
import com.jxxp.pojo.ClientCompany;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.CompanyOther;
import com.jxxp.pojo.CompanyQuestion;
import com.jxxp.pojo.CompanyWholeInfo;
import com.jxxp.pojo.ReportType;
import com.jxxp.pojo.User;
import com.jxxp.service.ClientCompanyService;
import com.jxxp.service.CompanyBranchService;
import com.jxxp.service.CompanyService;
import com.jxxp.service.QuestionService;
import com.jxxp.service.ReportTypeService;

@Controller("companyBackController")
@RequestMapping("/admin/companyBack")
public class CompanyBackController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(CompanyBackController.class);

	@Resource
	private CompanyService companyService;
	@Resource
	private ReportTypeService reportTypeService;
	@Resource
	private QuestionService questionService;
	@Resource
	private CompanyBranchService companyBranchService;
	@Resource
	private ClientCompanyService clientCompanyService;

	/**
	 * @author gcx 获取问题模版
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getQuestTemlate.do")
	public String getQuestTemlate(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		List<Map<String, String>> questList = questionService.getMarkQuestions(company);
		log.debug("questList:" + questList);
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
			ModelMap modelMap) throws Exception {

		// 获取前台复选框CompanyQuestion集合
		String comQuestJson = request.getParameter("comQuestList");
		List<CompanyQuestion> comQuestList = JSON.parseArray(comQuestJson, CompanyQuestion.class);
		// 获取该客户所属的公司
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		boolean flag = companyService.saveCompanyQuestions(company, comQuestList);
		try {
			PrintWriter out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}

		} catch (IOException e) {
			log.error("添加问题失败！", e);
		}
		return null;

	}

	/**
	 * @author gcx
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAllReportTypes.do")
	public String getAllReportTypes(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
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
	 * 添加公司所选择的问题类型
	 * 
	 * @author gcx
	 * @param company
	 * @param reportType
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/addQuestionTypes.do")
	public String addQuestionTypes(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		String typeJson = request.getParameter("reportType");
		List<ReportType> rtList = JSON.parseArray(typeJson, ReportType.class);
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

	/**
	 * 更新公司信息包括基本信息和其他信息
	 * 
	 * @author gcx
	 * @param wholeCompany
	 *            包含Company和CompanyOther
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/updateCompanyWholeInfo.do")
	public String updateCompanyWholeInfo(CompanyWholeInfo wholeCompany, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		// 获取文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("logo");
		// 设置公司其他信息
		setCompanyOtherInfo(file, wholeCompany, request);
		// 调用service,存储公司所有信息
		boolean flag = companyService.updateCompanyWholeInfo(wholeCompany);
		PrintWriter out;
		try {
			out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.debug("更新公司信息异常");
		}
		return null;

	}

	/**
	 * 添加分支机构
	 * 
	 * @author gcx
	 * @param branch
	 *            分支机构
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addCompanyBranches.do", method = RequestMethod.POST)
	public String addCompanyBranches(CompanyBranch branch, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		Company owner = user.getUserCompany();
		branch.setOwner(owner);
		boolean flag = companyBranchService.addCompanyBranch(branch);
		PrintWriter out;
		try {
			out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.debug("添加分支机构异常");
		}
		return null;
	}

	private void saveLogo(MultipartFile file, String dirPath) throws Exception {
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
		File saveFile = new File(filePath);
		file.transferTo(saveFile);

	}

	/**
	 * 获取公司分支机构
	 * 
	 * @author gcx
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getCompanyBranches.do")
	public String getCompanyBranches(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		List<CompanyBranch> branchList = companyBranchService.getCompanyBranches(company
				.getCompanyId());
		model.put("branchList", branchList);
		return "/jsp/admin/pages/branchAdmin";
	}

	/**
	 * 更新公司分支机构
	 * 
	 * @author gcx
	 * @param branch
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateCompanyBranch.do")
	public String updateCompanyBranch(CompanyBranch branch, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		Company owner = user.getUserCompany();
		branch.setOwner(owner);
		boolean flag = companyBranchService.updateBranch(branch);
		PrintWriter out;
		try {
			out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");

			}
		} catch (IOException e) {
			log.debug("更新分支机构异常");
		}
		return null;
	}

	/**
	 * 删除分支机构
	 * 
	 * @param branchId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteCompanyBranch.do")
	public String deleteCompanyBranch(long branchId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		boolean flag = companyBranchService.deleteBranch(branchId);
		PrintWriter out;
		try {
			out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");

			}
		} catch (IOException e) {
			log.debug("删除分支机构异常");
		}
		return null;
	}

	/**
	 * 获取公司信息（基本信息和其他信息）
	 * 
	 * @author gcx
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getOwnerCompanyInfo.do")
	public String getOwnerCompanyInfo(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		Company company = user.getUserCompany();
		company = companyService.getCompanyById(company.getCompanyId());
		model.put("company", company);
		return "/jsp/admin/pages/enterEditor";
	}

	/**
	 * 添加公司基本信息(Company)
	 * 
	 * @author gcx
	 * @param company
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addWholeCompany.do")
	public String addWholeCompany(CompanyWholeInfo wholeCompany, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request
				.getSession().getServletContext());
		// 设置编码
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		if (commonsMultipartResolver.isMultipart(request)) {
			// 获取文件
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("logo");
			// 设置公司其他信息
			setCompanyOtherInfo(file, wholeCompany, request);
		}
		// 调用service,存储公司所有信息
		boolean flag = companyService.saveWholeCompany(wholeCompany);
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.debug("添加公司信息异常");
		}

		return null;

	}

	private void setCompanyOtherInfo(MultipartFile file, CompanyWholeInfo wholeCompany,
			HttpServletRequest request) throws Exception {
		if (file != null && !file.isEmpty()) {
			Company company = wholeCompany.getCompany();
			String dirPath = request.getSession().getServletContext().getRealPath("/")
					+ "fileupload/logo/" + company.getCompanyId();
			// 保存文件
			saveLogo(file, dirPath);
			// 完善公司其他信息 CompanyOther
			CompanyOther other = wholeCompany.getCompanyOther();
			if (other != null) {
				// 获取文件的高度和宽度
				String webPath = request.getSession().getServletContext().getContextPath();
				String accessPath = webPath + "/fileupload/logo/" + company.getCompanyId();
				FileInputStream fis = new FileInputStream(new File(dirPath + "/"
						+ file.getOriginalFilename()));
				BufferedImage image = ImageIO.read(fis);
				int width = image.getWidth();
				int height = image.getHeight();
				other.setLogoUrl(accessPath + "/" + file.getOriginalFilename());
				other.setLogoPath(accessPath);
				other.setLogoWidth(width);
				other.setLogoHeight(height);

			}
		}

	}

	/**
	 * 删除单个企业
	 * 
	 * @author gcx
	 * @param companyIds
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/delCompanyById.do")
	public String delCompanyById(Long companyId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Long companyIds[] = { companyId };
		boolean flag = companyService.delCompanyByIds(companyIds);
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 批量删除企业
	 * 
	 * @param companyIds
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/delCompanyByIds.do")
	public String delCompanyByIds(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		String companyIds = request.getParameter("companyIds");
		log.debug("companyIds====" + companyIds);
		String[] strIds = companyIds.split(",");
		Long ids[] = new Long[strIds.length];
		for (int i = 0; i < strIds.length; i++) {
			ids[i] = Long.parseLong(strIds[i]);
		}
		boolean flag = companyService.delCompanyByIds(ids);
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据公司用户名模糊查询匹配的公司，如果用户名为空，则是所有公司
	 * 
	 * @author gcx
	 * @param companyName
	 *            公司名字
	 * @return 公司
	 */

	@RequestMapping("/getCompanyPage.do")
	public String getCompanyPage(String companyName, Integer pageNow, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		Page page = null;
		List<Company> companyList = new ArrayList<Company>();
		int totalCount = companyService.getCompanyByName(companyName).size();
		if (pageNow != null) {
			page = new Page(totalCount, pageNow);
			companyList = companyService.getCompanyPaging(page, companyName);
		} else {
			page = new Page(totalCount, 1);
			companyList = companyService.getCompanyPaging(page, companyName);
		}
		Map<String, Object> companyResultMap = new HashMap<String, Object>();
		companyResultMap.put("page", page);
		companyResultMap.put("companyList", companyList);
		// 将公司信息集合和page组装成map，返回给前台
		String companyMapJson = JSON.toJSONString(companyResultMap,
				SerializerFeature.WriteMapNullValue);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(companyMapJson);
		} catch (IOException e) {
			log.error("add company failed", e);
		}
		return null;

	}

	@RequestMapping("/addClientCompany.do")
	public String addClientCompany(ClientCompany clientCompany, Integer pageNow,
			HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		// 调用service,存储客户公司公司所有信息
		boolean flag = clientCompanyService.addClientCompany(clientCompany);
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.debug("添加客户公司信息异常");
		}
		return null;
	}

	@RequestMapping("/updateClientCompany.do")
	public String updateClientCompany(ClientCompany clientCompany, Integer pageNow,
			HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		// 调用service,存储客户公司公司所有信息
		boolean flag = clientCompanyService.updateClientCompany(clientCompany);
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			if (flag) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.debug("修改客户公司信息异常");
		}
		return null;
	}
}
