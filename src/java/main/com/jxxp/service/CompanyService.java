package com.jxxp.service;

import java.util.List;
import java.util.Map;

import com.jxxp.pojo.AreaInfo;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.pojo.CompanyWholeInfo;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportType;

/**
 * 此文件包含与公司相关的一些接口<br>
 * 如果在处理过程中出现问题，则需要抛出RuntimeException，并指明对应问题
 * 
 * @author pan
 * 
 */
public interface CompanyService {
	/**
	 * 保存公司的相关信息
	 * 
	 * @param company
	 *            欲保存的公司信息
	 * @return 保存成功返回true，否则返回false
	 */
	boolean saveCompanyInfo(Company company);

	/**
	 * 保存完整的公司信息，公司的其他信息以及以后可能添加的更多公司信息，不再提供单独的业务接口进行保存<br>
	 * 统一以此接口保存公司的所有信息（即便基本相关信息或其他相关信息没有发生变化）
	 * 
	 * @param wholeCompany
	 *            要保存的公司完整信息，将本数据完全覆盖到已有数据，不做其他处理
	 * @return 所有信息保存成功则返回true，否则返回false，返回false需要恢复为之前的数据
	 */
	boolean saveWholeCompany(CompanyWholeInfo wholeCompany);

	/**
	 * 获得公司的基本信息
	 * 
	 * @param name
	 *            公司名称，后期若支持相似名字合并，则可以是合并后的任意一个公司名称
	 * @return 返回查询到的公司基本信息
	 */
	Company getCompany(String name);

	/**
	 * 获得公司的所有信息，包括基本信息、其余信息，以及未来可能添加的更多信息
	 * 
	 * @param name
	 *            公司名称，后期若支持相似名字合并，则可以是合并后的任意一个公司名称
	 * @return 返回查询到的公司完整信息
	 */
	CompanyWholeInfo getCompanyWhole(String name);

	/**
	 * 保存公司选定的举报问题<br>
	 * 公司只能在平台设定的所有举报问题中选择全部或者其中的一部分，暂不支持自行设定举报问题<br>
	 * 本接口用于设置公司的所有举报问题，会删掉公司原有的举报问题，仅保存本次设置的举报问题<br>
	 * 考虑到实际情况，暂不提供不删除原有举报问题而增加或删除举报问题的接口，仅提供此接口就能够满足要求
	 * 
	 * @param company
	 *            需要保存问题的公司
	 * @param questList
	 *            公司选定的问题列表
	 * @return 全部列表保存成功则返回true，否则返回false，返回false需要回滚数据，不能对原有数据发生改变
	 */
	boolean saveCompanyQuestions(Company company, List<QuestionInfo> questList);

	/**
	 * 获取某个公司选定的举报问题<br>
	 * 
	 * @param company
	 *            需要获取举报问题的公司
	 * @return 获取的举报问题以map格式返回，map的key是question的questKey，值是对应的questionInfo
	 */
	Map<String, QuestionInfo> getCompanyQuestions(Company company);

	/**
	 * 保存公司设置的举报类型列表<br>
	 * 举报类型可随公司的需求自由设置，公司员工和平台管理员都可以设置公司的举报类型<br>
	 * 本接口用于设置公司的所有举报类型，会删掉公司原有的举报类型，仅保存本次设置的举报类型
	 * 
	 * @param company
	 *            需要设置举报类型的公司
	 * @param rtList
	 *            公司设置的举报类型信息列表
	 * @return 全部举报类型信息保存成功则返回true，否则返回false，返回false需要回滚数据，不能对原有数据发生改变
	 */
	boolean saveCompanyReportType(Company company, List<ReportType> rtList);

	/**
	 * 给公司增加一个举报类型<br>
	 * 不删除原有举报类型数据
	 * 
	 * @param company
	 *            需要增加举报类型的公司
	 * @param reportType
	 *            需要增加的举报类型
	 * @return 增加成功则返回true，否则返回false
	 */
	boolean addCompanyReportType(Company company, ReportType reportType);

	/**
	 * 获取公司设置的举报类型
	 * 
	 * @param company
	 * @return
	 */
	List<ReportType> getCompanyReportType(Company company);

	/**
	 * 获取某地的公司分支机构信息<br>
	 * 公司的总部也算作一个分支机构
	 * 
	 * @param area
	 *            欲查询分支机构的信息
	 * @param company
	 *            与查询分支机构的公司
	 * @return 返回查询到的公司分支机构的信息列表，无分支机构则返回空列表
	 */
	List<CompanyBranch> getCompanyBranchByArea(AreaInfo area, Company company);
	
	/**
	 * 获取所有公司列表信息<br>
	 * 
	 * @return 返回查询到的公司信息列表
	 */
	List<Company> getAllCompanyList();
	

}
