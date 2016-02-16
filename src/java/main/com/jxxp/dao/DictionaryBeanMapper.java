package com.jxxp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	DictionaryBean getDictionary(@Param("dictType") String dictType,
			@Param("dictName") String dictName, @Param("dictValue") int dictValue);

	/**
	 * @param dictType
	 *            类型
	 * @return 获得某个类别下的所有字典
	 */
	List<DictionaryBean> getByType(String dictType);

}
