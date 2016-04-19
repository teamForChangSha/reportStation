package com.jxxp.service.impl;

import java.util.ArrayList;
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
import com.jxxp.pojo.CompanyQuestion;
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
		Company company = wholeCompany.getCompany();
		boolean flag1 = saveCompanyInfo(company);
		boolean flag2 = true;
		if (wholeCompany.getCompanyOther() != null) {
			CompanyOther other = wholeCompany.getCompanyOther();
			System.out.println("-------------" + other.getCompanyId());
			other.setCompanyId(company.getCompanyId());
			flag2 = companyOtherMapper.insert(other) > 0;
		}
		return flag1 && flag2;
	}

	@Override
	public List<Company> getCompanyByName(String companyName) {
		return companyMapper.getAllByName(companyName);
	}

	/*
	 * 不需要此方法，因为在获公司本身有公司其他信息对象
	 */
	@Override
	public CompanyWholeInfo getCompanyWhole(String name) {
		Company company = companyMapper.findByName(name);
		CompanyOther companyOther = companyOtherMapper.getByCompanyId(company.getCompanyId());
		return new CompanyWholeInfo(company, companyOther);
	}

	@Transactional
	public boolean saveCompanyQuestions(Company company, List<CompanyQuestion> comQuestList) {
		boolean flag = false;
		long companyId = company.getCompanyId();
		// 则先删除原来的问题列表
		companyQuestionMapper.deleteByCompanyId(company.getCompanyId());
		if (comQuestList.size() > 0) {
			int count = companyQuestionMapper.insertQuestionList(comQuestList, companyId);
			flag = count > 0 ? true : false;
		} else {
			flag = true;
		}
		return flag;
	}

	@Override
	public Map<String, QuestionInfo> getCompanyQuestions(Company company) {
		Map<String, QuestionInfo> map = new HashMap<String, QuestionInfo>();
		List<CompanyQuestion> comQuests = companyQuestionMapper.getAllByCompany(company
				.getCompanyId());
		List<QuestionInfo> questions = questionInfoMapper.getAllByCompany(company.getCompanyId());
		// 如果公司没有选择问题则使用默认问题列表
		if (questions.size() <= 0) {
			questions = questionInfoMapper.getQuestionTemlate();
		}
		for (QuestionInfo question : questions) {
			// 装配quesntion对象，把公司自定义的问题规定的是否必填的属性赋值给question
			for (CompanyQuestion comQuest : comQuests) {
				if (comQuest.getQuestId() == question.getQuestId()) {
					question.setIsNeeded(comQuest.getIsNeeded());
					break;
				}
			}
			map.put(question.getQuestKey(), question);
		}
		return map;
	}

	@Transactional
	public boolean saveCompanyReportType(Company company, List<ReportType> rtList) {
		boolean flag = false;
		int count = reportTypeMapper.getAllByCompanyId(company.getCompanyId()).size();
		if (count > 0) {
			flag = reportTypeMapper.deleteByCompanyId(company.getCompanyId()) > 0;
		}
		for (ReportType reportType : rtList) {
			if (company != null) {
				reportType.setOwner(company);
				reportType.setIsStandard(1);
			}
			flag = reportTypeMapper.insert(reportType) > 0;
			if (!flag) {
				break;
			}
		}
		return flag;
	}

	@Override
	public List<ReportType> getCompanyReportType(Company company) {
		List<ReportType> dataList = new ArrayList<ReportType>();
		dataList = reportTypeMapper.getAllByCompanyId(company.getCompanyId());
		// 如果该公司未选择举报类型，则使用默认的主要类型
		if (dataList.size() == 0) {
			dataList = reportTypeMapper.getMainDefaultList();
		}
		return dataList;
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
		Company company = companyWholeInfo.getCompany();
		flag = companyMapper.update(companyWholeInfo.getCompany()) > 0;
		CompanyOther companyOther = companyWholeInfo.getCompanyOther();
		// 判断是否有公司其他信息
		if (companyOther != null) {
			companyOther.setCompanyId(company.getCompanyId());
			if (companyOtherMapper.getByCompanyId(company.getCompanyId()) != null) {
				flag = companyOtherMapper.update(companyWholeInfo.getCompanyOther()) > 0;
			} else {

				flag = companyOtherMapper.insert(companyOther) > 0;
			}
		}
		return flag;
	}

	@Override
	public Company getPlatformCompany() {
		// TODO Auto-generated method stub
		return companyMapper.getPlatformCompany();
	}

}
