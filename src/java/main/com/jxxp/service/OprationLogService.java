package com.jxxp.service;

import java.util.List;
import java.util.Map;

import com.jxxp.pojo.OprationLog;

/**
 * @author cj
 *
 */
public interface OprationLogService {
	
	/**
	 * 添加日志的方法
	 * @param oprationLog 日志对象
	 * @return  添加成功返回true，失败则返回false
	 */
	public boolean addLog(OprationLog oprationLog);
	
	/**
	 * 根据参数集合查询日志的方法
	 * @param params 参数集合
	 * @return  成功返回用户日志信息列表，失败则返回空列表
	 */
	public List<OprationLog> getLogByParams(Map<String,Object> params);
}
