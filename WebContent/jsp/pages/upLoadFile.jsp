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
			.form-info {
				display: block;
				width: 100%;
				height: 34px;
				padding: 5px 12px;
				font-size: 14px;
				line-height: 1.42857;
				color: #555;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<div class="form-group">
				<span>在这里你可以上传文件或者公司审核相关报告。
				<br /><br />
				你有50MB的磁盘空间用于上传。
			</span>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<form action="case/fileUpload.do" enctype="multipart/form-data" method="post" class="form-horizontal">
						<input type="text" name="trackingNo" value="${trackingNo }" hidden="true" />
						<div class="form-group">
							<label class="control-label col-sm-2">文件</label>
							<div class="col-sm-8">
								<input class="form-info" name="file" type="file" accept="image/*,video/*,audio/*" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">描述</label>
							<div class="col-sm-8">
								<textarea rows="3" name="desc" class="form-control"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="col-sm-6">
					<label class="control-label">已上传文件</label>
					<br/>
					<div style="height:115px; overflow:auto;" class="form-control">
						<c:forEach items = "${fileList}" var = "file" varStatus = "i">
							<c:out value="${file.attachFileName }"></c:out><span class="pull-right">${file.description }</span><br/><br/>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="page-header"></div>
			<div class="form-group text-center">
				<input type="submit" class="btn btn-default" value="上传" />
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function() {
			$("input[type=submit]").click(function() {
				var file = $("input[name=file]").val();
				if(file!=null&&file!=""&&file.length>0){
					$("form").submit();
				}
			});
		});
	</script>

</html>