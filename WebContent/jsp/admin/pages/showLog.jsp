<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <meta charset="UTF-8">
    <title></title>
    <link href="jsp/css/datepicker.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/datepicker.min.js"></script>
    <!-- Include English language -->
    <script src="jsp/js/datepicker.en.js"></script>
    <style type="text/css">
        .row {
            margin-top: 10px;
        }

        .container {
            margin-top: 30px;
            margin-bottom: 30px;
        }

        .table-info {
            background-color: #4a8bc2;
            background-image: linear-gradient(to bottom, #4a8bc2, #4a8bc2);
            color: #fff;
        }

        th,
        td {
            text-align: center;
        }

        .table > tbody > tr > td {
            vertical-align: inherit;
        }

        .col-sm-8 {
            padding-left: 0;
        }
    </style>
</head>

<body>
<div class="container">
    <!-- 操作日志 -->
    <div class="row">
        <h1>
            <small>操作日志</small>
        </h1>
        <div class="page-header"></div>
        <form action="admin/user/getLogByParams.do" method="post" class="form-inline">
            <div class="form-group">
                <label class="control-label">按条件查询</label>
                <input type="text" class="datepicker-here form-control" data-position="right top" name="beginTime"
                       placeholder="请输入开始时间"/>
                 <input type="text" class="datepicker-here form-control" data-position="right top" name="endTime"
                       placeholder="请输入结束时间"/>
                <c:if test="${user.userType>=3}">
                    <input type="text" class="form-control" name="oprator"
                           placeholder="请输入操作人姓名"/>
                </c:if>
                <input type="submit" class="btn btn-default" value="查询"/>
            </div>
        </form>
        <br>

        <div class="col-sm-8">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="table-info">
                    <th>操作日期</th>
                    <th>操作内容</th>
                    <th>操作人</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${logList}" var="log" varStatus="i">
                    <tr>
                        <td><fmt:formatDate value="${log.logDate}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                        <td>${log.opration}</td>
                        <td>${log.oprator.userName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        var logDate = $("input[name=beginTime]");
        logDate.datepicker({
            language: 'zh',
            autoClose: true,
            navTitles: {
                days: '<h6>当前日期:yyyy年mm月dd日</h6> '
            }
        });
        var endDate = $("input[name=endTime]");
        endDate.datepicker({
            language: 'zh',
            autoClose: true,
            navTitles: {
                days: '<h6>当前日期:yyyy年mm月dd日</h6> '
            }
        });
    });
</script>
</html>
