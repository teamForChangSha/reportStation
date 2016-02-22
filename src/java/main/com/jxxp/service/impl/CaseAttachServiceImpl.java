package com.jxxp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.CaseAttachMapper;
import com.jxxp.pojo.CaseAttach;
import com.jxxp.service.CaseAttachService;
@Service("caseAttachService")
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
		return caseAttachMapper.insert(caseAttach) > 0;
	}

}
