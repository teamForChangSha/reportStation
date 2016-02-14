package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxxp.pojo.CaseAttach;

/**
 * @author gcx
 * 
 */
public interface CaseAttachMapper {

	/**
	 * 由于在生成案件前，案件追踪号由程序生成，并且案件追踪号唯一 所以附件信息中保存案件追踪号来标识属于哪一个案件信息
	 * 为了防止循环，附件信息实体类中没有案件的对象这个属性，因此在保存附件信息的时候应该指定案件追踪号来标识附件属于哪个案件
	 * 
	 * @param caseAttach
	 *            附件对象
	 * @param trackingNo
	 *            案件追踪号
	 * @return
	 */
	int insert(@Param("caseAttach") CaseAttach caseAttach, @Param("trackingNo") String trackingNo);

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
