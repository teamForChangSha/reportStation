package com.jxxp.dao;

import org.apache.ibatis.annotations.Param;

public interface CompanyQuestionMapper {
	int insert(@Param("questId") long questId, @Param("companyId") long companyId);

	/**
	 * 保存公司所选则的问题
	 * 
	 * @param questIds
	 *            问题id数组
	 * @param companyId
	 *            公司id
	 * @return
	 */
	int insertAll(@Param("questIds") long[] questIds, @Param("companyId") long companyId);

	int deleteByDoubleId(@Param("questId") long questId, @Param("companyId") long companyId);
}