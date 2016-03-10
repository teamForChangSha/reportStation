$(function () {
    var companyData = null;
    $.post("company/getAllByName.do", "companyName=", function (res) {
        if (res != null || res.length > 0) {
            companyData = res;
            $("#companyData").find("li").remove();
            $.each(JSON.parse(res), function (i, company) {
                var li = $("<li/>");
                var a = $("<a/>").attr("data-id", company.companyId).text(
                    company.companyName);
                a.click(function () {
                    hiddenEle();
                    leftEle.companyName.next().next().hide();
                    leftEle.companyName.val(this.innerHTML);
                    leftEle.companyName
                        .attr("data-id", $(this).attr("data-id"));
                });
                li.append(a);
                $("#companyData").append(li);
            });
        }
    });

    var leftEle = {
        province: $("select[name=province]"),
        city: $("select[name=city]"),
        institutions: $("select[name=branchId]"),
        companyName: $("input[name=companyName]"),
        getArea: $("#getArea"),
        next: $("#next"),
        sendCompany: $("#sendCompany")
    };

    var rightEle = {
        trankingNo: $("input[name=trankingNo]"),
        accecCode: $("input[name=accecCode]"),
        getCaseInfoByNo: $("#getCaseInfoByNo"),
        mobile: $("input[name=mobile]"),
        getTempPwd: $("#getTempPwd"),
        tempPwd: $("input[name=tempPwd]"),
        getCaseInfoByPhone: $("#getCaseInfoByPhone")
    };

    var regStr = /(1[3-9]\d{9}$)/;
    var t, i = 60;

    /**
     * 初始化省份
     */
    function initProvince() {
        var url = "getProvinceByCompany.do?companyId="
            + leftEle.companyName.attr("data-id");
        $.getJSON(url, function (result) {
            if (result == null || result.length <= 0) {
                return;
            }
            leftEle.province.empty();
            var opt = $("<option/>").text("--请选择--").attr("value", "-1");
            leftEle.province.append(opt);
            $(result).each(
                function () {
                    var opt = $("<option/>").text(this.name).attr("value",
                        this.areaId);
                    leftEle.province.append(opt);
                });
            leftEle.province.parent().removeClass("hidden");
        });
    }
    ;

    /**
     * 初始化市
     */
    function initCity() {
        var url = "getCityByCompany.do?companyId="
            + leftEle.companyName.attr("data-id") + "&parentId="
            + leftEle.province.find("option:selected").val();
        $.getJSON(url, function (result) {
            if (result == null || result.length <= 0) {
                return;
            }
            leftEle.city.empty();
            var opt = $("<option/>").text("--请选择--").attr("value", "-1");
            leftEle.city.append(opt);
            $(result).each(
                function () {
                    var opt = $("<option/>").text(this.name).attr("value",
                        this.areaId);
                    leftEle.city.append(opt);
                });
            leftEle.city.parent().removeClass("hidden");
        });
    }
    ;

    /**
     * 初始化机构
     */
    function initInstitutions() {
        var url = "getCompanyBranch.do?companyId="
            + leftEle.companyName.attr("data-id") + "&areaId="
            + leftEle.city.find("option:selected").val();
        $.getJSON(url, function (result) {
            if (result == null || result.length <= 0) {
                return;
            }
            leftEle.institutions.empty();
            var opt = $("<option/>").text("--请选择--").attr("value", "-1");
            leftEle.institutions.append(opt);
            $(result).each(
                function () {
                    var opt = $("<option/>").text(this.branchName).attr(
                        "value", this.branchId);
                    leftEle.institutions.append(opt);
                });
            leftEle.institutions.parent().removeClass("hidden");
        });
    }
    ;

    /**
     * 获取企业下的机构
     */
    leftEle.getArea.click(function () {
        showErrorIcon(leftEle.companyName)
        initProvince();
    });

    leftEle.sendCompany.keydown(function (e) {
        if (e.keyCode == 13) {
            return false;
        }
    });

    /**
     * 转到企业问题类型页面
     */
    leftEle.next.click(function () {
        leftEle.sendCompany.submit();
    });

    leftEle.province.change(function () {
        leftEle.city.parent().addClass("hidden");
        leftEle.institutions.parent().addClass("hidden");
        leftEle.next.parent().addClass("hidden");
        if ($(this).find("option:selected").val() != "-1") {
            initCity();
        }
    });

    leftEle.city.change(function () {
        leftEle.institutions.parent().addClass("hidden");
        leftEle.next.parent().addClass("hidden");
        if ($(this).find("option:selected").val() != "-1") {
            initInstitutions();
        }
    });

    leftEle.institutions.change(function () {
        leftEle.next.parent().addClass("hidden");
        if ($(this).find("option:selected").val() != "-1") {
            leftEle.next.parent().removeClass("hidden");
        }
    });

    leftEle.companyName.focus(function () {
        hiddenErrorIcon(leftEle.companyName);
        leftEle.companyName.next().next().css("display", "block");
    });

    leftEle.companyName.keyup(function () {
        leftEle.companyName.removeAttr("data-id");
        hiddenEle();
        AutoComplete();
    });

    /**
     * 自动完成提示
     */
    function AutoComplete() {
        $("#companyData").hide();
        $("#companyData li").hide();
        if (companyData != null) {
            $("#companyData li:contains(" + leftEle.companyName.val() + ")").show();
            $("#companyData").show();
        } else {
            var data = "companyName=" + leftEle.companyName.val();
            $.post("company/getAllByName.do", data, function (res) {
                if (res == null || res.length < 0)
                    return;
                leftEle.companyName.next().next().find("li").remove();
                $.each(JSON.parse(res), function (i, company) {
                    var li = $("<li/>");
                    var a = $("<a/>").attr("data-id", company.companyId).text(
                        company.companyName);
                    a.click(function () {
                        hiddenEle();
                        leftEle.companyName.next().next().css("display", "none");
                        leftEle.companyName.val(this.innerHTML);
                        leftEle.companyName
                            .attr("data-id", $(this).attr("data-id"));
                    });
                    li.append(a);
                    leftEle.companyName.next().next().append(li);
                    leftEle.companyName.next().next().css("display", "block");
                });
            });
        }
    }
    ;

    /**
     * 给文档添加click时间，如果不是companyName输入框或者自动完成列表设置自动完成列表不显示
     */
    $(document).bind(
        'click',
        function (e) {
            if ($(e.target) == leftEle.companyName.next().next()
                || $(e.target).get(0).name == "companyName")
                return;
            leftEle.companyName.next().next().hide();
        });

    hiddenErrorIcon(leftEle.companyName);
    hiddenErrorIcon(rightEle.trankingNo);
    hiddenErrorIcon(rightEle.accecCode);
    hiddenErrorIcon(rightEle.mobile);
    hiddenErrorIcon(rightEle.tempPwd);

    /**
     * 隐藏企业机构控件
     */
    function hiddenEle() {
        leftEle.companyName.removeAttr("data-id");
        leftEle.province.parent().addClass("hidden");
        leftEle.city.parent().addClass("hidden");
        leftEle.institutions.parent().addClass("hidden");
        leftEle.next.parent().addClass("hidden");
    }
    ;

    /**
     * 通过案件编号查询
     */
    rightEle.getCaseInfoByNo.click(function () {
        if (!showErrorIcon(rightEle.trankingNo)
            && !showErrorIcon(rightEle.accecCode)) {
            $("#reportPanel").modal('hide');
            $("#selectByNum").submit();
        }
    });

    /**
     * 获取临时密码
     */
    rightEle.getTempPwd.click(function () {
        if (!reg(rightEle.mobile)) {
            return;
        }
        rightEle.getTempPwd.attr("disabled", true);
        rightEle.getTempPwd.html("(" + i + ")秒后重试");
        t = setInterval(function () {
            if (i == 0) {
                clearInterval(t);
                t = null;
                rightEle.getTempPwd.removeAttr("disabled");
                rightEle.getTempPwd.html("获取临时密码");
                i = 60;
            } else {
                rightEle.getTempPwd.attr("disabled", true);
                i--;
                rightEle.getTempPwd.html("(" + i + ")秒后重试");
            }
        }, 1000);
        $.get("case/getTempPwd.do?mobile=" + rightEle.mobile.val(), function (res, status) {
        });
    });

    /**
     * 通过实名手机号查询
     */
    rightEle.getCaseInfoByPhone.click(function () {
        if (reg(rightEle.mobile) && !showErrorIcon(rightEle.tempPwd)) {
            clearInterval(t);
            t = null;
            rightEle.getTempPwd.removeAttr("disabled");
            rightEle.getTempPwd.html("获取临时密码");
            i = 60;
            $("#reportPanel").modal('hide');
            $("#selectByPhone").submit();
        }
    });

    /**
     * 登陆
     */
    var user = {
        name: $("#username"),
        pwd: $("#password"),
        btn: $("#loginBtn")
    }
    user.pwd.get(0).onkeydown = function (e) {
        if (e.keyCode == 13) {
            login();
        }
    };
    user.btn.get(0).onclick = login;
    function login() {
        if (isEmty(user.name.val())) {
            return;
        }
        if (isEmty(user.pwd.val())) {
            return;
        }
        var data = "loginName=" + user.name.val() + "&userPwd="
            + md5(user.pwd.val());
        $.post("admin/user/login.do", data, function (res, status) {
            if (status == "success") {
                if (res == "success") {
                    location.href = "jsp/admin/admin.jsp";
                } else {
                    Modal.alert({msg: res});
                }
            }
        });
    };

    $("#forgetPwd").click(function () {
        Modal.alert({msg:'请联系管理员重置密码'});
    });

    /**
     * 验证手机号
     */
    function reg(ele) {
        if (!regStr.test(ele.val())) {
            ele.next().addClass("glyphicon-remove");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 显示错误icon
     */
    function showErrorIcon(ele) {
        if (isEmty(ele.val())) {
            ele.next().addClass("glyphicon-remove");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 隐藏错误icon
     */
    function hiddenErrorIcon(ele) {
        ele.focus(function () {
            ele.next().removeClass("glyphicon-remove");
        });
        ele.next().removeClass("glyphicon-remove");
    }

    /**
     * 判断是否为空
     *
     * @param str
     * @returns {Boolean}
     */
    function isEmty(str) {
        str = $.trim(str);
        if (str == null || str.length <= 0 || str == "") {
            return true;
        }
        return false;
    }
});
