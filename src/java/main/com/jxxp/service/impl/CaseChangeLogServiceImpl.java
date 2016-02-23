package com.jxxp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.CaseChangeLogMapper;
import com.jxxp.pojo.CaseChangeLog;
import com.jxxp.service.CaseChangeLogService;

@Service("caseChangeLogService")
public class CaseChangeLogServiceImpl implements CaseChangeLogService {
	@Resource
	private CaseChangeLogMapper caseChangeLogMapper;
	
	@Override
	public boolean addCaseChangeLog(CaseChangeLog caseChangeLog) {
		return caseChangeLogMapper.insert(caseChangeLog) > 0;
	}

}
