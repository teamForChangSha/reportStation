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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jxxp.pojo.User;

public class ResourceFilter implements Filter  {
	private static final Logger log = LoggerFactory.getLogger(ResourceFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("resource filter init...");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.debug("in resource filter...");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		if(session != null){
			User user = (User) session.getAttribute("user");
			if(user == null) {
				String auth = (String) session.getAttribute("auth");
				if(auth == null || "no".equals(auth)) {
					response.sendRedirect("/reportStation/");
				}
			}
		} else {
			response.sendRedirect("/reportStation/");
		}
		
		
		
		
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
