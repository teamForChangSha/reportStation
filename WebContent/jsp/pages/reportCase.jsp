<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>

	<head>
		<base href="<%=basePath%>" />
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="jsp/css/common_top.css" />
		<script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<style type="text/css">
			body {
				padding-bottom: 0;
			}
			
			.navbar-text {
				float: inherit;
			}
			
			#phone-input-group {
				padding-right: 15px;
				padding-left: 15px;
			}
			
			.form-file {
				padding: 6px 0;
				opacity: 0;
				filter: alpha(opacity=0);
				cursor: pointer;
			}
			
			.btn-default {
				position: absolute;
				z-index: -1;
			}
			
			.col-xs-6 {
				margin-bottom: 10px;
			}
			
			.glyphicon-remove-circle {
				position: absolute;
				right: 16px;
				top: 2px;
				cursor: pointer;
				color: #C12E2A;
			}
			
			.xinghao {
				color: #C12E2A;
				font-weight: bold;
				padding: 4px;
			}
			
			input[type=text].form-control {
				padding: 6px 32px 6px 12px;
			}
			
			label {
				font-weight: 400;
			}
			
			.navbar-right {
				margin-right: 15px;
			}
			
			#content,
			#bom {
				margin-top: 30px;
				padding: 20px;
				border: 1px solid #DDDDDD;
				border-radius: 4px;
				box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.075);
			}
			
			.form-info {
				display: block;
				width: 100%;
				height: 34px;
				padding: 8px 12px;
				font-size: 14px;
				line-height: 1.42857;
				color: #555;
			}
			
			.bg-info,
			.bg-danger {
				border-radius: 6px;
				padding: 6px;
			}
			
			.has-error {
				color: red;
			}
		</style>
	</head>

	<body>
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation" id="top">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
				</div>

				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav pull-right">
						<li><a href="<%=basePath%>">首页</a></li>
						<li><a href="#" target="_blank">商业行为和道德准则</a></li>
						<li><a href="#">常见问题</a></li>
					</ul>
				</div>
			</div>
		</nav>

		<div class="container">
			<div class="form-horizontal">
				<div class="row">
					<div class="col-sm-2"></div>
					<div class="col-sm-8" id="content">
						<div class="form-group text-center">
							<span>(<span class="xinghao">*</span>为必填项)
							</span>
						</div>
						<div class="form-group text-center">
							<input type="checkbox" value="true" name="agreed" />
							<label><span class="xinghao">*</span>是 – 我同意这项报告相关的 <a href="#" class="btn-link" data-toggle="modal" data-target="#agreeMentPanel">条款与条件</a> </label>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>请提供一下信息</strong>
							</span>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">组织/机构：</label>
							<div class="col-sm-8">
								<span class="form-info"><strong>${companyBranch.owner.companyName
									} / ${companyBranch.branchName }</strong></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">发生事故的地址：</label>
							<div class="col-sm-8">
								<span class="form-info">${companyBranch.address }</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">省份：</label>
							<div class="col-sm-8">
								<span class="form-info">${companyBranch.province.name }</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">城市：</label>
							<div class="col-sm-8">
								<span class="form-info">${companyBranch.city.name }</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">邮编：</label>
							<div class="col-sm-8">
								<span class="form-info">${companyBranch.postCode }</span>
							</div>
						</div>
						<div class="form-group text-center">
							<div class="page-header"></div>
							<span><strong><span class="xinghao">*</span>对于这项报告，您是否希望保持匿名身份？</strong>
							</span>
							<br />
							<label class="radio-inline">
								<input type="radio" name="quest_2_value" value="true" /> 是
							</label>
							<label class="radio-inline">
								<input type="radio" checked name="quest_2_value" value="false" /> 否
							</label>
						</div>
						<form id="userInfo" action="" method="post">
							<div class="form-group text-center">
								<span><strong>如果您希望 Hyatt 知道您的身份，请完成以下内容：</strong></span>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">手机号：</label>
								<div class="input-group col-sm-5" id="phone-input-group">
									<input name="mobile" type="tel" maxlength="11" class="form-control" placeholder="实名手机号"> <span class="input-group-btn">
									<button id="getCode" class="btn btn-default" type="button">获取验证码</button>
								</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">验证码：</label>
								<div class="col-sm-3">
									<input name="code" type="text" maxlength="6" class="form-control" placeholder="手机验证码"></input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">您的姓名：</label>
								<div class="col-sm-3">
									<input name="name" type="text" class="form-control" placeholder="姓名"></input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">您的证件号：</label>
								<div class="col-sm-3">
									<select name="idName" class="form-control">
										<option>-请选择证件类型-</option>
										<option>身份证</option>
										<option>护照</option>
									</select>
								</div>
								<div class="col-sm-4">
									<input name="idNo" type="text" class="form-control" placeholder="证件号"></input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">邮箱：</label>
								<div class="col-sm-4">
									<input name="email" type="email" class="form-control" placeholder="邮箱地址"></input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">与您联系的最佳时间和方式：</label>
								<div class="col-sm-7">
									<textarea name="bestContact" type="text" rows="6" class="form-control"></textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!--具体问题-->
				<div class="row">
					<div class="col-sm-2"></div>
					<div class="col-sm-8" id="bom">
						<div class="form-group text-center">
							<h4 class="bg-info">
							<strong>${rtList} </strong>
						</h4>
						</div>
						<div id="quest_1" class="form-group text-center hidden">
							<span><strong>您是 ${companyBranch.owner.companyName } 的员工吗？</strong>
							</span>
							<br />
							<label class="radio-inline">
								<input type="radio" name="quest_1_value" value="true" /> 是
							</label>
							<label class="radio-inline">
								<input type="radio" checked="true" name="quest_1_value" value="false" /> 否
							</label>
						</div>
						<div id="quest_2" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong>请说明与此行为相关的人的身份</strong></span>
							<br /> <span> 例如：无名氏，内部审计主管，未知，晚间监督员</span>
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3"></div>
							<div class="col-sm-3">
								<span class="form-info">名字</span>
							</div>
							<div class="col-sm-3">
								<span class="form-info">职衔 </span>
							</div>
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3 control-label">#1</div>
							<div class="col-sm-3">
								<input type="text" name="quest_3_value_name1" class="form-control" />
							</div>
							<div class="col-sm-3">
								<input type="text" name="quest_3_value_position1" class="form-control" />
							</div>
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3 control-label">#2</div>
							<div class="col-sm-3">
								<input type="text" name="quest_3_value_name2" class="form-control" />
							</div>
							<div class="col-sm-3">
								<input type="text" name="quest_3_value_position2" class="form-control" />
							</div>
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3 control-label">#3</div>
							<div class="col-sm-3">
								<input type="text" name="quest_3_value_name3" class="form-control" />
							</div>
							<div class="col-sm-3">
								<input type="text" name="quest_3_value_position3" class="form-control" />
							</div>
						</div>
						<div id="quest_3" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong>您是否怀疑或知道监督或管理人员与此有关？</strong></span>
							<br />
							<label class="radio-inline">
								<input type="radio" value="true" name="quest_4_value" /> 是
							</label>
							<label class="radio-inline">
								<input type="radio" value="false" name="quest_4_value" /> 否
							</label>
							<label class="radio-inline">
								<input type="radio" name="quest_4_value" value="not" /> 不知道 / 不愿意透露
							</label>
							<p>如果选择是，请指出是谁？</p>
							<div class="col-sm-3"></div>
							<div class="col-sm-7">
								<textarea type="text" name="quest_4_value1" rows="6" class="form-control" placeholder="例如：无名氏，内部审计主管"></textarea>
							</div>
						</div>
						<div id="quest_4" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong>管理人员注意到此问题了吗？</strong></span>
							<br />
							<label class="radio-inline">
								<input type="radio" value="true" name="quest_5_value" /> 是
							</label>
							<label class="radio-inline">
								<input type="radio" value="false" name="quest_5_value" /> 否
							</label>
							<label class="radio-inline">
								<input type="radio" value="not" name="quest_5_value" /> 不知道 / 不愿意透露
							</label>
						</div>
						<div id="quest_5" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong>此事件的总体特征是什么？</strong></span>
							<br />
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3"></div>
							<div class="col-sm-7">
								<textarea type="text" name="quest_6_value" rows="6" class="form-control"></textarea>
								<p>这仅为一般说明，稍后我们将询问详细情况。</p>
							</div>
						</div>
						<div id="quest_6" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong>您估计这一行为的货币价值是多少？</strong></span>
							<br />
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3"></div>
							<div class="col-sm-2">
								<select name="quest_7_value1" class="form-control">
									<option value="currency0">USD</option>
									<option value="currency1">CAD</option>
									<option value="currency2">MXN</option>
									<option value="currency3">BRL</option>
									<option value="currency4">CNY</option>
									<option value="currency5">EUR</option>
									<option value="currency6">GBP</option>
									<option value="currency7">IDR</option>
									<option value="currency8">INR</option>
									<option value="currency9">JPY</option>
									<option value="currency10">NGN</option>
									<option value="currency11">RUB</option>
								</select>
							</div>
							<div class="col-sm-3">
								<select name="quest_7_value2" class="form-control">
									<option>USD&lt;99</option>
									<option>USD100-USD999</option>
									<option>CAD100-CAD999</option>
								</select>
							</div>
						</div>
						<div id="quest_7" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong>该事件或违规行为在哪里发生的？</strong></span>
							<br />
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3"></div>
							<div class="col-sm-7">
								<textarea type="text" rows="6" name="quest_8_value" class="form-control"></textarea>
								<p>我们了解到该事件可能没有确切的发生地点，但如果事件有某些文档或业务交易的记录，请相应指明。</p>
							</div>
						</div>
						<div id="quest_8" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong>请指出该事件发生的确切或大致时间：</strong></span>
							<br />
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3"></div>
							<div class="col-sm-7">
								<textarea type="text" name="quest_9_value" rows="6" class="form-control"></textarea>
								<p>例如：2002 年 5 月 3 日，星期二；两周前；大约一个月以前</p>
							</div>
						</div>
						<div id="quest_9" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong><span class="xinghao">*</span>您认为此问题持续了多少时间？</strong>
							</span>
							<br />
							<br />
							<div class="col-sm-4"></div>
							<div class="col-sm-4">
								<select name="quest_10_value" class="form-control">
									<option>-请选择一项-</option>
									<option>一次</option>
									<option>一周</option>
									<option>1至3个月</option>
									<option>三个月至1年</option>
									<option>一年以上</option>
									<option>不知道</option>
								</select>
							</div>
						</div>
						<div id="quest_10" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong><span class="xinghao">*</span>您是如何发现此违规行为的？</strong>
							</span>
							<br />
							<br />
							<div class="col-sm-4"></div>
							<div class="col-sm-4">
								<select name="quest_11_value1" class="form-control">
									<option>-请选择一项-</option>
									<option>这是发生在我身上</option>
									<option>我观察得知</option>
									<option>我听到的</option>
									<option>同事告知</option>
									<option>公司以外的人告知</option>
									<option>无意中听到的</option>
									<option>偶然发现了文档或文件</option>
									<option>其他</option>
								</select>
							</div>
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3"></div>
							<div class="col-sm-7">
								<p>如果选择其它，请说明：</p>
								<textarea type="text" name="quest_11_value2" rows="6" class="form-control"></textarea>
							</div>
						</div>
						<div id="quest_11" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong><span class="xinghao">*</span>您先前是否报告过这一问题？</strong>
							</span>
							<br />
							<label class="radio-inline">
								<input type="radio" value="true" name="quest_12_value" /> 是
							</label>
							<label class="radio-inline">
								<input type="radio" value="false" name="quest_12_value" /> 否
							</label>
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3"></div>
							<div class="col-sm-7">
								<p>如果答案为“是”，您是何时以何种方式向谁报告的？</p>
								<textarea type="text" name="quest_12_value1" rows="6" class="form-control"></textarea>
							</div>
						</div>
						<div id="quest_12" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong><span class="xinghao">*</span>请指出试图隐藏此问题的人以及他们隐藏采取的步骤：</strong>
							</span>
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3"></div>
							<div class="col-sm-7">
								<textarea type="text" name="quest_13_value" rows="6" class="form-control" placeholder="例如：忽略事件；更改文档；说这不是个问题；说他们会调查"></textarea>
								<p>请指出其姓名和职位。</p>
							</div>
						</div>
						<div id="quest_13" class="form-group text-center hidden">
							<div class="page-header"></div>
							<span><strong><span class="xinghao">*</span>请提供关于所谓违规行为的所有细节，包括目击者的位置以及任何对于此情形的评估和最终解决有价值的其它信息。
							</strong>
							</span>
						</div>
						<div class="form-group hidden">
							<div class="col-sm-3"></div>
							<div class="col-sm-6">
								<textarea type="text" name="quest_14_value" rows="6" class="form-control"></textarea>
							</div>
							<div class="col-sm-3">
								<p>请花一些时间尽可能提供细节，但请小心不要提供泄漏您身份的细节，除非您自己愿意。如果您是唯一的知情人，了解这一点非常重要。</p>
							</div>
						</div>
						<div class="form-group text-center">
							<div class="page-header"></div>
							<span><strong> 此外，在可能的情况下，请附上任何支持您在本次报告中所提供信息的文档。</strong></span>
							<br /> <a href="#" class="btn-link" data-toggle="modal" data-target="#upLoadPanel">单击此处上传文件</a>
							<br />
							<p>（可上传大多数常见的文件类型。）</p>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<p class="bg-danger">
								您提交报告时，将会发给您一个举报编号。请记下此编号并存放在安全的地方。在5-6个营业日后，您通过网站或热线电话返回 EthicsPoint 时，我们会要求您使用此举报编号以及您选择的密码。在5-6个营业日后返回，您可以查阅任何"后续问题"或提交关于此事件的更多信息。
							</p>
						</div>
						<div class="form-group text-center">
							<h3>
							<span class="label label-danger">当前案件编号为：${trackingNo }</span>
						</h3>
							<br /> <span><strong><span class="xinghao">*</span>请输入举报密码：</strong>
							</span>
							<p>您的密码必须相匹配而且长度不得少于四个字符。</p>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">密码<span class="xinghao">*</span></label>
							<div class="col-sm-4">
								<input type="password" name="quest_15_value" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">再次输入密码<span class="xinghao">*</span></label>
							<div class="col-sm-4">
								<input type="password" name="quest_16_value" class="form-control" />
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group">
							<div class="col-sm-4"></div>
							<div class="col-sm-4">
								<input type="button" id="submitReport" class="form-control btn btn-primary" value="提交报告" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<form id="questForm">
				<input type="text" name="quest_1" hidden="true" />
				<input type="text" name="quest_2" hidden="true" />
				<input type="text" name="quest_3" hidden="true" />
				<input type="text" name="quest_4" hidden="true" />
				<input type="text" name="quest_5" hidden="true" />
				<input type="text" name="quest_6" hidden="true" />
				<input type="text" name="quest_7" hidden="true" />
				<input type="text" name="quest_8" hidden="true" />
				<input type="text" name="quest_9" hidden="true" />
				<input type="text" name="quest_10" hidden="true" />
				<input type="text" name="quest_11" hidden="true" />
				<input type="text" name="quest_12" hidden="true" />
				<input type="text" name="quest_13" hidden="true" />
			</form>
			<input type="text" name="trackingNo" value="${trackingNo }" hidden="true" />
			<input type="text" name="rtList" value="${rtList }" hidden="true" />
		</div>
		<div class="page-header"></div>
		<p class="navbar-text text-center">Copyright © 2016-2018 用户举报系统</p>

		<!--条款对话框-->
		<div class="modal fade bs-example-modal-lg" id="agreeMentPanel" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h5 class="modal-title" id="exampleModalLabel">条款与条件</h5>
					</div>
					<div class="modal-body">
						<iframe src="jsp/pages/agreement.html" frameborder="0" width="100%" height="400px"></iframe>
					</div>
				</div>
			</div>
		</div>
		<!--上传文件对话框-->
		<div class="modal fade bs-example-modal-lg" id="upLoadPanel" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h5 class="modal-title" id="exampleModalLabel">上传文件</h5>
					</div>
					<div class="modal-body">
						<iframe src="case/showFileList.do?${trackingNo }" frameborder="0" width="100%" height="400px"></iframe>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		var questionList = []; 
		<c:forEach items = "${questionMap}" var = "question" varStatus = "i" >
			var questionJson = {};
			questionJson["quest_id"]="${question.value.questId}";
			questionJson["quest_key"]="${question.value.questKey}";
			questionJson["is_needed"]="${question.value.isNeeded}";
			
			questionList.unshift(questionJson);
		</c:forEach>
	</script>
<script src="jsp/js/reportCase.js" type="text/javascript" charset="utf-8"></script>
</html>