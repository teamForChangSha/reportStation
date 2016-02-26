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
import com.jxxp.dao.CompanyQuestionMapper;
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
	@Resource
	private CompanyQuestionMapper companyQuestionMapper;

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
	public List<Company> getCompanyByName(String companyName) {
		return companyMapper.getAllByName(companyName);
	}

	@Override
	public CompanyWholeInfo getCompanyWhole(String name) {
		Company company = companyMapper.findByName(name);
		CompanyOther companyOther = companyOtherMapper.getByCompanyId(company.getCompanyId());
		return new CompanyWholeInfo(company, companyOther);
	}

	@Transactional
	public boolean saveCompanyQuestions(Company company, List<QuestionInfo> questList) {
		boolean flag = false;
		long companyId = company.getCompanyId();
		List<QuestionInfo> oldQuestList = questionInfoMapper.getAllByCompany(companyId);
		// 则先删除原来的问题列表
		if (oldQuestList != null) {
			companyQuestionMapper.deleteQuestionList(oldQuestList, companyId);
		}
		int count = companyQuestionMapper.insertQuestionList(questList, companyId);
		flag = count > 0 ? true : false;
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
		int count = reportTypeMapper.getAllByCompanyId(company.getCompanyId()).size();
		if(count > 0) {
			flag = reportTypeMapper.deleteByCompanyId(company.getCompanyId()) > 0;
		}
		for (ReportType reportType : rtList) {
			reportType.setOwner(company);
			flag = reportTypeMapper.insert(reportType) > 0;
			if (!flag) {
				break;
			}
		}
		return flag;
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

	@Override
	public boolean addCompanyOther(CompanyOther companyOther) {
		return companyOtherMapper.insert(companyOther) > 0;
	}

	@Override
	public boolean updateCompanyWholeInfo(CompanyWholeInfo companyWholeInfo) {
		boolean flag = false;
		flag = companyMapper.update(companyWholeInfo.getCompany()) > 0;
		if (companyOtherMapper.getByCompanyId(companyWholeInfo.getCompany().getCompanyId()) != null) {
			flag = companyOtherMapper.update(companyWholeInfo.getCompanyOther()) > 0;
		} else {
			flag = companyOtherMapper.insert(companyWholeInfo.getCompanyOther()) > 0;
		}
		return flag;
	}

}
