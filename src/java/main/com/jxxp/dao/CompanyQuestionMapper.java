package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.QuestionInfo;

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
	int insertQuestionIds(@Param("questIds") long[] questIds, @Param("companyId") long companyId);

	int deleteByDoubleId(@Param("questId") long questId, @Param("companyId") long companyId);

	/**
	 * 批量删除某个公司已有的问题列表
	 * 
	 * @param questList
	 *            公司问题集合
	 * @param companyId
	 *            公司id
	 * @return
	 */
	int deleteByCompanyId(long companyId);

	/**
	 * 批量插入某个公司的问题列表
	 * 
	 * @param questList
	 *            问题列表
	 * @param companyId
	 *            公司id
	 * @return
	 */
	int insertQuestionList(@Param("questList") List<QuestionInfo> questList,
			@Param("companyId") long companyId);
}