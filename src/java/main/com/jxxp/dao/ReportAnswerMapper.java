package com.jxxp.dao;

import com.jxxp.pojo.ReportAnswer;

/**
 * 
 * @author gcx
 * 
 */
public interface ReportAnswerMapper {

	/**
	 * 保存举报者对某个问题的答复
	 * 
	 * @param answer
	 * @return
	 */
	int insert(ReportAnswer answer);

	/**
	 * @param raId
	 *            答复id
	 * @return
	 */
	ReportAnswer findById(long raId);

	/**
	 * @param questKey
	 *            举报所填问题的关键索引，为维护问题的可读性，程序中应使用此索引值来表示问题
	 * @return
	 */
	ReportAnswer getAnswerByquestKey(String questKey);

	int deleteById(long raId);

}
