package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.OprationLog;

public interface OprationLogMapper {

	int insert(OprationLog record);

	/**
	 * 某个时间的日志信息
	 * 
	 * @param logDate
	 *            时间
	 * @param oprator
	 * @param oprationKey
	 * @return
	 */
	List<OprationLog> getLogByParams(@Param("logDate") String logDate,
			@Param("oprator") Long oprator, @Param("oprationKey") String oprationKey);

	int deleteById(Long logId);

	OprationLog getById(Long logId);

	int update(OprationLog record);

	/**
	 * 获取最近max个用户进行的某项操作
	 * 
	 * @param max
	 *            最近进行某个操作的用户个数
	 * @param opration
	 *            操作
	 * @return
	 */
	List<OprationLog> getRecentOprationLog(@Param("max") Integer max,
			@Param("opration") String opration);

	/**
	 * 查询某个用户最新（即时间倒叙）进行的某项操作的次数
	 * 
	 * @param 时间倒叙排序
	 *            ，取的记录数目 用于limit 中
	 * @param opration
	 *            某个操作
	 * @param oprator
	 *            操作人
	 * @return
	 */
	List<OprationLog> getLastOprationLog(@Param("max") Integer max,
			@Param("opration") String opration, @Param("oprator") Long oprator);

	/**
	 * 某个时间段某人所做的操作的日志信息，如果所有关键字都为空则是查询所有
	 * 
	 * @param logDate
	 *            时间
	 * @param oprator
	 * @param oprationKey
	 * @return
	 */
	List<OprationLog> getLogByKeys(@Param("beginDate") String beginDate,
			@Param("endDate") String endDate, @Param("oprator") Long oprator,
			@Param("oprationKey") String oprationKey);

}