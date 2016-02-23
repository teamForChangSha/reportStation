package com.jxxp.service;

import com.jxxp.pojo.CaseChangeLog;

public interface CaseChangeLogService {
	/**
	 * 添加案件改变记录信息
	 * 
	 * @param CaseChangeLog
	 *            案例改变记录对象
	 * @return 添加成功返回true，否则返回false
	 */
	boolean addCaseChangeLog(CaseChangeLog caseChangeLog, long rcId);
}
