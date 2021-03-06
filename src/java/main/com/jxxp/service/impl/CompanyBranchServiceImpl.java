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

	@Override
	public boolean updateBranch(CompanyBranch branch) {
		int flag = companyBranchMapper.update(branch);
		if (flag > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteBranch(long branchId) {
		int flag = companyBranchMapper.deleteById(branchId);
		if (flag > 0) {
			return true;
		}
		return false;
	}

}
