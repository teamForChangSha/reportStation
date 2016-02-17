package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.CaseComment;

/**
 * 举报信息所添加的备注信息，举报者可对案件追加信息，客户方的用户和管理方的用户可对案件添加备注
 * 
 * @author gcx
 * 
 */
public interface CaseCommentMapper {

	/**
	 * 添给某个案件加备注信息
	 * 
	 * @param comment
	 *            备注对象
	 * @param caseId
	 *            案件id号
	 * @return
	 */
	int insert(@Param("comment") CaseComment comment, @Param("caseId") Long caseId);

	/**
	 * 追加信息的id
	 * 
	 * @param ccId
	 * @return
	 */
	CaseComment findById(long ccId);

	/**
	 * 通过举报人获得该举报人提交的某个举报案件的备注
	 * 
	 * @param rcId
	 *            案件id号
	 * @param reporterId
	 *            举报人id
	 * @return
	 */
	List<CaseComment> getByReporter(@Param("rcId") Long rcId, @Param("reporterId") Long reporterId);

	/**
	 * 通过举报人获得该举报人提交的某个举报案件的备注
	 * 
	 * @param rcId
	 *            案件id号
	 * @param userId
	 *            用户（客户方或者管理方的用户）id
	 * @return
	 */
	List<CaseComment> getByUser(@Param("rcId") Long rcId, @Param("userId") Long userId);

	List<CaseComment> getAllByReportCaseId(long crId);

	/**
	 * @param ccId
	 *            追加信息的id
	 * @return
	 */
	int deleteById(long ccId);

}
