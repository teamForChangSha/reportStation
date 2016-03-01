package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.CaseAttach;

/**
 * @author gcx
 * 
 */
public interface CaseAttachMapper {

	int insert(CaseAttach caseAttach);

	/**
	 * 修改附件 目前不需要更改附件信息，保留
	 * 
	 * @param CaseAttach
	 * @return
	 */
	int update(CaseAttach CaseAttach);

	/**
	 * 通过id获得案件的附件
	 * 
	 * @param caId
	 *            附件id
	 * @return 附件
	 */
	CaseAttach getById(long caId);

	/**
	 * 由于在生成案件前，案件追踪号由程序生成，并且案件追踪号唯一 所以附件信息中保存案件追踪号来标识属于哪一个案件信息
	 * 根据案件追踪号来获取属于该案件的所有附件信息
	 * 
	 * @param trackingNo
	 *            案件追踪号trackingNo
	 * @return 获得某个案件的所有附件
	 */
	List<CaseAttach> getAllByTrackingNo(String trackingNo);

	/**
	 * 删除
	 * 
	 * @param caId
	 *            附件id
	 * @return
	 */
	int deleteById(long caId);
}
