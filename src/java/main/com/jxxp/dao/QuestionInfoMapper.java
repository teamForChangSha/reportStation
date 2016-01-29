package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.QuestionInfo;

/**
 * @author gcx
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

	/**
	 * 通过问题的关键索引获得该问题对象
	 * 
	 * @param questKey
	 *            举报所填问题的关键索引
	 * @return
	 */
	QuestionInfo findByKey(String questKey);

	/**
	 * 通过客户公司的id号获取该公司所选择的所有问题
	 * 
	 * @param companyId
	 *            公司Id
	 * @return
	 */
	List<QuestionInfo> getAllByCompany(Long companyId);

	int deleteById(long id);

}
