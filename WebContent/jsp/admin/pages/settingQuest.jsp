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
    <meta http-equiv="X-UA-Compatible" content="IE=7,IE=8,IE=edge">
    <meta http-equiv="cache-control" content="no-cache">
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
        <small>公司问题设置</small>
    </h1>
    <div class="page-header"></div>
    <div class="row">
        请选择需要展示的问题(<strong>如未选择任何问题，将默认使用系统提供的所有问题</strong>)
    </div>
    <form>
        <div class="row">
            <div class="col-sm-8">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr class="table-info">
                        <th>编号</th>
                        <th>问题描述</th>
                        <th>是否展示</th>
                        <th>是否必填</th>
                    </tr>
                    </thead>
                    <tbody id="quests">
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8 text-right">
                <input type="button" class="btn btn-default" value="提交"/>
            </div>
        </div>
    </form>
</div>
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        $('[data-toggle="tooltip"]').tooltip();

        <c:forEach items="${questList}" var="quest" varStatus="i">
        var tr = $("<tr/>");
        var td1 = $("<td/>").text("${quest.questId}");
        var txt = "${quest.quest}";
        var td2 = $("<td/>").addClass("overflow-text")
                .attr("data-toggle", "tooltip").attr("data-placement", "bottom").attr("title", txt)
                .text(txt);
        var td3 = $("<td/>");
        var input1 = $("<input/>").attr("type","checkbox").val("${quest.questId}");
        if(${quest.mark=='1'}){
            input1.attr("checked",true);
        }
        td3.append(input1);
        var td4 = $("<td/>");
        var input2 = $("<input/>").attr("type","checkbox").val("${quest.questId}");
        if(${quest.isNeeded=='1'}){
            input2.attr("checked",true);
        }
        td4.append(input2);
        tr.append(td1).append(td2).append(td3).append(td4);
        $("#quests").append(tr);
        </c:forEach>

        if ($("tbody tr td:nth-child(3) input[type=checkbox]:checked").length <= 0){
            $("tbody tr td:nth-child(3)").each(function (i, e) {
                $(e).find("input").get(0).checked = true;
            });
        }

        $("input[type=button]").click(function () {
            if ($("tbody tr td:nth-child(3) input[type=checkbox]:checked").length <= 0){
                console.log($("tbody tr td:nth-child(3) input[type=checkbox]:checked").length);
            return Modal.alert({msg: "您未选择问题将使用系统默认的问题!"})
                        .on(function (e) {
                            if (e) {
                                sendForm();
                            }
                        });
            } else {
                sendForm();
            }
        });

        function sendForm() {
            var datas = [];
            $("tbody tr").each(function () {
                var data = {};
                var id = $(this).find("td:nth-child(3) input").filter(':checked').val();
                var isNeeded = $(this).find("td:nth-child(4) input").is(':checked');
                if (id == null) return;
                data['questId'] = id;
                data['isNeeded'] = isNeeded == true ? 1 : 0;
                datas.push(data);
            });
            $.post("admin/companyBack/addCompanyQuestions.do",
                    "comQuestList=" + JSON.stringify(datas),
                    function (res, status) {
                        if (status == "success") {
                            if (res == "success") {
                                Modal.alert({
                                    msg: '操作成功！',
                                }).on(function (e) {
                                    location.reload();
                                });
                            } else {
                                Modal.alert({msg:'操作失败！'});
                            }
                        } else {
                            Modal.alert({msg:'操作失败！'});
                        }
                    });
        }
    });
</script>
</html>