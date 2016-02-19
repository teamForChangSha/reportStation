package com.jxxp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.ReporterMapper;
import com.jxxp.pojo.Reporter;
import com.jxxp.service.ReporterService;
@Service("reporterService")
public class ReporterServiceImpl implements ReporterService {
	@Resource
	private ReporterMapper reporterMapper;
	
	@Override
	public Reporter getByMobile(String mobile) {
		return reporterMapper.getByMobile(mobile);
	}

	@Override
	public boolean addReporter(Reporter reporter) {
		return reporterMapper.insert(reporter) > 0;
	}

}
