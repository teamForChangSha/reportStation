package com.jxxp.service.impl;

import javax.annotation.Resource;

import com.jxxp.dao.ReporterMapper;
import com.jxxp.pojo.Reporter;
import com.jxxp.service.ReporterService;

public class ReporterServiceImpl implements ReporterService {
	@Resource
	private ReporterMapper reporterMapper;
	
	@Override
	public Reporter getByMobile(String mobile) {
		return null;
	}

	@Override
	public boolean addReporter(Reporter reporter) {
		return reporterMapper.insert(reporter) > 0;
	}

}
