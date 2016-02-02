package com.jxxp.dao;

import java.util.List;

import com.jxxp.pojo.AreaInfo;

/**
 * @author gcx
 * 
 */

public interface AreaInfoMapper {
	/**
	 * 保存一个行政区域
	 * 
	 * @param AreaInfo
	 * @return 成功保存的政区域个数
	 */
	int insert(AreaInfo AreaInfo);

	/**
	 * 更新行政区域的信息
	 * 
	 * @param AreaInfo
	 * @return 成功更新的政区域个数
	 */

	int update(AreaInfo AreaInfo);

	/**
	 * 通过id获得区域信息（省/市区）
	 * 
	 * @param areaId
	 * @return 行政区域
	 */

	AreaInfo findById(long areaId);

	/**
	 * @param name
	 *            行政区域名字
	 * @return 某个行政区域
	 */
	AreaInfo findByName(String name);

	/**
	 * 通过上级行政区域获取下级行政区域（目前：是通过省获取属于该省的所有市区）
	 * 
	 * @param AreaInfo
	 *            父级行政区域
	 * @return 某上级行政区域的所有下级行政区域
	 */
	List<AreaInfo> getAreaByParent(long parentId);

	/**
	 * 通过级别获得行政区区域
	 * 
	 * @param level
	 *            级别
	 * @return 行政区域
	 */
	List<AreaInfo> getAllByLevel(int level);

	/**
	 * 删除
	 * 
	 * @param cId
	 * @return 删除的记录数目
	 */
	int deleteById(long areaId);

	List<AreaInfo> getAll();
}
