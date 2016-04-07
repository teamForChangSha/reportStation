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
		String logDate = null;
		Long oprator = null;
		String oprationKey = null;
		if (params.get("logDate") != null) {
			logDate = (String) params.get("logDate");
		}
		if (params.get("oprator") != null) {
			oprator = (Long) params.get("oprator");
		}
		if (params.get("oprationKey") != null) {
			oprationKey = (String) params.get("oprationKey");
		}
		return oprationLogMapper.getLogByParams(logDate, oprator, oprationKey);
	}
}
