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
        .pagination li a {
            cursor: default;
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
                <input type="button" id="selected" class="btn btn-default" value="查询"/>
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
                <tbody id="logList">
                </tbody>
            </table>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-8  text-center">
            <nav>
                <ul class="pagination pagination-sm" id="pageBar">
                </ul>
            </nav>
            <span id="pageText"></span>
        </div>
    </div>
</div>
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript" src="jsp/js/jquery.page.js"></script>
<script type="text/javascript" src="jsp/js/jquery.form.js"></script>
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

        $.get("admin/user/getLogByParams.do", function (res, state) {
            if (state == "success") {
                var json = JSON.parse(res);
                setLogListData(json.logList);
                setPageBar(json.page);
            }
        });

        /**
         * 获取日志列表
         */
        function getLogList(a, b, c, d) {
            var data = "beginTime=" + a + "&endTime=" + b + "&oprator=" + c + "&pageNow=" + d;
            $.post("admin/user/getLogByParams.do", data, function (res, state) {
                if (state == "success") {
                    var json = JSON.parse(res);
                    setLogListData(json.logList);
                }
            });
        }

        /**
         * 设置log列表数据
         */
        function setLogListData(logs) {
            $("#logList").empty();
            $.each(logs,function(i,log){
                var tr = $("<tr/>");
                var td1 = $("<td/>").text(formatDate(log.logDate));
                var td2 = $("<td/>").text(log.opration);
                var td3 = $("<td/>").text(log.oprator.userName);
                tr.append(td1).append(td2).append(td3);
                $("#logList").append(tr);
            });
        }

        /**
         * 设置分页
         * @param str
         */
        function setPageBar(str) {
            $("#pageBar").createPage({
                pageCount: str.totalPageCount,
                current: str.pageNow,
                backFn: function (p) {
                    getLogList("", "", "", p);
                }
            });
            $("#pageText").text('共' + str.totalPageCount + "页，" + str.totalCount + "条");
        }

        /**
         * 搜索操作
         */
        $("#selected").click(function(){
            $("form").ajaxSubmit(function(res,state){
                if (state == "success") {
                    var json = JSON.parse(res);
                    setLogListData(json.logList);
                    setPageBar(json.page);
                }else{
                    Modal.alert({msg:"操作失败!"});
                }
            });
        });

        /**
         * 格式化时间
         * @param str
         * @returns {string}
         */
        function formatDate(str) {
            if (str == null) return;
            var date = new Date(str);
            var mounth = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth();
            var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            var h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var m = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            return date.getFullYear() + "-" + mounth + "-" + day + " " + h + ":" + m + ":" + s;
        }
    });
</script>
</html>
