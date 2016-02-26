package com.jxxp.service;

import java.util.List;
import java.util.Map;

import com.jxxp.pojo.CaseAttach;
import com.jxxp.pojo.CaseComment;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.QuestionInfo;
import com.jxxp.pojo.ReportAnswer;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;

/**
 * 举报案情时的相关接口<br>
 * 如果在处理过程中出现问题，则需要抛出RuntimeException，并指明对应问题
 * 
 * @author pan
 * 
 */
public interface CaseService {
	/**
	 * 提交一个举报信息<br>
	 * 每个举报需要给用户分配一个追踪号，由用户设置访问密码<br>
	 * 如果是实名举报，需要保存该举报人的信息，以便于实名举报人登录后能够找到对应的举报信息
	 * 
	 * @param caseInfo
	 *            需要提交保存的举报信息的全部信息
	 * @return 保存正常返回true，否则返回false
	 */
	boolean saveCaseInfo(ReportCase caseInfo);

	/**
	 * 给某个举报信息追加一个comment<br>
	 * 只有举报信息的举报人、被举报公司的员工，以及后台人员能够追加信息
	 * 
	 * @param comment
	 *            需要追加的comment相关信息
	 * @return 追加正常返回true，否则返回false
	 */
	boolean addCaseComment(CaseComment comment, Long caseId);

	/**
	 * 得到某个实名举报人举报的所有举报信息
	 * 
	 * @param reporter
	 *            需要查询的实名举报人信息
	 * @return 该实名举报人曾经举报的所有举报信息
	 */
	List<ReportCase> getCaseList(Reporter reporter);

	/**
	 * 得到匿名举报的某个举报信息
	 * 
	 * @param trackingNo
	 *            案件的跟踪号
	 * @param accessCode
	 *            该案件的访问密码
	 * @return 返回匹配的举报信息，若未能匹配，则返回null
	 */
	ReportCase getReportCase(String trackingNo, String accessCode);

	/**
	 * 根据rcID得到某个举报信息
	 * 
	 * @param rcId
	 *            案件的编号
	 *            
	 * @return 返回匹配的举报信息，若未能匹配，则返回null
	 */
	ReportCase getReportCaseById(long rcId);
	
	/**
	 * 获得一个跟踪号，跟踪号根据被举报对象的相关信息生成<br>
	 * 跟踪号不能与系统已有的跟踪号重复
	 * 
	 * @param company
	 *            被举报对象信息
	 * @return 得到一个新的跟踪号，跟踪号不能与系统已有的跟踪号重复
	 */
	String getNewTrackingNo(Company company);

	/**
	 * 保存案例信息以及相关数据
	 * 
	 * @param reporter
	 *            实名举报人信息
	 * @param reportCase
	 *            案例信息
	 * @param answerList
	 *            问题回复集合           
	 * @return 返回保存结果，保存成功true，失败false
	 */
	boolean saveCase(Reporter reporter, ReportCase reportCase,
			List<ReportAnswer> answerList);

	/**
	 * 查询某个公司的举报信息，
	 * 
	 * @param Company
	 *            公司对象           
	 * @return 返回ReportCase列表，失败返回的列表长度为0
	 */
	List<ReportCase> getCaseByCompany(Company company,Map<String,String> map);
	
	/**
	 * 修改一个举报信息<br>
	 * 
	 * @param caseInfo
	 *            需要修改的举报信息的全部信息
	 * @return 保存正常返回true，否则返回false
	 */
	boolean updateCaseInfo(ReportCase caseInfo);
}
