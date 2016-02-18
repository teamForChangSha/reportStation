package com.jxxp.dao;

import java.util.List;

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
	ReportAnswer getById(long raId);

	/**
	 * @param 案件id
	 * @return
	 */
	List<ReportAnswer> getAnswerByCaseId(long rcId);

	int deleteById(long raId);

}
