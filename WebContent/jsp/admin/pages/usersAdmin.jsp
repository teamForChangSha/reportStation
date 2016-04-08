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
                    <input type="text" id="companyId" name="companyId" hidden/>
                    <input type="text" id="selectCompanyInput" class="form-control"
                           placeholder="请选择公司"/>
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" data-toggle="modal"
                                        data-target="#getCompanyPanl">
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
                    <th>最近登录时间</th>
                    <th>状态</th>
                    <th>有效期</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="user" varStatus="i">
                    <tr>
                        <td>${user.userName}</td>
                        <td>${user.userCompany.companyName}</td>
                        <td>
                            <c:if test="${user.userType==1}">试用</c:if>
                            <c:if test="${user.userType==2}">公司用户</c:if>
                            <c:if test="${user.userType==3}">系统操作员</c:if>
                            <c:if test="${user.userType==4}">系统管理员</c:if>
                        </td>
                        <td>最近登录时间</td>
                        <td>
                            <c:if test="${user.userState==1}">新增</c:if>
                            <c:if test="${user.userState==2}">有效</c:if>
                            <c:if test="${user.userState==3}">停用</c:if>
                            <c:if test="${user.userState==4}">注销</c:if>
                        </td>
                        <td>有效期至</td>
                        <td>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    操作 <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <c:if test="${user.userState==4}">
                                        <li class="disabled"><a href="javascript:;">重置密码</a></li>
                                        <li class="disabled"><a href="javascript:;">修改信息</a></li>
                                        <li role="separator" class="divider"></li>
                                        <li class="disabled"><a href="javascript:;">停用</a></li>
                                        <li class="disabled"><a href="javascript:;">注销</a></li>
                                    </c:if>
                                    <c:if test="${user.userState!=4}">
                                        <li><a href="javascript:;" onclick="resetPwd(${user.userId})">重置密码</a></li>
                                        <li><a href="javascript:;"
                                               onclick="updataInfo(${user.userId},${user.userState},'${user.userName}',${user.userType}
                                                       ,${user.userCompany.companyId},'${user.userCompany.companyName}',${user.mobile},${user.workNo},'${user.remark}'
                                                       ,'<fmt:formatDate value='${user.stateChanged}' type='date'
                                                                         pattern='yyyy年MM月dd日 HH:mm:ss'/>')">修改信息</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <c:if test="${user.userState==3}">
                                            <li><a href="javascript:;" onclick="enableUser(${user.userId})">启用</a></li>
                                        </c:if>
                                        <c:if test="${user.userState!=3}">
                                            <li><a href="javascript:;" onclick="disableUser(${user.userId})">停用</a></li>
                                        </c:if>
                                        <li><a href="javascript:;" onclick="unRegister(${user.userId})">注销</a></li>
                                    </c:if>

                                </ul>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
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
                                <option value="1">试用</option>
                                <option value="2">公司用户</option>
                                <option value="3">系统操作员</option>
                                <option value="4">系统管理员</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">选择公司：</label>

                        <div class="col-sm-8">
                            <div class="input-group">
                                <input type="text" id="upCompany" name="userCompany.companyId" hidden/>
                                <input type="text" id="upCompanyInput" class="form-control"
                                       placeholder="请选择公司"/>
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" data-toggle="modal"
                                        data-target="#getCompanyPanl">
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
                                <option value="1">试用</option>
                                <option value="2">公司用户</option>
                                <option value="3">系统操作员</option>
                                <option value="4">系统管理员</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">选择公司：</label>

                        <div class="col-sm-8">
                            <div class="input-group">
                                <input type="text" id="addCompany" name="userCompany.companyId" hidden/>
                                <input type="text" id="addCompanyInput" class="form-control"
                                       placeholder="请选择公司"/>
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" data-toggle="modal"
                                        data-target="#getCompanyPanl">
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
<div class="modal fade bs-example-modal-md" id="getCompanyPanl" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
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
            $("html,body").animate({scrollTop: $("#addUser").offset().top}, 500);
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
            return Modal.alert({msg: '请选择企业'});
        });
        addEle.addCompanyInput.keyup(function () {
            addEle.addCompanyInput.val("");
            addEle.addCompany.val("");
            return Modal.alert({msg: '请选择企业'});
        });
        search.selectCompanyInput.keyup(function () {
            search.selectCompanyInput.val("");
            search.companyId.val("");
            $("#stopAllUser").addClass("hidden");
            return Modal.alert({msg: '请选择企业'});
        });

        window.resetPwd = function (userId) {
            hiddenPanle();
            Modal.confirm({
                msg: '重置后默认密码为"123456"',
            }).on(function (e) {
                if (e) {
                    var url = "admin/user/resetUserPwd.do";
                    var data = "userId=" + userId + "&userPwd=" + md5("123456");
                    $.post(url, data, function (res, status) {
                        console.log("=====");
                        alertMsg(res, status);
                    });
                }
            });
        };

        window.updataInfo = function (userId, userState, userName, userType, companyId, companyName, mobile, workNo, remark, stateChanged) {
            console.log(userId + "," + userState + "," + userName + "," + userType + "," + companyId + "," + companyName + "," + mobile + "," + workNo + "," + remark + "," + stateChanged);
            hiddenPanle();
            panle.updataUserInfo.removeClass("hidden");
            upEle.userId.val(userId);
            upEle.userState.val(userState);
            upEle.upName.val(userName);
            upEle.upType.get(0).value = userType;
            upEle.upCompany.val(companyId);
            upEle.upCompanyInput.val(companyName);
            upEle.upMobile.val(mobile);
            upEle.upWorkNo.val(workNo);
            upEle.upRemark.text(remark);
            upEle.upStateChanged.text(stateChanged);
            $("html,body").animate({scrollTop: $("#updataUserInfo").offset().top}, 500);
        };

        window.disableUser = function (userId) {
            Modal.confirm({
                title: '警告',
                msg: '你确定要停用该用户吗?',
            }).on(function (e) {
                if (e) {
                    var url = "admin/user/changeUserState.do?userId=" + userId + "&userState=3";
                    $.get(url, function (res, status) {
                        alertMsg(res, status);
                    });
                }
            });
        };

        window.enableUser = function (userId) {
            var url = "admin/user/changeUserState.do?userId=" + userId + "&userState=2";
            $.get(url, function (res, status) {
                alertMsg(res, status);
            });
        };

        window.unRegister = function (userId) {
            Modal.confirm({
                title: '警告',
                msg: '注销后该用将无法再启用，你确定要注销吗?',
            }).on(function (e) {
                if (e) {
                    var url = "admin/user/changeUserState.do?userId=" + userId + "&userState=4";
                    $.get(url, function (res, status) {
                        alertMsg(res, status);
                    });
                }
            });
        };


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

        return resetPwd, updataInfo, disableUser, enableUser, unRegister;
    });
    function hideModal() {
        $("#getCompanyPanl").modal('hide');
        $("#stopAllUser").removeClass("hidden");
    }
</script>

</html>
