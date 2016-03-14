package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.CompanyQuestion;

public interface CompanyQuestionMapper {
	/**
	 * 通过客户公司的id号获取该公司所选择的所有问题
	 * 
	 * @param companyId
	 *            公司Id
	 * @return
	 */
	List<CompanyQuestion> getAllByCompany(long companyId);

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

	/**
	 * 使用问题id和公司id唯一标识公司和问题的关系，因此用问题id和公司id删除关系表中唯一的一条记录
	 * 
	 * @param questId
	 * @param companyId
	 * @return
	 */
	int deleteByDoubleId(@Param("questId") long questId, @Param("companyId") long companyId);

	/**
	 * 批量删除某个公司已有的问题列表
	 * 
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
	int insertQuestionList(@Param("comQuestList") List<CompanyQuestion> comQuestList,
			@Param("companyId") long companyId);
}