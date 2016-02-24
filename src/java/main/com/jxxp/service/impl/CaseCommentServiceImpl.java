package com.jxxp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.CaseCommentMapper;
import com.jxxp.pojo.CaseComment;
import com.jxxp.service.CaseCommentService;

@Service("caseCommentService")
public class CaseCommentServiceImpl implements CaseCommentService {
	@Resource 
	private CaseCommentMapper caseCommentMapper;
	
	@Override
	public boolean addCaseComment(CaseComment caseComment, long rcId) {
		return caseCommentMapper.insert(caseComment, rcId) > 0;
	}

}
