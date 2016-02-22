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
			th {
				text-align: center;
			}
			
			body {
				padding: 60px 0 60px;
			}
			
			#bottom {
				float: inherit;
			}
			
			.navbar-right {
				margin-right: 15px;
			}
			
			.table-hover > tbody > tr:hover {
				cursor: pointer;
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
						<li><a href="userPages/privacy.html" target="_blank">商业行为和道德准则</a></li>
						<li><a href="#">常见问题</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<div class="container">
			<div class="row">
				<div class="col-sm-2">

				</div>
				<div class="col-sm-8 text-center">
					<div class="page-header">
						<p><strong></strong></p>
						<p>以下是您之间提交的所有举报信息</p>
					</div>
					<table class="table table-bordered table-hover">
						<thead>
							<tr class="info">
								<th>被举报公司</th>
								<th>举报原因</th>
								<th>当前状态</th>
								<th>举报时间</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<nav class="navbar navbar-default navbar-fixed-bottom">
			<div class="container text-center">
				<p class="navbar-text" id="bottom">Copyright © 2016-2018 用户举报系统</p>
			</div>
		</nav>
	</body>
	<script type="text/javascript">
	$(function() {
		<c:forEach items="${caseList}" var="caseInfo" varStatus = "i">
			$("strong").text("您好：${caseInfo.reporter.name}");
			var tr = $("<tr/>");
			var td1 = $("<td/>").addClass("hidden").text("${caseInfo.rcId}");
			var td2 = $("<td/>").text("${caseInfo.company.companyName}");
			var td3 = $("<td/>").text("${caseInfo.rtList}");
			var url = "dict/getDictName.do?dictType=case.state&dictValue=${caseInfo.caseState}";
			$.get(url,function(res){
				td3 = $("<td/>").text(res);
			});
			var td4 = $("<td/>").text("${caseInfo.caseState}");
			var td5 = $("<td/>").text("<fmt:formatDate value="${caseInfo.createTime}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/>");
			tr.append(td1).append(td2).append(td3).append(td4).append(td5);
			tr.click(function() {
				location.href = "case/showCaseById.do?rcId="+$(this).find("td:first").text();
			});
			$("tbody").append(tr);
		</c:forEach>
	});
	</script>

</html>