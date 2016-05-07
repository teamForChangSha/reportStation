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
    <meta http-equiv="X-UA-Compatible" content="IE=7,IE=8,IE=edge">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <style type="text/css">
        .container {
            margin-top: 30px;
            margin-bottom: 30px;
        }

        .row {
            margin-top: 20px;
        }

        select[multiple] {
            margin-top: 10px;
            height: 200px;
        }
    </style>
</head>

<body>
<div class="container">
    <h1>
        <small>企业名称维护</small>
    </h1>
    <div class="page-header"></div>
    <div class="row text-center">
        <div class="col-sm-4">
            <div class="form-inline">
                <div class="form-group">
                    <label class="control-label">名称：</label>
                    <input type="text" onkeyup="onCompanyKeyup(this)" class="form-control">
                </div>
            </div>
            <select name="enters" multiple class="form-control">
            </select>
        </div>
        <div class="col-sm-2" style="padding-top: 60px;">
            <p>选择同意公司的不同名称</p>
            <button id="mergeBtn" class="btn btn-default">==></button>
            <br/><br/>
            <button id="removeBtn" class="btn btn-default"><==</button>
        </div>
        <div class="col-sm-3" style="padding-top: 8px;">
            <span class="control-label">点击选择公司的主要名称</span>
            <select name="merges" multiple class="form-control" style="margin-top: 15px;">
            </select>
        </div>
        <div class="col-sm-3" style="padding-top: 100px;">
            <div class="form-group">
                <label class="control-label">公司主要名称：</label>
                <input type="text" id="name" class="form-control">
            </div>
        </div>
    </div>
    <div class="row">
        <button class="btn btn-warning" onclick="sendMerges()" style="margin-left: 20px;">确定</button>
    </div>
</div>
</body>
<script type="text/javascript">
    $(function () {

        getAllCompanyByName('');

        function getAllCompanyByName(a) {
            $("select[name=enters]").empty();
            var data = "companyName=" + a;
            $.post("company/getAllByName.do", data, function (res) {
                $.each(JSON.parse(res), function (i, company) {
                    var opt = $("<option/>").val(company.companyId).text(company.companyName);
                    $("select[name=enters]").append(opt);
                });
            });
        }

        window.onCompanyKeyup = function (a) {
            getAllCompanyByName($(a).val());
        }

        $("#mergeBtn").click(function () {
            $("select[name=enters] option:selected").each(function () {
                $("select[name=enters] option:selected").remove();
                var opt = $("<option/>").val($(this).val()).text($(this).text());
                $("select[name=merges]").append(opt);
            });
        });
        $("#removeBtn").click(function () {
            $("select[name=merges] option:selected").each(function () {
                $("select[name=merges] option:selected").remove();
                var opt = $("<option/>").val($(this).val()).text($(this).text());
                $("select[name=enters]").append(opt);
            });
        });

        $("select[name=merges]").change(function () {
            $("#name").val($("select[name=merges] option:selected").text());
        });

        window.sendMerges = function () {
            var companys = new Array();
            $("select[name=merges] option").each(function () {
                companys.push($(this).val());
            });
            //TODO 不知道发送到哪个action明天确定再添加
        };

    });
</script>

</html>