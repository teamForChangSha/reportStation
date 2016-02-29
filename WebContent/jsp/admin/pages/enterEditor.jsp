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
			.form-info {
				display: block;
				width: 100%;
				height: 34px;
				padding: 5px 12px;
				font-size: 14px;
				line-height: 1.42857;
				color: #555;
			}
			.container{
				padding: 30px 0;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<div class="row">
				<form action="company/updateCompanyWholeInfo.do" enctype="multipart/form-data" method="post" class="form-horizontal">
					<input type="text" name="company.companyId" hidden value="${user.userCompany.companyId}" />
					<div class="form-group">
						<label class="col-sm-2 control-label">公司名字：</label>
						<div class="col-sm-4">
							<input type="text" value="${user.userCompany.companyName}" name="company.companyName" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司名称代码：</label>
						<div class="col-sm-4">
							<input type="text" value="${user.userCompany.companyCode}" name="company.companyCode" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司描述：</label>
						<div class="col-sm-6">
							<textarea rows="5" value="${user.userCompany.description}" name="company.description" class="form-control"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司联系电话：</label>
						<div class="col-sm-4">
							<input type="text" value="${user.userCompany.phone}" name="company.phone" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司类型：</label>
						<div class="col-sm-3">
							<select id="type" name="company.companyType" class="form-control">
								<option value="0">--请选择--</option>
								<option value="1">国有</option>
								<option value="2">民营</option>
								<option value="3">股份</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司状态：</label>
						<div class="col-sm-4">
							<input type="text" id="state" disabled="" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">服务协议文本：</label>
						<div class="col-sm-6">
							<textarea rows="5" value="${user.userCompany.otherInfo.serviceProtocol}" name="companyOther.serviceProtocol" class="form-control"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">服务协议HTML：</label>
						<div class="col-sm-6">
							<textarea rows="5" value="${user.userCompany.otherInfo.spHtml}" name="companyOther.spHtml" class="form-control"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司LOGO：</label>
						<div class="col-sm-4">
							<input type="file" name="logo" accept="image/*" class="form-info" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">最后修改时间：</label>
						<div class="col-sm-4">
							<input type="text" disabled="" value='<fmt:formatDate value="${user.userCompany.stateChanged}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/>' name="company.stateChanged" class="form-control" />
						</div>
						<div class="col-sm-2 text-right">
							<input type="button" class="btn btn-warning" value="修改" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function() {
			var url = "dict/getDictName.do?dictType=company.state&dictValue=${user.userCompany.companyState }";
			$.get(url,function(res){
				$("#state").val(res);
			});
			
			$("#type").get(0).value = "${user.userCompany.companyState}";
			
			$("input[type=button]").click(function() {
				if ($("select[name=company.companyType]").find("option:selected").val() == "0") {
					return alert("请选择企业类型");
				}
				$("form").submit();
			});
		});
	</script>

</html>