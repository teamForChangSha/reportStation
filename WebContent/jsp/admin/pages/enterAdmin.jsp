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
        .tableTh_tSeach_list {
            background: #595d60;
            color: white;
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

        .table-hover > tbody > tr:hover {
            cursor: pointer;
        }
    </style>
</head>

<body>
<div class="container">
    <h1>
        <small>公司信息设置</small>
    </h1>
    <div class="page-header"></div>
    <div class="row">
        <div class="form-inline">
            <div class="form-group">
                <label class="control-label">搜索关键字：</label>
                <input type="text" class="form-control" style="width: 300px;" placeholder="可模糊搜索企业名称、手机号等">
            </div>
            <input type="button" class="btn btn-default" value="搜索"/>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="table-info">
                    <th>添加时间</th>
                    <th>单位名称</th>
                    <th>联系电话</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>

    <div class="row">
        <input type="button" name="addCompany" class="btn btn-default" value="添加企业"/>
        <input type="button" name="import" style="margin-left: 10px;" class="btn btn-default" value="导入单位信息"/>
    </div>

    <%--行业数据--%>
    <div class="hide" id="hangye">
        <table class="table table-bordered table-hover">
            <tbody>

            <tr>
                <td colspan="4" class="tableTh_tSeach_list">计算机/互联网/通信/电子</td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">计算机软件</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">计算机硬件</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">计算机服务(系统、数据服务、维修)</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">通信/电信/网络设备</a></td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">通信/电信运营、增值服务</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">互联网/电子商务</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">网络游戏</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">电子技术/半导体/集成电路</a></td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">仪器仪表/工业自动化</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">会计/金融/银行/保险</td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">会计/审计</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">金融/投资/证券</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">银行</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">保险</a></td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">信托/担保/拍卖/典当</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">贸易/消费/制造/营运</td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">贸易/进出口</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">批发/零售</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">快速消费品(食品、饮料、化妆品)</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">服装/纺织/皮革</a></td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">家具/家电/玩具/礼品</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">奢侈品/收藏品/工艺品/珠宝</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">办公用品及设备</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">机械/设备/重工</a></td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">汽车及零配件</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">制药/医疗</td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">制药/生物工程</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">医疗/护理/卫生</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">医疗设备/器械</a></td>
                <td>&nbsp;</td>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">广告/媒体</td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">广告</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">公关/市场推广/会展</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">影视/媒体/艺术/文化传播</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">文字媒体/出版</a></td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">印刷/包装/造纸</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">房地产/建筑</td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">房地产</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">建筑/建材/工程</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">家居/室内设计/装潢</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">物业管理/商业中心</a></td>
            </tr>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">专业服务/教育/培训</td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">中介服务</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">租赁服务</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">专业服务(咨询、人力资源、财会)</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">外包服务</a></td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">检测，认证</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">法律</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">教育/培训/院校</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">学术/科研</a></td>
            </tr>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">服务业</td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">餐饮业</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">酒店/旅游</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">娱乐/休闲/体育</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">美容/保健</a></td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">生活服务</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">物流/运输</td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">交通/运输/物流</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">航天/航空</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">能源/原材料</td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">石油/化工/矿产/地质</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">采掘业/冶炼</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">电气/电力/水利</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">新能源</a></td>
            </tr>
            <tr class="tableUnitLine">
                <td><a href="javascript:void(0);" onclick="getInd(this)">原材料和加工</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            <tr>
                <td colspan="4" class="tableTh_tSeach_list">政府/非赢利机构/其他</td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">政府/公共事业</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">非盈利机构</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">环保</a></td>
                <td><a href="javascript:void(0);" onclick="getInd(this)">农/林/牧/渔</a></td>
            </tr>
            <tr>
                <td><a href="javascript:void(0);" onclick="getInd(this)">多元化业务集团公司</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tbody>
        </table>
    </div>
</div>

<!--添加企业-->
<div class="modal fade bs-example-modal-lg" id="addCompanPanel" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" onclick="addClose()">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title text-center">添加企业</h5>
            </div>
            <div class="modal-body" id="addCompanHtml">
                <form id="addForm" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司名字：</label>

                        <div class="col-sm-5">
                            <input type="text" id="addCompanyName" name="company.companyName" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司名称代码：</label>

                        <div class="col-sm-5">
                            <input type="text" id="addCompanyCode" name="company.companyCode" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司股票代码：</label>

                        <div class="col-sm-5">
                            <input type="text" id="addStockCode" name="company.stockCode" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司联系电话：</label>

                        <div class="col-sm-5">
                            <input type="text" id="addPhone" name="company.phone" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司状态：</label>

                        <div class="col-sm-5">
                            <select id="addCompanyState" name="company.companyState" class="form-control">
                                <option value="2">待审核</option>
                                <option value="1">正常</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司类型：</label>

                        <div class="col-sm-5">
                            <select id="addCompanyType" name="company.companyType" class="form-control">
                                <option value="0">--请选择--</option>
                                <option value="1">国有</option>
                                <option value="2">民营</option>
                                <option value="3">股份</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">所属行业：</label>

                        <div class="col-sm-5">
                            <div class="input-group">
                                <input id="addIndustry" type="text" name="company.industries"
                                       class="form-control"/>
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" onclick="addSelctedIndustry()">
                                <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                            </button>
                        </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司描述：</label>

                        <div class="col-sm-5">
                            <textarea id="addDescription" rows="5" name="company.description"
                                      class="form-control"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4"></div>

                        <div class="col-sm-5 text-center">
                            <input type="button" class="btn btn-default form-control" value="添加" onclick="sendAdd()"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!--修改企业-->
<div class="modal fade bs-example-modal-lg" id="upCompanPanel" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" onclick="upClose()">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title text-center">修改企业</h5>
            </div>
            <div class="modal-body" id="upCompanHtml">
                <form id="upForm" class="form-horizontal">
                    <input type="text" id="upCompanyId" name="company.companyId" hidden/>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司名字：</label>

                        <div class="col-sm-5">
                            <input type="text" id="upCompanyName" name="company.companyName" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司名称代码：</label>

                        <div class="col-sm-5">
                            <input type="text" id="upCompanyCode" name="company.companyCode" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司股票代码：</label>

                        <div class="col-sm-5">
                            <input type="text" id="upStockCode" name="company.stockCode" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司联系电话：</label>

                        <div class="col-sm-5">
                            <input type="text" id="upPhone" name="company.phone" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司状态：</label>

                        <div class="col-sm-5">
                            <select id="upCompanyState" name="company.companyState" class="form-control">
                                <option value="2">待审核</option>
                                <option value="1">正常</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司类型：</label>

                        <div class="col-sm-5">
                            <select id="upCompanyType" name="company.companyType" class="form-control">
                                <option value="0">--请选择--</option>
                                <option value="1">国有</option>
                                <option value="2">民营</option>
                                <option value="3">股份</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">所属行业：</label>

                        <div class="col-sm-5">
                            <div class="input-group">
                                <input id="upIndustry" type="text" name="company.industries"
                                       class="form-control"/>
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" onclick="upSelctedIndustry()">
                                <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                            </button>
                        </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">公司描述：</label>

                        <div class="col-sm-5">
                            <textarea id="upDescription" rows="5" name="company.description"
                                      class="form-control"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4"></div>

                        <div class="col-sm-5 text-center">
                            <input type="button" class="btn btn-default form-control" value="提交" onclick="sendUp()"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        for (var i = 0; i < 3; i++) {
            var tr = $("<tr/>");
            var td1 = $("<td/>").text("2015-12-01 14:20:30");
            var td2 = $("<td/>").text("苹果中国");
            var td3 = $("<td/>").text("18600000000");
            var td4 = $("<td/>");
            var a1 = $("<a/>").attr("class", "btn btn-link").text("修改信息");
            a1.click(function () {
                hiddenPanle();
                panle.updataPwd.removeClass("hidden");
                //TODO
            });
            var a2 = $("<a/>").attr("class", "btn btn-link").text("停用");
            a2.click(function () {
                hiddenPanle();
                panle.updataUserInfo.removeClass("hidden");
                //TODO
            });
            td4.append(a1).append(a2);
            tr.append(td1).append(td2).append(td3).append(td4);
//            $("tbody").append(tr);
        }

        /*添加企业*/
        $("input[name=addCompany]").click(function () {
            $("#addCompanPanel").modal("show");
        });

        /*发送添加企业请求*/
        window.sendAdd = function () {
            console.log($("#addForm").serialize());
            $.post("admin/companyBack/addWholeCompany.do",$("#addForm").serialize(),function(res,state){
                alertMsg(res,state);
            });
        };

        /*添加选择企业*/
        var t = 0;
        var addBool = true, upBool = true;
        var addTemp;
        window.addSelctedIndustry = function () {
            addTemp = $("#addCompanHtml").html();
            t = 1;
            addBool = false;
            saveAddInfo();
            $("#addCompanHtml").html($("#hangye").html());
        };
        window.addClose = function () {
            if (addBool) {
                $("#addCompanPanel").modal("hide");
            } else {
                $("#addCompanHtml").html(addTemp);
                addBool = true;
                getAddInfo();
            }
        };
        function saveAddInfo(){
            localStorage.setItem("addCompanyName",$("#addCompanyName").val());
            localStorage.setItem("addCompanyCode",$("#addCompanyCode").val());
            localStorage.setItem("addStockCode",$("#addStockCode").val());
            localStorage.setItem("addPhone",$("#addPhone").val());
            localStorage.setItem("addCompanyState",$("#addCompanyState").find("option:selected").val());
            localStorage.setItem("addCompanyType",$("#addCompanyType").find("option:selected").val());
            localStorage.setItem("addIndustry",$("#addIndustry").val());
            localStorage.setItem("addDescription",$("#addDescription").val());
        }
        function getAddInfo(){
            $("#addCompanyName").val(localStorage.getItem("addCompanyName"));
            $("#addCompanyCode").val(localStorage.getItem("addCompanyCode"));
            $("#addStockCode").val(localStorage.getItem("addStockCode"));
            $("#addPhone").val(localStorage.getItem("addPhone"));
            $("#addCompanyState").get(0).value = localStorage.getItem("addCompanyState");
            $("#addCompanyType").get(0).value = localStorage.getItem("addCompanyType");
            $("#addIndustry").val(localStorage.getItem("addIndustry"));
            $("#addDescription").val(localStorage.getItem("addDescription"));
        }
        /*添加选择企业 END*/

        /*更新选择企业*/
        var upTemp;
        window.upSelctedIndustry = function () {
            upTemp = $("#upCompanHtml").html();
            t = 2;
            upBool = false;
            saveUpInfo();
            $("#upCompanHtml").html($("#hangye").html());
        };
        window.upClose = function () {
            if (upBool) {
                $("#upCompanPanel").modal("hide");
            } else {
                $("#upCompanHtml").html(upTemp);
                upBool = true;
                getUpInfo();
            }
        };
        function saveUpInfo(){
            localStorage.setItem("upCompanyId",$("#upCompanyId").val());
            localStorage.setItem("upCompanyName",$("#upCompanyName").val());
            localStorage.setItem("upCompanyCode",$("#upCompanyCode").val());
            localStorage.setItem("upStockCode",$("#upStockCode").val());
            localStorage.setItem("upPhone",$("#upPhone").val());
            localStorage.setItem("upCompanyState",$("#upCompanyState").find("option:selected").val());
            localStorage.setItem("upCompanyType",$("#upCompanyType").find("option:selected").val());
            localStorage.setItem("upIndustry",$("#upIndustry").val());
            localStorage.setItem("upDescription",$("#upDescription").val());
        }
        function getUpInfo(){
            $("#upCompanyId").val(localStorage.getItem("upCompanyId"));
            $("#upCompanyName").val(localStorage.getItem("upCompanyName"));
            $("#upCompanyCode").val(localStorage.getItem("upCompanyCode"));
            $("#upStockCode").val(localStorage.getItem("upStockCode"));
            $("#upPhone").val(localStorage.getItem("upPhone"));
            $("#upCompanyState").get(0).value = localStorage.getItem("upCompanyState");
            $("#upCompanyType").get(0).value = localStorage.getItem("upCompanyType");
            $("#upIndustry").val(localStorage.getItem("upIndustry"));
            $("#upDescription").val(localStorage.getItem("upDescription"));
        }
        /*更新选择企业 END*/

        /*行业表格每一项被点击事件*/
        window.getInd = function (ele) {
            if (t == 1) {
                $("#addCompanHtml").html(addTemp);
                addBool = true;
                getAddInfo();
                $("#addIndustry").val($(ele).text());
            }
            if (t == 2) {
                $("#upCompanHtml").html(upTemp);
                upBool = true;
                getUpInfo();
                $("#upIndustry").val($(ele).text());
            }
        };


        /*更新企业*/
        window.updataCompany = function (id) {
            //TODO
            $("#upCompanPanel").modal("show");
        };
        /*发送更新请求*/
        window.sendUp = function(){
            console.log($("#upForm").serialize());
            //TODO
            $.post("admin/companyBack/addWholeCompany.do",$("#upForm").serialize(),function(res,state){
                alertMsg(res,state);
            });
        };
        $("input[name=import]").click(function () {
            $("#upCompanPanel").modal("show");
        });

        /*删除企业*/
        window.delCompany = function (id) {
            Modal.confirm({
                title: '警告',
                msg: '你确定要删除吗?',
            }).on(function (e) {
                if (e) {
                    $.get("admin/companyBack/deleteCompanyBranch.do?branchId=" + id, function (res, status) {
                        alertMsg(res, status);
                    });
                }
            });
        };

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