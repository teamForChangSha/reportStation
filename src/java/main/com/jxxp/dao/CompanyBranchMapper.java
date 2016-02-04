package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.AreaInfo;
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
	 * 获取某地的公司分支机构信息，若地区为空则获取某个公司下的所有分支机构<br>
	 * 公司的总部也算作一个分支机构
	 * 
	 * @param area
	 *            地区
	 * @param company
	 *            公司
	 * @return
	 */
	List<CompanyBranch> getAllByArea(@Param("area") AreaInfo area,
			@Param("company") Company company);
	/**
	 * 获取某地的公司分支机构信息，若地区为空则获取某个公司下的所有分支机构<br>
	 * 公司的总部也算作一个分支机构
	 * 
	 * @param area
	 *            地区
	 * @param company
	 *            公司
	 * @return
	 */

	CompanyBranch getById(long branchId);

	int deleteById(long branchId);

}
