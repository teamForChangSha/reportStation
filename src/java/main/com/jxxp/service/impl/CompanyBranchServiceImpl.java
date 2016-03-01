package com.jxxp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.CompanyBranchMapper;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.service.CompanyBranchService;

@Service("companyBranchService")
public class CompanyBranchServiceImpl implements CompanyBranchService {
	@Resource
	private CompanyBranchMapper companyBranchMapper;

	@Override
	public boolean addCompanyBranch(CompanyBranch companyBranch) {
		return companyBranchMapper.insert(companyBranch) > 0;
	}

	@Override
	public List<CompanyBranch> getCompanyBranches(long companyId) {
		return companyBranchMapper.getAllByCompany(companyId);

	}

}
