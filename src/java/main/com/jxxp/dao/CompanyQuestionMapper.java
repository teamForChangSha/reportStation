package com.jxxp.dao;

import org.apache.ibatis.annotations.Param;

public interface CompanyQuestionMapper {
	int insert(@Param("questId") long questId, @Param("companyId") long companyId);

	int deleteByDoubleId(@Param("questId") long questId, @Param("companyId") long companyId);
}