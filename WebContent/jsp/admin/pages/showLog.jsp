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
			.row {
				margin-top: 10px;
			}
			
			.container {
				margin-top: 30px;
				margin-bottom: 30px;
			}
			.table-info {
				background-color: #4a8bc2;
				background-image: linear-gradient(to bottom, #4a8bc2, #4a8bc2);
				color: #fff;
			}
			th,
			td {
				text-align: center;
			}
			
			.table > tbody > tr > td {
				vertical-align: inherit;
			}
			
			.col-sm-8 {
				padding-left: 0;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<!-- 操作日志 -->
			<div class="row">
				<h1><small>操作日志</small></h1>
				<div class="page-header"></div>
				<div class="col-sm-8">
					<table class="table table-bordered table-hover">
						<thead>
							<tr class="table-info">
								<th>操作日期</th>
								<th>操作内容</th>
								<th>操作人</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${logList}" var="log" varStatus = "i">
								<tr>
									<td><fmt:formatDate value="${log.logDate}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
									<td>${log.opration}</td>
									<td>${log.oprator.userName}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>
