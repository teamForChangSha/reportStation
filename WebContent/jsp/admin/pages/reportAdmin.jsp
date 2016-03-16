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

    </style>
</head>

<body>
<div class="container">
    <form id="selectForm" action="admin/caseBack/showCaseByCompany.do" method="post">
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
                <input type="button" id="submitSelect" class="btn btn-default" value="搜索"/>
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
</div>

<!--修改案件状态对话框-->
<div class="modal fade bs-example-modal-sm" id="updataReportStatus" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title text-center">更改案件状态</h5>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">
                    <p class="hidden alert alert-warning">当前案件已交由平台方处理，您暂时无法更改，请等待平台处理结果或致电管理员</p>

                    <div class="form-group">
                        <label class="col-sm-1 control-label"></label>
                        <div class="col-sm-10">
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
                    <div class="form-group text-center">
                        <div class="checkbox">
                            <label>
                                <input id="sendToPlatform" type="checkbox"/> 是否交由平台方处理
                            </label>
                        </div>
                    </div>
                    <div class="form-group text-center">
                        <input type="button" id="updataCancel" class="btn btn-default" value="取消">
                        <input type="button" id="updataBtn" class="btn btn-default" value="更改">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

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

        search.startTime.keyup(function () {
            search.startTime.val("");
        });
        search.endTime.keyup(function () {
            search.endTime.val("");
        });

        $("#submitSelect").click(function () {
            if (search.startTime.val() != "") {
                if (search.endTime.val() == "") {
                    return Modal.alert({msg: "请选择结束时间"});
                }
            }
            if (search.endTime.val() != "") {
                if (search.startTime.val() == "") {
                    return Modal.alert({msg: "请选择起始时间"});
                }
            }
            $("#selectForm").submit();
        });


        var caseId;

        var ele = {
            status: $("#status"),
            sendToPlatform: $("#sendToPlatform"),
            updataCancel: $("#updataCancel"),
            updataBtn: $("#updataBtn")
        }

        var i = 0;
        <c:forEach items="${caseList}" var="caseInfo" varStatus = "i">
        if ("${caseInfo.caseState}" == "1") {
            i++;
        }
        var tr = $("<tr/>");
        var td1 = $("<td/>").css("width", "60px").text("${caseInfo.reporter.name}" == "" ? "匿名" : "${caseInfo.reporter.name}");
        var td2 = $("<td/>").css("width", "180px").text('<fmt:formatDate value="${caseInfo.createTime}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/>');
        var td3 = $("<td/>").css("width", "80px");
        switch (${caseInfo.caseState}) {
            case 1:
                td3.text("新建");
                break;
            case 2:
                td3.text("已查看");
                break;
            case 3:
                td3.text("处理中");
                break;
            case 4:
                td3.text("处理完毕");
                break;
            case 5:
                td3.text("关闭案件");
                break;
        }
        var td4 = $("<td/>").css("width", "240px").text("${caseInfo.company.companyName}");
        var td5 = $("<td/>").text("${caseInfo.rtList}");
        var updata = $("<button/>").addClass("btn btn-link").text("修改");
        var look = $("<button/>").addClass("btn btn-link").text("查看");
        var td6 = $("<td/>").css("width", "130px");
        td6.append(updata).append(look);
        tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
        look.click(function () {
            $("#reprotInfoPanel iframe").attr("src", "admin/caseBack/showCaseById.do?rcId=${caseInfo.rcId}");
            $("#reprotInfoPanel").modal('show');
        });
        updata.bind("click", function () {
            $("tbody tr").removeClass("danger");
            $(this).parent().parent().addClass("danger");
            $("#updataReportStatus").modal('show');
            caseId = "${caseInfo.rcId}";
            ele.status.get(0).value = "${caseInfo.caseState}";
            if ("${user.userCompany.companyId}" == "1") {
                if ("${user.userCompany.companyId}" == "${caseInfo.currentHandler.companyId}") {
                    $("#updataReportStatus p").removeClass("hidden");
                    ele.sendToPlatform.get(0).checked = true;
                    ele.updataBtn.removeAttr("disabled");
                } else {
                    $("#updataReportStatus p").addClass("hidden");
                    ele.sendToPlatform.get(0).checked = false;
                    ele.updataBtn.attr("disabled", true);
                }
            } else {
                if ("${user.userCompany.companyId}" == "${caseInfo.currentHandler.companyId}") {
                    $("#updataReportStatus p").addClass("hidden");
                    ele.sendToPlatform.get(0).checked = false;
                    ele.updataBtn.removeAttr("disabled");
                } else {
                    $("#updataReportStatus p").removeClass("hidden");
                    ele.sendToPlatform.get(0).checked = true;
                    ele.updataBtn.attr("disabled", true);
                }
            }
        });

        if ("${user.userCompany.companyId}" != "${caseInfo.currentHandler.companyId}") {
            if ("${user.userCompany.companyId}" == "1") {
                updata.unbind("click").click(function () {
                    Modal.alert({msg: '案件未交由平台方处理，您目前只能查看'});
                });
            } else {
                updata.unbind("click").click(function () {
                    Modal.alert({msg: '案件已交由平台方处理，请耐心等待处理结果，或联系平台管理方'});
                });
            }
        }
        $("tbody").append(tr);
        </c:forEach>
        $(window.parent.document).find(".badge").text("" + i);

        $("#updataReportStatus .close").click(function () {
            $("tbody tr").removeClass("danger");
        });
        ele.updataCancel.click(function () {
            $("tbody tr").removeClass("danger");
            $("#updataReportStatus").modal('hide');
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
            $.post(url, data, function (res, status) {
                if (status == "success") {
                    if (res == "success") {
                        Modal.alert({msg: "操作成功!"})
                                .on(function (e) {
                                    location.href = "admin/caseBack/showCaseByCompany.do";
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
    });
</script>

</html>
