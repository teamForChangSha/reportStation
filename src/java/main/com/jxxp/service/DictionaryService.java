package com.jxxp.service;

import java.util.List;

import com.jxxp.pojo.DictionaryBean;

/**
 * 
 * @author cj 字典类Service接口
 * 
 */
public interface DictionaryService {

	/***
	 * 根据类型和值获取中文描述
	 * 
	 * @author cj
	 * @param dictType
	 *            类型
	 * @param dictValue
	 *            值
	 * @return 匹配的字典对象，否则返回null
	 */
	DictionaryBean getDictName(String dictType, Integer dictValue);

	/***
	 * 根据类型获取字典类集合
	 * 
	 * @author cj
	 * @param dictType
	 *            类型
	 * @return 匹配的字典类集合，否则返回空列表
	 */
	List<DictionaryBean> getDictTypeList(String dictType);
}
