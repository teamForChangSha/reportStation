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

	Company findByName(String companyName);

	/**
	 * 通过公司的名字进行模糊查询
	 * 
	 * @param companyName
	 * @return 匹配名字的公司集合
	 */
	List<Company> getAllByName(String companyName);

	/**
	 * 管理员作为一个平台公司，该公司特殊处理，companyType=0的为平台公司
	 * 
	 * @return
	 */
	Company getPlatformCompany();

	Company getById(long companyId);

	List<Company> getAllCompany();

	int deleteById(long CompanyId);

}
