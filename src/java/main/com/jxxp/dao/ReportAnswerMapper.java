package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	 * 取得某个问题的答案
	 * 
	 * @param answer
	 * @param questId
	 * @return
	 */
	int insertByQuestionId(@Param("answer") ReportAnswer answer, @Param("questId") long questId);

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

	/**
	 * 删除某个案件的所有答案
	 * 
	 * @param rc_id
	 *            案件id
	 * @return
	 */
	int delByCaseId(Long rc_id);

	int deleteById(long raId);

}
