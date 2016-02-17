package com.jxxp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jxxp.dao.CaseAttachMapper;
import com.jxxp.pojo.CaseAttach;
import com.jxxp.service.CaseAttachService;

public class CaseAttachServiceImpl implements CaseAttachService {
	@Resource
	private CaseAttachMapper caseAttachMapper;
	@Override
	public List<CaseAttach> getCaseAttachByTrackingNo(String trackingNo) {
		// TODO Auto-generated method stub
		return caseAttachMapper.getAllByTrackingNo(trackingNo);
	}

	@Override
	public boolean addCaseAttach(CaseAttach caseAttach) {
		// TODO Auto-generated method stub
		return caseAttachMapper.insert(caseAttach, caseAttach.getTrackingNo()) > 0;
	}

	@Override
	public boolean updateTempCaseAttach(String trackingNo, String filePath) {
		// TODO Auto-generated method stub
		return false;
	}

}
