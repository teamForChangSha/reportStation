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
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css" />
		<script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	<style type="text/css">
			.table-info {
				background-color: #4a8bc2;
				background-image: linear-gradient(to bottom, #4a8bc2, #4a8bc2);
				color: #fff;
			}
			
			.row {
				margin-top: 10px;
			}
			
			.container {
				margin-top: 30px;
				margin-bottom: 30px;
			}
			
			#time {
				width: 90px;
			}
			
			th,
			td {
				text-align: center;
			}
			
			.table > tbody > tr > td {
				vertical-align: inherit;
			}
			
			.col-sm-12 {
				padding-left: 0;
			}
			
			.glyphicon {
				color: #C12E2A;
				border: 3px solid #C12E2A;
				border-radius: 50px;
			}
			
			.table-hover > tbody > tr:hover {
				cursor: pointer;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<div class="row">
				<div class="form-inline">
					<div class="form-group">
						<label class="control-label">选择时间段：</label>
						<input type="datetime-local" id="time" class="form-control">
					</div>
					<div class="form-group">
						<label class="control-label">到</label>
						<input type="datetime-local" id="time" class="form-control">
					</div>
					<div class="form-group" style="margin-left: 10px;">
						<label class="control-label">审批状态</label>
						<select class="form-control">
							<option value="-1">-请选择-</option>
							<option value="身份证">管理员</option>
							<option value="护照">企业用户</option>
							<option value="护照">普通用户</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-inline">
					<div class="form-group">
						<label class="control-label">输入客户名：</label>
						<input type="text" class="form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-inline">
					<div class="form-group">
						<label class="control-label">搜索关键字：</label>
						<input type="text" class="form-control" style="width: 300px;" placeholder="可模糊搜索用户名、手机号、省份证">
					</div>
					<input type="button" class="btn btn-default" value="搜索" />
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<table class="table table-bordered table-hover">
						<thead>
							<tr class="table-info">
								<th>举报人</th>
								<th>举报时间</th>
								<th>举报状态</th>
								<th>举报对象</th>
								<th>举报类型</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function() {
			<c:forEach items="${caseList}" var="caseInfo" varStatus = "i">
			console.log("您好：${caseInfo.reporter.name}");
			var url = "dict/getDictName.do?dictType=case.state&dictValue=${caseInfo.caseState}";
			$.get(url,function(res){
				var tr = $("<tr/>");
				var td1 = $("<td/>").addClass("hidden").text("${caseInfo.rcId}");
				var td2 = $("<td/>").text("${caseInfo.reporter.name}");
				var td3 = $("<td/>").text('<fmt:formatDate value="${caseInfo.createTime}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/>');
				var	td4 = $("<td/>").text(res);
				var td5 = $("<td/>").text("${caseInfo.company.companyName}");
				var td6 = $("<td/>").text("${caseInfo.rtList}");
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				tr.click(function() {
					location.href = "case/showCaseById.do?rcId="+$(this).find("td:first").text();
				});
				$("tbody").append(tr);
			});
			</c:forEach>
		});
	</script>

</html>
