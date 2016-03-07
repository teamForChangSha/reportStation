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
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
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
    <h1>
        <small>举报类型设置</small>
    </h1>
    <div class="page-header"></div>
    <div class="row">
        以下为系统默认举报类型供选择
    </div>
    <div class="row" id="systemTypeList">
        <div class="col-sm-8">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="table-info">
                    <th>类型</th>
                    <th class="">描述</th>
                    <th>是否需要</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
    <div class="row hidden" id="enterTypeList" style="padding: 0 15px;">
        <div class="row">
            <div class="page-header"></div>
            <p>企业订制举报类型</p>

            <div class="col-sm-8">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr class="table-info">
                        <th>类型</th>
                        <th class="">描述</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8 text-right">
                <input type="button" id="reported" class="btn btn-default" value="提交"/>
            </div>
        </div>
    </div>
    <div class="row hidden" id="addType">
        <div class="page-header"></div>
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">类型名称：</label>

                <div class="col-sm-4">
                    <input id="typeName" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">类型描述：</label>

                <div class="col-sm-4">
                    <textarea id="typeDesc" type="text" rows="6" class="form-control"></textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>

                <div class="col-sm-4 text-right">
                    <input type="button" id="cancel" class="btn btn-default" style="margin-right: 10px;" value="取消"/>
                    <input type="button" id="ok" class="btn btn-default" value="确定"/>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <input type="button" name="addType" class="btn btn-default" value="添加类型"/>
    </div>
</div>
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        var reportTypes = [];
        var ele = {
            sysType: $("#systemTypeList"),
            enterType: $("#enterTypeList"),
            addType: $("#addType"),
            cancel: $("#cancel"),
            ok: $("#ok"),
            addTypeBtn: $("input[name=addType]"),
            typeName: $("#typeName"),
            typeDesc: $("#typeDesc"),
            reported: $("#reported")
        }
        $('[data-toggle="tooltip"]').tooltip();
        /**
         * 添加系统提供的默认举报类型
         */
                <c:forEach items="${delfRtList}" var="reportType" varStatus = "i">
        var tr = $("<tr/>");
        var td1 = $("<td/>").text("${reportType.rtTitle}");
        var txt = "${reportType.rtDesc}";
        var td2 = $("<td/>").addClass("overflow-text")
                .attr("data-toggle", "tooltip").attr("data-placement", "bottom").attr("title", txt)
                .text(txt);
        var checkbox = $("<input/>").attr("type", "checkbox");
        checkbox.change(function () {
            if ($(this).is(":checked")) {
                if (ele.enterType.hasClass("hidden")) {
                    ele.enterType.removeClass("hidden");
                }
                $(this).parent().parent().addClass("hidden");
                var tr = $("<tr/>");
                var td1 = $("<td/>").text($(this).parent().prev().prev().text());
                var td2 = $("<td/>").addClass("overflow-text")
                        .attr("data-toggle", "tooltip").attr("data-placement", "bottom").attr("title", $(this).parent().prev().text())
                        .text($(this).parent().prev().text());
                var cancel = $("<input/>").attr("type", "button").addClass("btn btn-link").val("取消");
                cancel.click(function () {
                    $(this).parent().parent().remove();
                    if ($("#enterTypeList tbody tr").length == 0) {
                        ele.enterType.addClass("hidden");
                    }
                    $("#systemTypeList tbody tr td:nth-child(1)").each(function () {
                        if ($(this).text() == cancel.parent().prev().prev().text()) {
                            $(this).parent().removeClass("hidden");
                            $(this).parent().find("input").removeAttr("checked");
                        }
                    });
                });
                var td3 = $("<td/>").append(cancel);
                tr.append(td1).append(td2).append(td3);
                $("#enterTypeList tbody").append(tr);
            } else {
                $("#enterTypeList tbody tr td:nth-child(1)").each(function () {
                    if ($(this).text() == checkbox.parent().prev().prev().text()) {
                        $(this).parent().remove();
                    }
                });
            }
        });
        var td3 = $("<td/>").append(checkbox);
        tr.append(td1).append(td2).append(td3);
        $("#systemTypeList tbody").append(tr);
        </c:forEach>

        /**
         * 添加企业订制的问题类型
         */
        <c:forEach items="${rtList}" var="reportType" varStatus = "i">
        $("#systemTypeList tbody tr td:nth-child(1)").each(function () {
            if ($(this).text() == "${reportType.rtTitle}") {
                $(this).parent().addClass("hidden");
            }
        });
        var tr = $("<tr/>");
        var td1 = $("<td/>").text("${reportType.rtTitle}");
        var txt = "${reportType.rtDesc}";
        var td2 = $("<td/>").addClass("overflow-text")
                .attr("data-toggle", "tooltip").attr("data-placement", "bottom").attr("title", txt)
                .text(txt);
        var cancel = $("<input/>").attr("type", "button").addClass("btn btn-link").val("取消");
        cancel.click(function () {
            $("#systemTypeList tbody tr td:nth-child(1)").each(function () {
                if ($(this).text() == cancel.parent().prev().prev().text()) {
                    $(this).parent().removeClass("hidden");
                }
            });
            $(this).parent().parent().remove();
            if ($("#enterTypeList tbody tr").length == 0) {
                ele.enterType.addClass("hidden");
            }
        });
        var td3 = $("<td/>").append(cancel);
        tr.append(td1).append(td2).append(td3);
        $("#enterTypeList tbody").append(tr);
        </c:forEach>
        if ($("#enterTypeList tbody tr").length > 0) {
            ele.enterType.removeClass("hidden");
        }
        ele.reported.click(function () {
            $("#enterTypeList tbody tr").each(function () {
                var title = $(this).find("td:nth-child(1)").text();
                title = title.replace(/[\r\n]/g, "").replace(/\"/g, "'");
                var desc = $(this).find("td:nth-child(2)").text();
                desc = desc.replace(/[\r\n]/g, "").replace(/\"/g, "'");
                var reportType = {};
                reportType["rtTitle"] = title;
                reportType["rtDesc"] = desc;
                reportTypes.unshift(reportType);
            });
            $.post("admin/companyBack/addQuestionTypes.do", "reportType=" + JSON.stringify(reportTypes), function (res, status) {
                if (status == "success") {
                    if (res == "success") {
                        Modal.alert({
                            msg: '操作成功！',
                        }).on(function (e) {
                            location.reload();
                        });
                    } else {
                        Modal.alert({
                            msg: '操作失败！',
                        });
                    }
                } else {
                    Modal.alert({
                        msg: '操作失败！',
                    });
                }
            });
        });
        ele.addTypeBtn.click(function () {
            ele.addType.removeClass("hidden");
            $(this).addClass("hidden");
        });
        ele.cancel.click(function () {
            ele.addType.addClass("hidden");
            ele.addTypeBtn.removeClass("hidden");
        });
        ele.ok.click(function () {
            var name = ele.typeName.val();
            var desc = ele.typeDesc.val();
            if (!isEmty(name) && !isEmty(desc)) {
                var tr = $("<tr/>");
                var td1 = $("<td/>").text(name);
                var td2 = $("<td/>").addClass("overflow-text")
                        .attr("data-toggle", "tooltip").attr("data-placement", "bottom").attr("title", desc).text(desc);
                var cancel = $("<input/>").attr("type", "button").addClass("btn btn-link").val("取消");
                cancel.click(function () {
                    $(this).parent().parent().remove();
                    if ($("#enterTypeList tbody tr").length == 0) {
                        ele.enterType.addClass("hidden");
                    }
                });
                var td3 = $("<td/>").append(cancel);
                tr.append(td1).append(td2).append(td3);
                $("#enterTypeList tbody").append(tr);
                ele.addType.addClass("hidden");
                ele.addTypeBtn.removeClass("hidden");
                ele.typeName.val("");
                ele.typeDesc.val("");
                if (ele.enterType.hasClass("hidden")) {
                    ele.enterType.removeClass("hidden");
                }
            }
        });

        function isEmty(str) {
            str = $.trim(str);
            if (str == null || str.length <= 0 || str == "") {
                return true;
            }
            return false;
        }
    });
</script>

</html>