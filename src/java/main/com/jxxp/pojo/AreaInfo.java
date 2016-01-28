package com.jxxp.pojo;

/**
 * 用于表达省市地区，有可能扩充至国家
 * 
 * @author pan
 * 
 */
public class AreaInfo {
	private long areaId;

	/**
	 * 所属级别，省为同一级别，市为同一级别，直辖市为省一级
	 */
	private int level;

	/**
	 * 地区名称
	 */
	private String name;

	/**
	 * 上级行政单位的id，不直接使用对象，因为通常的用法是从省到市，暂不考虑反过来索引
	 */
	private long parentId;

}
