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
        th,
        td {
            text-align: center;
        }

        .table > tbody > tr > td {
            vertical-align: inherit;
        }

        .table-info {
            background-color: #4a8bc2;
            background-image: linear-gradient(to bottom, #4a8bc2, #4a8bc2);
            color: #fff;
        }

        .container {
            padding: 20px 0;
        }

        .col-sm-10 {
            padding-left: 0;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="row">
        <h1>
            <small>分支机构管理</small>
        </h1>
        <div class="page-header"></div>
        <div class="col-sm-10">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="table-info">
                    <th>名称</th>
                    <th>地址</th>
                    <th>电话</th>
                    <th>联系人</th>
                    <th>邮编</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${branchList}" var="branch" varStatus="i">
                    <tr>
                        <td>${branch.branchName }</td>
                        <td>${branch.address }</td>
                        <td>${branch.phone }</td>
                        <td>${branch.contactor }</td>
                        <td>${branch.postCode }</td>
                        <td>
                            <input type="button" class="btn btn-link" onclick="updata(this,${branch.branchId})"
                                   value="修改"/>
                            <input type="button" class="btn btn-link" onclick="del(this,${branch.branchId})"
                                   value="删除"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row">
        <input type="button" name="addBranch" class="btn btn-default" value="添加分支机构"/>
    </div>

    <!--添加分支机构-->
    <div class="row hidden" id="addBranchPanel">
        <h1>
            <small>添加分支机构</small>
        </h1>
        <div class="page-header"></div>
        <form method="post" class="form-horizontal">
            <input type="text" id="branchId" name="branchId" hidden/>

            <div class="form-group">
                <div class="col-sm-2">选择机构省市</div>
                <div class="col-sm-2">
                    <select id="seachprov" name="province.areaId" class="form-control"
                            onChange="initCity(this.value);"></select>
                </div>
                <div class="col-sm-2">
                    <select id="seachcity" name="city.areaId" class="form-control"></select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">分支机构名称：</label>

                <div class="col-sm-4">
                    <input type="text" name="branchName" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">分支机构地址：</label>

                <div class="col-sm-4">
                    <input type="text" name="address" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">分支机构联系电话：</label>

                <div class="col-sm-4">
                    <input type="text" name="phone" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">分支机构联系人名称：</label>

                <div class="col-sm-4">
                    <input type="text" name="contactor" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">分支机构邮编：</label>

                <div class="col-sm-4">
                    <input type="text" name="postCode" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>

                <div class="col-sm-4">
                    <input type="button" name="cancel" class="btn btn-default col-sm-5" value="取消"/>
                    <input type="button" name="add" class="btn btn-default col-sm-5 pull-right" value="提交"/>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        /* 初始化省市 */
        (function () {
            var b = $("#seachprov");
            var url = "admin/areaBack/getAllProvice.do";
            $.getJSON(url, function (result) {
                if (result == null || result.length <= 0) {
                    return;
                }
                b.empty();
                var opt = $("<option/>").text("--请选择--").attr("value", "0");
                b.append(opt);
                $(result).each(
                        function () {
                            var opt = $("<option/>").text(this.name).attr("value",
                                    this.areaId);
                            b.append(opt);
                        });
            });
        })();

        $("input[name=addBranch]").click(function () {
            $("#addBranchPanel small").text("添加分支机构");
            $("#addBranchPanel").removeClass("hidden");
            $("#branchId").removeAttr("name").val("");
            $("#seachprov").parent().parent().removeClass("hidden");
            $("#seachprov").get(0).value = 0;
            $("#seachcity").get(0).value = 0;
            $("input[name=branchName]").val("");
            $("input[name=address]").val("");
            $("input[name=phone]").val("");
            $("input[name=contactor]").val("");
            $("input[name=postCode]").val("");
        });
        $("input[name=cancel]").click(function () {
            $("#addBranchPanel").addClass("hidden");
        });

        $("input[name=add]").click(function () {
            var url;
            if ($("#branchId").val() != "") {
                url = "admin/companyBack/updateCompanyBranch.do"
            } else {
                url = "admin/companyBack/addCompanyBranches.do"
                if ($("#seachprov").find("option:selected").val() == "0") {
                    return alr("请选择省");
                }
                if ($("#seachcity").find("option:selected").val() == "0") {
                    return alr("请选择市");
                }
            }
            var branchName = $.trim($("input[name=branchName]").val());
            if (branchName == null || branchName == "" || branchName.length <= 0) {
                return alr("请输入分支机构名称");
            }
            var address = $.trim($("input[name=address]").val());
            if (address == null || address == "" || address.length <= 0) {
                return alr("请输入分支机构地址");
            }
            var data = $("form").serialize();
            $.post(url, data, function (res, status) {
                alertMsg(res, status);
            });
        });

        function alr(masg) {
            Modal.alert({
                msg: masg,
            });
        }
    });

    /**
     * 初始化市
     */
    function initCity(a) {
        var c = $("#seachcity");
        var url = "admin/areaBack /getChildrenArea.do?areaId=" + a;
        $.getJSON(url, function (result) {
            if (result == null || result.length <= 0) {
                return;
            }
            c.empty();
            var opt = $("<option/>").text("--请选择--").attr("value", "0");
            c.append(opt);
            $(result).each(
                    function () {
                        var opt = $("<option/>").text(this.name).attr("value",
                                this.areaId);
                        c.append(opt);
                    });
        });
    }

    function updata(ele, id) {
        $("#addBranchPanel small").text("修改分支机构信息");
        $("#addBranchPanel").removeClass("hidden");
        $("#branchId").attr("name", "branchId").val(id);
        $("#seachprov").parent().parent().addClass("hidden");
        $("input[name=branchName]").val($(ele).parent().prev().prev().prev().prev().prev().text());
        $("input[name=address]").val($(ele).parent().prev().prev().prev().prev().text());
        $("input[name=phone]").val($(ele).parent().prev().prev().prev().text());
        $("input[name=contactor]").val($(ele).parent().prev().prev().text());
        $("input[name=postCode]").val($(ele).parent().prev().text());

    }

    function del(ele, id) {
        Modal.confirm({
            title: '警告',
            msg: '你确定要删除吗?',
        }).on(function (e) {
            if (e) {
                $.get("admin/companyBack/deleteCompanyBranch.do?branchId=" + id, function (res, status) {
                    alertMsg(res, status);
                });
            }
        });
    }

    /* 弹出操作消息 */
    function alertMsg(res, status) {
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
    }
</script>

</html>