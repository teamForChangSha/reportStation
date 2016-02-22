package com.jxxp.service;

import java.util.List;

import com.jxxp.pojo.CaseAttach;

/**
 * 
 * @author cj
 * 
 */
public interface CaseAttachService {
	/**
	 * 获取上传附件列表
	 * 
	 * @param trackingNo
	 *            案件的跟踪号
	 *            
	 * @return 返回附件列表，若未能匹配，则返回空列表
	 */
	List<CaseAttach> getCaseAttachByTrackingNo(String trackingNo);
	
	/**
	 * 增加一个附件信息<br>
	 * 
	 * @param comment
	 *            需要追加的CaseAttach相关信息
	 * @return 增加正常返回true，否则返回false
	 */
	boolean addCaseAttach(CaseAttach caseAttach);
	
}
