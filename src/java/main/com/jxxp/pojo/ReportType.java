package com.jxxp.pojo;

/**
 * 用于表示一个举报类型，网站本身有一个标准的举报类型列表，若客户不定制，则使用标准的类型<br>
 * 若客户定制举报类型，则不管是否使用标准的举报类型中的某些部分，都将所定制的举报类型作为客户自定义的举报类型列表
 * 
 * @author pan
 * 
 */
public class ReportType {
	private long rtId;

	/**
	 * 用于表示该举报类型是网站标准举报类型还是用户自定义类型<br>
	 * true为网站标准举报类型
	 */
	private boolean isStandard;

	/**
	 * 当isStandard为true时，此字段值可忽略<br>
	 * 否则为当前举报类型项属于哪个公司
	 */
	private Company owner;

	/**
	 * 该举报类型的标题
	 */
	private String rtTitle;

	/**
	 * 该举报类型的描述信息
	 */
	private String rtDesc;

	public long getRtId() {
		return rtId;
	}

	public void setRtId(long rtId) {
		this.rtId = rtId;
	}

	public boolean isStandard() {
		return isStandard;
	}

	public void setStandard(boolean isStandard) {
		this.isStandard = isStandard;
	}

	public Company getOwner() {
		return owner;
	}

	public void setOwner(Company owner) {
		this.owner = owner;
	}

	public String getRtTitle() {
		return rtTitle;
	}

	public void setRtTitle(String rtTitle) {
		this.rtTitle = rtTitle;
	}

	public String getRtDesc() {
		return rtDesc;
	}

	public void setRtDesc(String rtDesc) {
		this.rtDesc = rtDesc;
	}

}
