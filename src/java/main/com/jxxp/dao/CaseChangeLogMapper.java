package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.CaseChangeLog;

/**
 * 
 * @author gcx
 * 
 */
public interface CaseChangeLogMapper {

	/**
	 * 
	 * @param caseLog
	 *            案件日志对象
	 * @param rcId
	 *            案件id
	 * @return
	 */
	int insert(@Param("caseLog") CaseChangeLog caseLog, @Param("rcId") Long rcId);

	/**
	 * 更改用户的信息，比如更改用户的状态、更改密码等操作
	 * 
	 * @param caseLog
	 * @return
	 */
	int update(CaseChangeLog caseLog);

	/**
	 * @param rcId
	 * @return 某个案件的所有操作记录
	 */
	List<CaseChangeLog> getAllByCaseId(Long rcId);

	int deleteById(long ccId);
}
