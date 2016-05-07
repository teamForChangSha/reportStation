<%@ page language="java" contentType="text/html; charset=UTF-8" %>
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
    <meta http-equiv="X-UA-Compatible" content="IE=7,IE=8,IE=edge">
    <title></title>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/common_top.css"/>
    <style>
        h1{
            margin-top: 100px;
            font-weight: bold;
            color: #595d60;
            font-size: 50px;
        }
        h2{
            color: #265a88;
            font-weight: 700;
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
                <li><a href="userPages/privacy.html" target="_blank">商业行为和道德准则</a></li>
                <li><a href="#">常见问题</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container text-center">
    <h1>500</h1>
    <h2>服务器正在维护中...</h2>
    <br>
    <a href="" class="btn btn-link">&lt;返回首页</a>
</div>
<nav class="navbar navbar-default navbar-fixed-bottom">
    <div class="container">
        <p class="navbar-text" id="bottom">Copyright © 2016-2018 51report.com</p>
    </div>
</nav>
</body>
</html>
