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
            margin-bottom: 15px;
        }

        .form-inline .form-control {
            width: 148px;
        }

        .dropdown-menu {
            max-height: 300px;
            overflow: auto;
        }

        .dropdown-menu > li {
            cursor: pointer;
        }

        #searchCompanyMenu {
            margin-left: 100px;
            margin-top: -370px;
        }

        #upCompanyMenu, #addCompanyMenu {
            margin-left: 15px;
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
        <form id="selectForm" action="admin/user/getUsersByParams.do" method="post" class="form-inline">
            <div class="form-group">
                <label class="control-label">用户类型：</label>
                <select id="userType" name="userType" class="form-control">
                    <option>-请选择-</option>
                    <option value="1">试用</option>
                    <option value="2">公司用户</option>
                    <option value="3">系统操作员</option>
                    <c:if test="${user.userType==4}">
                        <option value="4">系统管理员</option>
                    </c:if>
                </select>
            </div>
            <div class="form-group">
                <label class="control-label">用户状态：</label>
                <select id="userStatus" name="userState" class="form-control">
                    <option>-请选择-</option>
                    <option value="1">新增</option>
                    <option value="2">有效</option>
                    <option value="3">停用</option>
                    <option value="4">注销</option>
                </select>
            </div>
            <br>
            <div class="form-group">
                <label class="control-label">所属公司：</label>

                <input type="text" id="companyId" name="companyId" hidden/>
                <input type="text" id="selectCompanyInput" class="form-control"
                       placeholder="请选择公司" autocomplete="off" onkeyup="searchCompany(this)"
                       onfocus="searchCompanyFoucs(this)"/>
                <ul id="searchCompanyMenu" class="dropdown-menu"></ul>
            </div>
            <div class="form-group">
                <label class="control-label">搜关键字：</label>
                <input type="text" id="keyWord" name="keyWord" autocomplete="on"
                       placeholder="用户名"  class="form-control"/>
                <input type="submit" id="selected" class="btn btn-default" value="搜索"/>
            </div>
        </form>
    </div>
<script>
    $(function(){
        $("#selected").click(function () {
            console.log($("#selectForm").serialize());
        });
    })
</script>
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
                <c:forEach items="${userAndLogList}" var="userAndLog" varStatus="i">
                    <tr>
                        <td>${userAndLog.user.userName}</td>
                        <td>${userAndLog.user.userCompany.companyName}</td>
                        <td>
                            <c:if test="${userAndLog.user.userType==1}">试用</c:if>
                            <c:if test="${userAndLog.user.userType==2}">公司用户</c:if>
                            <c:if test="${userAndLog.user.userType==3}">系统操作员</c:if>
                            <c:if test="${userAndLog.user.userType==4}">系统管理员</c:if>
                        </td>
                        <td>
                            <c:if test="${userAndLog.lastLoginLog.logDate==null}">最近无操作</c:if>
                            <c:if test="${userAndLog.lastLoginLog.logDate!=null}">
                                <fmt:formatDate value="${userAndLog.lastLoginLog.logDate}" type="date"
                                                pattern="yyyy年MM月dd日 HH:mm:ss"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${userAndLog.user.userState==1}">新增</c:if>
                            <c:if test="${userAndLog.user.userState==2}">有效</c:if>
                            <c:if test="${userAndLog.user.userState==3}">停用</c:if>
                            <c:if test="${userAndLog.user.userState==4}">注销</c:if>
                        </td>
                        <td><fmt:formatDate value="${userAndLog.user.expiryDate}" type="date"
                                            pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                        <td>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    操作 <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <c:if test="${userAndLog.user.userState==4}">
                                        <li class="disabled"><a href="javascript:;">重置密码</a></li>
                                        <li class="disabled"><a href="javascript:;">修改信息</a></li>
                                        <li role="separator" class="divider"></li>
                                        <li class="disabled"><a href="javascript:;">停用</a></li>
                                        <li class="disabled"><a href="javascript:;">注销</a></li>
                                    </c:if>
                                    <c:if test="${userAndLog.user.userState!=4}">
                                        <li><a href="javascript:;"
                                               onclick="resetPwd(${userAndLog.user.userId})">重置密码</a></li>
                                        <li><a href="javascript:;" data-toggle="modal" data-target="#updataUserInfo"
                                               onclick="updataInfo(${userAndLog.user.userId},${userAndLog.user.userState},'${userAndLog.user.userName}',${userAndLog.user.userType}
                                                       ,${userAndLog.user.userCompany.companyId},'${userAndLog.user.userCompany.companyName}',${userAndLog.user.mobile},'${userAndLog.user.phone}','${userAndLog.user.email}'
                                                       ,'${userAndLog.user.weixin}','${userAndLog.user.department}','${userAndLog.user.position}','${userAndLog.user.workNo}','${userAndLog.user.address}'
                                                       ,'<fmt:formatDate value="${userAndLog.user.expiryDate}"
                                                                         type="date"
                                                                         pattern="yyyy-MM-dd"/>','${userAndLog.user.remark}'
                                                       ,'<fmt:formatDate value="${userAndLog.user.stateChanged}"
                                                                         type="date"
                                                                         pattern="yyyy年MM月dd日 HH:mm:ss"/>')">修改信息</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <c:if test="${userAndLog.user.userState==3}">
                                            <li><a href="javascript:;" onclick="enableUser(${userAndLog.user.userId})">启用</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${userAndLog.user.userState!=3}">
                                            <li><a href="javascript:;" onclick="disableUser(${userAndLog.user.userId})">停用</a>
                                            </li>
                                        </c:if>
                                        <li><a href="javascript:;"
                                               onclick="unRegister(${userAndLog.user.userId})">注销</a></li>
                                    </c:if>
                                    <li role="separator" class="divider"></li>
                                    <li><a href="javascript:;" data-toggle="modal" data-target="#toViewUseLog"
                                           onclick="lookUseLog('${userAndLog.user.userId}')">查看操作日志</a></li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-sm-10">
            <input type="button" data-toggle="modal" data-target="#addUser" class="btn btn-default" value="添加用户"/>
            <input type="button" id="stopAllUsers" onclick="stopAllUser()" class="btn btn-warning hide"
                   value="停用该公司所有用户"/>
        </div>
    </div>

    <!--修改用户信息-->
    <div class="modal fade bs-example-modal-md" id="updataUserInfo" tabindex="-1" role="dialog"
         aria-labelledby="mySmallModalLabel">
        <div class="modal-dialog modal-md">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h5 class="modal-title text-center">修改用户信息</h5>
                </div>
                <div class="modal-body" id="upHtml"/>
                <div class="row">
                    <div class="col-sm-12">
                        <form id="upForm" class="form-horizontal">
                            <input type="text" id="userId" hidden name="userId">
                            <input type="text" id="userState" hidden name="userState">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户名字：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upName" name="userName" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户类型：</label>

                                <div class="col-sm-8">
                                    <select id="upType" name="userType" class="form-control">
                                        <option value="0">-请选择-</option>
                                        <option value="1">试用</option>
                                        <option value="2">公司用户</option>
                                        <option value="3">系统操作员</option>
                                        <c:if test="${user.userType==4}">
                                            <option value="4">系统管理员</option>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户状态：</label>

                                <div class="col-sm-8">
                                    <select id="upStae" name="userState" class="form-control">
                                        <option value="1">新增</option>
                                        <option value="2">有效</option>
                                        <option value="3">停用</option>
                                        <option value="4">注销</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择公司：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upCompany" name="userCompany.companyId" hidden/>
                                    <input type="text" id="upCompanyInput" class="form-control"
                                           placeholder="请选择公司" autocomplete="off" onkeyup="searchCompany(this)"
                                           onfocus="searchCompanyFoucs(this)"/>
                                    <ul id="upCompanyMenu" class="dropdown-menu"></ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">手机号码：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upMobile" name="mobile" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">办公电话：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upPhone" name="phone" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">邮箱地址：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upEmail" name="email" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">微信号码：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upWechat" name="weixin" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">所属部门：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upDepartment" name="department" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">当前职位：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upPosition" name="position" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">员工工号：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upWorkNo" name="workNo" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">联系地址：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upAddress" name="address" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">有效期至：</label>
                                <input type="text" id="upToDate" name="expiryDate" hidden>

                                <div class="col-sm-1" style="padding: 0;margin-left: 16px;width: 14%">
                                    <select id="ufullYear" class="form-control" onchange="uchangeDay()">
                                    </select>
                                </div>
                                <div class="col-sm-1">
                                    <p class="form-control-static">年</p>
                                </div>
                                <div class="col-sm-1" style="padding: 0;width: 12%">
                                    <select id="ufullMounth" class="form-control" onchange="uchangeDay()">
                                        <option value="1">01</option>
                                        <option value="2">02</option>
                                        <option value="3">03</option>
                                        <option value="4">04</option>
                                        <option value="5">05</option>
                                        <option value="6">06</option>
                                        <option value="7">07</option>
                                        <option value="8">08</option>
                                        <option value="9">09</option>
                                        <option value="10">10</option>
                                        <option value="11">11</option>
                                        <option value="12">12</option>
                                    </select>
                                </div>
                                <div class="col-sm-1">
                                    <p class="form-control-static">月</p>
                                </div>
                                <div class="col-sm-1" style="padding: 0;width: 12%">
                                    <select id="ufullDay" class="form-control">
                                    </select>
                                </div>
                                <div class="col-sm-1">
                                    <p class="form-control-static">日</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注信息：</label>

                                <div class="col-sm-8">
                                            <textarea rows="3" id="upRemark" name="remark"
                                                      class="form-control"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">更改日期：</label>

                                <div class="col-sm-8">
                                    <p id="upStateChanged" class="form-control-static"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4"></div>
                                <div class="col-sm-4">
                                    <input type="button" onclick="sendUp()" class="btn btn-default form-control"
                                           value="提交">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--添加用户-->
<div class="modal fade bs-example-modal-md" id="addUser" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title text-center">添加用户</h5>
            </div>
            <div class="modal-body" id="addHtml">
                <div class="row">
                    <div class="col-sm-12">
                        <form id="addForm" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户名字：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addName" name="userName" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户账号：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addLoginName" name="loginName" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户密码：</label>

                                <div class="col-sm-8">
                                    <input type="password" id="addUserPwd" name="userPwd" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户类型：</label>

                                <div class="col-sm-8">
                                    <select id="addType" name="userType" class="form-control">
                                        <option value="0">-请选择-</option>
                                        <option value="1">试用</option>
                                        <option value="2">公司用户</option>
                                        <option value="3">系统操作员</option>
                                        <c:if test="${user.userType==4}">
                                            <option value="4">系统管理员</option>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户状态：</label>

                                <div class="col-sm-8">
                                    <select id="addStae" name="userState" class="form-control">
                                        <option value="1">新增</option>
                                        <option value="2">有效</option>
                                        <option value="3">停用</option>
                                        <option value="4">注销</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择公司：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addCompany" name="userCompany.companyId" hidden/>
                                    <input type="text" id="addCompanyInput" class="form-control"
                                           placeholder="请选择公司" autocomplete="off" onkeyup="searchCompany(this)"
                                           onfocus="searchCompanyFoucs(this)"/>
                                    <ul id="addCompanyMenu" class="dropdown-menu"></ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">手机号码：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addMobile" name="mobile" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">办公电话：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addPhone" name="phone" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">邮箱地址：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addEmail" name="email" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">微信号码：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addWechat" name="weixin" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">所属部门：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addDepartment" name="department" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">当前职位：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addPosition" name="position" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">员工工号：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addWorkNo" name="workNo" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">联系地址：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addAddress" name="address" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">有效期至：</label>
                                <input type="text" id="addToDate" name="expiryDate" hidden>

                                <div class="col-sm-1" style="padding: 0;margin-left: 16px;width: 14%">
                                    <select id="fullYear" class="form-control" onchange="changeDay()">
                                    </select>
                                </div>
                                <div class="col-sm-1">
                                    <p class="form-control-static">年</p>
                                </div>
                                <div class="col-sm-1" style="padding: 0;width: 12%">
                                    <select id="fullMounth" class="form-control" onchange="changeDay()">
                                        <option value="1">01</option>
                                        <option value="2">02</option>
                                        <option value="3">03</option>
                                        <option value="4">04</option>
                                        <option value="5">05</option>
                                        <option value="6">06</option>
                                        <option value="7">07</option>
                                        <option value="8">08</option>
                                        <option value="9">09</option>
                                        <option value="10">10</option>
                                        <option value="11">11</option>
                                        <option value="12">12</option>
                                    </select>
                                </div>
                                <div class="col-sm-1">
                                    <p class="form-control-static">月</p>
                                </div>
                                <div class="col-sm-1" style="padding: 0;width: 12%">
                                    <select id="fullDay" class="form-control">
                                    </select>
                                </div>
                                <div class="col-sm-1">
                                    <p class="form-control-static">日</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注信息：</label>

                                <div class="col-sm-8">
                                            <textarea rows="3" id="addRemark" name="remark"
                                                      class="form-control"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 control-label"></div>
                                <div class="col-sm-4">
                                    <input type="button" id="addBtn" onclick="sendAdd()"
                                           class="btn btn-default form-control"
                                           value="提交">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<!--查看用户操作日志-->
<div class="modal fade bs-example-modal-md" id="toViewUseLog" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title text-center">用户操作日志</h5>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="table-info">
                                <th>操作日期</th>
                                <th>操作内容</th>
                                <th>操作人</th>
                            </tr>
                            </thead>
                            <tbody id="logTable">
                            </tbody>
                        </table>
                    </div>
                </div>
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
                <h5 class="modal-title text-center">选择企业</h5>
            </div>
            <div class="modal-body" id="selectedHtml">
            </div>
        </div>
    </div>
</div>

<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        var phoneReg = /(1[3-9]\d{9}$)/;
        var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        var userNameReg = /^[A-Za-z0-9]+$/;
        /*添加用户日期设置*/
        var tempDate = new Date();
        var tempYear = tempDate.getFullYear();
        for (var i = 10; i > 0; i--) {
            var opt = $("<option/>").text((tempYear + i)).val((tempYear + i));
            $("#fullYear").append(opt);
        }
        for (var i = 0; i < 41; i++) {
            var opt = $("<option/>").text((tempYear - i)).val((tempYear - i));
            $("#fullYear").append(opt);
        }
        $("#fullYear").get(0).value = tempYear;
        $("#fullMounth").get(0).value = (tempDate.getMonth() + 1);
        window.changeDay = function () {
            $("#fullDay").empty();
            var y = $("#fullYear").find("option:selected").val();
            var m = $("#fullMounth").find("option:selected").val();
            var max = new Date(y, m, 0).getDate();
            for (var i = 1; i <= max; i++) {
                var opt = $("<option/>").text((i < 10 ? "0" + i : i)).val(i);
                $("#fullDay").append(opt);
            }
            $("#fullDay").get(0).value = tempDate.getDate();
        }
        changeDay();
        /*添加用户日期设置 END*/

        /*修改用户信息日期设置*/
        for (var i = 10; i > 0; i--) {
            var opt = $("<option/>").text((tempYear + i)).val((tempYear + i));
            $("#ufullYear").append(opt);
        }
        for (var i = 0; i < 41; i++) {
            var opt = $("<option/>").text((tempYear - i)).val((tempYear - i));
            $("#ufullYear").append(opt);
        }
        $("#ufullYear").get(0).value = tempYear;
        $("#ufullMounth").get(0).value = (tempDate.getMonth() + 1);
        window.uchangeDay = function () {
            $("#ufullDay").empty();
            var y = $("#ufullYear").find("option:selected").val();
            var m = $("#ufullMounth").find("option:selected").val();
            var max = new Date(y, m, 0).getDate();
            for (var i = 1; i <= max; i++) {
                var opt = $("<option/>").text((i < 10 ? "0" + i : i)).val(i);
                $("#ufullDay").append(opt);
            }
            $("#ufullDay").get(0).value = tempDate.getDate();
        }
        uchangeDay();
        /*修改用户信息日期设置 END*/

        /*停用某公司所有用户*/
        window.stopAllUser = function () {
            Modal.confirm({
                title: '警告',
                msg: '你确定要停用该公司所有用户吗?',
            }).on(function (e) {
                if (e) {
                    var url = "admin/user/stopCompanyAllUsers.do";
                    var data = "companyId=" + $("#companyId").val();
                    $.post(url, data, function (res, status) {
                        alertMsg(res, status);
                    });
                }
            });
        };

        /*搜索公司*/
        window.searchCompany = function (ele) {
            if ($(ele).get(0).id == $("#selectCompanyInput").get(0).id) {
                $("#stopAllUsers").addClass("hide");
            }
            $(ele).prev().val('');
            AutoComple(ele);
        };
        window.searchCompanyFoucs = function (ele) {
            AutoComple(ele);
        };
        function AutoComple(ele) {
            $(ele).next().hide();
            $(ele).next().empty();
            var data = "companyName=" + $(ele).val();
            $.post("company/getAllByName.do", data, function (res) {
                if (res == null || res.length < 0)
                    return;
                $.each(JSON.parse(res), function (i, company) {
                    var li = $("<li/>");
                    var a = $("<a/>").attr("data-id", company.companyId).text(
                            company.companyName);
                    a.click(function () {
                        $(ele).val(this.innerHTML);
                        $(ele).prev().val($(this).attr("data-id"));
                        $(ele).next().hide();
                        if ($(ele).get(0).id == $("#selectCompanyInput").get(0).id) {
                            $("#stopAllUsers").removeClass("hide");
                        }
                    });
                    li.append(a);
                    $(ele).next().append(li);
                    $(ele).next().show();
                });
            });
        }

        /*搜索公司END*/

        /*重置密码*/
        window.resetPwd = function (userId) {
            Modal.confirm({
                msg: '重置后默认密码为"123456"',
            }).on(function (e) {
                if (e) {
                    var url = "admin/user/resetUserPwd.do";
                    var data = "userId=" + userId + "&userPwd=" + md5("123456");
                    $.post(url, data, function (res, status) {
                        alertMsg(res, status);
                    });
                }
            });
        };

        /*更新用户信息*/
        window.updataInfo = function (userId, userState, userName, userType, companyId, companyName, mobile, phone, email
                , weixin, department, position, workNo, address, expiryDate, remark, stateChanged) {
            $("#userId").val(userId);
            $("#upStae").get(0).value = userState;
            $("#upName").val(userName);
            $("#upType").get(0).value = userType;
            $("#upCompany").val(companyId);
            $("#upCompanyInput").val(companyName);
            $("#upMobile").val(mobile);
            $("#upPhone").val(phone);
            $("#upEmail").val(email);
            $("#upWechat").val(weixin);
            $("#upDepartment").val(department);
            $("#upPosition").val(position);
            $("#upWorkNo").val(workNo);
            $("#upAddress").val(address);
            $("#upRemark").text(remark);
            $("#upStateChanged").text(stateChanged);
            if (expiryDate != "") {
                var temp = expiryDate.split("-");
                for (var i = 0; i < temp.length; i++) {
                    $("#ufullYear").get(0).value = (temp[i] * 1);
                    $("#ufullMounth").get(0).value = (temp[i] * 1);
                    $("#ufullDay").get(0).value = (temp[i] * 1);
                }
            } else {
                $("#ufullYear").get(0).value = tempYear;
                $("#ufullMounth").get(0).value = (tempDate.getMonth() + 1);
                $("#ufullDay").get(0).value = tempDate.getDate();
            }
        };

        /*停用该用户*/
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

        /*启用该用户*/
        window.enableUser = function (userId) {
            var url = "admin/user/changeUserState.do?userId=" + userId + "&userState=2";
            $.get(url, function (res, status) {
                alertMsg(res, status);
            });
        };

        /*注销该用户*/
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

        /*查看该用户操作日志*/
        window.lookUseLog = function (userId) {
            $("#logTable").empty();
            $.get("admin/user/getUserLog.do?oprator=" + userId, function (res, status) {
                if (status == "success") {
                    $.each(JSON.parse(res), function (i, log) {
                        var tr = $("<tr/>");
                        var date = new Date(log.logDate);
                        var mounth = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth();
                        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
                        var h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
                        var m = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                        var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
                        var td1 = $("<td/>").text(date.getFullYear() + "-" + mounth + "-" + day + " " + h + ":" + m + ":" + s);
                        var td2 = $("<td/>").text(log.opration);
                        var td3 = $("<td/>").text(log.oprator.userName);
                        tr.append(td1).append(td2).append(td3);
                        $("#logTable").append(tr);
                    });
                }
            });
        }


        /* 更新用户信息 */
        window.sendUp = function () {
            if (isEmty($("#upName").val())) {
                return alr("请输入用户名字");
            }
            if ($("#upType").find("option:selected").val() == "0") {
                return alr("请选择用户类型");
            }
            if (isEmty($("#upCompany").val())) {
                return alr("请选择所属公司");
            }
            if (!rePhone($("#upMobile").val())) {
                return alr("请输入正确的手机号");
            }
            if (!reEmail($("#upEmail").val())) {
                return alr("请输入正确的E-Mail");
            }
            $("#upToDate").val($("#ufullYear").find("option:selected").text() + "-"
                    + $("#ufullMounth").find("option:selected").text() + "-" + $("#ufullDay").find("option:selected").text());
            $.post("admin/user/updateUser.do", $("#upForm").serialize(), function (res, status) {
                alertMsg(res, status);
            });
        };

        /* 添加企业用户 */
        window.sendAdd = function () {
            if (isEmty($("#addName").val())) {
                return alr("请输入用户名字");
            }
            if (isEmty($("#addLoginName").val())) {
                return alr("请输入登录账号");
            } else if (!reUserName($("#addLoginName").val())) {
                return alr("登录账号只能输入字母或数字");
            } else if ($("#addLoginName").val().length < 6 || $("#addLoginName").val().length > 12) {
                return alr("登录账号长度必须在6-12位之间");
            }
            if (isEmty($("#addUserPwd").val())) {
                return alr("请输入密码");
            }
            if ($("#addType").find("option:selected").val() == "0") {
                return alr("请选择用户类型");
            }
            if (isEmty($("#addCompany").val())) {
                return alr("请选择所属公司");
            }
            if (!rePhone($("#addMobile").val())) {
                return alr("请输入正确的手机号");
            }
            if (!reEmail($("#addEmail").val())) {
                return alr("请输入正确的E-Mail");
            }
            $("#addToDate").val($("#fullYear").find("option:selected").text() + "-"
                    + $("#fullMounth").find("option:selected").text() + "-" + $("#fullDay").find("option:selected").text());
            $("#addUserPwd").val(md5($("#addUserPwd").val()));
            console.log($("#addForm").serialize());
            $.post("admin/user/addUser.do", $("#addForm").serialize(), function (res, status) {
                alertMsg(res, status);
            });
        };

        $(document).bind('click', function (e) {
            console.log($(e.target).get(0).id);
            if ($(e.target).get(0).id!=$("#selectCompanyInput").get(0).id||$(e.target).get(0).id!=$("#searchCompanyMenu").get(0).id
            )
                return;
            leftEle.companyName.next().next().hide();
        });

        /* 判断是否为空 */
        function isEmty(str) {
            str = $.trim(str);
            if (str == null || str.length <= 0 || str == "") {
                return true;
            }
            return false;
        }

        function rePhone(str) {
            if (!phoneReg.test(str)) {
                return false;
            }
            return true;
        }

        function reEmail(str) {
            if (!emailReg.test(str)) {
                return false;
            }
            return true;
        }

        function reUserName(str) {
            if (!userNameReg.test(str)) {
                return false;
            }
            return true;
        }

        function alr(masg) {
            alert(masg);
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
</script>

</html>
