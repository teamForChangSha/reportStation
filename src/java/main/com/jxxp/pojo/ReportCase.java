package com.jxxp.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用于表达一个举报案件的信息
 * 
 * @author pan
 * 
 */
public class ReportCase {
	private long rcId;

	/**
	 * 案件所属于的举报类型列表（举报类型由用户选择，可多选）
	 */
	private List<ReportType> rtList;

	/**
	 * 举报创建时间
	 */
	private Date createTime;

	/**
	 * 案件所属公司
	 */
	private Company company;

	/**
	 * 案件所属分支机构
	 */
	private CompanyBranch branch;

	/**
	 * 若案件是实名举报，则此字段为实名举报人
	 */
	private Reporter reporter;

	/**
	 * 当前处理公司，目前平台允许企业将问题处理转交给管理平台处理，因此handler可能会发生改变
	 */
	private Company currentHandler;

	/**
	 * 案件状态，取值见字典，字典类型为case.state
	 */
	private int caseState;

	/**
	 * 案件状态最后修改的时间
	 */
	private Date stateChanged;

	/**
	 * 案件访问密码，匿名举报用户凭此密码和trackingNo一起来查看案件信息
	 */
	private String accessCode;

	/**
	 * 案件追踪码，不直接使用案件编号对外暴露实现细节
	 */
	private String trackingNo;

	/**
	 * 案件所属的附件列表
	 */
	private List<CaseAttach> attachList;

	/**
	 * 案件所属的追加列表
	 */
	private List<CaseComment> commentList;

	/**
	 * 案件所属的用户输入内容映射表<br>
	 * 以Map形式保存用户输入，map的key为ReportDetail中的key，值为对应的ReportDetail
	 */
	private Map<String, ReportDetail> detail;

	/**
	 * 本案件的状态改变列表
	 */
	private List<CaseChangeLog> changeList;

	/**
	 * 该公司所选取的问题列表
	 */
	private List<QuestionInfo> questList;
}
