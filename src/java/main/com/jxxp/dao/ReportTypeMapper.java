package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.ReportType;

/**
 * 举报类型的数据层接口，用户可自定义举报类型自定义，用户不自定义类型则使用标准类型
 * 
 * @author gcx
 * 
 */
public interface ReportTypeMapper {

	/**
	 * 增加一个举报类型，定制默认举报类型
	 * 
	 * @param reportType
	 * @return
	 */
	int insert(ReportType reportType);

	/**
	 * 增加一个举报类型，公司定制一个举报类型
	 * 
	 * @param reportType
	 *            举报类型
	 * @param companyId
	 *            公司id
	 * @return
	 */
	int insertByCompany(@Param("reportType") ReportType reportType,
			@Param("companyId") Long companyId);

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

	int deleteByCompanyId(long companyId);

	/**
	 * 得到属于某个案件的所有举报类型
	 * 
	 * @param companyId
	 *            公司id
	 * @return
	 */
	List<ReportType> getAllByCompanyId(long companyId);

}
