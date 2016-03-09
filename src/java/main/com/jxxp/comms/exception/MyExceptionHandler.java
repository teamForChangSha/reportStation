package com.jxxp.comms.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionHandler implements HandlerExceptionResolver {
	private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest req,
			HttpServletResponse res, Object obj, Exception exp) {
		String fullStackTrace = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(exp);
		log.error(fullStackTrace);
		
		Map<String, Object> model = new HashMap<String, Object>();  
		model.put("errorMsg", exp); 
		
		return new ModelAndView("/jsp/pages/error", model);
	}

}
