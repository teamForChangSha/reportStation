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
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/md5.js" type="text/javascript" charset="utf-8"></script>
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

        .col-sm-10 {
            padding-left: 0;
        }

        .label {
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: 400;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            color: #333;
        }

        .text-right, .text-left {
            color: #d9534f;
        }

        .form-inline .form-group {
            display: inherit;
            margin-bottom: 15px;
        }

        .form-inline .form-control {
            width: 200px;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="row">
        <h1>
            <small>用户管理</small>
        </h1>
        <div class="page-header"></div>
        <form action="admin/user/getUsersByParams.do" method="post" class="form-inline">
                <div class="form-group">
                    <label class="control-label">所属公司：</label>

                        <div class="input-group">
                            <input type="text" id="companyId" name="companyId" hidden />
                            <input type="text" id="selectCompanyInput" class="form-control"
                                   placeholder="请选择公司"/>
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" data-toggle="modal" data-target="#getCompanyPanl">
                                    <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                </button>
                            </span>
                        </div>
                </div>
            <div class="form-group">
                <label class="control-label">用户类型：</label>
                <select id="userType" name="userType" class="form-control">
                    <option value="">-请选择-</option>
                    <option value="1">公司用户</option>
                    <option value="2">平台管理员</option>
                    <option value="3">超级管理员</option>
                </select>
            </div>
            <div class="form-group">
                <label class="control-label">用户状态：</label>
                <select id="userStatus" name="userState" class="form-control">
                    <option value="">-请选择-</option>
                    <option value="1">正常</option>
                    <option value="2">注销</option>
                    <option value="3">待审核</option>
                    <option value="4">停用</option>
                </select>
            </div>
            <div class="form-group">
                <label class="control-label">搜关键字：</label>
                <input type="text" id="keyWord" name="keyWord" placeholder="按电话或用户名称搜索" class="form-control"/>
                <input type="submit" id="selected" class="btn btn-default" value="搜索"/>
                <input type="button" id="stopAllUser" class="btn btn-warning hidden" value="停用该公司所有用户"/>
            </div>
        </form>
    </div>

    <div class="row">
        <div class="col-sm-10">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="table-info">
                    <th>用户名</th>
                    <th>所属公司</th>
                    <th>用户类型</th>
                    <th>联系电话</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        <div class="col-sm-10">
            <input type="button" id="register" class="btn btn-default" value="添加用户"/>
        </div>
    </div>

    <!--修改用户信息-->
    <div class="row hidden" id="updataUserInfo">
        <div class="col-sm-10">
            <h1>
                <small>修改用户信息</small>
            </h1>
            <div class="page-header"></div>
            <div class="col-sm-6">
                <form id="upForm" class="form-horizontal">
                    <input type="text" id="userId" hidden name="userId">
                    <input type="text" id="userState" hidden name="userState">

                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户名字：</label>

                        <div class="col-sm-8">
                            <input type="text" id="upName" name="userName" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户类型：</label>

                        <div class="col-sm-8">
                            <select id="upType" name="userType" class="form-control">
                                <option value="0">-请选择-</option>
                                <option value="1">公司用户</option>
                                <option value="2">平台管理员</option>
                                <option value="3">超级管理员</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">选择公司：</label>

                        <div class="col-sm-8">
                            <div class="input-group">
                                <input type="text" id="upCompany" name="userCompany.companyId" hidden />
                                <input type="text" id="upCompanyInput" class="form-control"
                                       placeholder="请选择公司"/>
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" data-toggle="modal" data-target="#getCompanyPanl">
                                    <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                </button>
                            </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">手机号码：</label>

                        <div class="col-sm-8">
                            <input type="text" id="upMobile" name="mobile" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">员工工号：</label>

                        <div class="col-sm-8">
                            <input type="text" id="upWorkNo" name="workNo" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">备注信息：</label>

                        <div class="col-sm-8">
                            <textarea rows="3" id="upRemark" name="remark" class="form-control"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">更改日期：</label>

                        <div class="col-sm-8">
                            <p id="upStateChanged" class="form-control-static"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label"></label>

                        <div class="col-sm-8">
                            <input type="button" id="closeUpdataUser" class="btn btn-default col-sm-5" value="取消">
                            <input type="button" id="upBtn" class="btn btn-default col-sm-5 pull-right" value="提交">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!--添加用户-->
    <div class="row hidden" id="addUser">
        <div class="col-sm-10">
            <h1>
                <small>添加用户</small>
            </h1>
            <div class="page-header"></div>
            <div class="col-sm-6">
                <form id="addForm" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户名字：</label>

                        <div class="col-sm-8">
                            <input type="text" id="addName" name="userName" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户账号：</label>

                        <div class="col-sm-8">
                            <input type="text" id="addLoginName" name="loginName" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户密码：</label>

                        <div class="col-sm-8">
                            <input type="password" id="addUserPwd" name="userPwd" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户类型：</label>

                        <div class="col-sm-8">
                            <select id="addType" name="userType" class="form-control">
                                <option value="0">-请选择-</option>
                                <option value="1">公司用户</option>
                                <option value="2">平台管理员</option>
                                <option value="3">超级管理员</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">选择公司：</label>

                        <div class="col-sm-8">
                            <div class="input-group">
                                <input type="text" id="addCompany" name="userCompany.companyId" hidden />
                                <input type="text" id="addCompanyInput" class="form-control"
                                       placeholder="请选择公司"/>
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" data-toggle="modal" data-target="#getCompanyPanl">
                                    <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                </button>
                            </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">手机号码：</label>

                        <div class="col-sm-8">
                            <input type="text" id="addMobile" name="mobile" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">员工工号：</label>

                        <div class="col-sm-8">
                            <input type="text" id="addWorkNo" name="workNo" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">备注信息：</label>

                        <div class="col-sm-8">
                            <textarea rows="3" id="addRemark" name="remark" class="form-control"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label"></label>

                        <div class="col-sm-8">
                            <input type="button" id="closeAddUser" class="btn btn-default col-sm-5" value="取消">
                            <input type="button" id="addBtn" class="btn btn-default col-sm-5 pull-right" value="提交">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!--选择企业对话框-->
<div class="modal fade bs-example-modal-md" id="getCompanyPanl" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title" id="exampleModalLabel">选择企业</h5>
            </div>
            <div class="modal-body">
                <iframe src="jsp/admin/pages/companyPanel.jsp" frameborder="0" width="100%" height="650px"></iframe>
            </div>
        </div>
    </div>
</div>

<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        var panle = {
            updataUserInfo: $("#updataUserInfo"),
            addUser: $("#addUser"),
        }
        var close = {
            updataUserInfo: $("#closeUpdataUser"),
            addUser: $("#closeAddUser"),
            register: $("#register"),
        }
        var upEle = {
            userId: $("#userId"),
            userState: $("#userState"),
            upName: $("#upName"),
            upType: $("#upType"),
            upCompany: $("#upCompany"),
            upCompanyInput: $("#upCompanyInput"),
            upMobile: $("#upMobile"),
            upWorkNo: $("#upWorkNo"),
            upRemark: $("#upRemark"),
            upStateChanged: $("#upStateChanged"),
            upBtn: $("#upBtn")
        }
        var addEle = {
            addName: $("#addName"),
            addLoginName: $("#addLoginName"),
            addUserPwd: $("#addUserPwd"),
            addType: $("#addType"),
            addCompany: $("#addCompany"),
            addCompanyInput: $("#addCompanyInput"),
            addMobile: $("#addMobile"),
            addWorkNo: $("#addWorkNo"),
            addRemark: $("#addRemark"),
            addBtn: $("#addBtn")
        }

        var search = {
            companyId: $("#companyId"),
            selectCompanyInput: $("#selectCompanyInput"),
            stopAllUser: $("#stopAllUser"),
            userType: $("#userType"),
            userStatus: $("#userStatus"),
            keyWord: $("#keyWord"),
            selected: $("#selected")
        }

        if ("${user.userType}" != "3") {
            search.userType.find("option[value=3]").hide();
            upEle.upType.find("option[value=3]").hide();
            addEle.addType.find("option[value=3]").hide();
            search.userType.find("option[value=2]").hide();
            upEle.upType.find("option[value=2]").hide();
            addEle.addType.find("option[value=2]").hide();
        }

        close.updataUserInfo.get(0).onclick = hiddenPanle;
        close.addUser.get(0).onclick = hiddenPanle;
        close.register.click(function () {
            hiddenPanle();
            addEle.addCompany.val("");
            addEle.addCompanyInput.val("");
            panle.addUser.removeClass("hidden");
        });
        search.stopAllUser.click(function () {
            Modal.confirm({
                title: '警告',
                msg: '你确定要停用该公司所有用户吗?',
            }).on(function (e) {
                if (e) {
                    var url = "admin/user/stopCompanyAllUsers.do";
                    var data = "companyId=" + search.companyId.val();
                    $.post(url, data, function (res, status) {
                        alertMsg(res, status);
                    });
                }
            });
        });

        upEle.upCompanyInput.keyup(function () {
            upEle.upCompanyInput.val("");
            upEle.upCompany.val("");
            return Modal.alert({msg:'请选择企业'});
        });
        addEle.addCompanyInput.keyup(function () {
            addEle.addCompanyInput.val("");
            addEle.addCompany.val("");
            return Modal.alert({msg:'请选择企业'});
        });
        search.selectCompanyInput.keyup(function () {
            search.selectCompanyInput.val("");
            search.companyId.val("");
            $("#stopAllUser").addClass("hidden");
            return Modal.alert({msg:'请选择企业'});
        });

        <c:forEach items="${userList}" var="user" varStatus = "i">
        var tr = $("<tr/>");
        var td1 = $("<td/>").text("${user.userName}");
        var td2 = $("<td/>").text("${user.userCompany.companyName}");
        var td3 = $("<td/>");
        if ("${user.userType}" == "1") {
            td3.text("公司用户");
        } else if ("${user.userType}" == "2") {
            td3.text("平台管理员");
        } else {
            td3.text("超级管理员");
        }
        var td4 = $("<td/>").text("${user.mobile}");
        var td5 = $("<td/>");
        var a1 = $("<a/>").attr("class", "btn btn-link").text("重置密码");
        a1.bind("click", function () {
            hiddenPanle();
            Modal.confirm({
                msg: '重置后默认密码为"123456"',
            }).on(function (e) {
                if (e) {
                    var url = "admin/user/resetUserPwd.do";
                    var data = "userId=${user.userId}&userPwd=" + md5("123456");
                    $.post(url, data, function (res, status) {
                        alertMsg(res, status);
                    });
                }
            });
        });
        var a2 = $("<a/>").attr("class", "btn btn-link").text("修改信息");
        a2.bind("click", function () {
            hiddenPanle();
            panle.updataUserInfo.removeClass("hidden");
            upEle.userId.val("${user.userId}");
            upEle.userState.val("${user.userState}");
            upEle.upName.val("${user.userName}");
            upEle.upType.get(0).value = "${user.userType}";
            upEle.upCompany.val("${user.userCompany.companyId}");
            upEle.upCompanyInput.val("${user.userCompany.companyName}");
            upEle.upMobile.val("${user.mobile}");
            upEle.upWorkNo.val("${user.workNo}");
            upEle.upRemark.text("${user.remark}");
            upEle.upStateChanged.text('<fmt:formatDate value="${user.stateChanged}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/>');
        });
        var a3 = $("<a/>").attr("class", "btn btn-link").text("停用");
        if ("${user.userState}" == "4") {
            a3.text("启用");
            var url = "admin/user/changeUserState.do?userId=${user.userId}&userState=1";
            a3.bind("click", function () {
                $.get(url, function (res, status) {
                    alertMsg(res, status);
                });
            });
        } else {
            a3.bind("click", function () {
                Modal.confirm({
                    title: '警告',
                    msg: '你确定要停用该用户吗?',
                }).on(function (e) {
                    if (e) {
                        var url = "admin/user/changeUserState.do?userId=${user.userId}&userState=4";
                        $.get(url, function (res, status) {
                            alertMsg(res, status);
                        });
                    }
                });
            });
        }

        var a4 = $("<a/>").attr("class", "btn btn-link").text("注销");
        a4.bind("click", function () {
            Modal.confirm({
                title: '警告',
                msg: '注销后该用将无法再启用，你确定要注销吗?',
            }).on(function (e) {
                if (e) {
                    var url = "admin/user/changeUserState.do?userId=${user.userId}&userState=2";
                    $.get(url, function (res, status) {
                        alertMsg(res, status);
                    });
                }
            });
        });
        if ("${user.userState}" == "2" || "${user.userState}" == "3") {
            a1.attr("disabled", true);
            a2.attr("disabled", true);
            a3.attr("disabled", true);
            a4.attr("disabled", true);
            a1.unbind("click");
            a2.unbind("click");
            a3.unbind("click");
            a4.unbind("click");
        }
        /*var a5 = $("<span/>").attr("class", "label").text("已审核");
         if ("
        ${user.userState}" == "3") {
         a5 = $("<a/>").attr("class", "btn btn-link").text("待审核");
         a5.click(function () {
         var url = "admin/user/changeUserState.do?userId=
        ${user.userId}&userState=1";
         $.get(url, function (res, status) {
         alertMsg(res, status);
         });
         });
         }*/
        td5.append(a1).append(a2).append(a3).append(a4);
        tr.append(td1).append(td2).append(td3).append(td4).append(td5);
        $("tbody").append(tr);
        </c:forEach>

        /* 更新用户信息 */
        upEle.upBtn.click(function () {
            if (isEmty(upEle.upName.val())) {
                return alr("请输入用户名称");
            }
            if (upEle.upType.find("option:selected").val() == "0") {
                return alr("请选择用户类型");
            }
            if (isEmty(upEle.upCompany.val())) {
                return alr("请选择所属公司");
            }
            $.post("admin/user/updateUser.do", $("#upForm").serialize(), function (res, status) {
                alertMsg(res, status);
            });
        });

        /* 添加企业用户 */
        addEle.addBtn.click(function () {
            if (isEmty(addEle.addName.val())) {
                return alr("请输入用户名称");
            }
            if (isEmty(addEle.addLoginName.val())) {
                return alr("请输入登录名");
            }
            if (isEmty(addEle.addUserPwd.val())) {
                return alr("请输入密码");
            }
            if (addEle.addType.find("option:selected").val() == "0") {
                return alr("请选择用户类型");
            }
            if (isEmty(addEle.addCompany.val())) {
                return alr("请选择所属公司");
            }
            addEle.addUserPwd.val(md5(addEle.addUserPwd.val()));
            $.post("admin/user/addUser.do", $("#addForm").serialize(), function (res, status) {
                alertMsg(res, status);
            });
        });

        /* 隐藏添加修改用户的面板 */
        function hiddenPanle() {
            panle.updataUserInfo.addClass("hidden");
            panle.addUser.addClass("hidden");
        }

        /* 判断是否为空 */
        function isEmty(str) {
            str = $.trim(str);
            if (str == null || str.length <= 0 || str == "") {
                return true;
            }
            return false;
        }

        function alr(masg) {
            Modal.alert({
                msg: masg,
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
    });
    function hideModal(){
        $("#getCompanyPanl").modal('hide');
        $("#stopAllUser").removeClass("hidden");
    }
</script>

</html>
