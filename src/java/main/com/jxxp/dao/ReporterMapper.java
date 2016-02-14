package com.jxxp.dao;

import com.jxxp.pojo.Reporter;

/**
 * @author gcx
 * 
 */
public interface ReporterMapper {

	/**
	 * 添加一个举报者
	 * 
	 * @param reporter
	 * @return
	 */
	int insert(Reporter reporter);

	/**
	 * 更改举报者的信息，比如更改密码
	 * 
	 * @param reporter
	 * @return
	 */

	int update(Reporter reporter);

	Reporter getById(long reporterId);

	int deleteById(long reporterId);

}
