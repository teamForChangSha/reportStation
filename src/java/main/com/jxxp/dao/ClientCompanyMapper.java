package com.jxxp.dao;

import com.jxxp.pojo.ClientCompany;

public interface ClientCompanyMapper {
	int insert(ClientCompany record);

	/**
	 * 通过公司id获取客户公司
	 * 
	 * @param companyId
	 * @return
	 */
	ClientCompany getClientCompanyById(Long companyId);
}