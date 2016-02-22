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
			.navbar-text {
				float: inherit;
			}
			
			.bg-success {
				background-image: linear-gradient(to bottom, #f5f5f5 0, #e8e8e8 100%);
				background-repeat: repeat-x;
				color: #333;
				background-color: #f5f5f5;
				border-color: #ddd;
				border-radius: 3px;
				padding: 10px 15px;
			}
			
			.row {
				margin: 0 10%;
			}
			
			.navbar-right {
				margin-right: 15px;
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
			<form action="showQuestionPage.do" method="post" class="form-horizontal">
				<div class="row" id="typeList">
					<p>
						<strong>请从以下列表中选择您要报告的问题类型：</strong>
					</p>
					<c:forEach items="${reportTypeList}" var="reportType">
						<div class="form-group bg-success">
							<div class="checkbox col-sm-6">
								<label>
									<input type="checkbox" name="reportType" value="${reportType.rtTitle }" /> ${reportType.rtTitle }
								</label>
							</div>
							<div class="col-sm-4 text-right pull-right">
								<button class="btn btn-link" type="button" data-toggle="popover" data-container="body" data-placement="bottom" data-content="${reportType.rtDesc }">查看描述</button>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="row text-center">
					<button id="next" type="button" class="btn btn-primary">下一步</button>
				</div>
			</form>
		</div>
		<nav class="navbar navbar-default navbar-fixed-bottom">
			<div class="container text-center">
				<p class="navbar-text">Copyright © 2016-2018 用户举报系统</p>
			</div>
		</nav>
	</body>
	<script type="text/javascript">
		$(function() {
			$('[data-toggle="popover"]').popover();
			$("#next").click(function() {
				if ($("input[type=checkbox]:checked").length <= 0) {
					return alert("请至少选择一个举报类型");
				}
				$("form").submit();
			});
		});
	</script>

</html>