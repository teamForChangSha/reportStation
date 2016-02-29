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
			
			span:focus {
				cursor: hand;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<div class="row">
			<h1><small>用户管理</small></h1>
			<div class="page-header"></div>
				<div class="form-inline">
					<div class="form-group">
						<label class="control-label">选择时间段：</label>
						<input type="datetime-local" id="time" class="form-control"/>
					</div>
					<div class="form-group">
						<label class="control-label">到</label>
						<input type="datetime-local" id="time" class="form-control"/>
					</div>
					<div class="form-group" style="margin-left: 10px;">
						<label class="control-label">用户类型</label>
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
						<label class="control-label">搜索关键字：</label>
						<input type="text"  style="width: 300px;" placeholder="可模糊搜索用户名、手机号、身份证" class="form-control">
					</div>
					<input type="button" class="btn btn-default" value="搜索" />
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<table class="table table-bordered table-hover">
						<thead>
							<tr class="table-info">
								<th>用户名</th>
								<th>注册时间</th>
								<th>用户类型</th>
								<th>联系电话</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>

			<div class="row">
				<input type="button" name="register" class="btn btn-default" value="添加用户" />
			</div>

			<!--修改用户信息-->
			<div class="row hidden" id="updataUserInfo">
				<div class="col-sm-12">
					<div class="page-header"></div>
					<h4 class="col-sm-3">修改用户信息</h4>
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">用户名字：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">用户类型：</label>
								<div class="col-sm-8">
									<select class="form-control">
										<option value="-1">-请选择-</option>
										<option value="身份证">管理员</option>
										<option value="护照">企业用户</option>
										<option value="护照">普通用户</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">机构选择：</label>
								<div class="col-sm-8">
									<select class="form-control">
										<option value="-1">-请选择-</option>
										<option value="身份证">管理员</option>
										<option value="护照">企业用户</option>
										<option value="护照">普通用户</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">手机号码：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"></label>
								<div class="col-sm-8">
									<input type="button" class="form-control btn btn-default" value="修改">
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-3  text-right"><span id="closeUpdataUser" class="glyphicon glyphicon-remove" aria-hidden="true"></span></div>
				</div>
			</div>

			<!--修改密码-->
			<div class="row hidden" id="updataPwd">
				<div class="col-sm-12">
					<div class="page-header"></div>
					<h4 class="col-sm-3">修改密码</h4>
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">旧密码：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">新密码：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">确认新密码：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"></label>
								<div class="col-sm-8">
									<input type="button" class="form-control btn btn-default" value="修改">
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-3 text-right"><span id="closeUpdataPwd" class="glyphicon glyphicon-remove" aria-hidden="true"></span></div>
				</div>
			</div>

			<!--添加用户-->
			<div class="row hidden" id="addUser">
				<div class="col-sm-12">
					<div class="page-header"></div>
					<h4 class="col-sm-3">添加用户</h4>
					<div class="col-sm-6">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">用户名字：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">用户类型：</label>
								<div class="col-sm-8">
									<select class="form-control">
										<option value="-1">-请选择-</option>
										<option value="身份证">管理员</option>
										<option value="护照">企业用户</option>
										<option value="护照">普通用户</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">机构选择：</label>
								<div class="col-sm-8">
									<select class="form-control">
										<option value="-1">-请选择-</option>
										<option value="身份证">管理员</option>
										<option value="护照">企业用户</option>
										<option value="护照">普通用户</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">手机号码：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label"></label>
								<div class="col-sm-8">
									<input type="button" class="form-control btn btn-default" value="修改">
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-3  text-right"><span id="closeAddUser" class="glyphicon glyphicon-remove" aria-hidden="true"></span></div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function() {
			var panle = {
				updataPwd: $("#updataPwd"),
				updataUserInfo: $("#updataUserInfo"),
				addUser: $("#addUser")
			}
			var close = {
				updataPwd: $("#closeUpdataPwd"),
				updataUserInfo: $("#closeUpdataUser"),
				addUser: $("#closeAddUser")
			}
			$("input[name=register]").click(function() {
				hiddenPanle();
				panle.addUser.removeClass("hidden");
			});
			close.updataPwd.get(0).onclick = hiddenPanle;
			close.updataUserInfo.get(0).onclick = hiddenPanle;
			close.addUser.get(0).onclick = hiddenPanle;
			for (var i = 0; i < 3; i++) {
				var tr = $("<tr/>");
				var td1 = $("<td/>").text("张三" + i);
				var td2 = $("<td/>").text("2015-12-01 14:20:30");
				var td3 = $("<td/>").text("普通用户");
				var td4 = $("<td/>").text("18600000000");
				var td5 = $("<td/>");
				var a1 = $("<a/>").attr("class", "btn btn-link").text("修改密码");
				a1.click(function() {
					hiddenPanle();
					panle.updataPwd.removeClass("hidden");
					//TODO
				});
				var a2 = $("<a/>").attr("class", "btn btn-link").text("修改信息");
				a2.click(function() {
					hiddenPanle();
					panle.updataUserInfo.removeClass("hidden");
					//TODO
				});
				var a3 = $("<a/>").attr("class", "btn btn-link").text("停用");
				a3.click(function() {
					//TODO
				});
				var a4 = $("<a/>").attr("class", "btn btn-link").text("注销");
				a4.click(function() {
					//TODO
				});
				td5.append(a1).append(a2).append(a3).append(a4);
				tr.append(td1).append(td2).append(td3).append(td4).append(td5);
				$("tbody").append(tr);
			}

			function hiddenPanle() {
				panle.updataPwd.addClass("hidden");
				panle.updataUserInfo.addClass("hidden");
				panle.addUser.addClass("hidden");
			}
		});
	</script>

</html>
