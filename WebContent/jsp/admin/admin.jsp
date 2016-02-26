<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html style="overflow: hidden;">

	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>后台管理系统</title>
		<link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css" />
		<link href="jsp/css/style.css" rel="stylesheet">
		<style type="text/css">
			.navbar-default {
				background-color: #4a8bc2;
				background-image: linear-gradient(to bottom, #4a8bc2, #4a8bc2);
				border: inherit;
				margin-bottom: 0;
				border-radius: 0;
				color: #fff;
			}
			
			.navbar-default .navbar-brand {
				color: #fff;
			}
			
			#sidebar > ul > li > a {
				color: #fff !important;
				width: auto;
			}
			
			#sidebar > ul > li > ul.sub > li > a {
				padding: 10px 10px 10px 10px;
			}
			
			.navbar-default .navbar-nav > li > a,
			.navbar-default .navbar-nav > li > a:hover {
				color: #fff;
			}
			
			.userName {
				font-weight: bold;
			}
		</style>
	</head>

	<body>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand">
        后台管理系统
      </a>
				</div>
				<ul class="nav navbar-nav pull-right">
					<li class="dropdown mtop5">
						<a id="date"></a>
					</li>
					<li class="dropdown mtop5">
						<a class="userName">您好：${user.userName}</a>
					</li>
					<li class="dropdown mtop5">
						<a href="loginOut.do"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>退出</a>
					</li>
				</ul>
			</div>
		</nav>
		<div id="container" class="row-fluid">
			<div tabindex="5000" style="overflow: auto;" class="sidebar-scroll">
				<div style="height: auto;" id="sidebar" class="nav-collapse in collapse">
					<ul class="sidebar-menu">
						<li class="sub-menu">
							<a href="javascript:;" class="">
								<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
								<span>公司设置</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="jsp/admin/pages/enterAdmin.jsp" target="MainIframe">公司基本信息</a></li>
								<li><a class="" href="company/getAllReportTypes.do" target="MainIframe">举报类型设置</a></li>
								<li><a class="" href="company/getQuestTemlate.do" target="MainIframe">问题设置</a></li>
							</ul>
						</li>
						<li class="sub-menu">
							<a href="jsp/admin/pages/users.jsp" class="" target="MainIframe">
								<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
								<span>用户管理</span>
							</a>
						</li>
						<li class="sub-menu">
							<a href="case/showCaseByCompany.do" class="" target="MainIframe">
								<span class="glyphicon glyphicon-open-file" aria-hidden="true"></span>
								<span>举报管理<span class="badge">4</span></span>
							</a>
						</li>
						<li class="sub-menu">
							<a href="javascript:;" class="">
								<span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
								<span>统计分析</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="" target="MainIframe">表单布局</a></li>
							</ul>
						</li>
						<li class="sub-menu">
							<a href="javascript:;" class="">
								<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
								<span>数据设计</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="jsp/admin/pages/enterAdmin.jsp" target="MainIframe">企业数据维护</a></li>
								<li><a class="" href="jsp/admin/pages/enterMerge.jsp" target="MainIframe">企业名称合并</a></li>
							</ul>
						</li>
						<li class="sub-menu">
							<a href="#" class="" target="MainIframe">
								<span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
								<span>信息修改</span>
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div id="main-content">
				<iframe src="#" id="MainIframe" name="MainIframe" style="height: 100%;width: 100%" frameborder="0">
				</iframe>
			</div>
		</div>
		<div id="footer">
			Copyright © 2016-2018 用户举报系统
		</div>
		<script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="jsp/js/common-scripts.js"></script>
		<script type="text/javascript">
			$(function() {
				var winheight = $(window).height();
				$("#main-content").height(winheight - 81);
			});
			$(window).resize(function() {
				var winheight = $(window).height();
				$("#main-content").height(winheight - 81);
			});
			$(function() {
				initTime();
				setInterval(initTime, 1000);

				function initTime() {
					var time = new Date();
					var month = (time.getMonth() + 1) < 10 ? "0" + (time.getMonth() + 1) : (time.getMonth() + 1);
					var day = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
					var hour = time.getHours() < 10 ? "0" + time.getHours() : time.getHours();
					var minute = time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes();
					var second = time.getSeconds() < 10 ? "0" + time.getSeconds() : time.getSeconds();
					var text = time.getFullYear() + "年" + month + "月" + day + "日 " + hour + ":" + minute + ":" + second;
					$("#date").text(text);
				};
			});
		</script>
	</body>

</html>