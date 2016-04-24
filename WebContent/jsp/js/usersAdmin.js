$(function () {
    /*获取用户列表*/
    $.get("admin/user/getUsersByParams.do", function (res, state) {
        if (state == "success") {
            var json = JSON.parse(res);
            setUserListData(json);
            setPageBar(json.page);
        }
    });
    function getUserList(pageNum) {
        $.get("admin/user/getUsersByParams.do?pageNow=" + pageNum, function (res, state) {
            if (state == "success") {
                var json = JSON.parse(res);
                setUserListData(json);
            }
        });
    }

    function setUserListData(str) {
        $("#userList").empty();
        $.each(str.userAndLogList, function (i, userAndLogList) {
            var tr = $("<tr/>");
            var td1 = $("<td/>").text(userAndLogList.user.userName);
            var td2 = $("<td/>").text(userAndLogList.user.userCompany.companyName);
            var td3 = $("<td/>");
            switch (userAndLogList.user.userType) {
                case 1:
                    td3.text("试用");
                    break;
                case 2:
                    td3.text("公司用户");
                    break;
                case 3:
                    td3.text("系统操作员");
                    break;
                case 4:
                    td3.text("系统管理员");
                    break;
            }

            var td4 = $("<td/>");
            if (userAndLogList.lastLoginLog != null) {
                td4.text(formatDate(userAndLogList.lastLoginLog.logDate))
            }
            var td5 = $("<td/>");
            switch (userAndLogList.user.userState) {
                case 1:
                    td5.text("新增");
                    break;
                case 2:
                    td5.text("有效");
                    break;
                case 3:
                    td5.text("停用");
                    break;
                case 4:
                    td5.text("注销");
                    break;
            }
            var td6 = $("<td/>").text(formatDate(userAndLogList.user.expiryDate));
            var td7 = $("<td/>");
            var div = $("<div/>").addClass("btn-group");
            var btn = $("<button/>").addClass("btn btn-default dropdown-toggle")
                .attr("data-toggle", "dropdown").attr("aria-haspopup", "true").attr("aria-expanded", "false").text("操作");
            var span = $("<span/>").addClass("caret");
            var menu = $("<ul/>").addClass("dropdown-menu");

            var li1 = $("<li/>");
            var a1 = $("<a/>").text("重置密码");
            li1.append(a1);
            var li2 = $("<li/>");
            var a2 = $("<a/>").text("修改信息");
            li2.append(a2);
            var li3 = $("<li/>");
            var a3 = $("<a/>").text("停用");
            li3.append(a3);
            var li4 = $("<li/>");
            var a4 = $("<a/>").text("注销");
            li4.append(a4);
            if (userAndLogList.user.userState == 4) {
                li1.addClass("disabled");
                li2.addClass("disabled");
                li3.addClass("disabled");
                li4.addClass("disabled");
            } else {
                a1.bind("click", function resetPwd() {
                    Modal.confirm({
                        msg: '重置后默认密码为"123456"',
                    }).on(function (e) {
                        if (e) {
                            var url = "admin/user/resetUserPwd.do";
                            var data = "userId=" + userAndLogList.user.userId + "&userPwd=" + md5("123456");
                            $.post(url, data, function (res, status) {
                                alertMsg(res, status);
                            });
                        }
                    });
                });
                a2.attr("data-toggle", "modal").attr("data-target", "#updataUserInfo");
                a2.bind("click", function updataInfo() {
                    $("#userId").val(userAndLogList.user.userId);
                    $("#upStae").get(0).value = userAndLogList.user.userState;
                    console.log(typeof userAndLogList.user.userState);
                    $("#upName").val(userAndLogList.user.userName);
                    $("#upType").get(0).value = userAndLogList.user.userType;
                    console.log(userAndLogList.user.userCompany.companyId);
                    console.log(userAndLogList.user.userCompany.companyName);
                    $("#upCompany").val(userAndLogList.user.userCompany.companyId);
                    $("#upCompanyInput").val(userAndLogList.user.userCompany.companyName);
                    $("#upMobile").val(userAndLogList.user.mobile);
                    $("#upPhone").val(userAndLogList.user.phone);
                    $("#upEmail").val(userAndLogList.user.email);
                    $("#upWechat").val(userAndLogList.user.weixin);
                    $("#upDepartment").val(userAndLogList.user.department);
                    $("#upPosition").val(userAndLogList.user.position);
                    $("#upWorkNo").val(userAndLogList.user.workNo);
                    $("#upAddress").val(userAndLogList.user.address);
                    $("#upRemark").text(userAndLogList.user.remark);
                    $("#upStateChanged").text(formatDate(userAndLogList.user.stateChanged));
                    if (userAndLogList.user.expiryDate != null) {
                        var temp = formatDate(userAndLogList.user.expiryDate).split("-");
                        for (var i = 0; i < temp.length; i++) {
                            $("#ufullYear").get(0).value = (temp[i] * 1);
                            $("#ufullMounth").get(0).value = (temp[i] * 1);
                            $("#ufullDay").get(0).value = (temp[i].substr(0, 2) * 1);
                        }
                    } else {
                        $("#ufullYear").get(0).value = tempYear;
                        $("#ufullMounth").get(0).value = (tempDate.getMonth() + 1);
                        $("#ufullDay").get(0).value = tempDate.getDate();
                    }
                });

                if (userAndLogList.user.userState == 3) {
                    a3.text("启用");
                } else {
                    a3.text("停用");
                }
                a3.bind('click', function () {
                    if (a3.text() == "启用") {
                        var url = "admin/user/changeUserState.do?userId=" + userAndLogList.user.userId + "&userState=2";
                        $.get(url, function (res, status) {
                            a3.text("停用");
                            td5.text("有效");
                            alertMsg(res, status);
                        });
                    }
                    if (a3.text() == "停用") {
                        Modal.confirm({
                            title: '警告',
                            msg: '你确定要停用该用户吗?',
                        }).on(function (e) {
                            if (e) {
                                var url = "admin/user/changeUserState.do?userId=" + userAndLogList.user.userId + "&userState=3";
                                $.get(url, function (res, status) {
                                    a3.text("启用");
                                    td5.text("停用");
                                    alertMsg(res, status);
                                });
                            }
                        });
                    }
                });
                /*注销该用户*/
                a4.click(function () {
                    Modal.confirm({
                        title: '警告',
                        msg: '注销后该用将无法再启用，你确定要注销吗?',
                    }).on(function (e) {
                        if (e) {
                            var url = "admin/user/changeUserState.do?userId=" + userAndLogList.user.userId + "&userState=4";
                            $.get(url, function (res, status) {
                                li1.addClass("disabled");
                                a1.unbind('click');
                                li2.addClass("disabled");
                                a2.unbind('click');
                                li3.addClass("disabled");
                                a3.unbind('click');
                                li4.addClass("disabled");
                                a4.unbind('click');
                                alertMsg(res, status);
                            });
                        }
                    });
                });
            }
            var line = $("<li/>").attr("role", "separator").addClass("divider");
            var lookLog = $("<li/>");
            var a5 = $("<a/>").text("查看操纵日志")
                .attr("data-toggle", "modal").attr("data-target", "#toViewUseLog")
                .attr("onclick", "lookUseLog(" + userAndLogList.user.userId + ")");
            lookLog.append(a5);
            menu.append(li1).append(li2).append(li3).append(li4).append(line).append(lookLog);
            btn.append(span);
            div.append(btn).append(menu);
            td7.append(div);
            tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
            $("#userList").append(tr);
        });
    }

    /**
     * 分页操作
     */
    function setPageBar(str) {
        $("#pageBar").createPage({
            pageCount: str.totalPageCount,
            current: str.pageNow,
            backFn: function (p) {
                getUserList(p);
            }
        });
        $("#pageText").text('共' + str.totalPageCount + "页，" + str.totalCount + "条");
    }

    /*格式化时间*/
    function formatDate(str) {
        if (str == null) return;
        var date = new Date(str);
        var mounth = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth();
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var m = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        return date.getFullYear() + "-" + mounth + "-" + day + " " + h + ":" + m + ":" + s;
    }

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

    /*按条件搜索用户*/
    $("#selected").click(function () {
        console.log($("#selectForm").serialize());
        $.post("admin/user/getUsersByParams.do", $("#selectForm").serialize(), function (res, state) {
            if (state == "success") {
                var json = JSON.parse(res);
                setUserListData(json);
                setPageBar(json.page);
            } else {
                Modal.alert({
                    msg: '操作失败！',
                });
            }
        });
    });

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
        console.log($("#upForm").serialize());
        $.post("admin/user/updateUser.do", $("#upForm").serialize(), function (res, status) {
            if (status == "success") {
                if (res == "success") {
                    $("#updataUserInfo").modal("hide");
                    Modal.alert({
                        msg: '操作成功！',
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
            if (status == "success") {
                if (res == "success") {
                    $("#addUser").modal("hide");
                    Modal.alert({
                        msg: '操作成功！',
                    }).on(function () {
                        getUserList(0);
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
    };

//        $(document).bind('click', function (e) {
//            console.log($(e.target).get(0).id);
//            if ($(e.target).get(0).id != $("#selectCompanyInput").get(0).id || $(e.target).get(0).id != $("#searchCompanyMenu").get(0).id
//            )
//                return;
//            leftEle.companyName.next().next().hide();
//        });

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