<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=7,IE=8,IE=edge">
    <meta http-equiv="cache-control" content="no-cache">
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/common_top.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <title>51report-错误页面</title>
    <style>
        h3{
            margin-top: 100px;
        }
    </style>
</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation" id="top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav pull-right">
                <li><a href="<%=basePath%>">首页</a></li>
                <li><a href="#" target="_blank">商业行为和道德准则</a></li>
                <li><a href="#">常见问题</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container text-center">
    <h3><span class="label label-danger">${errorMsg }</span></h3>
    <br/>
    <a href="" class="btn btn-link">&lt;返回首页</a>
</div>

<nav class="navbar navbar-default navbar-fixed-bottom">
    <div class="container">
        <p class="navbar-text" id="bottom">Copyright © 2016-2018 51report.com</p>
    </div>
</nav>
</body>

</html>