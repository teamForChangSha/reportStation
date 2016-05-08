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
    <meta http-equiv="X-UA-Compatible" content="IE=7,IE=8,IE=edge">
    <meta charset="UTF-8">
    <title></title>
    <link href="jsp/css/datepicker.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/jquery.bs_pagination.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/datepicker.min.js"></script>
    <!-- Include English language -->
    <script src="jsp/js/datepicker.en.js"></script>
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

        .col-sm-12 {
            padding-left: 0;
        }

    </style>
</head>

<body>
<div class="container">
    <form id="selectForm" action="admin/caseBack/showCaseByCompany.do" method="post">
        <div class="row">
            <h1>
                <small>举报管理</small>
            </h1>
            <div class="page-header"></div>
            <div class="form-inline">
                <div class="form-group">
                    <label class="control-label">选择时间段：</label>
                    <input type='text' name="startTime" style="width: 110px;" class="datepicker-here form-control"
                           data-position="right top"/>
                </div>
                <div class="form-group">
                    <label class="control-label">到</label>
                    <input type='text' name="endTime" style="width: 110px;" class="datepicker-here form-control"
                           data-position="right top"/>
                </div>
                <div class="form-group" style="margin-left: 10px;">
                    <label class="control-label">审批状态</label>
                    <select id="selectStatus" class="form-control" name="caseState">
                        <option value="">-请选择-</option>
                        <option value="1">新建</option>
                        <option value="2">已查看</option>
                        <option value="3">处理中</option>
                        <option value="4">处理完毕</option>
                        <option value="5">关闭案件</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-inline">
                <div class="form-group">
                    <label class="control-label">快捷时间段：</label>
                    <input type="button" id="oneMounth" class="btn btn-default" value="最近一个月"/>
                </div>
                <div class="form-group">
                    <input type="button" id="threeMounth" class="btn btn-default" value="最近三个月"/>
                </div>
                <div class="form-group">
                    <input type="button" id="oneYear" class="btn btn-default" value="最近一年"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-inline">
                <div class="form-group">
                    <label class="control-label">案件号查询：</label>
                    <input type='text' name="trackingNo" style="width: 200px;" class="form-control" placeholder="请输入案件编号"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-inline">
                <div class="form-group">
                    <label class="control-label">所举报类型：</label>
                    <input type='text' name="rtList" style="width: 200px;" class="form-control" placeholder="请输入举报类型"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-inline">
                <div class="form-group">
                    <label class="control-label">搜索关键字：</label>
                    <input type="text" name="keyWord" class="form-control" style="width: 200px;"
                           placeholder="可查询问题答案关键字">
                </div>
                <input type="button" id="submitSelect" class="btn btn-default" value="搜索"/>
                <input type="button" id="exportBtn" class="btn btn-default" value="导出"/>
            </div>
        </div>
    </form>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="table-info">
                    <th>选择</th>
                    <th>举报人</th>
                    <th>案件编号</th>
                    <th>举报时间</th>
                    <th>举报状态</th>
                    <th>举报对象</th>
                    <th>举报类型</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="reports">
                </tbody>
            </table>
        </div>
    </div>
    <div class="col-sm-10">
        <div class="row text-center">
            <div id="pageBar"></div>
        </div>
    </div>
</div>

<!--修改案件状态对话框-->
<div class="modal fade bs-example-modal-sm" id="updataReportStatus" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title text-center">更改案件状态</h5>
            </div>
            <div class="modal-body">
                <div class="form-horizontal" id="upStatePanle">
                    <input type="text" id="upStateId" hidden>
                    <input type="text" id="upStateCompanyId" hidden>
                    <p class="text-center"></p>

                    <div class="form-group">
                        <div class="col-sm-10 col-sm-offset-1">
                            <select id="status" class="form-control">
                                <option value="">-请选择-</option>
                                <option value="1">新建</option>
                                <option value="2">已查看</option>
                                <option value="3">处理中</option>
                                <option value="4">处理完毕</option>
                                <option value="5">关闭案件</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group text-center">
                        <div class="checkbox">
                            <label>
                                <input id="sendToPlatform" type="checkbox"/> 是否交由平台方处理
                            </label>
                        </div>
                    </div>
                    <div class="form-group text-center">
                        <input type="button" id="updataCancel" class="btn btn-default" value="取消">
                        <input type="button" id="updataBtn" class="btn btn-default" value="更改">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--查看案件信息对话框-->
<div class="modal fade bs-example-modal-lg" id="reprotInfoPanel" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title text-center">案件详情</h5>
            </div>
            <div class="modal-body">
                <iframe frameborder="0" width="100%" height="650px"></iframe>
            </div>
        </div>
    </div>
</div>

<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script>
    var userCompanyId = ${user.userCompany.companyId};
</script>
<script src="jsp/js/reportAdmin.js" type="text/javascript" charset="utf-8"></script>

</html>
