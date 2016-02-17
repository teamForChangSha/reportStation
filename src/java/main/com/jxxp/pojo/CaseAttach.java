package com.jxxp.pojo;

import java.awt.Image;

/**
 * 用于表示案件的附件，附件可以是图片、音频、视频<br>
 * 网站目前未规划播放附件，因此仅保存附件的信息
 * 
 * @author pan
 * 
 */
public class CaseAttach {
	private long caId;
	/**
	 * 案例追踪号，与案例关联
	 */
	private String trackingNo;
	/**
	 * 附件名称，可由用户输入，否则用文件名代替
	 */
	private String attachName;

	/**
	 * 访问附件的url（含文件名），此url的访问需要验证用户权限<br>
	 * 附件当前仅当可由举报人、被举报公司、后台管理员可访问到
	 */
	private String attachUrl;

	/**
	 * 附件在文件系统中的保存路径（不含文件名）
	 */
	private String attachPath;

	/**
	 * 附件的扩展名，目前不判断附件类型，仅使用扩展名来初步确认属于什么类型
	 */
	private String attachExt;

	/**
	 * 附件的文件名称
	 */
	private String attachFileName;

	/**
	 * 图片和视频的缩略图，扩展用，第一版不开发功能
	 */
	private Image thumb;

	/**
	 * 附件大小，单位为字节
	 */
	private long attachSize;
	/**
	 * 附件状态，0为临时文件，1为正常文件
	 */
	private int state;
	/**
	 * 附件描述
	 */
	private String desc;
	
	public long getCaId() {
		return caId;
	}

	public void setCaId(long caId) {
		this.caId = caId;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getAttachUrl() {
		return attachUrl;
	}

	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public String getAttachExt() {
		return attachExt;
	}

	public void setAttachExt(String attachExt) {
		this.attachExt = attachExt;
	}

	public String getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public Image getThumb() {
		return thumb;
	}

	public void setThumb(Image thumb) {
		this.thumb = thumb;
	}

	public long getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(long attachSize) {
		this.attachSize = attachSize;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
