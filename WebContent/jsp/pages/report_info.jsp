<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
		<meta charset="utf-8">
		<link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="jsp/css/common_top.css" />
		<script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<title></title>
		<style type="text/css">
			.navbar-text {
				float: inherit;
			}
			
			label {
				font-weight: 400;
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
			
			#content {
				margin-top: 30px;
				padding: 20px;
				border: 1px solid #DDDDDD;
				border-radius: 4px;
				box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.075);
			}
		</style>
	</head>

	<body>
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation" id="top">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
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
			<div class="row">
				<div class="col-sm-2">

				</div>
				<div class="col-sm-8 form-horizontal" id="content">
					<div class="form-group text-center">
						<h3><span class="label label-danger" id="caseId">案件编号：${reportCase.trackingNo }</span></h3>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">组织/机构：</label>
						<div class="col-sm-8">
							<span class="form-info" id="organization"><strong>${reportCase.branch.owner.companyName }/ ${reportCase.branch.branchName }</strong></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">发生事故的地址：</label>
						<div class="col-sm-8">
							<span class="form-info" id="caseAddr">${reportCase.branch.address }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">省份：</label>
						<div class="col-sm-8">
							<span class="form-info" id="caseProvince">${reportCase.branch.province.name }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">城市：</label>
						<div class="col-sm-8">
							<span class="form-info" id="caseCity">${reportCase.branch.city.name }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">邮编：</label>
						<div class="col-sm-8">
							<span class="form-info">${reportCase.branch.postCode }</span>
						</div>
					</div>
					<div class="page-header"></div>
					<c:forEach items = "${questionAnswerList}" var = "quest" varStatus = "i" >
						<div class="form-group">
						<label class="col-sm-4 control-label">${quest.question }：</label>
						<div class="col-sm-8">
							<span class="form-info">
								<c:if test="${quest.questValue=='true'}">是</c:if>
								<c:if test="${quest.questValue=='false'}">不是</c:if>
							</span>
						</div>
					</div>
					</c:forEach>
					<%-- <div class="form-group">
						<label class="col-sm-4 control-label">是否为${reportCase.branch.owner.companyName }的员工：</label>
						<div class="col-sm-8">
							<span class="form-info" id="isEmployees">是</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">姓名：</label>
						<div class="col-sm-8">
							<span class="form-info">张三</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">证件号：</label>
						<div class="col-sm-8">
							<span class="form-info">430421000000000000</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">手机号：</label>
						<div class="col-sm-8">
							<span class="form-info">18600000000</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">最佳联系方式：</label>
						<div class="col-sm-8">
							<span class="form-info">早上09：00到11：00或者18：00到21：00</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">事件相关人员：</label>
						<div class="col-sm-8">
							<span class="form-info">张三（总经理），李四（财务经理），王五（市场经理）</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">相关监管人员/管理人员：</label>
						<div class="col-sm-8">
							<span class="form-info">无</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">管理人员是否注意到此事：</label>
						<div class="col-sm-8">
							<span class="form-info">是</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">此事件总体特征：</label>
						<div class="col-sm-8">
							<span class="form-info">时间总体特征描述</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">此事件预计价值：</label>
						<div class="col-sm-8">
							<span class="form-info">USD999-USD9999</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">该事件发生的时间：</label>
						<div class="col-sm-8">
							<span class="form-info">2015-12-01</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">该事件持续的时间：</label>
						<div class="col-sm-8">
							<span class="form-info">1至3个月</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">此事件的获取途径：</label>
						<div class="col-sm-8">
							<span class="form-info">我观察得知</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">之前是否报告过此事件：</label>
						<div class="col-sm-8">
							<span class="form-info">向管理人员反馈过此事件</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">试图隐藏此问题的人以及他们隐藏采取的步骤：</label>
						<div class="col-sm-8">
							<span class="form-info">张三不愿告诉李四李四不愿告诉王五</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">此事件的具体描述细节：</label>
						<div class="col-sm-8">
							<span class="form-info">张三不愿告诉李四李四不愿告诉王五</span>
						</div>
					</div> --%>
					<div class="page-header"></div>
					<div class="form-group">
						<label class="col-sm-4 control-label">其他材料：</label>
						<div class="col-sm-8">
							<c:forEach items = "${reportCase.attachList}" var = "attach" varStatus = "i" >
								<p><a href="${attach.attachUrl }">${attach.attachFileName }</a></p>
							</c:forEach>
						</div>
					</div>
					<div class="page-header"></div>
					<div class="form-group">
						<label class="col-sm-4 control-label">案件状态：</label>
						<div class="col-sm-8">
							<span class="form-info" id="caseState">${reportCase.caseState }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">案件处理流程：</label>
						<div class="col-sm-8">
							<c:forEach items = "${reportCase.changeList}" var = "change" varStatus = "i" >
								<span class="form-info">${change.operator }
									<time class="pull-right"><fmt:formatDate value="${change.changeTime}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/></time>
								</span>
							</c:forEach>
						</div>
					</div>
					<c:forEach items = "${reportCase.commentList}" var = "comment" varStatus = "i" >
						<div class="form-group">
							<label class="col-sm-4 control-label">${comment.owner }：</label>
							<div class="col-sm-8">
								<textarea rows="3" readonly class="form-control">${comment.content }</textarea>
							</div>
					</div>
					</c:forEach>
					<div class="form-group">
						<div class="col-sm-4"></div>
						<div class="col-sm-8 text-right">
							<input id="addNote" type="button" class="btn btn-primary" value="增加" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<nav class="navbar navbar-default navbar-fixed-bottom">
			<div class="container text-center">
				<p class="navbar-text">Copyright © 2016-2018 用户举报系统</p>
			</div>
		</nav>
	</body>
	<script type="text/javascript">
		$(function() {
			var ele = {
				addNote: $("#addNote")
			}
			ele.addNote.click(function() {
				var rootDiv = $("<div/>").attr("class", "form-group");
				var lable = $("<label/>").attr("class", "col-sm-4 control-label").text("追加人：");
				var div = $("<div/>").attr("class", "col-sm-8");
				var textarea = $("<textarea/>").attr("class", "form-control").attr("rows", 3);
				rootDiv.append(lable);
				div.append(textarea);
				rootDiv.append(div);
				$(this).parent().parent().before(rootDiv);
			});
			var url = "dict/getDictName.do?dictType=case.state&dictValue=${reportCase.caseState }";
			$.get(url,function(res){
				$("#caseState").text(res);
			});
		});
	</script>

</html>