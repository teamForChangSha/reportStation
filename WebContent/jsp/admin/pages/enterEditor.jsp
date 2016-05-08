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
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
    <style type="text/css">
        .container {
            padding: 30px 0;
        }

        .form-horizontal .form-group {
            margin-right: 0;
            margin-left: 0;
        }

        .tableTh_tSeach_list {
            background: #595d60;
            color: white;
        }

        .table-hover > tbody > tr:hover {
            background-color: inherit;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="row">
        <h1>
            <small>公司信息设置</small>
        </h1>
        <div class="page-header"></div>
        <form id="enterInfo" action="admin/companyBack/updateCompanyWholeInfo.do" enctype="multipart/form-data"
              method="post" class="form-horizontal">
            <input type="text" name="company.companyId" hidden value="${company.companyId}"/>

            <div class="form-group">
                <label class="col-sm-2 control-label">公司名字：</label>

                <div class="col-sm-3">
                    <input type="text" value="${company.companyName}" name="company.companyName" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">公司名称代码：</label>

                <div class="col-sm-3">
                    <input type="text" value="${company.companyCode}" name="company.companyCode" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">公司股票代码：</label>

                <div class="col-sm-3">
                    <input type="text" value="${company.stockCode}" name="company.stockCode" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">公司联系电话：</label>

                <div class="col-sm-3">
                    <input type="text" id="tel" value="${company.phone}" name="company.phone" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">公司状态：</label>

                <div class="col-sm-3">
                    <c:if test="${company.companyState==1 }">
                        <p id="state" class="form-control-static">正常</p>
                    </c:if>
                    <c:if test="${company.companyState==2 }">
                        <p id="state" class="form-control-static">待审核</p>
                    </c:if>
                    <input type="text" hidden value="${company.companyState }" name="company.companyState"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">公司类型：</label>

                <div class="col-sm-3">
                    <select id="type" name="company.companyType" class="form-control">
                        <option value="0">--请选择--</option>
                        <option value="1">国有</option>
                        <option value="2">民营</option>
                        <option value="3">股份</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">所属行业：</label>

                <div class="col-sm-3">
                    <div class="input-group form-group">
                        <input id="industry" type="text" value="${company.industries}" name="company.industries"
                               class="form-control"/>
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" data-toggle="modal"
                                    data-target="#selectIndustry">
                                <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                            </button>
                        </span>
                    </div>
                </div>
            </div>
            <%--jsp/admin/pages/industrySelect.jsp选择行业--%>
            <div class="form-group">
                <label class="col-sm-2 control-label">公司描述：</label>

                <div class="col-sm-5">
                    <textarea rows="5" name="company.description" class="form-control">${company.description}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">服务协议文本：</label>

                <div class="col-sm-5">
                    <textarea rows="5" name="companyOther.serviceProtocol"
                              class="form-control">${company.otherInfo.serviceProtocol}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">服务协议HTML：</label>

                <div class="col-sm-5">
                    <textarea rows="5" name="companyOther.spHtml"
                              class="form-control">${company.otherInfo.spHtml}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">公司LOGO：</label>

                <div class="col-sm-3">
                    <input type="file" name="logo" accept="image/*" class="form-control-static"/>
                </div>
                <div class="col-sm-2">
                    <p class="thumbnail">
                        <img src="jsp/css/img/placeholder.png"/>
                    </p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">通过邮件接受举报：</label>

                <div class="col-sm-2 checkbox">
                    <label>
                        <input id="isSend2" type="text" value="0" name="companyOther.isSend" hidden/>
                        <input value="true" id="isSend" type="checkbox"/>是否接收
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">接收方式：</label>

                <div class="col-sm-2">
                    <select id="sendType" name="companyOther.sendType" class="form-control">
                        <option value="1">单个接收</option>
                        <option value="2">每周接收一次</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">邮箱及姓名：</label>

                <div class="col-sm-2">
                    <input id="contacts1" type="text" value="${company.otherInfo.contacts1}"
                           name="companyOther.contacts1" class="form-control" placeholder="联系人姓名"/>
                </div>
                <div class="col-sm-3">
                    <input id="email1" type="text" value="${company.otherInfo.email1}"
                           name="companyOther.email1" class="form-control" placeholder="联系人邮箱"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>

                <div class="col-sm-2">
                    <input id="contacts2" type="text" value="${company.otherInfo.contacts2}"
                           name="companyOther.contacts2" class="form-control" placeholder="联系人姓名"/>
                </div>
                <div class="col-sm-3">
                    <input id="email2" type="text" value="${company.otherInfo.email2}"
                           name="companyOther.email2" class="form-control" placeholder="联系人邮箱"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>

                <div class="col-sm-2">
                    <input id="contacts3" type="text" value="${company.otherInfo.contacts3}"
                           name="companyOther.contacts3" class="form-control" placeholder="联系人姓名"/>
                </div>
                <div class="col-sm-3">
                    <input id="email3" type="text" value="${company.otherInfo.email3}"
                           name="companyOther.email3" class="form-control" placeholder="联系人邮箱"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">最后修改时间：</label>

                <div class="col-sm-3">
                    <p class="form-control-static">
                        <fmt:formatDate value="${company.stateChanged}" type="date" pattern="yyyy年MM月dd日 HH:mm:ss"/>
                    </p>
                </div>
                <div class="col-sm-2 text-right">
                    <input type="button" id="updata" class="btn btn-warning form-control" value="修改"/>
                </div>
            </div>
        </form>
    </div>
</div>

<!--选择行业对话框-->
<div class="modal fade bs-example-modal-lg" id="selectIndustry" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title" id="exampleModalLabel">行业选择</h5>
            </div>
            <div class="modal-body">
                <table class="table table-bordered table-hover">
                    <tbody>

                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">计算机/互联网/通信/电子</td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('01')">计算机软件</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('37')">计算机硬件</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('38')">计算机服务(系统、数据服务、维修)</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('31')">通信/电信/网络设备</a></td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('39')">通信/电信运营、增值服务</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('32')">互联网/电子商务</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('40')">网络游戏</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('02')">电子技术/半导体/集成电路</a></td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('35')">仪器仪表/工业自动化</a></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">会计/金融/银行/保险</td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('41')">会计/审计</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('03')">金融/投资/证券</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('42')">银行</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('43')">保险</a></td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('62')">信托/担保/拍卖/典当</a></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">贸易/消费/制造/营运</td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('04')">贸易/进出口</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('22')">批发/零售</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('05')">快速消费品(食品、饮料、化妆品)</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('06')">服装/纺织/皮革</a></td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('44')">家具/家电/玩具/礼品</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('60')">奢侈品/收藏品/工艺品/珠宝</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('45')">办公用品及设备</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('14')">机械/设备/重工</a></td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('33')">汽车及零配件</a></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">制药/医疗</td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('08')">制药/生物工程</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('46')">医疗/护理/卫生</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('47')">医疗设备/器械</a></td>
                        <td>&nbsp;</td>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">广告/媒体</td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('12')">广告</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('48')">公关/市场推广/会展</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('49')">影视/媒体/艺术/文化传播</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('13')">文字媒体/出版</a></td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('15')">印刷/包装/造纸</a></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">房地产/建筑</td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('26')">房地产</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('09')">建筑/建材/工程</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('50')">家居/室内设计/装潢</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('51')">物业管理/商业中心</a></td>
                    </tr>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">专业服务/教育/培训</td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('34')">中介服务</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('63')">租赁服务</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('07')">专业服务(咨询、人力资源、财会)</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('59')">外包服务</a></td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('52')">检测，认证</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('18')">法律</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('23')">教育/培训/院校</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('24')">学术/科研</a></td>
                    </tr>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">服务业</td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('11')">餐饮业</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('53')">酒店/旅游</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('17')">娱乐/休闲/体育</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('54')">美容/保健</a></td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('27')">生活服务</a></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">物流/运输</td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('21')">交通/运输/物流</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('55')">航天/航空</a></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">能源/原材料</td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('19')">石油/化工/矿产/地质</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('16')">采掘业/冶炼</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('36')">电气/电力/水利</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('61')">新能源</a></td>
                    </tr>
                    <tr class="tableUnitLine">
                        <td><a href="javascript:void(0);" onclick="getInd('56')">原材料和加工</a></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    <tr>
                        <td colspan="4" class="tableTh_tSeach_list">政府/非赢利机构/其他</td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('28')">政府/公共事业</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('57')">非盈利机构</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('20')">环保</a></td>
                        <td><a href="javascript:void(0);" onclick="getInd('29')">农/林/牧/渔</a></td>
                    </tr>
                    <tr>
                        <td><a href="javascript:void(0);" onclick="getInd('58')">多元化业务集团公司</a></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        /* 设置select需要选中的状态 */
        $("#type").get(0).value = "${company.companyType}";
        /* 设置LOGO的图片 */
        var imgUrl = "${company.otherInfo.logoUrl}";
        if (imgUrl != "") {
            $("img").attr("src", imgUrl);
        }
        console.log("${company.otherInfo.isSend}");
        $("#isSend").get(0).checked = "${company.otherInfo.isSend}"==0?false:true;
        $("#isSend2").val("${company.otherInfo.isSend}");
        console.log("====${company.otherInfo.sendType}");
        $("#sendType").get(0).value = "${company.otherInfo.sendType}";
        $("#isSend").change(function () {
            if ($("#isSend").is(':checked')) {
                $("#isSend2").val(1);
            } else {
                $("#isSend2").val(0);
            }
        });


        /* 设置logo的缩略图 */
        $("input[name=logo]").change(function () {
            if (window.File && window.FileList && window.FileReader) {
                var oFReader = new FileReader();
                oFReader.onload = function (file) {
                    var image = new Image();
                    image.src = file.target.result;
                    var h = image.height;
                    var w = image.width;
                    var fileSize = $("input[name=logo]").get(0).files[0].size / 1024;
                    if (h > 200 || w > 200 || fileSize > 1024) {
                        $("input[name=logo]").val("");
                        return alert("图片宽高不能超过200像素，大小不能超过1M");
                    }
                    $("img").attr("src", file.target.result);
                };
                oFReader.readAsDataURL($("input[name=logo]").get(0).files[0]);
            }
        });

        /* 更新企业信息 */
        var telReg = /^\(?\d{3,4}[-\)]?\d{7,8}$/;
        $("#updata").click(function () {
            if ($("#type").find("option:selected").val() == "0") {
                return Modal.alert({msg:'请选择企业类型'});
            }
            if($("#tel").val()!=''){
                if(!telReg.test($("#tel").val())){
                    return Modal.alert({msg:'请输入正确的电话号码!'});
                }
            }
            if ($("#isSend").is(':checked')) {
                for (var i = 1; i < 4; i++) {
                    if (!isEmty($("#contacts" + i))) {
                        if (!regEmail($("#email" + i))) {
                            return alert("您填写了姓名，请输入正确的邮箱地址!");
                        }
                    }
                    if (!isEmty($("#email" + i))) {
                        if (regEmail($("#email" + i))) {
                            if (isEmty($("#contacts" + i))) {
                                return alert("您填写了邮箱地址，请输入对应的姓名!");
                            }
                        } else {
                            return alert("您填写的邮箱地址有误!");
                        }
                    }
                }
                var temp = 0;
                for (var i = 1; i < 4; i++) {
                    if (!isEmty($("#contacts" + i))) {
                        temp++;
                    }
                }
                if (temp == 0) {
                    return alert("你选择了通过邮件接收举报，请至少填写一个有效邮箱地址,及对应的用户姓名!");
                }
            } else {
                for (var i = 1; i < 4; i++) {
                    $("#contacts" + i).val("");
                    $("#email" + i).val("");
                }
            }
            $("#enterInfo").ajaxSubmit(function (res, state) {
                if(state=="success"){
                    if(res=="success"){
                        Modal.alert({msg:"操作成功!"});
                    }else{
                        Modal.alert({msg:"操作失败!"});
                    }
                }else{
                    Modal.alert({msg:"操作失败!"});
                }
            })
        });

        $("td a").click(function () {
            $("#selectIndustry").modal('hide');
            $("#industry").val($(this).text());
        });

        /**
         * 判断是否为空
         *
         * @param str
         * @returns {Boolean}
         */
        function isEmty(str) {
            str = $.trim(str.val());
            if (str == null || str.length <= 0 || str == "") {
                return true;
            }
            return false;
        }

        var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

        function regEmail(ele) {
            if (!emailReg.test(ele.val())) {
                return false;
            }
            return true;
        }
    });
</script>

</html>