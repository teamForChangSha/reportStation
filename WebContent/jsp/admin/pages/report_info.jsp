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
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <title></title>
    <style type="text/css">
        .navbar-text {
            float: inherit;
        }

        label {
            font-weight: 400;
        }

        .form-info {
            display: block;
            width: 100%;
            height: 34px;
            padding: 8px 12px;
            font-size: 14px;
            line-height: 1.42857;
            color: #555;
        }

    </style>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm-12 form-horizontal" id="content">
            <div class="form-group text-center">
                <h3><span class="label label-danger" id="caseId">案件编号：${reportCase.trackingNo }</span></h3>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">组织/机构：</label>

                <div class="col-sm-8">
                    <span class="form-info"
                          id="organization"><strong>${reportCase.company.companyName }</strong></span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">省份：</label>

                <div class="col-sm-8">
                    <span class="form-info" id="caseProvince">${reportCase.province }</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">城市：</label>

                <div class="col-sm-8">
                    <span class="form-info" id="caseCity">${reportCase.city }</span>
                </div>
            </div>
            <c:forEach items="${questionAnswerList}" var="quest" varStatus="i">
                <c:if test="${i.index=='0'}">
                    <div class="page-header"></div>
                </c:if>
                <div class="form-group">
                    <c:if test="${quest.questKey=='quest_1'}">
                        <label class="col-sm-6 control-label">您是${reportCase.company.companyName }员工吗：</label>
                    </c:if>
                    <c:if test="${quest.questKey!='quest_1'}">
                        <label class="col-sm-6 control-label">${quest.question }</label>
                    </c:if>
                    <div class="col-sm-6">
                        <span class="form-info">${quest.questValue}</span>
                    </div>
                </div>
            </c:forEach>
            <div class="page-header"></div>
            <div class="form-group">
                <label class="col-sm-4 control-label">其他材料：</label>

                <div class="col-sm-8">
                    <c:forEach items="${reportCase.attachList}" var="attach" varStatus="i">
                        <p class="form-info"><a href="${attach.attachUrl }"
                                                target="_black">${attach.attachFileName }</a></p>
                    </c:forEach>
                </div>
            </div>
            <div class="page-header"></div>
            <div class="form-group">
                <label class="col-sm-4 control-label">案件状态：</label>

                <div class="col-sm-8">
                    <span class="form-info" id="caseState"></span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">案件处理流程：</label>

                <div class="col-sm-8">
                    <c:forEach items="${reportCase.changeList}" var="change" varStatus="i">
								<span class="form-info">${change.operator.userName }
									<time class="pull-right"><fmt:formatDate value="${change.changeTime}" type="date"
                                                                             pattern="yyyy年MM月dd日 HH:mm:ss"/></time>
								</span>
                    </c:forEach>
                </div>
            </div>
            <c:forEach items="${reportCase.commentList}" var="comment" varStatus="i">
                <div class="form-group">
                    <c:if test="${comment.isReporter=='1'}">
                        <c:if test="${reportCase.reporter!=null}">
                            <label class="col-sm-4 control-label">${reportCase.reporter.name}：</label>
                        </c:if>
                        <c:if test="${reportCase.reporter==null}">
                            <label class="col-sm-4 control-label">举报人：</label>
                        </c:if>
                    </c:if>
                    <c:if test="${comment.isReporter!='1'}">
                        <label class="col-sm-4 control-label">${comment.owner.userName }：</label>
                    </c:if>
                    <div class="col-sm-8">
                        <textarea rows="3" readonly class="form-control">${comment.content }</textarea>
                    </div>
                </div>
            </c:forEach>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>

                <div class="col-sm-10 text-right">
                    <input type="button" id="append" class="btn btn-default" value="追加">
                </div>
            </div>
            <div id="appendPanel" class="hidden">
                <div class="form-group">
                    <label class="col-sm-4 control-label">${user.userName}：</label>

                    <div class="col-sm-8">
                        <textarea id="appendContent" class="form-control" rows="5"></textarea>
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
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        var url = "dict/getDictName.do?dictType=case.state&dictValue=${reportCase.caseState }";
        $.get(url, function (res) {
            $("#caseState").text(res);
        });
        var ele = {
            appendPanel: $("#appendPanel"),
            appendContent: $("#appendContent"),
            append: $("#append"),
            appendCancel: $("#appendCancel"),
            appendBtn: $("#appendBtn")
        }
        ele.append.click(function () {
            if (${user.userCompany.companyId!=reportCase.currentHandler.companyId}) {
                return alert("案件未交由平台方处理，您目前只能查看!");
            }
            ele.appendPanel.removeClass("hidden");
            ele.append.addClass("hidden");
        });
        ele.appendCancel.click(function () {
            ele.appendPanel.addClass("hidden");
            ele.append.removeClass("hidden");
        });
        ele.appendBtn.click(function () {
            if (isEmty(ele.appendContent.val())) {
                return Modal.alert({msg: "请填写追加的内容!"});
            }
            var url = "admin/caseBack/addCaseComment.do";
            var data = "rcId=${reportCase.rcId}&content=" + ele.appendContent.val();
            $.post(url, data, function (res, status) {
                if (status == "success") {
                    if (res == "success") {
                        Modal.alert({msg: "操作成功!"})
                                .on(function (e) {
                                    location.reload();
                                });
                    } else {
                        Modal.alert({msg: "操作失败!"});
                    }
                } else {
                    Modal.alert({msg: "操作失败!"});
                }
            });
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