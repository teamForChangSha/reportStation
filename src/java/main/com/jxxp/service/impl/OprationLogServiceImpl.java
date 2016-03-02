package com.jxxp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.OprationLogMapper;
import com.jxxp.pojo.OprationLog;
import com.jxxp.service.OprationLogService;

@Service("oprationLogService")
public class OprationLogServiceImpl implements OprationLogService {
	@Resource
	private OprationLogMapper oprationLogMapper;
	
	@Override
	public boolean addLog(OprationLog oprationLog) {
		// TODO Auto-generated method stub
		return oprationLogMapper.insert(oprationLog) > 0;
	}

	@Override
	public List<OprationLog> getLogByParams(Map<String, Object> params) {
		String logDate = (String) params.get("logDate");
		Long oprator = (Long) params.get("oprator");
		return oprationLogMapper.getLogByParams(logDate, oprator);
	}

}
