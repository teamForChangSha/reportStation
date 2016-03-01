package com.jxxp.service;

import java.util.List;

import com.jxxp.pojo.CompanyBranch;

public interface CompanyBranchService {
	boolean addCompanyBranch(CompanyBranch companyBranch);

	List<CompanyBranch> getCompanyBranches(long companyId);
}
