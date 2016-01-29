package com.jxxp.dao;

import java.util.List;

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
	ReportCase findById(long rcId);

	/**
	 * 通过案件追踪码查询
	 * 
	 * @param trackingNo
	 *            案件追踪码
	 * @return 案件
	 */
	ReportCase findByNo(String trackingNo);

	/**
	 * 更新案件 比如更改举报的状态、追加备注等操作
	 * 
	 * @param reportCase
	 * @return
	 */

	int update(ReportCase reportCase);

	/**
	 * 通过举报人获得该举报人提交过的所有案件 TODO===讨论 参数为 id还是对象好些
	 * 
	 * @param report
	 *            举报人
	 * @return
	 */
	List<ReportCase> getCaseByReport(Reporter report);

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

}
