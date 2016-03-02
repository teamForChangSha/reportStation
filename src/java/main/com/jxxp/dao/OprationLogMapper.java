package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.OprationLog;

public interface OprationLogMapper {

	int insert(OprationLog record);

	List<OprationLog> getLogByParams(@Param("logDate") String logDate,
			@Param("oprator") Long oprator);

	int deleteById(Long logId);

	OprationLog getById(Long logId);

	int update(OprationLog record);

}