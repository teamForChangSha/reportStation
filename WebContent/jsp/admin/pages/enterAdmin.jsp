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
			<h1><small>公司信息设置</small></h1>
			<div class="page-header"></div>
			<div class="row">
				<div class="form-inline">
					<div class="form-group">
						<label class="control-label">搜索关键字：</label>
						<input type="text" class="form-control" style="width: 300px;" placeholder="可模糊搜索企业名称、手机号等">
					</div>
					<input type="button" class="btn btn-default" value="搜索" />
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<table class="table table-bordered table-hover">
						<thead>
							<tr class="table-info">
								<th>添加时间</th>
								<th>单位名称</th>
								<th>联系电话</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
			
			<div class="row">
				<input type="button" name="add" class="btn btn-default" value="添加企业" />
				<input type="button" name="import" style="margin-left: 10px;" class="btn btn-default" value="导入单位信息" />
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function() {
			for (var i = 0; i < 3; i++) {
				var tr = $("<tr/>");
				var td1 = $("<td/>").text("2015-12-01 14:20:30");
				var td2 = $("<td/>").text("苹果中国");
				var td3 = $("<td/>").text("18600000000");
				var td4 = $("<td/>");
				var a1 = $("<a/>").attr("class", "btn btn-link").text("修改信息");
				a1.click(function() {
					hiddenPanle();
					panle.updataPwd.removeClass("hidden");
					//TODO
				});
				var a2 = $("<a/>").attr("class", "btn btn-link").text("停用");
				a2.click(function() {
					hiddenPanle();
					panle.updataUserInfo.removeClass("hidden");
					//TODO
				});
				td4.append(a1).append(a2);
				tr.append(td1).append(td2).append(td3).append(td4);
				$("tbody").append(tr);
			}
		});
	</script>

</html>