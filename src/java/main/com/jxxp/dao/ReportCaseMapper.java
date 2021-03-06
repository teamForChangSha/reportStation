package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.comms.web.Page;
import com.jxxp.pojo.ReportCase;
import com.jxxp.pojo.Reporter;
import com.jxxp.pojo.User;

/**
 * @author gcx
 * 
 */
public interface ReportCaseMapper {

	int insert(ReportCase reportCase);

	/**
	 * 通过案件号获得案件
	 * 
	 * @param cId
	 *            案件号
	 * @return 案件
	 */
	ReportCase getById(long rcId);

	/**
	 * 通过案件追踪码和密码查询
	 * 
	 * @param trackingNo
	 * @param accessCode
	 * @return 案件
	 */
	ReportCase findByNo(@Param("trackingNo") String trackingNo,
			@Param("accessCode") String accessCode);

	/**
	 * 更新案件 比如更改举报的状态、追加备注等操作
	 * 
	 * @param reportCase
	 * @return
	 */

	int update(ReportCase reportCase);

	/**
	 * 通过举报人获得该举报人提交过的所有案件
	 * 
	 * @param report
	 *            举报人
	 * @return
	 */
	List<ReportCase> getCaseByReporter(Reporter reporter);

	/**
	 * 获得属于该企业用户下的所有举报案件
	 * 
	 * @param user
	 *            企业用户
	 * @return 案件集合
	 */
	List<ReportCase> getCaseByUser(User user);

	/**
	 * 取得所有案件
	 * 
	 * @return 所有案件
	 */
	List<ReportCase> getAllCase();

	/**
	 * 删除举报
	 * 
	 * @param cId
	 * @return
	 */
	int deleteById(long rcId);

	/**
	 * 用于模糊查询案件，如果公司companyId=1则是平台管理公司，将查询所有案件。否则查询该companyId的案件
	 * 
	 * @return
	 */
	/**
	 * @param page
	 * @param companyId
	 *            公司id
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param keyWord
	 *            关键字，答案中含有的关键字
	 * @param rtList
	 *            问题类型
	 * @param caseState
	 * @param rcId
	 * @return
	 */
	List<ReportCase> searchByKeysWithPage(@Param("page") Page page,
			@Param("companyId") long companyId, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("keyWord") String keyWord,
			@Param("rtList") String rtList, @Param("caseState") Integer caseState,
			@Param("trackingNo") String trackingNo);

	/**
	 * @param companyId
	 *            公司id
	 * @return 被举报公司的案件集合
	 */
	List<ReportCase> getAllByCompanyId(long companyId);

	/**
	 * 获取客户公司的案件
	 * 
	 * @param isClient
	 * @return
	 */
	List<ReportCase> getCaseByIsClient(@Param(value = "isClient") int isClient);

}
