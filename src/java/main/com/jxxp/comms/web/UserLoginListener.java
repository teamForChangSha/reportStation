package com.jxxp.comms.web;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jxxp.pojo.User;

public class UserLoginListener implements HttpSessionListener {
	private static final Logger log = LoggerFactory.getLogger(UserLoginListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		log.debug("session已创建！id:" + se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		ServletContext application = session.getServletContext();
		@SuppressWarnings("unchecked")
		Map<String, String> loginUsers = (Map<String, String>) application.getAttribute("loginUsers");
		User user = (User) session.getAttribute("user");
		if(user != null) {
			loginUsers.remove(user.getLoginName());
			application.setAttribute("loginUsers", loginUsers);
			
		}
		log.debug("session已失效！id:" + session.getId());
	}
	
}
