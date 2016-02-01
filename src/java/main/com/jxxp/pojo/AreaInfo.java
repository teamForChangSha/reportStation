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

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

}
