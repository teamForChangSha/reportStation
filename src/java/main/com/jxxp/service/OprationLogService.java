package com.jxxp.service;

import java.util.List;
import java.util.Map;

import com.jxxp.comms.web.Page;
import com.jxxp.pojo.OprationLog;

/**
 * @author cj
 * 
 */
public interface OprationLogService {

	/**
	 * 添加日志的方法
	 * 
	 * @param oprationLog
	 *            日志对象
	 * @return 添加成功返回true，失败则返回false
	 */
	public boolean addLog(OprationLog oprationLog);

	/**
	 * 根据参数集合查询日志的方法
	 * 
	 * @param page
	 *            分页对象，如果为空则不分页
	 * 
	 * @param params
	 *            参数集合
	 * @return 成功返回用户日志信息列表，失败则返回空列表
	 */
	public List<OprationLog> getLogByParams(Page page, Map<String, Object> params);

	/**
	 * 最近十个用户上次登录时间及查看/处理的情况简报。
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getLastOpration();
}
