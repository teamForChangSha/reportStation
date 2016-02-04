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
						<li><a href="../jsp/index.jsp">é¦é¡µ</a></li>
						<li><a href="userPages/privacy.html" target="_blank">åä¸è¡ä¸ºåéå¾·åå</a></li>
						<li><a href="#">å¸¸è§é®é¢</a></li>
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
						<p><strong>æ¨å¥½ï¼XXX</strong></p>
						<p>ä»¥ä¸æ¯æ¨ä¹é´æäº¤çææä¸¾æ¥ä¿¡æ¯</p>
					</div>
					<table class="table table-bordered table-hover">
						<thead>
							<tr class="info">
								<th>è¢«ä¸¾æ¥å¬å¸</th>
								<th>ä¸¾æ¥åå </th>
								<th>å½åç¶æ</th>
								<th>ä¸¾æ¥æ¶é´</th>
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
				<p class="navbar-text" id="bottom">Copyright Â© 2016-2018 ç¨æ·ä¸¾æ¥ç³»ç»</p>
			</div>
		</nav>
	</body>
	<script type="text/javascript">
		$(function() {
			for (var i = 0; i < 10; i++) {
				var tr = $("<tr/>");
				var td1 = $("<td/>").addClass("hidden").text(i);
				var td2 = $("<td/>").text("è¹æä¸­å½"+i);
				var td3 = $("<td/>").text("ä¾åºåè¡è´¿");
				var td4 = $("<td/>").text("è¢«ä¸¾æ¥äººå·²åå¤");
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