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
				padding: 5px 0;
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
				<h1><small>公司信息设置</small></h1>
				<div class="page-header"></div>
				<form id="enterInfo" action="admin/caseBack/updateCompanyWholeInfo.do" enctype="multipart/form-data" method="post" class="form-horizontal">
					<input type="text" name="company.companyId" hidden value="${company.companyId}" />
					<div class="form-group">
						<label class="col-sm-2 control-label">公司名字：</label>
						<div class="col-sm-4">
							<input type="text" value="${company.companyName}" name="company.companyName" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司名称代码：</label>
						<div class="col-sm-4">
							<input type="text" value="${company.companyCode}" name="company.companyCode" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司描述：</label>
						<div class="col-sm-6">
							<textarea rows="5" name="company.description" class="form-control">${company.description}</textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司联系电话：</label>
						<div class="col-sm-4">
							<input type="text" value="${company.phone}" name="company.phone" class="form-control" />
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
							<input type="text" hidden value="${company.companyState }" name="company.companyState" />
							<input type="text" id="state" disabled="" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">服务协议文本：</label>
						<div class="col-sm-6">
							<textarea rows="5" name="companyOther.serviceProtocol" class="form-control">${company.otherInfo.serviceProtocol}</textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">服务协议HTML：</label>
						<div class="col-sm-6">
							<textarea rows="5" name="companyOther.spHtml" class="form-control">${company.otherInfo.spHtml}</textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">公司LOGO：</label>
						<div class="col-sm-3">
							<input type="file" name="logo" accept="image/*" class="form-info" />
						</div>
						<div class="col-sm-3">
							<p class="thumbnail">
      							<img src="jsp/css/img/placeholder.png"   />
    						</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">最后修改时间：</label>
						<div class="col-sm-4">
							<input type="text" disabled="" value='<fmt:formatDate value="${company.stateChanged}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/>' class="form-control" />
						</div>
						<div class="col-sm-2 text-right">
							<input type="button" name="updata" class="btn btn-warning" value="修改" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function() {
			/* 获取状态的中文 */
			var url = "dict/getDictName.do?dictType=company.state&dictValue=${company.companyState }";
			$.get(url,function(res){
				$("#state").val(res);
			});
			/* 设置select需要选中的状态 */
			$("#type").get(0).value = "${company.companyType}";
			/* 设置LOGO的图片 */
			var imgUrl = "${company.otherInfo.logoUrl}";
			if(imgUrl!=""){
				$("img").attr("src",imgUrl);
			}
			/* 设置logo的缩略图 */
			$("input[name=logo]").change(function(){
				if (window.File && window.FileList && window.FileReader) {
					var oFReader = new FileReader();
					oFReader.onload = function(file) {
						$("img").attr("src",file.target.result);
					};
					oFReader.readAsDataURL($("input[name=logo]").get(0).files[0]);
				}
			});
			/* 更新企业信息 */
			$("input[name=updata]").click(function() {
				console.log("tijiao");
				if ($("#type").find("option:selected").val() == "0") {
					return alert("请选择企业类型");
				}
				$("#enterInfo").submit();
			});
			
			var msg = "${msg}";
			if(msg!=""){
				alert(msg);
				location.href = "admin/caseBack/getOwnerCompanyInfo.do";
			}
		});
	</script>

</html>