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
				color: #268ddf;
				font-size: 50px;
				text-shadow: 0 5px 5px #268ddf;
				font-weight: bold;
			}
			h4{
				color: #df7b14;
				font-size: 26px;
				text-shadow: 0 5px 5px #df7b14;
				font-weight: bold;
			}
			.container{
				/*background: #fcfcfc;*/
				background: url("jsp/css/img/bg.jpg");
				background-repeat: no-repeat;
				background-position: center;
				background-size: cover;
			}
		</style>
	</head>
		<div class="container text-center">
			<h1>欢迎光临举报系统管理端</h1>
			<h4>welcome to report manage system</h4>
		</div>
	<body>
	</body>
<script>
	$(function() {
		var winheight = $(window).height();
		$(".container").css("height",winheight+"px");
		$("h1").css("margin-top",winheight/2-50+"px");
	});
</script>
</html>
