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
    <script src="jsp/js/jquery.bs_pagination.js" type="text/javascript" charset="utf-8"></script>
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

        .pagination li a {
            cursor: default;
        }

        .btn-group {
            display: inline;
        }

        .xinghao {
            color: #C12E2A;
            font-weight: bold;
            padding: 4px;
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
        <form id="selectForm" class="form-inline">
            <div class="form-group">
                <label class="control-label">用户类型：</label>
                <select id="userType" name="userType" class="form-control">
                    <option value="">-请选择-</option>
                    <option value="1">试用</option>
                    <option value="2">公司用户</option>
                    <c:if test="${user.userType>2}">
                        <option value="3">系统操作员</option>
                    </c:if>
                    <c:if test="${user.userType==4}">
                        <option value="4">系统管理员</option>
                    </c:if>
                </select>
            </div>
            <div class="form-group">
                <label class="control-label">用户状态：</label>
                <select id="userStatus" name="userState" class="form-control">
                    <option value="">-请选择-</option>
                    <option value="1">新增</option>
                    <option value="2">有效</option>
                    <option value="3">停用</option>
                    <option value="4">注销</option>
                </select>
            </div>
            <br>

            <div class="form-group">
                <label class="control-label">所属公司：</label>

                <div class="btn-group">
                    <input type="text" id="companyId" name="companyId" hidden/>
                    <input type="text" id="selectCompanyInput" class="form-control"
                           placeholder="请选择公司" autocomplete="off" onkeyup="searchCompany(this)"/>
                    <ul id="searchCompanyMenu" class="dropdown-menu"></ul>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label">搜关键字：</label>
                <input type="text" id="keyWord" name="keyWord" autocomplete="on"
                       placeholder="用户名" class="form-control"/>
                <input type="button" id="selected" class="btn btn-default" value="搜索"/>
                <input type="button" data-toggle="modal" data-target="#addUser" class="btn btn-default" value="添加用户"/>
                <input type="button" id="stopAllUsers" onclick="stopAllUser()" class="btn btn-warning hide"
                       value="停用该公司所有用户"/>
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
                <tbody id="userList">
                </tbody>
            </table>
        </div>
        <div class="col-sm-10">
            <div class="row text-center">
                <div id="pageBar"></div>
            </div>
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
                    <h5 class="modal-title text-center">修改用户信息（带<span class="xinghao">*</span>为必填）</h5>
                </div>
                <div class="modal-body" id="upHtml"/>
                <div class="row">
                    <div class="col-sm-12">
                        <form id="upForm" class="form-horizontal">
                            <input type="text" id="userId" hidden name="userId">

                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>用户名字：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="upName" name="userName" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>用户类型：</label>

                                <div class="col-sm-8">
                                    <select id="upType" name="userType" class="form-control">
                                        <option value="0">-请选择-</option>
                                        <option value="1">试用</option>
                                        <option value="2">公司用户</option>
                                        <c:if test="${user.userType>2}">
                                            <option value="3">系统操作员</option>
                                        </c:if>
                                        <c:if test="${user.userType==4}">
                                            <option value="4">系统管理员</option>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>用户状态：</label>

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
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>选择公司：</label>

                                <div class="col-sm-8">
                                    <div class="btn-group">
                                        <input type="text" id="upCompany" name="userCompany.companyId" hidden/>
                                        <input type="text" id="upCompanyInput" class="form-control"
                                               placeholder="请选择公司" autocomplete="off" onkeyup="searchCompany(this)"/>
                                        <ul id="upCompanyMenu" class="dropdown-menu"></ul>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>手机号码：</label>

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
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>邮箱地址：</label>

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
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>有效期至：</label>
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
                <h5 class="modal-title text-center">添加用户（带<span class="xinghao">*</span>为必填）</h5>
            </div>
            <div class="modal-body" id="addHtml">
                <div class="row">
                    <div class="col-sm-12">
                        <form id="addForm" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>用户名字：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addName" name="userName" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>用户账号：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="addLoginName" name="loginName" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>用户密码：</label>

                                <div class="col-sm-8">
                                    <input type="password" id="addUserPwd" name="userPwd" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>用户类型：</label>

                                <div class="col-sm-8">
                                    <select id="addType" name="userType" class="form-control">
                                        <option value="0">-请选择-</option>
                                        <option value="1">试用</option>
                                        <option value="2">公司用户</option>
                                        <c:if test="${user.userType>2}">
                                            <option value="3">系统操作员</option>
                                        </c:if>
                                        <c:if test="${user.userType==4}">
                                            <option value="4">系统管理员</option>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>用户状态：</label>

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
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>选择公司：</label>

                                <div class="col-sm-8">
                                    <div class="btn-group">
                                        <input type="text" id="addCompany" name="userCompany.companyId" hidden/>
                                        <input type="text" id="addCompanyInput" class="form-control"
                                               placeholder="请选择公司" autocomplete="off" onkeyup="searchCompany(this)"/>
                                        <ul id="addCompanyMenu" class="dropdown-menu"></ul>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>手机号码：</label>

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
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>邮箱地址：</label>

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
                                <label class="col-sm-3 control-label"><span class="xinghao">*</span>有效期至：</label>
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
<script>
    var userId = "${user.userId}"
</script>
<script type="text/javascript" src="jsp/js/usersAdmin.js"></script>

</html>
