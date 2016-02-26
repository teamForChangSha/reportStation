package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.CompanyOther;

/**
 * 
 * @author gcx
 * 
 */
public interface CompanyOtherMapper {

	/**
	 * 添加公司其他信息
	 * 
	 * @param other
	 *            其他信息
	 * @return 所保存的记录数目
	 */
	int insert(CompanyOther other);

	/**
	 * 更改公司其他信息
	 * 
	 * @param other
	 *            其他信息
	 * @return 公司其他信息
	 */
	int update(CompanyOther other);

	/**
	 * 通过公司id号找到属于该公司的其他信息
	 * 
	 * @param companyId
	 *            所属的公司id号
	 * @return 公司的其他信息
	 */
	CompanyOther getByCompanyId(long companyId);

	List<CompanyOther> getAllOthers();

	int deleteById(long companyId);

}
