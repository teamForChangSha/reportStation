package com.jxxp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.CompanyBranchMapper;
import com.jxxp.dao.CompanyMapper;
import com.jxxp.dao.CompanyOtherMapper;
import com.jxxp.dao.QuestionInfoMapper;
import com.jxxp.dao.ReportTypeMapper;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.CompanyOther;
import com.jxxp.pojo.CompanyWholeInfo;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportType;
import com.jxxp.service.CompanyService;

/**
 * 
 * @author cj
 * 
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Resource
	private CompanyMapper companyMapper;
	@Resource
	private CompanyOtherMapper companyOtherMapper;
	@Resource
	private QuestionInfoMapper questionInfoMapper;
	@Resource
	private ReportTypeMapper reportTypeMapper;
	@Resource
	private CompanyBranchMapper companyBranchMapper;

	@Override
	public boolean saveCompanyInfo(Company company) {
		return companyMapper.insert(company) > 0;
	}

	@Transactional
	public boolean saveWholeCompany(CompanyWholeInfo wholeCompany) {
		boolean flag1 = saveCompanyInfo(wholeCompany.getCompany());
		boolean flag2 = companyOtherMapper.insert(wholeCompany.getCompanyOther()) > 0;
		return flag1 && flag2;
	}

	@Override
	public Company getCompany(String name) {
		return companyMapper.findByName(name);
	}

	@Override
	public CompanyWholeInfo getCompanyWhole(String name) {
		Company company = companyMapper.findByName(name);
		CompanyOther companyOther = companyOtherMapper.findByCompanyId(company.getCompanyId());
		return new CompanyWholeInfo(company, companyOther);
	}

	@Transactional
	public boolean saveCompanyQuestions(Company company, List<QuestionInfo> questList) {
		boolean flag = false;
		for (int i = 0; i < questList.size(); i++) {
//			flag = questionInfoMapper
//					.add(company.getCompanyId(), questList.get(i).getQuestKey());
			if (!flag)
				break;
		}
		return flag;
	}

	@Override
	public Map<String, QuestionInfo> getCompanyQuestions(Company company) {
		Map<String, QuestionInfo> map = new HashMap<String, QuestionInfo>();
		List<QuestionInfo> questions = questionInfoMapper.getAllByCompany(company.getCompanyId());
		for (QuestionInfo question : questions) {
			map.put(question.getQuestKey(), question);
		}
		return map;
	}

	@Transactional
	public boolean saveCompanyReportType(Company company, List<ReportType> rtList) {
		boolean flag = false;
		if (reportTypeMapper.deleteByCompanyId(company.getCompanyId()) >= 0) {
			for (ReportType reportType : rtList) {
				flag = reportTypeMapper.insertByCompany(reportType, company.getCompanyId()) > 0;
				if (!flag) {
					break;
				}
			}
		}
		return flag;
	}

	@Override
	public boolean addCompanyReportType(Company company, ReportType reportType) {
		return reportTypeMapper.insertByCompany(reportType, company.getCompanyId()) > 0;
	}

	@Override
	public List<ReportType> getCompanyReportType(Company company) {
		return reportTypeMapper.getAllByCompanyId(company.getCompanyId());
	}

	@Override
	public List<CompanyBranch> getCompanyBranchByArea(long areaId, long companyId) {
		return companyBranchMapper.getAllByArea(areaId, companyId);
	}

	@Override
	public List<Company> getAllCompanyList() {
		return companyMapper.getAllCompany();
	}

	@Override
	public Company getCompanyById(long companyId) {
		Company company = companyMapper.getById(companyId);
		return company;
	}

	@Override
	public CompanyBranch getCompanyBranchById(long branchId) {
		CompanyBranch branch = companyBranchMapper.getById(branchId);
		return branch;
	}

}
