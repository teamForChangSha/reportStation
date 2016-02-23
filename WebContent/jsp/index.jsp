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
		<script src="jsp/js/localdata.js" type="text/javascript" charset="utf-8"></script>
		<script src="jsp/js/AreaData_min.js" type="text/javascript"></script>
		<style type="text/css">
			#loginPanel {
				top: 20%;
			}
			
			#username {
				background-image: url(css/img/input_icons.png);
				background-repeat: no-repeat;
				background-position: 0 -64px;
				padding: 6px 12px 6px 32px;
			}
			
			#username:focus {
				background-position: 0 -104px;
			}
			
			#password {
				background-image: url(css/img/input_icons.png);
				background-repeat: no-repeat;
				background-position: 0 -144px;
				padding: 6px 12px 6px 32px;
			}
			
			#password:focus {
				background-position: 0 -184px;
			}
			
			.modal {
				top: 10%;
			}
			
			input[name=accecCode]+span,
			input[name=tempPwd]+span {
				right: 50px;
			}
			
			input[name=mobile]+span {
				right: 110px;
			}
			input[name=companyName]+span {
				right: 44px;
			}
			
			.glyphicon-ok {
				color: #419641;
			}
			
			.glyphicon-remove {
				color: #C12E2A;
			}
			
			.dropdown-menu {
				max-height: 300px;
				overflow: auto;
			}
			
			.alert {
				margin: 0 -15px;
			}
			
			.page-header {
				margin: 20px 0 20px;
			}
			
			#row-right {
				padding: 0 15px;
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
						<li><a href="#" data-toggle="modal" data-target="#loginPanel">企业用户登录</a></li>
						<li><a href="#" data-toggle="modal" data-target="#reportPanel">举报查询</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<div class="container">
			<div class="row">
				<div class="col-sm-4">
					<div class="col-sm-10">
						<div class="page-header text-center">
							<h1>
							<small>我要举报</small>
						</h1>
						</div>
						<form id="sendCompany" action="showReportType.do" method="post" class="form-horizontal">
							<div class="input-group form-group">
								<span class="input-group-btn">
									<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										选择企业 <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
									</button>
									<ul class="dropdown-menu" id="companys"></ul>
								</span>
								<input type="text" name="companyName" class="form-control" placeholder="请输入被举报的企业" /> 
								<span class="glyphicon form-control-feedback" aria-hidden="true"></span> 
								<ul class="dropdown-menu" style="left:92px;"></ul>
								<span class="input-group-btn">
									<button id="getArea" class="btn btn-default" type="button">GO!</button>
								</span>
							</div>
							<div class="form-group hidden">
								<select class="form-control" name="province">
								</select>
							</div>
							<div class="form-group hidden">
								<select class="form-control" name="city">
								</select>
							</div>
							<div class="form-group hidden">
								<select class="form-control" name="branchId">
								</select>
							</div>
							<div class="form-group hidden">
								<input type="button" class="btn btn-primary btn-block" id="next" value="下一步" />
							</div>
						</form>
						<div class="alert alert-warning">
							完成举报后，您将获得一个唯一的<strong>“案件编号”</strong>。 记录您的案件编号和密码并将其保存在安全的地方。 5-6 个工作日后，请使用您的案件编号或密码检查报告的反馈或问题。
						</div>
						<br />
						<div class="alert alert-danger">
							不要使用此网站举报对生命或财产构成直接威胁的事件。通过此服务提交的报告可能不会立即得到答复。如果您需要紧急协助，请与当地的相关机构联系。
						</div>
					</div>
				</div>
				<div class="col-sm-7">
					<div class="page-header text-center">
						<h1>
						<small>我们承诺</small>
					</h1>
					</div>
					<div class="jumbotron">
						<div class="container">Belden 是一家拥有高度负责和诚信价值观的组织。 我们的 商业行为与道德准则 包含以最高道德标准开展业务的一般性指南。 Belden 承诺创建一个坦诚、诚实交流而非排除在外的环境。 我们希望在您认为有违反政策或标准的行为发生时，能够适应向您的主管或管理层举报，而不会遭到报复。 在您希望能够以保密、匿名的方式进行举报的情况下，我们鼓励您使用此热线电话，该电话由第三方热线电话提供商 EthicsPoint 提供。 我们鼓励您提交与我们的 商业行为和道德准则中所列违规行为有关的举报，询问有关政策和程序的指南并提供积极的建议和故事。
							EthicsPoint 将会按照您的选择以完全保密和匿名的方式，把您提供的信息发送给我们。 我们保证您的意见将会得到听取。</div>
					</div>
				</div>
			</div>
		</div>
		<nav class="navbar navbar-default navbar-fixed-bottom">
			<div class="container">
				<p class="navbar-text" id="bottom">Copyright © 2016-2018 用户举报系统</p>
			</div>
		</nav>

		<!--登陆对话框-->
		<div class="modal fade bs-example-modal-sm" id="loginPanel" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h5 class="modal-title" id="exampleModalLabel">企业用户登录</h5>
					</div>
					<div class="modal-body">
						<form action="" method="post">
							<div class="form-group">
								<input type="text" id="username" class="form-control" placeholder="手机/用户名">
							</div>
							<div class="form-group">
								<input type="text" id="password" class="form-control" placeholder="密码">
							</div>
							<div class="form-group">
								<input type="submit" value="登陆" class="btn btn-primary form-control" />
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a href="userPages/forget_pwd.html" target="_blank" id="forgetPwd" type="button" class="btn btn-link">忘记密码</a>
					</div>
				</div>
			</div>
		</div>

		<!--查询对话框-->
		<div class="modal fade bs-example-modal-sm" id="reportPanel" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h5 class="modal-title" id="exampleModalLabel">举报查询</h5>
					</div>
					<div class="modal-body">
						<div class="row" id="row-right">
							<div class="col-sm-12">
								<form id="selectByNum" action="case/showCaseByTrankingNo.do" method="post" class="form-horizontal" style="margin-bottom: 15px;">
									<div class="form-group">
										<input type="text" name="trankingNo" class="form-control" placeholder="请输入跟踪单号" />
										<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
									</div>
									<div class="input-group form-group">
										<input name="accecCode" type="password" class="form-control" placeholder="请输入密码" /> <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
										<span class="input-group-btn">
										<button id="getCaseInfoByNo" class="btn btn-default" type="button">查询</button>
									</span>
									</div>
								</form>
							</div>
						</div>

						<div class="row" id="row-right">
							<div class="col-sm-12">
								<form id="selectByPhone" action="case/showCaseList.do" method="post" class="form-horizontal">
									<div class="input-group form-group">
										<input name="mobile" type="text" maxlength="11" class="form-control" placeholder="请输入实名手机号"> <span class="glyphicon form-control-feedback" aria-hidden="true"></span> <span class="input-group-btn">
										<button id="getTempPwd" class="btn btn-default" type="button">获取临时密码</button>
									</span>
									</div>
									<div class="input-group form-group">
										<input name="tempPwd" type="password" class="form-control" placeholder="请输入临时密码"> <span class="glyphicon form-control-feedback" aria-hidden="true"></span> <span class="input-group-btn">
										<button id="getCaseInfoByPhone" class="btn btn-default" type="button">查询</button>
									</span>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script src="jsp/js/index.js" type="text/javascript" charset="utf-8"></script>

</html>
