package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.ReportType;

/**
 * 举报类型的数据层接口，用户可自定义举报类型自定义，用户不自定义类型则使用标准类型
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
	 * 获取默认问题类型列表
	 * 
	 * @return
	 */
	List<ReportType> getAllStandard();

}
