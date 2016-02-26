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

import com.jxxp.pojo.User;
import com.jxxp.service.UserService;
/*
 * 用户Controller
 * @author cj
 */
@Controller("userController")
@RequestMapping("/user")
public class UserController {	
	private static final Logger log = LoggerFactory.getLogger(CaseController.class);

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
					request.getSession().setAttribute("user", user);
					out.print("success");
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
	 * 用户登录
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

}
