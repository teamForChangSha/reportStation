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
<html style="overflow: hidden;">

<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="cache-control" content="no-cache">
    <title>后台管理系统</title>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <link href="jsp/css/style.css" rel="stylesheet">
    <style type="text/css">
        .navbar-default {
            background-color: #4a8bc2;
            background-image: linear-gradient(to bottom, #4a8bc2, #4a8bc2);
            border: inherit;
            margin-bottom: 0;
            border-radius: 0;
            color: #fff;
        }

        .navbar-default .navbar-brand {
            color: #fff;
        }

        #sidebar > ul > li > a {
            color: #fff !important;
            width: auto;
        }

        #sidebar > ul > li > ul.sub > li > a {
            padding: 10px 10px 10px 10px;
        }

        .navbar-default .navbar-nav > li > a,
        .navbar-default .navbar-nav > li > a:hover {
            color: #fff;
        }

        .userName {
            font-weight: bold;
        }
    </style>
</head>

<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">
                后台管理系统
            </a>
        </div>
        <ul class="nav navbar-nav pull-right">
            <li class="dropdown mtop5">
                <a>上次登录：<fmt:formatDate value="${lastLogin.logDate}" type="date"
                                        pattern="yyyy年MM月dd日 HH:mm:ss"/></a>
            </li>
            <li class="dropdown mtop5">
                <a id="date"></a>
            </li>
            <li class="dropdown mtop5">
                <a class="userName">您好：${user.userName}</a>
            </li>
            <li class="dropdown mtop5">
                <a class="userName">账户状态：
                    <c:if test="${user.userState=='1'}">试用</c:if>
                    <c:if test="${user.userState=='2'}">正常</c:if>
                    <c:if test="${user.userState=='3'}">停用</c:if>
                    <c:if test="${user.userState=='4'}">注销</c:if>
                </a>
            </li>
            <li class="dropdown mtop5">
                <a href="admin/user/loginOut.do"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>退出</a>
            </li>
        </ul>
    </div>
</nav>
<div id="container" class="row-fluid">
    <div tabindex="5000" style="overflow: auto;" class="sidebar-scroll">
        <div style="height: auto;" id="sidebar" class="nav-collapse in collapse">
            <ul class="sidebar-menu">
                <li class="sub-menu">
                    <a href="admin/caseBack/showLastCase.do" target="MainIframe">
                        <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                        <span>后台首页</span>
                    </a>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;" class="">
                        <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
                        <span>公司设置</span>
                        <span class="arrow"></span>
                    </a>
                    <ul class="sub">
                        <li><a class="" href="admin/companyBack/getOwnerCompanyInfo.do" target="MainIframe">公司信息设置</a>
                        </li>
                        <li class="hide"><a class="" href="admin/companyBack/getCompanyBranches.do" target="MainIframe">分支机构管理</a>
                        </li>
                        <li><a class="" href="admin/companyBack/getAllReportTypes.do" target="MainIframe">举报类型设置</a>
                        </li>
                        <li><a class="" href="admin/companyBack/getQuestTemlate.do" target="MainIframe">公司问题设置</a></li>
                    </ul>
                </li>
                <c:if test="${user.userType>1}">
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            <span>用户管理</span>
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub">
                            <c:if test="${user.userType==2}">
                                <li><a class="" href="jsp/admin/pages/updataUserInfo.jsp" target="MainIframe">信息修改</a>
                                </li>
                            </c:if>
                            <c:if test="${user.userType>=3}">
                                <li><a class="" href="admin/user/getUsersByParams.do" target="MainIframe">用户管理</a></li>
                            </c:if>
                            <li><a class="" href="admin/user/getLogByParams.do" target="MainIframe">操作日志</a></li>
                        </ul>
                    </li>
                </c:if>
                <li class="sub-menu">
                    <a href="javascript:;" class="">
                        <span class="glyphicon glyphicon-open-file" aria-hidden="true"></span>
                        <span>举报管理</span>
                        <span class="arrow"></span>
                    </a>
                    <ul class="sub">
                        <li><a class="" href="admin/caseBack/showCaseByCompany.do" target="MainIframe">所有举报<span
                                class="badge"></span></a>
                        </li>
                        <li><a class="" href="javascript:;" target="MainIframe">举报统计</a>
                        </li>
                    </ul>
                </li>
                <c:if test="${user.userType==4}">
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
                            <span>统计分析</span>
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="javascript:;" target="MainIframe">表单布局</a></li>
                        </ul>
                    </li>
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                            <span>数据设计</span>
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="jsp/admin/pages/enterAdmin.jsp" target="MainIframe">企业数据维护</a></li>
                            <li><a class="" href="jsp/admin/pages/enterMerge.jsp" target="MainIframe">企业名称合并</a></li>
                        </ul>
                    </li>
                </c:if>
                <li class="sub-menu">
                    <a href="javascript:;" class="" data-toggle="modal" data-target="#updatePwd">
                        <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                        <span>修改密码</span>
                    </a>
                </li>
                <li class="sub-menu">
                    <a href="mailto:178972841@qq.com" class="">
                        <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                        <span>联系管理</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>

    <div id="main-content">
        <iframe src="admin/caseBack/showLastCase.do" id="MainIframe" name="MainIframe" style="height: 100%;width: 100%"
                frameborder="0">
        </iframe>
    </div>
</div>
<div id="footer">
    Copyright © 2016-2018 51report.com
</div>

<!--修改密码-->
<div style="margin-top: 100px" class="modal fade bs-example-modal-sm" id="updatePwd" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title" id="exampleModalLabel">修改密码</h5>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <input type="password" id="oldPwd" class="form-control" placeholder="旧密码">
                    </div>
                    <div class="form-group">
                        <input type="password" id="newPwd" class="form-control" placeholder="新密码">
                    </div>
                    <div class="form-group">
                        <input type="password" id="reNewPwd" class="form-control" placeholder="重复密码">
                    </div>
                    <div class="form-group">
                        <input type="button" id="loginBtn" value="登陆" class="btn btn-primary form-control"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
<script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
<script src="jsp/js/md5.js" type="text/javascript" charset="utf-8"></script>
<script src="jsp/js/common-scripts.js"></script>
<script type="text/javascript">
    $(function () {
        var winheight = $(window).height();
        $("#main-content").height(winheight - 81);
    });
    $(window).resize(function () {
        var winheight = $(window).height();
        $("#main-content").height(winheight - 81);
    });
    $(function () {
        initTime();
        setInterval(initTime, 1000);

        function initTime() {
            var time = new Date();
            var month = (time.getMonth() + 1) < 10 ? "0" + (time.getMonth() + 1) : (time.getMonth() + 1);
            var day = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
            var hour = time.getHours() < 10 ? "0" + time.getHours() : time.getHours();
            var minute = time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes();
            var second = time.getSeconds() < 10 ? "0" + time.getSeconds() : time.getSeconds();
            var text = time.getFullYear() + "年" + month + "月" + day + "日 " + hour + ":" + minute + ":" + second;
            $("#date").text("当前时间：" + text);
        };

        $("#loginBtn").click(function () {
            var oldPwd = $("#oldPwd").val();
            var newPwd = $("#newPwd").val();
            var reNewPwd = $("#reNewPwd").val();
            if (isEmty(oldPwd)) {
                return alert("请输入旧密码");
            }
            if (isEmty(newPwd)) {
                return alert("请输入新密码");
            }
            if (newPwd != reNewPwd) {
                return alert("两次新密码不一致");
            }
            var url = "admin/user/updatePwd.do?userPwd=" + md5(newPwd) + "oldPwd=" + md5(oldPwd);
            $.get(url, function (res, status) {
                if (status == "success") {
                    if (res == "success") {
                        Modal.prompt({
                            msg: '操作成功,请用新密码重新登陆',
                        }).on(function (e, res) {
                            if (e) {
                                location.href = "admin/user/loginOut.do";
                            }
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
        /* 判断是否为空 */
        function isEmty(str) {
            if (str == null || str == "") {
                return true;
            }
            return false;
        }
    });
</script>
</body>

</html>