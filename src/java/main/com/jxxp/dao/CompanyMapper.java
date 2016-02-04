package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.Company;

/**
 * @author gcx
 * 
 */
public interface CompanyMapper {

	/**
	 * 添加一个业务公司
	 * 
	 * @param Company
	 * @return
	 */
	int insert(Company Company);

	/**
	 * 对公司信息做修改
	 * 
	 * @param Company
	 * @return
	 */
	int update(Company Company);

	/**
	 * 通过公司名字获取公司信息
	 * 
	 * @param companyName
	 *            公司名字
	 * @return
	 */
	Company findByName(String companyName);

	Company getById(long CompanyId);

	List<Company> getAllCompany();

	int deleteById(long CompanyId);

}
