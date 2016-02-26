package com.jxxp.service;

import java.util.List;

import com.jxxp.pojo.ReportType;

public interface ReportTypeService {
	
	/**
	 * 更新举报类型信息
	 * 
	 * @param reportType
	 *            需要跟新的举报类型
	 * @return 更新reportType，成功返回true，否则返回false
	 */
	public boolean updateReportType(ReportType reportType);
	
	/**
	 * 返回默认举报类型列表
	 * 
	 * @return 默认reportType集合，没有则返回空列表
	 */
	public List<ReportType> getDefaultList();
	
	
}