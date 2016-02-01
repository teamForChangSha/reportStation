package com.jxxp.pojo;

/**
 * 数据字典，使用dictType标明字典值分类，标记其的不同作用<br>
 * 一个bean代表一个字典值项
 * 
 * @author pan
 * 
 */
public class DictionaryBean {
	private long dictId;

	/**
	 * 字典值类型，需要使用字典取值的属性需注明使用的字典类型，且不能与库中已有类型相同<br>
	 * 字典值类型中可以有.以增加可读性
	 */
	private String dictType;

	/**
	 * 字典值的名称，用于显示
	 */
	private String dictName;

	/**
	 * 字典值的实际取值，用于程序中的传递
	 */
	private String dictValue;

	/**
	 * 字典值的显示顺序，用于需要指定显示顺序的场景
	 */
	private int displayOrder;

	/**
	 * 当前字典值的描述信息，用于对名称的解释
	 */
	private String dictDesc;

	public long getDictId() {
		return dictId;
	}

	public void setDictId(long dictId) {
		this.dictId = dictId;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getDictDesc() {
		return dictDesc;
	}

	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}

}
