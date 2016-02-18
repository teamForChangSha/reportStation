package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	ReportCase getByTrackingNo(String trackingNo);
}
