package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;

/**
 * 
 * @author gcx
 * 
 */
public interface CompanyBranchMapper {

	/**
	 * 添加一个公司的分支机构
	 * 
	 * @param branch
	 *            分支机构
	 * @return
	 */
	int insert(CompanyBranch branch);

	/**
	 * 更改分支机构信息
	 * 
	 * @param branch
	 *            分支机构
	 * @return
	 */
	int update(CompanyBranch branch);

	/**
	 * 获取某个公司下的所有分支机构
	 * 
	 * @param companyId
	 *            公司id号
	 * @return
	 */
	List<CompanyBranch> getAllByCompany(Company companyId);

	CompanyBranch findById(long branchId);

	int deleteById(long branchId);

}
