<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="../bootstrap3/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="../bootstrap3/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="../bootstrap3/css/common_top.css" />
		<script src="../bootstrap3/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../bootstrap3/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
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
						<li><a href="../index.html">首页</a></li>
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
						<p><strong>您好：XXX</strong></p>
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
		for (var i = 0; i < 10; i++) {
			var tr = $("<tr/>");
			var td1 = $("<td/>").addClass("hidden").text(i);
			var td2 = $("<td/>").text("苹果中国"+i);
			var td3 = $("<td/>").text("供应商行贿");
			var td4 = $("<td/>").text("被举报人已回复");
			var td5 = $("<td/>").text("2016-02-12");
			tr.append(td1).append(td2).append(td3).append(td4).append(td5);
			tr.click(function() {
				//				alert($(this).find("td:first").text());
				location.href = "report_info.html";
			});
			$("tbody").append(tr);
		}
	});
	</script>

</html>