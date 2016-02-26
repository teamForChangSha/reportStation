package com.jxxp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.ReportTypeMapper;
import com.jxxp.pojo.ReportType;
import com.jxxp.service.ReportTypeService;

@Service("reportTypeService")
public class ReportTypeServiceImpl implements ReportTypeService {
	@Resource
	private ReportTypeMapper reportTypeMapper;

	@Override
	public boolean updateReportType(ReportType reportType) {
		int count = reportTypeMapper.update(reportType);
		boolean flag = count > 0 ? true : false;
		return flag;
	}

	@Override
	public List<ReportType> getDefaultList() {
		// TODO Auto-generated method stub
		return reportTypeMapper.getAllByCompanyId(0);
	}

}
