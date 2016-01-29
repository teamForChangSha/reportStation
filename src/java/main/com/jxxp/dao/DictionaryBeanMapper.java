package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.DictionaryBean;

/**
 * 
 * @author gcx
 * 
 */
public interface DictionaryBeanMapper {

	/**
	 * 通过标识
	 * 
	 * @param dictType
	 *            类型
	 * @param dictName
	 *            字典名称
	 * @return 获取得到的字典对象
	 */
	DictionaryBean getDictionary(Long dictType, String dictName);

	/**
	 * @param dictType
	 * @return 获得某个类别下的所有字典
	 */
	List<DictionaryBean> findByType(Long dictType);

}
