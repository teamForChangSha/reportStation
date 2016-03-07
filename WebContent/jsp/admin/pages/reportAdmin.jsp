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

        #time {
            width: 90px;
        }

        th,
        td {
            text-align: center;
        }

        .table > tbody > tr > td {
            vertical-align: inherit;
        }

        .col-sm-12 {
            padding-left: 0;
        }

        .glyphicon {
            color: #C12E2A;
            border: 3px solid #C12E2A;
            border-radius: 50px;
        }
    </style>
</head>

<body>
<div class="container">
    <form action="admin/caseBack/showCaseByCompany.do" method="post">
        <div class="row">
            <h1>
                <small>举报管理</small>
            </h1>
            <div class="page-header"></div>
            <div class="form-inline">
                <div class="form-group">
                    <label class="control-label">选择时间段：</label>
                    <input type='text' name="startTime" style="width: 110px;" class="datepicker-here form-control"
                           data-position="right top"/>
                </div>
                <div class="form-group">
                    <label class="control-label">到</label>
                    <input type='text' name="endTime" style="width: 110px;" class="datepicker-here form-control"
                           data-position="right top"/>
                </div>
                <div class="form-group" style="margin-left: 10px;">
                    <label class="control-label">审批状态</label>
                    <select id="selectStatus" class="form-control">
                        <option value="-1">-请选择-</option>
                        <option value="1">新建</option>
                        <option value="2">已查看</option>
                        <option value="3">处理中</option>
                        <option value="4">处理完毕</option>
                        <option value="5">关闭案件</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-inline">
                <div class="form-group">
                    <label class="control-label">所举报类型：</label>
                    <input type='text' name="rtList" style="width: 200px;" class="form-control" placeholder="请输入举报类型"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-inline">
                <div class="form-group">
                    <label class="control-label">搜索关键字：</label>
                    <input type="text" name="keyWord" class="form-control" style="width: 200px;"
                           placeholder="可查询问题答案关键字">
                </div>
                <input type="submit" class="btn btn-default" value="搜索"/>
            </div>
        </div>
    </form>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="table-info">
                    <th>举报人</th>
                    <th>举报时间</th>
                    <th>举报状态</th>
                    <th>举报对象</th>
                    <th>举报类型</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>

    <!--状态更新-->
    <div class="row hidden" id="updataStatus">
        <div class="col-sm-12">
            <div class="page-header"></div>
            <h4 class="col-sm-2">状态更新</h4>

            <div class="col-sm-4">
                <div class="form-horizontal">
                    <p class="hidden alert alert-warning">当前案件已交由平台方处理，您暂时无法更改，请等待平台处理结果或致电管理员</p>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">状态：</label>

                        <div class="col-sm-8">
                            <select id="status" class="form-control">
                                <option value="-1">-请选择-</option>
                                <option value="1">新建</option>
                                <option value="2">已查看</option>
                                <option value="3">处理中</option>
                                <option value="4">处理完毕</option>
                                <option value="5">关闭案件</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label"></label>

                        <div class="col-sm-8">
                            <div class="checkbox">
                                <label>
                                    <input id="sendToPlatform" type="checkbox"/> 是否交由平台方处理
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label"></label>

                        <div class="col-sm-8">
                            <input type="button" id="updataCancel" class="btn btn-default" value="取消">
                            <input type="button" id="updataBtn" class="btn btn-default" value="更改">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--追加信息-->
    <div class="row hidden" id="appendInfo">
        <div class="col-sm-12">
            <div class="page-header"></div>
            <h4 class="col-sm-2">追加信息</h4>

            <div class="col-sm-6">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">${user.userName}：</label>

                        <div class="col-sm-10">
                            <textarea id="content" class="form-control" rows="5"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>

                        <div class="col-sm-10 text-right">
                            <input type="button" id="appendCancel" class="btn btn-default" value="取消">
                            <input type="button" id="appendBtn" class="btn btn-default" value="确定">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        var search = {
            startTime: $("input[name=startTime]"),
            endTime: $("input[name=endTime]"),
            rtList: $("input[name=rtList]"),
            keyword: $("input[name=keyword]")
        }
        search.startTime.datepicker({
            language: 'zh',
            autoClose: true,
            navTitles: {
                days: '<h6>当前日期:yyyy年mm月dd日</h6> '
            }
        });
        search.endTime.datepicker({
            language: 'zh',
            autoClose: true,
            navTitles: {
                days: '<h6>当前日期:yyyy年mm月dd日</h6> '
            }
        });
        search.endTime.blur(function () {
            if (new Date(search.endTime.val()) < new Date(search.startTime.val())) {
                search.endTime.val("");
                return Modal.alert({msg: "结束时间应大于开始时间!"});
            }
        });
        search.startTime.blur(function () {
            if (new Date(search.endTime.val()) < new Date(search.startTime.val())) {
                search.startTime.val("");
                return Modal.alert({msg: "开始时间应小于结束时间!"});
            }
        });


        var updataStatus = $("#updataStatus");
        var appendInfo = $("#appendInfo");
        var caseId;

        var ele = {
            status: $("#status"),
            sendToPlatform: $("#sendToPlatform"),
            content: $("#content"),
            updataCancel: $("#updataCancel"),
            updataBtn: $("#updataBtn"),
            appendCancel: $("#appendCancel"),
            appendBtn: $("#appendBtn")
        }

        var i = 0;
        <c:forEach items="${caseList}" var="caseInfo" varStatus = "i">
        if ("${caseInfo.caseState}" == "1") {
            i++;
        }
        var url = "dict/getDictName.do?dictType=case.state&dictValue=${caseInfo.caseState}";
        $.get(url, function (res) {
            var tr = $("<tr/>");
            var td1 = $("<td/>").text("${caseInfo.reporter.name}"==""?"匿名":"${caseInfo.reporter.name}");
            var td2 = $("<td/>").text('<fmt:formatDate value="${caseInfo.createTime}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/>');
            var td3 = $("<td/>").text(res);
            var td4 = $("<td/>").text("${caseInfo.company.companyName}");
            var td5 = $("<td/>").text("${caseInfo.rtList}");
            var updata = $("<button/>").addClass("btn btn-link").text("修改");
            var look = $("<button/>").addClass("btn btn-link").text("查看");
            var append = $("<button/>").addClass("btn btn-link").text("追加");
            var td6 = $("<td/>");
            td6.append(updata).append(look).append(append);
            tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
            look.click(function () {
                location.href = "admin/caseBack/showCaseById.do?rcId=${caseInfo.rcId}";
            });
            updata.click(function () {
                $("tbody tr").removeClass("danger");
                $(this).parent().parent().addClass("danger");
                hiddenAll();
                updataStatus.removeClass("hidden");
                caseId = "${caseInfo.rcId}";
                ele.status.get(0).value = "${caseInfo.caseState}";
                if ("${user.userCompany.companyId}" != "${caseInfo.currentHandler.companyId}") {
                    updataStatus.find("p").removeClass("hidden");
                    ele.sendToPlatform.get(0).checked = true;
                    ele.updataBtn.attr("disabled", true);
                } else {
                    updataStatus.find("p").addClass("hidden");
                    ele.sendToPlatform.get(0).checked = false;
                    ele.updataBtn.removeAttr("disabled");
                }
            });
            append.click(function () {
                $("tbody tr").removeClass("danger");
                $(this).parent().parent().addClass("danger");
                hiddenAll();
                appendInfo.removeClass("hidden");
                caseId = "${caseInfo.rcId}";
            });
            $("tbody").append(tr);
        });
        </c:forEach>
        $(window.parent.document).find(".badge").text("" + i);

        ele.updataCancel.click(function () {
            updataStatus.addClass("hidden");
            $("tbody tr").removeClass("danger");
        });
        ele.appendCancel.click(function () {
            appendInfo.addClass("hidden");
            $("tbody tr").removeClass("danger");
        });
        ele.updataBtn.click(function () {
            if (ele.status.find("option:selected").val() == "-1") {
                return Modal.alert({msg: "请选择需要改变的状态!"});
            }
            var url = "admin/caseBack/updateCaseState.do";
            var sendToPlatform = "0";
            if (ele.sendToPlatform.is(':checked')) {
                sendToPlatform = "1";
            }
            var data = "rcId=" + caseId + "&state=" + ele.status.find("option:selected").val()
                    + "&sendToPlatform=" + sendToPlatform + "&companyId=" + "${user.userCompany.companyId}";
            console.log(data);
            $.post(url, data, function (res, status) {
                if (status == "success") {
                    if (res == "success") {
                        Modal.alert({msg: "操作成功!"})
                                .on(function (e) {
                                    location.href = "admin/caseBack/showCaseByCompany.do";
                                    updataStatus.addClass("hidden");
                                    $("tbody tr").removeClass("danger");
                                });

                    } else {
                        Modal.alert({msg: "操作失败!"});
                    }
                } else {
                    Modal.alert({msg: "操作失败!"});
                }
            });
        });
        ele.appendBtn.click(function () {
            if (isEmty(ele.content.val())) {
                return Modal.alert({msg: "请填写追加的内容!"});
            }
            var url = "admin/caseBack/addCaseComment.do";
            var data = "rcId=" + caseId + "&content=" + ele.content.val();
            console.log(data);
            $.post(url, data, function (res, status) {
                if (status == "success") {
                    if (res == "success") {
                        Modal.alert({msg: "操作成功!"})
                                .on(function (e) {
                                    appendInfo.addClass("hidden");
                                    $("tbody tr").removeClass("danger");
                                });
                    } else {
                        Modal.alert({msg: "操作失败!"});
                    }
                } else {
                    Modal.alert({msg: "操作失败!"});
                }
            });
        });
        $("#selectStatus").change(function () {
            $("tbody tr td:nth-child(3)").each(function () {
                console.log($(this).text() + "\n" + $("#selectStatus").find("option:selected").text());
                if ($(this).text() == $("#selectStatus").find("option:selected").text()) {
                    $(this).parent().removeClass("hidden");
                } else {
                    $(this).parent().addClass("hidden");
                }
                if ($("#selectStatus").find("option:selected").val() == "-1") {
                    $(this).parent().removeClass("hidden");
                }
            });
        });

        function hiddenAll() {
            updataStatus.addClass("hidden");
            appendInfo.addClass("hidden");
        }

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
