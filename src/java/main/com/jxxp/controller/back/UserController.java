package com.jxxp.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.jxxp.pojo.User;
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
		return "redirect:/admin.do";
	}
	
	/*
	 * 用户修改密码
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
	
}
