package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.CaseAttach;

public interface CaseAttachMapper {
	/**
	 * 保存案件的附件
	 * 
	 * @param CaseAttach
	 * @return
	 */
	int insert(CaseAttach CaseAttach);

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
	 * 查询
	 * 
	 * @param rcId
	 *            案件id
	 * @return 获得某个案件的所有附件
	 */
	List<CaseAttach> getAllByCaseId(Long rcId);

	/**
	 * 删除
	 * 
	 * @param caId
	 *            附件id
	 * @return
	 */
	int deleteById(long caId);
}
