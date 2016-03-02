package com.jxxp.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jxxp.pojo.OprationLog;
import com.jxxp.pojo.User;
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
	
	
	/*
	 * 用户登录
	 * @author cj
	 */
	@RequestMapping("/login.do")
	public String login( HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		String loginName = request.getParameter("loginName");
		String userPwd = request.getParameter("userPwd");
		User user = new User();
		user.setLoginName(loginName);
		user.setUserPwd(userPwd);
		user = userService.longin(user);
		log.debug("User:" + user);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(user != null) {
				if(user.getUserState() != 1) {
					out.print("该账号无法使用！");
				} else {
					ServletContext application = request.getSession().getServletContext();
					@SuppressWarnings("unchecked")
					Map<String, String> loginUsers = (Map<String, String>) application.getAttribute("loginUsers");
					
					String sessionId = request.getSession(false).getId();
					log.debug("loginUsers:" + loginUsers);
					if(loginUsers == null || loginUsers.size() == 0) {
						loginUsers = new HashMap<String, String>();
						loginUsers.put(user.getLoginName(), sessionId);
						application.setAttribute("loginUsers", loginUsers);
						
						request.getSession().setAttribute("user", user);
						if(saveOprationLog("登录", user.getUserId())) {
							log.debug("用户操作日志记录成功！");
						}
						out.print("success");
					} else {
						String userSessionId = loginUsers.get(user.getLoginName());
						log.debug("userSessionId:" + userSessionId);
						log.debug("sessionId:" + sessionId);
						if(userSessionId == null || !userSessionId.equals(sessionId)) {
							out.print("登录失败，该用户已登录！");
						}
					}
				}
			} else {
				out.print("登录失败，用户名或者密码错误！");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}

	/*
	 * 用户注销
	 * @author cj
	 */
	@RequestMapping("/loginOut.do")
	public String loginOut( HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		HttpSession session = request.getSession();

		ServletContext application = request.getSession().getServletContext();
		@SuppressWarnings("unchecked")
		Map<String, String> loginUsers = (Map<String, String>) application.getAttribute("loginUsers");
		User user = (User) session.getAttribute("user");
		log.debug("loginUsers:" + loginUsers);
		if(user != null) {
			loginUsers.remove(user.getLoginName());
			application.removeAttribute("loginUsers");
			application.setAttribute("loginUsers",loginUsers);
		}
		@SuppressWarnings("unchecked")
		Enumeration<String> attributeNames = session.getAttributeNames();
		while(attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			session.removeAttribute(attributeName);
		}
		
		if(saveOprationLog("注销", user.getUserId())) {
			log.debug("用户操作日志记录成功！");
		}
		return "redirect:/admin.do";
	}
	
	/*
	 * 用户修改自己密码
	 * @author cj
	 */
	@RequestMapping("/updatePwd.do")
	public String updatePwd( HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		String userPwd = request.getParameter("userPwd");
		User user = (User) request.getSession().getAttribute("user");
		user.setUserPwd(userPwd);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(userService.update(user)) {
				if(saveOprationLog("修改密码", user.getUserId())) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}
	
	/*
	 * 重置密码
	 * @author cj
	 */
	@RequestMapping("/resetUserPwd.do")
	public String resetUserPwd( HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		String userPwd = request.getParameter("userPwd");
		String strId = request.getParameter("userId");
		long userId = Long.parseLong(strId);
		User user = (User) userService.getUserById(userId);
		user.setUserPwd(userPwd);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(userService.update(user)) {
				User oprator = (User)request.getSession().getAttribute("user");
				if(saveOprationLog("重置了登录名为"+ user.getLoginName() + "的用户密码", oprator.getUserId())) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}
	
	/*
	 * 修改用户状态
	 * @author cj
	 */
	@RequestMapping("/changeUserState.do")
	public String changeUserState( HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
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
			if(userService.update(user)) {
				User oprator = (User)request.getSession().getAttribute("user");
				if(saveOprationLog("变更了"+ user.getLoginName() + "的状态", oprator.getUserId())) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}
	
	
	
	/*
	 * 添加用户
	 * @author cj
	 */
	@RequestMapping("/addUser.do")
	public String addUser(User user, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(userService.addUser(user)) {
				User oprator = (User)request.getSession().getAttribute("user");
				if(saveOprationLog("增加了新用户："+ user.getLoginName() , oprator.getUserId())) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}
	
	/*
	 * 添加用户
	 * @author cj
	 */
	@RequestMapping("/updateUser.do")
	public String updateUser(User user, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(userService.update(user)) {
				User oprator = (User)request.getSession().getAttribute("user");
				if(saveOprationLog("修改了用户："+ user.getLoginName() + "的信息", oprator.getUserId())) {
					log.debug("用户操作日志记录成功！");
				}
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (IOException e) {
			log.error("流获取失败！",e);
		}
		return null;
	}
	
	/*
	 * 根据参数列表获取用户信息
	 * @author cj
	 */
	@RequestMapping("/getUsersByParams.do")
	public String getUsersByParams( HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		String keyWord = request.getParameter("keyWord");
		String strCompanyId = request.getParameter("companyId");
		String strUserType = request.getParameter("userType");
		String strUserState = request.getParameter("userState");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWord", keyWord);
		if(strCompanyId != null && strCompanyId.length() > 0 && strCompanyId.matches("^[0-9]*$")) {
			params.put("companyId", new Long(strCompanyId));
		}
		if(strUserType != null && strUserType.length() > 0 && strUserType.matches("^[0-9]*$")) {
			params.put("userType", new Integer(strUserType));
		}
		if(strUserState != null && strUserState.length() > 0 && strUserState.matches("^[0-9]*$")) {
			params.put("userState", new Integer(strUserState));
		}
		List<User> userList = userService.getUsersByParams(params);
		modelMap.put("userList", userList);
		
		return "/jsp/admin/pages/usersAdmin";
	}
	
	/*
	 * 根据参数列表获取日志信息
	 * @author cj
	 */
	@RequestMapping("/getLogByParams.do")
	public String getLogByParams( HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		String logDate = request.getParameter("logDate");
		User user = (User) request.getSession().getAttribute("user");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("logDate", logDate);
		params.put("oprator", user.getUserId());
		List<OprationLog> logList = oprationLogService.getLogByParams(params);
		modelMap.put("logList", logList);
		
		return "/jsp/admin/pages/showLog";
	}
	
	private boolean saveOprationLog(String msg, long opratorId) {
		OprationLog log = new OprationLog();
		log.setLogDate(new Date());
		log.setOpration(msg);
		log.setOprator(opratorId);
		
		return oprationLogService.addLog(log);
	}
	
}
