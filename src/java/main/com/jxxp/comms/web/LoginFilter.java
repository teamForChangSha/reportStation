package com.jxxp.comms.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jxxp.pojo.User;


public class LoginFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);
    private static final String[] IGNORE_URI = {"/login.jsp", "/login.do"}; 

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		log.debug("filter init...");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.debug("doFilter...");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
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
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		log.debug("filter destroy...");
	}

}
