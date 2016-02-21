package com.jxxp.service;

import com.jxxp.pojo.CaseComment;

public interface CaseCommentService {
	/**
	 * 添加案件追加信息
	 * 
	 * @param CaseComment
	 *            追加信息对象
	 * @return 添加成功返回true，否则返回false
	 */
	boolean addCaseComment(CaseComment caseComment);
}
