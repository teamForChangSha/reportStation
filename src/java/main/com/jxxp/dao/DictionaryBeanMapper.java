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
	 * @param dictValue
	 *            字典值
	 * @return 获取得到的字典对象
	 */
	DictionaryBean getDictionary(@Param("dictType") String dictType,
			@Param("dictValue") Integer dictValue);

	/**
	 * @param dictType
	 *            类型
	 * @return 获得某个类别下的所有字典
	 */
	List<DictionaryBean> getByType(String dictType);

	int insert(DictionaryBean dctionary);

	/**
	 * 根据类型和值删除某个字典类型
	 * 
	 * @param dictType
	 * @param dictValue
	 * @return
	 */
	int delByTypeAndValue(@Param("dictType") String dictType, @Param("dictValue") Integer dictValue);

}
