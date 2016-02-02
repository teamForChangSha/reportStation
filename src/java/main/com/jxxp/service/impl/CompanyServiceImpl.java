package com.jxxp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.CompanyMapper;
import com.jxxp.pojo.AreaInfo;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.CompanyWholeInfo;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportType;
import com.jxxp.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Resource
	private CompanyMapper companyMapper;

	@Override
	public boolean saveCompanyInfo(Company company) {
		int i = companyMapper.insert(company);
		if (i >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean saveWholeCompany(CompanyWholeInfo wholeCompany) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Company getCompany(String name) {
		Company company = companyMapper.findByName(name);
		return company;

	}

	@Override
	public CompanyWholeInfo getCompanyWhole(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveCompanyQuestions(Company company, List<QuestionInfo> questList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, QuestionInfo> getCompanyQuestions(Company company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveCompanyReportType(Company company, List<ReportType> rtList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addCompanyReportType(Company company, ReportType reportType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ReportType> getCompanyReportType(Company company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyBranch> getCompanyBranchByArea(AreaInfo area, Company company) {
		// TODO Auto-generated method stub
		return null;
	}

}
