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
			
			th,
			td {
				text-align: center;
			}
			
			.table {
				table-layout: fixed;
			}
			
			.table > tbody > tr > td {
				vertical-align: inherit;
			}
			
			.col-sm-8 {
				padding-left: 0;
			}
			
			.overflow-text {
				word-break: keep-all;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<div class="row">
				请选择需要展示的问题
			</div>
				<div class="row">
					<div class="col-sm-8">
						<table class="table table-bordered table-hover">
							<thead>
								<tr class="table-info">
									<th>编号</th>
									<th class="">问题描述</th>
									<th>是否展示</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-8 text-right">
						<input type="button" class="btn btn-default" value="提交" />
					</div>
				</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function() {
			$('[data-toggle="tooltip"]').tooltip(); 
			<c:forEach items = "${questList}" var = "quest" varStatus = "i" >
				var tr = $("<tr/>");
				var td1 = $("<td/>").text("${quest.questId}");
				var txt = "${quest.quest}";
				var td2 = $("<td/>").addClass("overflow-text")
					.attr("data-toggle", "tooltip").attr("data-placement", "bottom").attr("title", txt)
					.text(txt);
				var checkbox = $("<input/>").attr("name","questId ").attr("type", "checkbox").val("${quest.questId}");
				if("${quest.mark}"=="1"){
					checkbox.attr("checked",true);
				}
				var td3 = $("<td/>").append(checkbox);
				tr.append(td1).append(td2).append(td3);
				$("tbody").append(tr); 
			</c:forEach>
			$("input[type=button]").click(function(){
				var data = $("input[name=questId]").val();
				$.post("company/addCompanyQuestions.do",JSON.stringify(data),function(res,status){
					if (status=="success") {
						location.reload();
					}
				});
				console.log(JSON.stringify(data));
			});
		});
	</script>

</html>