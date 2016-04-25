package com.jxxp.controller.back;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 在SpringMVC中，bean中定义了Date，double等类型，如果没有做任何处理的话，日期以及double都无法绑定
 * 编写一个控制器的抽象类，主要是写一些控制器公用的方法，比如日期的手动绑定
 * 
 * @author gcx
 * 
 */
@Controller("userController")
public abstract class BaseController {
	@InitBinder
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(format, true);
		bin.registerCustomEditor(Date.class, dateEditor);
	}
}
