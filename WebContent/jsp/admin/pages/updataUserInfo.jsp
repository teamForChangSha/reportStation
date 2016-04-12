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
<html>

<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-8">
            <h1>
                <small>修改用户信息</small>
            </h1>
            <div class="page-header"></div>
        </div>
        <div class="col-sm-6">
            <form id="upForm" method="post" action="admin/user/updateUser.do" class="form-horizontal">
                <input type="text" value="${user.userId}" hidden name="userId">

                <div class="form-group">
                    <label class="col-sm-3 control-label">用户名字：</label>

                    <div class="col-sm-8">
                        <input type="text" value="${user.userName}" name="userName" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">用户类型：</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">
                            <c:if test="${user.userType==1}">试用</c:if>
                            <c:if test="${user.userType==2}">公司用户</c:if>
                            <c:if test="${user.userType==3}">系统操作员</c:if>
                            <c:if test="${user.userType==4}">系统管理员</c:if>
                        </p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">所属公司：</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${user.userCompany.companyName}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">手机号码：</label>

                    <div class="col-sm-8">
                        <input type="text" value="${user.mobile}" name="mobile" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">办公电话：</label>

                    <div class="col-sm-8">
                        <input type="text" value="${user.phone}" name="phone" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">邮箱地址：</label>

                    <div class="col-sm-8">
                        <input type="text" value="${user.email}" name="email" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">微信号码：</label>

                    <div class="col-sm-8">
                        <input type="text" value="${user.weixin}" name="weixin" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">所属部门：</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${user.department}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">当前职位：</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${user.position}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">员工工号：</label>

                    <div class="col-sm-8">
                        <input type="text" value="${user.workNo}" name="workNo" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">联系地址：</label>

                    <div class="col-sm-8">
                        <input type="text" value="${user.address}" name="address" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">有效期至：</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">
                            <fmt:formatDate value="${user.expiryDate}" type="date"
                                            pattern="yyyy年MM月dd日 HH:mm:ss"/>
                        </p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">备注信息：</label>

                    <div class="col-sm-8">
                        <textarea rows="3" name="remark" class="form-control">${user.remark}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">更改日期：</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">
                            <fmt:formatDate value="${user.stateChanged}" type="date"
                                            pattern="yyyy年MM月dd日 HH:mm:ss"/></p>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-4"></div>
                    <div class="col-sm-4">
                        <input type="button" id="upBtn" class="btn btn-default form-control"
                               value="提交">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script>
    $(function () {
        $("#upBtn").click(function () {
            var phoneReg = /(1[3-9]\d{9}$)/;
            var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            if (!phoneReg.test($("input[name=mobile]").val())) {
                $("html,body").animate({scrollTop: $("input[name=mobile]").offset().top}, 500);
                return Modal.alert({msg: '请输入正确的手机号!'});
            }
            if ($.trim($("input[name=email]").val()) != '') {
                if (!emailReg.test($("input[name=email]"))) {
                    $("html,body").animate({scrollTop: $("input[name=email]").offset().top}, 500);
                    return Modal.alert({msg: '请输入正确的邮箱地址!'});
                }
            }
            $("#upForm").submit();
        });
    });
</script>
</html>
