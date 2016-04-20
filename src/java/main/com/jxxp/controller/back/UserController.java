package com.jxxp.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.OprationLog;
import com.jxxp.pojo.User;
import com.jxxp.service.CompanyService;
import com.jxxp.service.OprationLogService;
import com.jxxp.service.UserService;

/*
 * 用户Controller
 * @author cj
 */
@Controller("userController")
@RequestMapping("/admin/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;
	@Resource
	private OprationLogService oprationLogService;
	@Resource
	private CompanyService companyService;

	/*
	 * 用户登录
	 * 
	 * @author cj
	 */
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		String loginName = request.getParameter("loginName");
		String userPwd = request.getParameter("userPwd");
		User user = new User();
		user.setLoginName(loginName);
		user.setUserPwd(userPwd);
		user = userService.longin(user);
		log.debug("User:" + user);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		OprationLog lastLogin = getUserLastLoginTime(user);
		request.getSession().setAttribute("lastLogin", lastLogin);
		try {
			out = response.getWriter();
			if (user != null) {
				if (user.getUserState() == User.USER_STATE_NEW) {
					out.print("新建账号无法使用！请联系管理员");
				} else if (user.getUserState() == User.USER_STATE_OFF) {
					out.print("该账号已注销！请联系管理员");
				} else if (user.getUserState() == User.USER_STATE_STOP) {
					out.print("该账号已停用！请联系管理员");
				} else {
					ServletContext application = request.getSession().getServletContext();
					@SuppressWarnings("unchecked")
					Map<String, String> loginUsers = (Map<String, String>) application
							.getAttribute("loginUsers");

					String sessionId = request.getSession(false).getId();
					log.debug("loginUsers:" + loginUsers);
					if (loginUsers == null || loginUsers.size() == 0) {
						loginUsers = new HashMap<String, String>();
						loginUsers.put(user.getLoginName(), sessionId);
						application.setAttribute("loginUsers", loginUsers);

						request.getSession().setAttribute("user", user);
						if (saveOprationLog("登录", user)) {
							log.debug("用户操作日志记录成功！");
						}
						out.print("success");
					} else {
						String userSessionId = loginUsers.get(user.getLoginName());
						log.debug("userSessionId:" + userSessionId);
						log.debug("sessionId:" + sessionId);
						if (userSessionId != null && !sessionId.equals(userSessionId)) {
							out.print("登录失败，该用户已登录！");
						} else {
							loginUsers.put(user.getLoginName(), sessionId);
							request.getSession().setAttribute("user", user);
							if (saveOprationLog("登录", user)) {
								log.debug("用户操作日志记录成功！");
							}
							out.print("success");
						}
					}
				}
			} else {
				out.print("登录失败，用户名或者密码错误！");
			}
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	/*
	 * 用户注销
	 * 
	 * @author cj
	 */
	@RequestMapping("/loginOut.do")
	public String loginOut(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		HttpSession session = request.getSession();

		ServletContext application = request.getSession().getServletContext();
		@SuppressWarnings("unchecked")
		Map<String, String> loginUsers = (Map<String, String>) application
				.getAttribute("loginUsers");
		User user = (User) session.getAttribute("user");
		log.debug("loginUsers:" + loginUsers);
		if (user != null) {
			loginUsers.remove(user.getLoginName());
			application.removeAttribute("loginUsers");
			application.setAttribute("loginUsers", loginUsers);
		}
		@SuppressWarnings("unchecked")
		Enumeration<String> attributeNames = session.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			session.removeAttribute(attributeName);
		}

		if (saveOprationLog("注销", user)) {
			log.debug("用户操作日志记录成功！");
		}
		return "redirect:/";
	}

	/*
	 * 用户修改自己密码
	 * 
	 * @author cj
	 */
	@RequestMapping("/updatePwd.do")
	public String updatePwd(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String userPwd = request.getParameter("userPwd");
		User user = (User) request.getSession().getAttribute("user");
		user.setUserPwd(userPwd);

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (userService.update(user)) {
				if (saveOprationLog("修改密码", user)) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	/*
	 * 重置密码
	 * 
	 * @author cj
	 */
	@RequestMapping("/resetUserPwd.do")
	public String resetUserPwd(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String userPwd = request.getParameter("userPwd");
		String strId = request.getParameter("userId");
		long userId = Long.parseLong(strId);
		User user = (User) userService.getUserById(userId);
		user.setUserPwd(userPwd);

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (userService.update(user)) {
				User oprator = (User) request.getSession().getAttribute("user");
				if (saveOprationLog("重置了登录名为" + user.getLoginName() + "的用户密码", oprator)) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	/*
	 * 修改用户状态
	 * 
	 * @author cj
	 */
	@RequestMapping("/changeUserState.do")
	public String changeUserState(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String strUserState = request.getParameter("userState");
		String strId = request.getParameter("userId");
		long userId = Long.parseLong(strId);
		int userState = Integer.parseInt(strUserState);
		User user = (User) userService.getUserById(userId);
		user.setUserState(userState);

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (userService.update(user)) {
				User oprator = (User) request.getSession().getAttribute("user");
				if (saveOprationLog("变更了" + user.getLoginName() + "的状态", oprator)) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	/*
	 * 添加用户
	 * 
	 * @author cj
	 */
	@RequestMapping("/addUser.do")
	public String addUser(User user, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (userService.addUser(user)) {
				User oprator = (User) request.getSession().getAttribute("user");
				if (saveOprationLog("增加了新用户：" + user.getLoginName(), oprator)) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	/*
	 * 添加用户
	 * 
	 * @author cj
	 */
	@RequestMapping("/updateUser.do")
	public String updateUser(User user, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (userService.update(user)) {
				User oprator = (User) request.getSession().getAttribute("user");
				if (saveOprationLog("修改了用户：" + user.getLoginName() + "的信息", oprator)) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！", e);
		}
		return null;
	}

	/*
	 * 根据参数列表获取用户信息，由于存在有用户信息但是没有登入（没有日志）返回所有用户最后登入的日志集合不合适，因此把集合的对象改为List<Map<
	 * String, Object>>,key= user表用户，key=lastLoginLog表示最近登录日志，为空则是没有登入；
	 * 
	 * @author gcx
	 */
	@RequestMapping("/getUsersByParams.do")
	public String getUsersByParams(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String keyWord = request.getParameter("keyWord");
		String strCompanyId = request.getParameter("companyId");
		String strUserType = request.getParameter("userType");
		String strUserState = request.getParameter("userState");

		Map<String, Object> params = new HashMap<String, Object>();
		// 用户级别判断
		User user = (User) request.getSession().getAttribute("user");
		params.put("user", user);
		params.put("keyWord", keyWord);
		if (strCompanyId != null && strCompanyId.length() > 0 && strCompanyId.matches("^[0-9]*$")) {
			params.put("companyId", new Long(strCompanyId));
		}
		if (strUserType != null && strUserType.length() > 0 && strUserType.matches("^[0-9]*$")) {
			params.put("userType", new Integer(strUserType));
		}
		if (strUserState != null && !strUserState.equals("") && strUserState.length() > 0
				&& strUserState.matches("^[0-9]*$")) {
			params.put("userState", new Integer(strUserState));
		}
		List<User> userList = userService.getUsersByParams(params);
		List<Map<String, Object>> userAndLogList = new ArrayList<Map<String, Object>>();
		// 获取用户的最后登入日志
		for (int i = 0; i < userList.size(); i++) {
			Map<String, Object> userAndLogMap = new HashMap<String, Object>();
			OprationLog lastLoginLog = getUserLastLoginTime(userList.get(i));
			userAndLogMap.put("user", userList.get(i));
			userAndLogMap.put("lastLoginLog", lastLoginLog);
			userAndLogList.add(userAndLogMap);
		}
		log.debug("userType=============" + strUserType + "----keyWord==" + keyWord
				+ "---userState=" + strUserState);
		modelMap.put("userAndLogList", userAndLogList);
		return "/jsp/admin/pages/usersAdmin";
	}

	/*
	 * 根据参数列表获取日志信息,参数为日期和用户id,为空则查询所有
	 * 
	 * @author gcx
	 */
	@RequestMapping("/getLogByParams.do")
	public String getLogByParams(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String oprator = request.getParameter("oprator");
		Map<String, Object> params = new HashMap<String, Object>();

		// 用户级别判断,客户公司只能查询本公司用户的日志,客户公司类型值为2
		if (user.getUserType() <= 2) {
			params.put("companyId", user.getUserCompany().getCompanyId());
		}
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		params.put("oprator", oprator);
		List<OprationLog> logList = oprationLogService.getLogByParams(params);
		modelMap.put("logList", logList);
		System.out.println("end time------------" + endTime);
		return "/jsp/admin/pages/showLog";
	}

	/**
	 * 停用某个公司的所有用户
	 * 
	 * @author gcx
	 * @param companyId
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/stopCompanyAllUsers.do")
	public String stopCompanyAllUsers(Long companyId, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		boolean falg = userService.stopAllUsersByCompanyId(companyId);
		Company stopedCompany = companyService.getCompanyById(companyId);
		try {
			PrintWriter out = response.getWriter();
			if (falg) {
				User oprator = (User) request.getSession().getAttribute("user");
				if (saveOprationLog("停用了" + stopedCompany.getCompanyName() + "公司的所有用户", oprator)) {
					log.debug("停用公司所有用户成功");

				}
				out.print("success");

			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.debug("停用公司所有用户异常");
		}
		return null;
	}

	private boolean saveOprationLog(String msg, User oprator) throws Exception {
		OprationLog log = new OprationLog();
		log.setLogDate(new Date());
		log.setOpration(msg);
		log.setOprator(oprator);

		return oprationLogService.addLog(log);
	}

	/**
	 * 获取关于用户最后登录的日志
	 * 
	 * @author gcx
	 * @param user
	 * @return
	 */
	public OprationLog getUserLastLoginTime(User user) {
		// 登入前获取用户上一次（最新一次）登入时间
		if (user != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("oprator", user.getUserName());
			params.put("oprationKey", "登录");
			List<OprationLog> operatList = oprationLogService.getLogByParams(params);
			// 由于在数据库中取出来的集合已经是根据时间按降序排序了，因为排在最前面的是最后登入的时间
			if (operatList.size() > 0) {
				return operatList.get(0);
			}

		}
		return null;
	}

	/*
	 * 获取单个用户所有日志信息,如果oprator=null则是查询所有
	 * 
	 * @author gcx
	 */
	@RequestMapping("/getUserLog.do")
	public String getLogByUser(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String strUserId = request.getParameter("oprator");
		Long userId = null;
		if (strUserId != null) {
			userId = Long.valueOf(strUserId);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oprator", userId);
		List<OprationLog> logList = oprationLogService.getLogByParams(params);
		PrintWriter out = response.getWriter();
		String jsonLogList = JSON.toJSONString(logList);
		out.print(jsonLogList);
		// modelMap.put("logList", logList);
		return null;
	}

	@InitBinder
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(format, true);
		bin.registerCustomEditor(Date.class, dateEditor);
	}

}
