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
        tr{
            cursor: pointer;
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
                        <td>被举报公司</td>
                        <td>举报类型</td>
                        <td>举报人</td>
                    </tr>
                    </thead>
                    <tbody class="text-center">
                    <c:forEach items="${caseList}" var="caseInfo" varStatus="i">
                        <tr onclick="look(${caseInfo.rcId},${caseInfo.caseState},this,${caseInfo.company.companyId},${caseInfo.currentHandler.companyId})">
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
                            <td>${caseInfo.company.companyName}</td>
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
                        <td>所属公司</td>
                        <td>账号状态</td>
                        <td>停留时间</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody class="text-center">
                    <c:forEach items="${userLogList}" var="userLog" varStatus="i">
                        <tr data-toggle="modal" data-target="#toViewUseLog" onclick="lookUseLog(${userLog.oprationLog.oprator.userId})">
                            <td><fmt:formatDate value="${userLog.oprationLog.logDate}" type="date"
                                                pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                            <td>${userLog.oprationLog.oprator.loginName}</td>
                            <td>${userLog.oprationLog.oprator.userCompany.companyName}</td>
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
                        <td>被举报公司</td>
                        <td>举报类型</td>
                        <td>举报人</td>
                    </tr>
                    </thead>
                    <tbody class="text-center">
                    <c:forEach items="${clientCaseList}" var="caseInfo" varStatus="i">
                        <tr onclick="look(${caseInfo.rcId},${caseInfo.caseState},this,${caseInfo.company.companyId},${caseInfo.currentHandler.companyId})">
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
                            <td>${caseInfo.company.companyName}</td>
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
                        <td>被举报公司</td>
                        <td>举报类型</td>
                        <td>举报人</td>
                    </tr>
                    </thead>
                    <tbody class="text-center">
                    <c:forEach items="${notClientCaseList}" var="caseInfo" varStatus="i">
                        <tr onclick="look(${caseInfo.rcId},${caseInfo.caseState},this,${caseInfo.company.companyId},${caseInfo.currentHandler.companyId})">
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
                            <td>${caseInfo.company.companyName}</td>
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
<!--查看案件信息对话框-->
<div class="modal fade bs-example-modal-lg" id="reprotInfoPanel" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title text-center">案件详情</h5>
            </div>
            <div class="modal-body">
                <iframe frameborder="0" width="100%" height="650px"></iframe>
            </div>
        </div>
    </div>
</div>

<!--查看用户操作日志-->
<div class="modal fade bs-example-modal-md" id="toViewUseLog" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title text-center">用户操作日志</h5>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="table-info">
                                <th>操作日期</th>
                                <th>操作内容</th>
                                <th>操作人</th>
                            </tr>
                            </thead>
                            <tbody id="logTable">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</html>

<script>
    $(function(){
        window.look = function (rcId, caseState, ele,companyId,currentCompanyId) {
            var url = "admin/caseBack/updateCaseState.do";
            var data = "rcId=" + rcId + "&state=" + 2
                    + "&sendToPlatform=0&companyId="+companyId;
            if (caseState < 2 && "${user.userCompany.companyId}"==currentCompanyId) {
                $.post(url, data, function (res, status) {
                    if (status == "success") {
                        if (res == "success") {
                            $(ele).find("td:nth-child(3)").text("已查看");
                        }
                    }
                });
            }
            $("#reprotInfoPanel iframe").attr("src", "admin/caseBack/showCaseById.do?rcId=" + rcId);
            $("#reprotInfoPanel").modal('show');
        };

        /*查看该用户操作日志*/
        window.lookUseLog = function (userId) {
            console.log(userId);
            $("#logTable").empty();
            $.get("admin/user/getUserLog.do?oprator=" + userId, function (res, status) {
                if (status == "success") {
                    $.each(JSON.parse(res), function (i, log) {
                        var tr = $("<tr/>");
                        var date = new Date(log.logDate);
                        var mounth = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth();
                        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
                        var h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
                        var m = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                        var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
                        var td1 = $("<td/>").text(date.getFullYear() + "-" + mounth + "-" + day + " " + h + ":" + m + ":" + s);
                        var td2 = $("<td/>").text(log.opration);
                        var td3 = $("<td/>").text(log.oprator.userName);
                        tr.append(td1).append(td2).append(td3);
                        $("#logTable").append(tr);
                    });
                }
            });
        }
    })
</script>
