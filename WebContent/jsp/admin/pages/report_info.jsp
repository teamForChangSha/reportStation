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
		<div class="container">
			<div class="row">
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
					<c:forEach items = "${questionAnswerList}" var = "quest" varStatus = "i" >
						<c:if test="${i.index=='0'}">
							<div class="page-header"></div>
						</c:if>
						<div class="form-group">
						<c:if test="${quest.questKey=='quest_1'}">
							<label class="col-sm-6 control-label">您是${reportCase.branch.owner.companyName }员工吗：</label>
						</c:if>
						<c:if test="${quest.questKey!='quest_1'}">
							<label class="col-sm-6 control-label">${quest.question }</label>
						</c:if>
						<div class="col-sm-6">
							<span class="form-info">${quest.questValue}</span>
						</div>
					</div>
					</c:forEach>
					<div class="page-header"></div>
					<div class="form-group">
						<label class="col-sm-4 control-label">其他材料：</label>
						<div class="col-sm-8">
							<c:forEach items = "${reportCase.attachList}" var = "attach" varStatus = "i" >
								<p class="form-info"><a href="${attach.attachUrl }" target="_black">${attach.attachFileName }</a></p>
							</c:forEach>
						</div>
					</div>
					<div class="page-header"></div>
					<div class="form-group">
						<label class="col-sm-4 control-label">案件状态：</label>
						<div class="col-sm-8">
							<span class="form-info" id="caseState"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">案件处理流程：</label>
						<div class="col-sm-8">
							<c:forEach items = "${reportCase.changeList}" var = "change" varStatus = "i" >
								<span class="form-info">${change.operator.userName }
									<time class="pull-right"><fmt:formatDate value="${change.changeTime}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/></time>
								</span>
							</c:forEach>
						</div>
					</div>
					<c:forEach items = "${reportCase.commentList}" var = "comment" varStatus = "i" >
						<div class="form-group">
							<c:if test="${comment.isReporter=='1'}">
								<label class="col-sm-4 control-label">${reportCase.reporter.name }：</label>
							</c:if>
							<c:if test="${comment.isReporter!='1'}">
								<label class="col-sm-4 control-label">${comment.ownerCompany.companyName }${comment.owner.userName }：</label>
							</c:if>
							<div class="col-sm-7">
								<textarea rows="3" readonly class="form-control">${comment.content }</textarea>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function() {
			var url = "dict/getDictName.do?dictType=case.state&dictValue=${reportCase.caseState }";
			$.get(url,function(res){
				$("#caseState").text(res);
			});
		});
	</script>

</html>