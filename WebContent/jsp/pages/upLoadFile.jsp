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
				<span>上传文件</span>
			</div>
			<div class="form-group">
				<span>在这里你可以上传文件和文件为公司审核相关报告。<br />

				文件可能有您的属性信息。之前上传一个文件,通过以下步骤查看属性。<br /> 1。右键单击该文件并选择Properties选项卡>总结。<br />

				2。删除任何你不想分享的信息。<br /> 3所示。保存文件。<br /> 重要:删除属性信息不能删除所有个人信息的文件。<br />

				文件上传:0<br /> 你有100 MB的磁盘空间用于上传。
			</span>
			</div>
			<form action="case/fileUpload.do" method="post" class="form-horizontal">
			<input type="text" name="trackingNo" value="${trackingNo }" hidden="true" />
				<div class="form-group">
					<label class="control-label col-sm-2">文件</label>
					<div class="col-sm-4">
						<input class="form-info" type="file" accept="image/*,video/*,audio/*" id="realNameUpFile" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2">描述</label>
					<div class="col-sm-4">
						<textarea rows="3" class="form-file"></textarea>
					</div>
				</div>
				<div class="page-header"></div>
				<div class="form-group text-center">
					<input type="submit" class="btn btn-default" value="上传" />
				</div>
			</form>
		</div>
	</body>
	<!--<script type="text/javascript">
		$("#realNameUpFile").change(function() {
			if (window.File && window.FileList && window.FileReader) {
				var oFReader = new FileReader();
				oFReader.onload = function(file) {
					loadImg(file);
				};
				oFReader.readAsDataURL(document.getElementById("realNameUpFile").files[0]);
			}
			if ($("#realName > div").length > 2) {
				$(this).hide()
				$("#realNameBtn").hide();
			}
		});
		var loadImg = function(file) {
			var div = document.createElement("div");
			div.setAttribute("class", "col-xs-6 col-md-3");
			var span = document.createElement("span");
			span.setAttribute("class", "glyphicon glyphicon-remove-circle");
			span.setAttribute("aria-hidden", "true");
			span.onclick = function() {
				$(this).parent().remove();
				if ($("#realName > div").length < 4) {
					$("#realNameUpFile").show()
					$("#realNameBtn").show();
				}
			};
			var img = document.createElement("img");
			img.setAttribute("class", "img-responsive img-thumbnail");
			img.src = file.target.result;
			div.appendChild(span);
			div.appendChild(img);
			document.getElementById("realName").appendChild(div)
		}
	</script>-->

</html>