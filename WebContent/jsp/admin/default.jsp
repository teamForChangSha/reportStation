<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>

<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <style>
        .table-info {
            background-color: #4a8bc2;
            background-image: linear-gradient(to bottom, #4a8bc2, #4a8bc2);
            color: #fff;
            text-align: center;
        }
    </style>
</head>
<div class="container">
    <div class="row" style="margin-top: 30px;">
        <div class="col-sm-3">
            <h1>
                <small>当前账号状态：
                    <c:if test="${user.userState=='1'}">正常</c:if>
                    <c:if test="${user.userState=='2'}">注销</c:if>
                    <c:if test="${user.userState=='3'}">待审核</c:if>
                    <c:if test="${user.userState=='4'}">停用</c:if>
                </small>
            </h1>
        </div>
        <div class="col-sm-3">
            <h1>
                <small>上次登录时间：<fmt:formatDate value="${user.stateChanged}" type="date"
                                              pattern="yyyy年MM月dd日 HH:mm:ss"/></small>
            </h1>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-8"><h1><small>最近案件简报</small></h1></div>
        <div class="col-sm-10">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="table-info">
                    <td>案件编号</td>
                    <td>举报时间</td>
                    <td>状态</td>
                    <td>举报类型</td>
                    <td>举报人</td>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>
<body>
</body>
<script>
    $(function () {
    });
</script>
</html>
