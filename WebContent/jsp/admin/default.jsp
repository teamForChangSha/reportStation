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
<body>
<div class="container">
    <c:if test="${user.userType<=2}">
        <div class="row">
            <div class="col-sm-8">
                <h1>
                    <small>最近案件简报</small>
                </h1>
                <div class="page-header"></div>
            </div>
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
                    <tbody class="text-center">
                    <c:forEach items="${caseList}" var="caseInfo" varStatus="i">
                        <tr>
                            <td>${caseInfo.trackingNo}</td>
                            <td><fmt:formatDate value="${caseInfo.createTime}" type="date"
                                                pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                            <td>
                                <c:if test='${caseInfo.caseState==1}'>新建</c:if>
                                <c:if test='${caseInfo.caseState==2}'>已查看</c:if>
                                <c:if test='${caseInfo.caseState==3}'>处理中</c:if>
                                <c:if test='${caseInfo.caseState==4}'>处理完毕</c:if>
                                <c:if test='${caseInfo.caseState==5}'>关闭案件</c:if>
                            </td>
                            <td>${caseInfo.rtList}</td>
                            <td>
                                <c:if test="${caseInfo.reporter.name==null}">匿名</c:if>
                                <c:if test="${caseInfo.reporter.name!=null}">${caseInfo.reporter.name}</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>

    <c:if test="${user.userType>=3}">
        <div class="row ">
            <div class="col-sm-8">
                <h1>
                    <small>最近十个用户操作情况简报</small>
                </h1>
                <div class="page-header"></div>
            </div>
            <div class="col-sm-10">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr class="table-info">
                        <td>登录时间</td>
                        <td>用户账号</td>
                        <td>账号状态</td>
                        <td>停留时间</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody class="text-center">
                    <c:forEach items="${userLogList}" var="userLog" varStatus="i">
                        <tr>
                            <td><fmt:formatDate value="${userLog.oprationLog.logDate}" type="date"
                                                pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                            <td>${userLog.oprationLog.oprator.loginName}</td>
                            <td>
                                <c:if test="${userLog.oprationLog.oprator.userState==1}">新增</c:if>
                                <c:if test="${userLog.oprationLog.oprator.userState==2}">有效</c:if>
                                <c:if test="${userLog.oprationLog.oprator.userState==3}">停用</c:if>
                                <c:if test="${userLog.oprationLog.oprator.userState==4}">注销</c:if>
                            </td>
                            <td>${userLog.times}</td>
                            <td>${userLog.oprationsStr}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8">
                <h1>
                    <small>客户公司相关案件</small>
                </h1>
                <div class="page-header"></div>
            </div>
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
                    <tbody class="text-center">
                    <c:forEach items="${clientCaseList}" var="caseInfo" varStatus="i">
                        <tr>
                            <td>${caseInfo.trackingNo}</td>
                            <td><fmt:formatDate value="${caseInfo.createTime}" type="date"
                                                pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                            <td>
                                <c:if test='${caseInfo.caseState==1}'>新建</c:if>
                                <c:if test='${caseInfo.caseState==2}'>已查看</c:if>
                                <c:if test='${caseInfo.caseState==3}'>处理中</c:if>
                                <c:if test='${caseInfo.caseState==4}'>处理完毕</c:if>
                                <c:if test='${caseInfo.caseState==5}'>关闭案件</c:if>
                            </td>
                            <td>${caseInfo.rtList}</td>
                            <td>
                                <c:if test="${caseInfo.reporter.name==null}">匿名</c:if>
                                <c:if test="${caseInfo.reporter.name!=null}">${caseInfo.reporter.name}</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8">
                <h1>
                    <small>非客户公司相关案件</small>
                </h1>
                <div class="page-header"></div>
            </div>
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
                    <tbody class="text-center">
                    <c:forEach items="${notClientCaseList}" var="caseInfo" varStatus="i">
                        <tr>
                            <td>${caseInfo.trackingNo}</td>
                            <td><fmt:formatDate value="${caseInfo.createTime}" type="date"
                                                pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                            <td>
                                <c:if test='${caseInfo.caseState==1}'>新建</c:if>
                                <c:if test='${caseInfo.caseState==2}'>已查看</c:if>
                                <c:if test='${caseInfo.caseState==3}'>处理中</c:if>
                                <c:if test='${caseInfo.caseState==4}'>处理完毕</c:if>
                                <c:if test='${caseInfo.caseState==5}'>关闭案件</c:if>
                            </td>
                            <td>${caseInfo.rtList}</td>
                            <td>
                                <c:if test="${caseInfo.reporter.name==null}">匿名</c:if>
                                <c:if test="${caseInfo.reporter.name!=null}">${caseInfo.reporter.name}</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
