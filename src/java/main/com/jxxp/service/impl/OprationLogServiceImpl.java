package com.jxxp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

	@Override
	public List<Map<String, Object>> getLastOpration() {
		List<Map<String, Object>> logsList = new ArrayList<Map<String, Object>>();
		List<OprationLog> logs = new ArrayList<OprationLog>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logs = oprationLogMapper.getRecentOprationLog(10, "登录");
		System.out.println("logsSize=====" + logs.size());
		for (int i = 0; i < logs.size(); i++) {
			// 存放一个用户登录日志key=oprationLog，停留时间key=times，以及操作key=oprationsStr
			Map<String, Object> logsMap = new HashMap<String, Object>();
			// 1.存放登录日志
			OprationLog oprationLog = logs.get(i);
			logsMap.put("oprationLog", oprationLog);
			// 获取用户最后一次操作
			List<OprationLog> lastLogs = oprationLogMapper.getLastOprationLog(1, null, oprationLog
					.getOprator().getUserId());
			if (lastLogs.size() > 0) {
				OprationLog outLog = lastLogs.get(0);
				if (outLog.getLogDate().getTime() > oprationLog.getLogDate().getTime()) {
					// 2、 获得此次登录停留的时间
					long times = outLog.getLogDate().getTime() - oprationLog.getLogDate().getTime();
					logsMap.put("times", times);
					// 3、 获取此次登录所进行的所有操作
					String oprationsStr = "";
					List<OprationLog> allOprations = oprationLogMapper.getLogByKeys(format
							.format(oprationLog.getLogDate()), format.format(outLog.getLogDate()),
							oprationLog.getOprator().getUserId(), null);
					for (int j = 0; j < allOprations.size(); j++) {
						oprationsStr = oprationsStr + " " + allOprations.get(j).getOpration();
					}
					logsMap.put("oprationsStr", oprationsStr);
				}
			} else {
				System.out.println("第一次登录，没有上次操作");
			}
			logsList.add(logsMap);
		}
		return logsList;
	}
}
