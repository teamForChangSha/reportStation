package com.jxxp.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.ClientCompanyMapper;
import com.jxxp.pojo.ClientCompany;
import com.jxxp.service.ClientCompanyService;

@Service("ClientCompanyService")
public class ClientCompanyServiceImpl implements ClientCompanyService {
	@Resource
	private ClientCompanyMapper clientCompanyMapper;

	@Override
	public boolean addClientCompany(ClientCompany client) {
		if (client != null) {
			client.setCreateTime(new Date());
		}
		int count = clientCompanyMapper.insert(client);
		boolean flag = false;
		if (count > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean delClientCompany(Long companyId) {
		int count = clientCompanyMapper.delClientCompany(companyId);
		boolean flag = false;
		if (count > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean updateClientCompany(ClientCompany client) {
		int count = clientCompanyMapper.update(client);
		boolean flag = false;
		if (count > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;

	}

}
