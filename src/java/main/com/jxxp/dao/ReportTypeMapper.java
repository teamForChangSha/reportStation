package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.ReportType;

/**
 * 举报类型的数据层接口，用户可自定义举报类型自定义，用户自定义的类型companyId不为空，用户不自定义类型则使用标准类型，标准类型包括主要类型和次要类型
 * (isStandard=0表示主要类型， isStandard=1表示次要类型，标准类型的companyId为空);
 * 
 * @author gcx
 * 
 */
public interface ReportTypeMapper {

	/**
	 * 增加一个举报类型
	 * 
	 * @param reportType
	 * @return
	 */
	int insert(ReportType reportType);

	/**
	 * 更改某个举报类型
	 * 
	 * @param reportType
	 * @return
	 */
	int update(ReportType reportType);

	/**
	 * @param rtId
	 *            举报类型id
	 * @return
	 */
	ReportType getById(long rtId);

	int deleteById(long rtId);

	/**
	 * 删除公司所有问题类型
	 * 
	 * @param companyId
	 * @return
	 */
	int deleteByCompanyId(long companyId);

	/**
	 * 得到属于某个案件的所有举报类型
	 * 
	 * @param companyId
	 *            公司id
	 * @return
	 */
	List<ReportType> getAllByCompanyId(long companyId);

	/**
	 * 获取标准（默认）问题类型列表(包含主要类型和次要类型)，此时companyId=null
	 * 
	 * @return 默认问题类型列表
	 */
	List<ReportType> getAllDefualt();

	/**
	 * 默认的问题类型列表中，isStandard=0表示主要类型，isStandard=1表示次要类型，默认问题类型中companyId= null
	 * 
	 * @return 默认问题类型列表中的主要类型
	 */
	List<ReportType> getMainDefaultList();

}
