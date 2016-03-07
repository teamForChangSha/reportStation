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
		<script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<style>
			h1{
				color: #64ff9e;
				font-size: 50px;
				text-shadow: 0 5px 5px #d40000;
			}
			body{
				background: #fcfcfc;
			}
		</style>
	</head>
		<div class="container text-center">
			<h1>欢迎光临举报系统管理端</h1>
		</div>
	<body>
	</body>
<script>
	$(function() {
		var winheight = $(window).height();
		$("h1").css("margin-top",winheight/2-50+"px");
	});
</script>
</html>
