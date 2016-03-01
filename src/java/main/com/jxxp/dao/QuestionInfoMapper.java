package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.QuestionInfo;

/**
 * @author gcx 所有公司的默认问题列表一样，如果公司选择了，不修改questioninfo表，只在company_question中建立关系
 * 
 */
public interface QuestionInfoMapper {

	/**
	 * 添加一个问题
	 * 
	 * @param question
	 * @return
	 */
	int insert(QuestionInfo question);

	/**
	 * 更改某个问题的信息
	 * 
	 * @param question
	 * @return
	 */
	int update(QuestionInfo question);

	QuestionInfo getById(long questId);

	/**
	 * 通过问题的关键索引获得该问题对象
	 * 
	 * @param questKey
	 *            举报所填问题的关键索引
	 * @return
	 */
	QuestionInfo getByKey(String questKey);

	/**
	 * 通过客户公司的id号获取该公司所选择的所有问题
	 * 
	 * @param companyId
	 *            公司Id
	 * @return
	 */
	List<QuestionInfo> getAllByCompany(long companyId);

	/**
	 * 获取默认问题列表，所有公司默认问题列表一样
	 * 
	 * @return
	 */
	List<QuestionInfo> getQuestionTemlate();

	int deleteById(long id);

}
