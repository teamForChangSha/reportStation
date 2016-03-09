package com.jxxp.comms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jxxp.pojo.User;

/** 
 * 登陆拦截器. 
 * 
 * @author cj 
 */
public class LoginInterceptor extends HandlerInterceptorAdapter { 
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
    private static final String[] IGNORE_URI = {"/login.jsp", "/login.do"}; 
  
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { 
        log.debug("==============Interceptor...");
    	boolean flag = false; 
        String url = request.getRequestURL().toString(); 
        log.debug("URL: " + url); 
        for (String s : IGNORE_URI) { 
            if (url.contains(s)) { 
                flag = true; 
                break; 
            } 
        } 
        if (!flag) { 
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
            	flag = true; 
            } else {
            	response.sendRedirect("../../admin.do");
            }
        }
        return flag; 
    } 
  
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception { 
        super.postHandle(request, response, handler, modelAndView); 
    } 
} 

