package com.jxxp.service;

import java.util.List;

import com.jxxp.pojo.AreaInfo;

/**
 * 与地域相关的服务，目前支持省市两级<br>
 * 如果在处理过程中出现问题，则需要抛出RuntimeException，并指明对应问题
 * 
 * @author pan
 * 
 */
public interface AreaService {

	/**
	 * 获取国内所有的省信息
	 * 
	 * @return 返回所有的省信息
	 */
	List<AreaInfo> getAllProvince();

	/**
	 * 获取省内的所有市信息
	 * 
	 * @param province
	 *            要查询的省份信息
	 * 
	 * @return 返回该省下所有的市信息
	 */
	List<AreaInfo> getProvinceCity(AreaInfo province);

	/**
	 * 获取某一个省份的信息
	 * 
	 * @param provinceId
	 *            要查询的省份id
	 * @return 返回查询到的省份信息，若未查询到，则返回null
	 */
	AreaInfo getProvince(long provinceId);

	/**
	 * 获取某一个市的信息
	 * 
	 * @param cityId
	 *            要查询的市id
	 * @return 返回查询到的市信息，若未查询到，则返回null
	 */
	AreaInfo getCity(long cityId);

	/**
	 * 获取公司旗下有分支机构的省列表的信息，根据公司ID
	 * 
	 * @param companyId
	 *            要查询的公司id
	 * @return 返回查询到的省份信息，若未查询到，则返回空列表
	 */
	List<AreaInfo> getProvinceByCompanyId(long companyId);

	/**
	 * 获取公司旗下有分支机构的市列表的信息，根据公司ID以及省份ID
	 * 
	 * @param companyId
	 *            要查询的公司id
	 * @param parentId
	 * 			      省份ID           
	 * @return 返回查询到的省份信息，若未查询到，则返回空列表
	 */
	List<AreaInfo> getCityByCompanyId(long companyId,long parentId);
	
	/**
	 * 通过上一级区域获取下一集区域
	 * 
	 * @param parent
	 *            上一级区域
	 * @return 所有下一级区域
	 */
	List<AreaInfo> getByParent(AreaInfo parent);

}
