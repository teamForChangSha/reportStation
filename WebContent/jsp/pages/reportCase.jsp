<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="cache-control" content="no-cache">
    <title>51report-案件举报</title>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/common_top.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <style type="text/css">
        body {
            padding-bottom: 0;
        }

        .navbar-text {
            float: inherit;
        }

        #phone-input-group {
            padding-right: 15px;
            padding-left: 15px;
        }

        .form-file {
            padding: 6px 0;
            opacity: 0;
            filter: alpha(opacity=0);
            cursor: pointer;
        }

        .col-xs-6 {
            margin-bottom: 10px;
        }

        .xinghao {
            color: #C12E2A;
            font-weight: bold;
            padding: 4px;
        }

        input[type=text].form-control {
            padding: 6px 32px 6px 12px;
        }

        label {
            font-weight: 400;
        }

        .navbar-right {
            margin-right: 15px;
        }

        #content,
        #bom {
            margin-top: 30px;
            padding: 20px;
            border: 1px solid #DDDDDD;
            border-radius: 4px;
            box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.075);
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

        .bg-info,
        .bg-danger {
            border-radius: 6px;
            padding: 6px;
        }

        .has-error {
            color: red;
        }

        .glyphicon-ok {
            color: #419641;
        }

        .glyphicon-remove {
            color: #C12E2A;
        }

        .glyphicon {
            right: 9px;
        }

        .table > tbody > tr > td {
            border-top: none;
        }

        .table {
            margin-bottom: 0px;
        }
    </style>
</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation" id="top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
            </button>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav pull-right">
                <li><a href="<%=basePath%>">首页</a></li>
                <li><a href="#" target="_blank">商业行为和道德准则</a></li>
                <li><a href="#">常见问题</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="form-horizontal">
        <div class="row">
            <div class="col-sm-2"></div>
            <div class="col-sm-8" id="content">
                <div class="form-group text-center">
                    <span>(<strong>说明：1.下文中所有带<span
                            class="xinghao">*</span>项目均必须填写2.请确保举报内容的真实性、客观性，不得捏造事实、制造假证</strong>。)</span>
                </div>
                <div class="form-group text-center">
                    <input type="checkbox" value="true" id="agreed" name="agreed"/>
                    <label><span class="xinghao">*</span>是 – 我同意这项报告相关的 <a href="javascript:;" class="btn-link"
                                                                           data-toggle="modal"
                                                                           data-target="#agreeMentPanel">条款与条件</a>
                    </label>
                </div>

                <%--联系信息模块--%>
                <div class="form-group text-center">
                    <div class="page-header"></div>
							<span><strong><span class="xinghao">*</span>请选择匿名举报或者实名举报</strong>
							</span>
                    <br/>
                    <label class="radio-inline">
                        <input type="radio" name="isAnonymous" value="true"/> 匿名
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="isAnonymous" value="false"/> 实名
                    </label>
                </div>
                <div id="contactInfo" class="form-group hidden">
                    <label class="col-sm-4 control-label">联系方式：
                        <p>（为方便后期跟进,请留下匿名联系方式）</p>
                    </label>

                    <div class="col-sm-7">
                        <textarea name="contactWay" rows="3" class="form-control" placeholder=""></textarea>
                    </div>
                </div>
                <form id="userInfo" action="" method="post">
                    <div class="form-group text-center">
                        <span><strong>如果您希望 ${companyBranch.owner.companyName} 知道您的身份，请完成以下内容：</strong></span>
                    </div>
                    <div class="form-group">
                        <input name="reporterId" type="text" hidden/>
                        <label class="col-sm-4 control-label">手机号：</label>

                        <div class="input-group col-sm-5" id="phone-input-group">
                            <input name="mobile" type="text" class="form-control" placeholder="实名手机号"> <span
                                class="input-group-btn">
									<button id="getCode" class="btn btn-default" type="button">获取验证码</button>
								</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">验证码：</label>

                        <div class="col-sm-3">
                            <input name="verifyCode" type="text" class="form-control" placeholder="请输入手机验证码"/>
                            <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">您的姓名：</label>

                        <div class="col-sm-3">
                            <input name="name" type="text" class="form-control" placeholder="姓名"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">您的证件号：</label>

                        <div class="col-sm-3">
                            <select name="idName" class="form-control">
                                <option value="-1">-请选择证件类型-</option>
                                <option value="身份证">身份证</option>
                                <option value="护照">护照</option>
                            </select>
                        </div>
                        <div class="col-sm-4">
                            <input name="idNo" type="text" class="form-control" placeholder="证件号"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">邮箱：</label>

                        <div class="col-sm-4">
                            <input name="email" type="text" class="form-control" placeholder="邮箱地址"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">与您联系的最佳时间和方式：</label>

                        <div class="col-sm-7">
                            <textarea name="bestContact" rows="6" class="form-control"></textarea>
                        </div>
                    </div>
                </form>

                <%--被举报公司信息模块--%>
                <div class="page-header"></div>
                <div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>被举报公司相关信息</strong>
							</span>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">公司名称：</label>

                    <div class="col-sm-8">
                        <span class="form-info"><strong>${companyBranch.owner.companyName}
                            / ${companyBranch.branchName }</strong></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">公司地址：</label>

                    <div class="col-sm-8">
                        <span class="form-info">${companyBranch.address }</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">公司所在省份：</label>

                    <div class="col-sm-8">
                        <span class="form-info">${companyBranch.province.name }</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">公司所在城市：</label>

                    <div class="col-sm-8">
                        <span class="form-info">${companyBranch.city.name }</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">所在地邮编：</label>

                    <div class="col-sm-8">
                        <span class="form-info">${companyBranch.postCode }</span>
                    </div>
                </div>
            </div>
        </div>

        <!--具体问题模块-->
        <div class="row">
            <div class="col-sm-2"></div>
            <div class="col-sm-8" id="bom">
                <div class="form-group text-center">
                    <h4 class="bg-info">
                        <c:forEach items="${fn:split(rtList, ',')}" var="info" varStatus="i">
                            <strong>${info} </strong><br><br>
                        </c:forEach>
                    </h4>
                </div>


                <%--第一个问题与该公司的关系--%>
                <div id="quest_1" class="form-group text-center hidden">
                    <span><strong>您与 ${companyBranch.owner.companyName } 的关系：</strong></span>
                    <br/><br/>

                    <div class="row">
                        <div class="col-sm-4"></div>
                        <div class="col-sm-4">
                            <select name="quest_1_select" class="form-control">
                                <option value="-1">-请选择一项-</option>
                                <option>员工</option>
                                <option>前员工</option>
                                <option>供应商</option>
                                <option>分包商</option>
                                <option>股东</option>
                                <option>客户</option>
                                <option>其它</option>
                            </select>
                        </div>
                    </div>
                    <br/>

                    <div class="form-group text-center">
                        <p>如果选择其它，请说明：</p>

                        <div class="col-sm-3"></div>
                        <div class="col-sm-7">
                            <textarea type="text" name="quest_1_textarea" rows="6" class="form-control"></textarea>
                        </div>
                    </div>
                </div>


                <%--第二个问题与事件相关的人员身份信息--%>
                <div id="quest_2" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>请说明该事件或违规行为主要相关人的身份</strong></span>
                    <br/> <span>(例如：未知/内部审计主管；张三/厂长)</span>

                    <div class="form-group">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-8">
                            <table class="table">
                                <tbody>
                                <tr>
                                    <td></td>
                                    <td>姓名</td>
                                    <td>职位</td>
                                    <td>部门</td>
                                </tr>
                                <tr>
                                    <td><span class="form-info">1</span></td>
                                    <td><input type="text" name="quest_2_input1_name" class="form-control"/></td>
                                    <td><input type="text" name="quest_2_input1_position" class="form-control"/></td>
                                    <td><input type="text" name="quest_2_input1_department" class="form-control"/></td>
                                </tr>
                                <tr>
                                    <td><span class="form-info">2</span></td>
                                    <td><input type="text" name="quest_2_input2_name" class="form-control"/></td>
                                    <td><input type="text" name="quest_2_input2_position" class="form-control"/></td>
                                    <td><input type="text" name="quest_2_input2_department" class="form-control"/></td>
                                </tr>
                                <tr>
                                    <td><span class="form-info">3</span></td>
                                    <td><input type="text" name="quest_2_input3_name" class="form-control"/></td>
                                    <td><input type="text" name="quest_2_input3_position" class="form-control"/></td>
                                    <td><input type="text" name="quest_2_input3_department" class="form-control"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <%--第三个问题是否怀疑与领导有关--%>
                <div id="quest_3" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>您是否怀疑或知道有领导或管理人员与该事件或违规行为有关？</strong></span>
                    <br/>
                    <label class="radio-inline">
                        <input type="radio" value="是" name="quest_3_radio"/> 是
                    </label>
                    <label class="radio-inline">
                        <input type="radio" value="否" name="quest_3_radio"/> 否
                    </label>
                    <label class="radio-inline">
                        <input type="radio" value="不愿意透漏" name="quest_3_radio"/> 不愿意透漏
                    </label>
                    <br/><br/>

                    <p>如果选择是，请说明是谁？</p>

                    <div class="col-sm-3"></div>
                    <div class="col-sm-7">
                        <textarea type="text" name="quest_3_textarea" rows="6" class="form-control"
                                  placeholder="例如：未知/内部审计主管；张三/厂长"></textarea>
                    </div>
                </div>

                <%--第四个问题公司高层管理是否注意到--%>
                <div id="quest_4" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>是否有公司高级管理人员注意到该事件或违规行为了吗？</strong></span>
                    <br/>
                    <label class="radio-inline">
                        <input type="radio" value="是" name="quest_4_radio"/> 是
                    </label>
                    <label class="radio-inline">
                        <input type="radio" value="否" name="quest_4_radio"/> 否
                    </label>
                    <label class="radio-inline">
                        <input type="radio" value="不知道" name="quest_4_radio"/> 不知道
                    </label>
                </div>

                <%--第五个问题该事件发生的时间--%>
                <div id="quest_5" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>请说明该事件或违规行为发生的时间：</strong></span>
                    <br/><br/>

                    <div class="form-group">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-7">
                            <textarea type="text" name="quest_5_textarea" rows="6" class="form-control"></textarea>

                            <p>（例如：2013年6月25日；一周前；一个半月前）</p>
                        </div>
                    </div>
                </div>

                <%--第六个问题该事件持续时间--%>
                <div id="quest_6" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>您认为此问题持续了多少时间？</strong></span>
                    <br/>
                    <br/>

                    <div class="row">
                        <div class="col-sm-4"></div>
                        <div class="col-sm-4">
                            <select name="quest_6_select" class="form-control">
                                <option value="-1">-请选择一项-</option>
                                <option>一次</option>
                                <option>一周</option>
                                <option>1至3个月</option>
                                <option>三个月至1年</option>
                                <option>一年以上</option>
                                <option>不知道</option>
                                <option>其它</option>
                            </select>
                        </div>
                    </div>
                    <br/>

                    <p>如果选择其它，请说明：</p>

                    <div class="col-sm-3"></div>
                    <div class="col-sm-7">
                        <textarea type="text" name="quest_6_textarea" rows="6" class="form-control"></textarea>
                    </div>
                </div>

                <%--第七个问题该事件发生的地点--%>
                <div id="quest_7" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>请说明该事件或违规行为在哪里发生的？</strong></span>
                    <br/><br/>

                    <div class="form-group">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-7">
                            <textarea type="text" rows="6" name="quest_7_textarea" class="form-control"></textarea>

                            <p>我们了解到该事件可能没有确切的发生地点，但如果事件有某些文档或业务交易的记录，请相应指明。</p>
                        </div>
                    </div>
                </div>

                <%--第八个问题该事件涉及的金额--%>
                <div id="quest_8" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>您估计该事件或违规行为涉及金额是多少？</strong></span>
                    <br/><br/>

                    <div class="form-group">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-3">
                            <select name="quest_8_select1" class="form-control">
                                <option value="-1">请选择</option>
                                <option>人民币（RMB）</option>
                                <option>美元（USD）</option>
                                <option>欧元（EUR）</option>
                                <option>英镑（GBP）</option>
                                <option>日元（JPY）</option>
                            </select>
                        </div>
                        <div class="col-sm-3">
                            <select name="quest_8_select2" class="form-control">
                                <option value="-1">-- 请选择 --</option>
                                <option>&lt;5000</option>
                                <option>5000-10000</option>
                                <option>10000-100000</option>
                                <option>100000-500000</option>
                                <option>500000-1000000</option>
                                <option>&gt;1000000</option>
                            </select>
                        </div>
                    </div>
                </div>

                <%--第九个问题如何发现此行为的--%>
                <div id="quest_9" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>您是如何发现该事件或违规行为的？</strong></span>
                    <br/>
                    <br/>

                    <div class="row">
                        <div class="col-sm-4"></div>
                        <div class="col-sm-4">
                            <select name="quest_9_select" class="form-control">
                                <option value="-1">-请选择一项-</option>
                                <option>我是当事人</option>
                                <option>我看到的</option>
                                <option>我听说的</option>
                                <option>同事告诉我的</option>
                                <option>公司外部的人告诉我的</option>
                                <option>其它</option>
                            </select>
                        </div>
                    </div>
                    <br/>

                    <p>如果选择其它，请说明：</p>

                    <div class="col-sm-3"></div>
                    <div class="col-sm-7">
                        <textarea type="text" name="quest_9_textarea" rows="6" class="form-control"></textarea>
                    </div>
                </div>

                <%--第十个问题之前是否报告过--%>
                <div id="quest_10" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>您之前是否报告过该事件或违规行为？</strong></span>
                    <br/>
                    <label class="radio-inline">
                        <input type="radio" value="是" name="quest_10_radio"/> 是
                    </label>
                    <label class="radio-inline">
                        <input type="radio" value="否" name="quest_10_radio"/> 否
                    </label>
                    <br/><br/>

                    <div class="form-group">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-7">
                            <p>如果选择“是”，请说明是何时以何种方式向谁报告的？</p>
                            <textarea type="text" name="quest_10_textarea" rows="6" class="form-control"></textarea>
                        </div>
                    </div>
                </div>

                <%--第十一个问题隐瞒人员及理由方式--%>
                <div id="quest_11" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>请说明试图隐瞒该事件或违规行为的人以及他们的隐瞒方式：</strong></span>
                    <br/><br/>

                    <div class="form-group">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-7">
                            <textarea type="text" name="quest_11_textarea" rows="6" class="form-control"></textarea>

                            <p>（例如：张三/厂长/修改财务数据；李四/市场主管/忽略此事）</p>
                        </div>
                    </div>
                </div>

                <%--第十二个问题事件的详细细节--%>
                <div id="quest_12" class="form-group text-center hidden">
                    <div class="page-header"></div>
                    <span><strong>请详细阐述该事件或违规行为的完整细节：</strong></span>
                    <br/><br/>

                    <div class="form-group">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-6">
                            <textarea type="text" name="quest_12_textarea" rows="6" class="form-control"></textarea>

                            <p>请花一些时间尽可能提供细节，但请小心不要提供泄漏您身份的细节，除非您自己愿意。如果您是唯一的知情人，了解这一点非常重要。</p>
                        </div>
                    </div>
                </div>

                <div class="form-group text-center">
                    <div class="page-header"></div>
                    <span><strong> 如果您有支持该事件或违规行为的文档或文件，请上传图片或视频</strong></span>
                    <br/> <a href="#" class="btn-link" data-toggle="modal" data-target="#upLoadPanel">单击此处上传文件</a>
                    <br/>

                    <p>（可上传大多数常见的文件类型。）</p>
                </div>
                <div class="page-header"></div>
                <div class="form-group text-center">
                    <p class="bg-danger">
                        您提交报告时，系统将会发给您一个举报编号，请牢记此编号。在7个工作日后，您可通过网页或热线电话返回51report，通过举报编号和您设定的密码查阅此事件的跟进情况。
                    </p>
                </div>
                <div class="form-group text-center">
                    <h3>
                        <span class="label label-danger">当前案件编号为：${trackingNo }</span>
                    </h3>
                    <br/> <span><strong><span class="xinghao">*</span>请设定密码：</strong>
							</span>

                    <p>您的密码必须相匹配而且长度不得少于六个字符。</p>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">密码<span class="xinghao">*</span></label>

                    <div class="col-sm-4">
                        <input type="password" name="quest_15_value" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">确认密码<span class="xinghao">*</span></label>

                    <div class="col-sm-4">
                        <input type="password" name="quest_16_value" class="form-control"/><span
                            class="hidden">两次密码不一致</span>
                    </div>
                </div>
                <div class="page-header"></div>
                <div class="form-group">
                    <div class="col-sm-4"></div>
                    <div class="col-sm-4">
                        <input type="button" id="submitReport" class="form-control btn btn-primary" value="提交报告"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <form id="questForm">
        <input type="text" name="quest_1" hidden="true"/>
        <input type="text" name="quest_2" hidden="true"/>
        <input type="text" name="quest_3" hidden="true"/>
        <input type="text" name="quest_4" hidden="true"/>
        <input type="text" name="quest_5" hidden="true"/>
        <input type="text" name="quest_6" hidden="true"/>
        <input type="text" name="quest_7" hidden="true"/>
        <input type="text" name="quest_8" hidden="true"/>
        <input type="text" name="quest_9" hidden="true"/>
        <input type="text" name="quest_10" hidden="true"/>
        <input type="text" name="quest_11" hidden="true"/>
        <input type="text" name="quest_12" hidden="true"/>
    </form>
</div>
<div class="page-header"></div>
<p class="navbar-text text-center">Copyright © 2016-2018 51report.com</p>

<!--条款对话框-->
<div class="modal fade bs-example-modal-lg" id="agreeMentPanel" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title" id="exampleModalLabel1">条款与条件</h5>
            </div>
            <div class="modal-body">
                <iframe src="jsp/pages/agreement.jsp" frameborder="0" width="100%" height="400px"></iframe>
            </div>
        </div>
    </div>
</div>
<!--上传文件对话框-->
<div class="modal fade bs-example-modal-lg" id="upLoadPanel" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title" id="exampleModalLabel2">上传文件</h5>
            </div>
            <div class="modal-body">
                <iframe src="case/showFileList.do?${trackingNo }" frameborder="0" width="100%" height="400px"></iframe>
            </div>
        </div>
    </div>
</div>
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    var trackingNo = "trackingNo=${trackingNo }";
    var rtList = "rtList=${rtList }";
    var questionList = [];
    <c:forEach items = "${questionMap}" var = "question" varStatus = "i" >
    var questionJson = {};
    questionJson["quest_id"] = "${question.value.questId}";
    questionJson["quest_key"] = "${question.value.questKey}";
    questionJson["is_needed"] = "${question.value.isNeeded}";
    questionList.unshift(questionJson);
    </c:forEach>
</script>
<script src="jsp/js/reportCase.js" type="text/javascript" charset="utf-8"></script>
</html>