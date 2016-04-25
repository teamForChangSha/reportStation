package com.jxxp.service;

import com.jxxp.pojo.ClientCompany;

/**
 * @author gcx
 * 
 */
public interface ClientCompanyService {
	/**
	 * 添加一个客户公司
	 * 
	 * @param client
	 * @return
	 */
	public boolean addClientCompany(ClientCompany client);

	public boolean delClientCompany(Long companyId);

	public boolean updateClientCompany(ClientCompany client);

}
